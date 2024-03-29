package com.greengrim.green.common.kas;

import java.math.BigInteger;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class ParseService {

    public JSONObject parseBody(HttpResponse<String> response) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        return (JSONObject) obj;
    }

    public String pebToDecimal(String peb) {
        DecimalFormat format = new DecimalFormat();
        format.applyLocalizedPattern("0.000000");
        String hexKlay = peb.substring(2);                 // 앞 2글자에서 코드 자르기 ex) 0x0 -> 0
        BigInteger bigInteger = new BigInteger(hexKlay, 16);   // 16진수 -> 10진수 bigInteger
        BigInteger naun = new BigInteger("1000000000000");      // 10의 12제곱 bigInteger
        long re = bigInteger.divide(naun).longValue();              // 클레이 나누기 10의 12제곱
        return format.format(Math.floor((double) re) / 1000000.0);
    }

    public String decimalToPeb(double decimal) {
        long pay = Double.valueOf(decimal * 1000000).longValue(); // 소수점 6째까지 받아서 곱해서 넘김
        String py = Long.toString(pay);
        BigInteger bigInteger = new BigInteger(py);                  // 보내려는 클레이 정수의 BigInteger
        BigInteger gob = new BigInteger("1000000000000");        // 10의 12제곱 BigInteger
        String hex = bigInteger.multiply(gob).toString(16);     // 정수 곱하기 10의 16제곱 을 16진수로 표현
        return "0x" + hex;
    }
}
