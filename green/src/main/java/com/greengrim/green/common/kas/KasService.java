package com.greengrim.green.common.kas;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.ErrorCode;
import com.greengrim.green.common.exception.errorCode.WalletErrorCode;
import com.greengrim.green.core.wallet.Wallet;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KasService {

    private final ParseService parseService;
    private final KasProperties kasProperties;

    private String checkResponse(HttpResponse<String> response, String parameter,
                                 ErrorCode errorCode)
            throws BaseException, ParseException, org.json.simple.parser.ParseException {
        if (response.statusCode() != 200) {
            throw new BaseException(errorCode);
        }
        JSONObject jsonObject = parseService.parseBody(response);
        if (jsonObject.get(parameter) == null) {
            throw new BaseException(errorCode);
        }
        return (String) jsonObject.get(parameter);
    }

    private String useKasApi(String query, String method, HttpRequest.BodyPublisher body,
                             String parameter, ErrorCode errorCode)
            throws IOException, InterruptedException, BaseException, ParseException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(query))
                .header("Content-Type", "application/json")
                .header("x-chain-id", kasProperties.getVersion())
                .header("Authorization", kasProperties.getAuthorization())
                .method(method, body)
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println(response.body());
        }
        return checkResponse(response, parameter, errorCode);
    }

    /**
     * 지갑 생성
     */
    public String createAccount()
            throws BaseException, IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        String url = "https://wallet-api.klaytnapi.com/v2/account";
        return useKasApi(url, "POST", HttpRequest.BodyPublishers.noBody(), "address",
                WalletErrorCode.FAILED_CREATE_WALLET);
    }

    /**
     * 지갑 Klay Balance 조회
     */
    public double getKlay(Wallet wallet)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        String url = "https://node-api.klaytnapi.com/v1/klaytn";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"id\": 1,\n  " +
                        "\"jsonrpc\": \"2.0\",\n " +
                        " \"method\": \"klay_getBalance\",\n  " +
                        "\"params\": [ \"" + wallet.getAddress() + "\",\"latest\"]\n" +
                        "}"
        );
        String balance = useKasApi(url, "POST", body, "result", WalletErrorCode.FAILED_GET_KLAY);
        return Double.parseDouble(parseService.pebToDecimal(balance));
    }

}
