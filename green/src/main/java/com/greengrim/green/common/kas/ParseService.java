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
        String hexKlay = peb.substring(2);      // 앞 2글자에서 코드 자르기 ex) 0x0 -> 0
        BigInteger bigInteger = new BigInteger(hexKlay, 16);     // 16진수 -> 10진수 bigInteger
        BigInteger naun = new BigInteger("1000000000000");      // 10의 12제곱 bigInteger
        long re = bigInteger.divide(naun).longValue();              // 클레이 나누기 10의 12제곱
        return format.format(Math.floor((double) re) / 1000000.0);
    }
}
