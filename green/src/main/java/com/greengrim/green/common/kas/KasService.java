package com.greengrim.green.common.kas;

import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.ErrorCode;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.common.exception.errorCode.WalletErrorCode;
import com.greengrim.green.core.nft.dto.NftRequestDto.RegisterNft;
import com.greengrim.green.core.wallet.Wallet;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class KasService {

    private final ParseService parseService;
    private final KasProperties kasProperties;

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

    /**
     * TODO: 로직 정하기 1)프론트에서 에셋 생성 2)백에서 에셋 생성
     * 에셋 업로드
     */
    public String uploadAsset(MultipartFile file)
            throws IOException, ParseException, org.json.simple.parser.ParseException, InterruptedException {
        String url = "https://metadata-api.klaytnapi.com/v1/metadata/asset";
        // TODO: from-data 로 전송하도록 바꾸기
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"file\": \"" + file + "\n" +
                        "}"
        );
        return useKasApi(url, "POST", body, "uri", NftErrorCode.FAILED_CREATE_ASSET);
    }

    /**
     * 메타 데이터 생성
     */
    public String uploadMetadata(RegisterNft registerNft)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        String url = "https://metadata-api.klaytnapi.com/v1/metadata";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"metadata\": {\n    " +
                        "\"name\": \"" + registerNft.getTitle() + "\",\n    " +
                        "\"description\": \"" + registerNft.getDescription() + "\",\n    " +
                        "\"image\": \"" + registerNft.getAsset() + "\"" +
                        "\n  }\n" +
                        "}");
        return useKasApi(url, "POST", body, "uri", NftErrorCode.FAILED_CREATE_METADATA);
    }

    /**
     * NFT 민팅
     */
    public String createNft(String walletAddress, String nftId, String metadataUri)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        String url = "https://kip17-api.klaytnapi.com/v2/contract/" + kasProperties.getNftContract()
                + "/token";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"to\": \"" + walletAddress + "\",\n  " +
                        "\"id\": \"" + nftId + "\",\n  " +
                        "\"uri\": \"" + metadataUri + "\"\n" +
                        "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", NftErrorCode.FAILED_CREATE_NFT);
    }

    /**
     * NFT 외부 전송
     */
    public String sendNftOutside(String sendAddress, String receiveAddress, String id)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        String url = "https://kip17-api.klaytnapi.com/v2/contract/" + kasProperties.getNftContract()
                + "/token/" + id;
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"sender\": \"" + sendAddress + "\",\n  " +
                        "\"owner\": \"" + sendAddress + "\",\n  " +
                        "\"to\": \"" + receiveAddress + "\"\n" +
                        "}"
        );
        return useKasApi(url, "POST", body, "transactionHash", NftErrorCode.FAILED_SEND_NFT);
    }

    /**
     * 클레이 외부 전송
     */
    public String sendKlayOutside(String sendAddress, String receiveAddress, double coin)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        return sendKlay(sendAddress, receiveAddress, coin);
    }

    /**
     * 클레이 수수료 전송
     */
    public String sendKlayToFeeAccount(Wallet sender, double coin)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        return sendKlay(sender.getAddress(), kasProperties.getFeeAccount(), coin);
    }

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

    private String sendKlay(String senderAddress, String receiverAddress, double coin)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        String url = "https://wallet-api.klaytnapi.com/v2/tx/fd-user/value";
        String hexPay = parseService.decimalToPeb(coin);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
                "{\n  " +
                        "\"from\": \"" + senderAddress + "\",\n  " +
                        "\"value\": \"" + hexPay + "\",\n  " +
                        "\"to\": \"" + receiverAddress + "\",\n  " +
                        "\"memo\": \"0x123\",\n  " +
                        "\"nonce\": 0,\n  " +
                        "\"gas\": 0,\n  " +
                        "\"submit\": true,\n  " +
                        "\"feePayer\": \"" + kasProperties.getFeePayerAccount() + "\"\n" +
                        "}");
        return useKasApi(url, "POST", body, "transactionHash", WalletErrorCode.FAILED_SEND_KLAY);
    }
}
