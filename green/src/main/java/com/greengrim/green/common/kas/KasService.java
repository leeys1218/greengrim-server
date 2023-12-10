package com.greengrim.green.common.kas;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.greengrim.green.common.exception.BaseException;
import com.greengrim.green.common.exception.ErrorCode;
import com.greengrim.green.common.exception.errorCode.NftErrorCode;
import com.greengrim.green.common.exception.errorCode.WalletErrorCode;
import com.greengrim.green.core.nft.Nft;
import com.greengrim.green.core.nft.dto.NftRequestDto.RegisterNft;
import com.greengrim.green.core.wallet.Wallet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class KasService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ParseService parseService;
    private final KasProperties kasProperties;

    private final AmazonS3Client amazonS3Client;
    private final RestTemplate restTemplate;

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
    public void uploadAsset()
            throws IOException, ParseException, org.json.simple.parser.ParseException, InterruptedException {
        String url = "https://metadata-api.klaytnapi.com/v1/metadata/asset";
        // TODO: from-data 로 전송하도록 바꾸기

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        S3Object s3Object = amazonS3Client.getObject(bucket, "2023-12-10T09_29_19.652239.jpg");
        InputStream inputStream = s3Object.getObjectContent();
        byte[] imageBytes = IOUtils.toByteArray(inputStream);
        inputStream.close();

        File jpgFile = new File("path/to/save/image.jpg");
        FileUtils.writeByteArrayToFile(jpgFile, imageBytes);
        Resource resource = new FileSystemResource(jpgFile.getAbsolutePath());

        body.add("file", resource);
        System.out.println(jpgFile.getAbsolutePath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("x-chain-id", kasProperties.getVersion());
        headers.add("Authorization", kasProperties.getAuthorization());

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity
            = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> postForEntity
            = restTemplate.postForEntity(url, httpEntity, JsonNode.class);

        System.out.println(postForEntity.getStatusCodeValue());
        System.out.println(postForEntity.getBody());
        System.out.println(postForEntity.getHeaders());
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

    /**
     * 구매자 지갑 -> 판매자 지갑 클레이 전송
     */
    public String sendKlayToSeller(Wallet sender, Wallet receiver, double coin)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
            return sendKlay(sender.getAddress(),receiver.getAddress(),coin);
    }

    /**
     * 구매자 지갑 -> 판매자 지갑 NFT 전송
     */
    public String sendNft(Wallet sender, Wallet receiver, Nft nft)
            throws IOException, org.json.simple.parser.ParseException, InterruptedException, ParseException {
        return sendNftOutside(sender.getAddress(),receiver.getAddress(),nft.getNftId());
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
