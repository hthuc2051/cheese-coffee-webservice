package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.Constants;
import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.services.LazadaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class LazadaServiceImpl implements LazadaService {
    @Override
    public String generateUrl(LazadaParamsDto dto) {
        String result = "";
        // For dummy
        // bytes = encryptHMACSHA256("/order/getaccess_tokentestapp_key123456order_id1234sign_methodsha256timestamp1517820392000","helloworld");
        try {
//            Map<String,String> params =
//                    new ObjectMapper().readValue(dto.getParams(), HashMap.class);
            result = signApiRequest(dto.getParams(), dto.getBody(), dto.getAppSecret(), dto.getSignMethod(), dto.getApiName());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String test() {
        String s = "";
        Map<String,String> params = new HashMap<>();
        params.put("access_token","test");
        params.put("app_key","123456");
        params.put("timestamp","1517820392000");
        params.put("order_id","1234");
        params.put("sign_method","sha256");

        try {
             s = signApiRequest(params,null,"helloworld","SHA-256","/order/get");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }


    public static String signApiRequest(Map<String, String> params, String body, String appSecret, String signMethod, String apiName) throws IOException {

        byte[] bytes = null;

        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(apiName);
        for (String key : keys) {
            String value = params.get(key);
            if (key != null && !key.equalsIgnoreCase("")) {
                query.append(key).append(value);
            }
        }

        if (body != null) {
            query.append(body);
        }



        if (signMethod.equals(Constants.SIGN_METHOD_SHA256)) {
            bytes = encryptHMACSHA256(query.toString(), appSecret);
        }else{

        }

        // finally : transfer sign result from binary to upper hex string
        return byte2hex(bytes);
    }


    private static byte[] encryptHMACSHA256(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), Constants.SIGN_METHOD_HMAC_SHA256);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }


    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
