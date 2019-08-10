package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.Constants;
import com.cheesecoffee.dtos.ShopeeParamsDto;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

@Service
public class ShopeeServiceImpl implements ShopeeService {
    @Override
    public String generateUrl(ShopeeParamsDto dto) throws IOException{
        return byte2hex(encryptHMACSHA256(dto.getUrl(),dto.getSecretKey()));
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
}
