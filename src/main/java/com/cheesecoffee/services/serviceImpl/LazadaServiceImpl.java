package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.Constants;
import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.services.LazadaService;
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
            result = signApiRequest(dto.getParams(), dto.getAppSecret(), dto.getSignMethod(), dto.getApiName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String signApiRequest(Map<String, String> params, String appSecret, String signMethod, String apiName) throws IOException {

        String url = "";

        byte[] bytes = null;

        // Append api name
        StringBuilder query = new StringBuilder();
        query.append(apiName);
        url += apiName + "?";

        // Append params arr
        String[] keys;
        if (params != null) {
            keys = params.keySet().toArray(new String[0]);
            Arrays.sort(keys);
            for (String key : keys) {
                String value = params.get(key);
                if (key != null && !key.equalsIgnoreCase("")) {
                    query.append(key).append(value);
                    url += key + "=" + value + "&";
                }
            }
        }
        // Append sign method
        if (signMethod.equals(Constants.SIGN_METHOD_SHA256)) {
            bytes = encryptHMACSHA256(query.toString(), appSecret);
        } else {
            //TODO: Custom exception later
        }
        String hexCode = byte2hex(bytes);
        String result = url + "sign=" + hexCode;
        return Constants.ENDPOINT_LAZADA + result;
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
        if (bytes.length > 0) {
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(bytes[i] & 0xFF);
                if (hex.length() == 1) {
                    sign.append("0");
                }
                sign.append(hex.toUpperCase());
            }
            return sign.toString();
        }
        return "";
    }


    @Override
    public String test() {
        String s = "";
        Map<String, String> params = new HashMap<>();
        params.put("access_token", "test");
        params.put("app_key", "123456");
        params.put("timestamp", "1517820392000");
        params.put("order_id", "1234");
        params.put("sign_method", "sha256");

        try {
            s = signApiRequest(params, "helloworld", "SHA-256", "/order/get");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
