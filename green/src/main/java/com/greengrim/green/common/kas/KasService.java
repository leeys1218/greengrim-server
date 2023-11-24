package com.greengrim.green.common.kas;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.ErrorCode;
import com.greengrim.green.common.exception.errorCode.WalletErrorCode;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KasService {
    private final KasProperties kasProperties;

    private String checkResponse(HttpResponse<String> response, String parameter,
                                 ErrorCode errorCode)
            throws BaseException, ParseException, org.json.simple.parser.ParseException {
        if (response.statusCode() != 200) {
            throw new BaseException(errorCode);
        }
        JSONObject jsonObject = parseBody(response);
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

    private JSONObject parseBody(HttpResponse<String> response) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        return (JSONObject) obj;
    }

}
