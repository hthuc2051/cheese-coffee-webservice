package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.Constants;
import com.cheesecoffee.common.CustomUtils;
import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.services.LazadaService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LazadaServiceImpl implements LazadaService {
    @Override
    public String generateUrl(LazadaParamsDto dto) {
        String result = "";
        try {
            result = signApiRequest(dto.getParams(), dto.getAppSecret(), dto.getSignMethod(), dto.getApiName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String signApiRequest(Map<String, String> params, String appSecret, String signMethod, String apiName) throws IOException {

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        long miliSecs = ts.getTime();
        params.put(Constants.PARAM_TIMESTAMP, String.valueOf(miliSecs));
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
                    if (key.equals(Constants.PARAM_CREATED_AFTER) ||
                            key.equals(Constants.PARAM_CREATED_BEFORE) ||
                            key.equals(Constants.PARAM_UPDATE_AFTER) ||
                            key.equals(Constants.PARAM_UPDATE_BEFORE)
                    ) {
                        String tempStr = value.replace(":", "%3A");
                        String timeStr = tempStr.replace("+", "%2B");
                        url += key + "=" + timeStr + "&";
                    } else {
                        url += key + "=" + value + "&";
                    }
                    query.append(key).append(value);
                }
            }
        }
        // Append sign method
        if (signMethod.equals(Constants.SIGN_METHOD_SHA256)) {
            bytes = CustomUtils.encryptHMACSHA256(query.toString(), appSecret);
        } else {
            return "";
        }
        String hexCode = CustomUtils.byte2hex(bytes);
        String result = url + "sign=" + hexCode;
        return Constants.ENDPOINT_LAZADA + result;
    }


    @Override
    public String test() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        long miliSecs = ts.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ts.getTime());

        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2018-02-10T16:00:00+08:00");
        String s3 = UriUtils.encodeQuery("2018-02-10T16:00:00+08:00", "UTF-8");
        String[] arr = zonedDateTime.toString().split("T");
        String result1 = zonedDateTime.toString().replace(":", "%3A");
        String result2 = result1.replace("+", "%2B");
        System.out.println(result2);
        Timestamp timestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());
        System.out.println(timestamp);
        String s = "";
        Map<String, String> params = new HashMap<>();
        params.put("access_token", "test");
        params.put("app_key", "123456");
        params.put("timestamp", "1517820392000");
        params.put("order_id", "1234");
        params.put("sign_method", "sha256");

        try {
            s = signApiRequest(params, "helloworld", "SHA-256", "/order/get");
            String str = "created_after2018-02-10T09%3A00%3A00%2B08%3A00limit10app_key100132sign_methodsha256timestamp1565460418549access_token50000301015o5nrdbYjujHQbruE19527bd76ibs5OWzjjIuhzGQsFFxCWKcAq";

            String s2 = "/orders/get" +
                    "access_token50000300727bl0LfsFinryoTA86fUdKwEuxDAiD14ef7d52v5kWXFndPkd9sjB" +
                    "app_key101327" +
                    "created_after2017-02-10T09:00:00+08:00" +
                    "limit10" +
                    "sign_methodsha256" +
                    "timestamp1565512893471";

            byte[] bytes = CustomUtils.encryptHMACSHA256(s2, "7rXIh6fsxTQHAsBdZsEMizgmz2PeOMHZ ");
            String hexCode = CustomUtils.byte2hex(bytes);
            System.out.println(hexCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
