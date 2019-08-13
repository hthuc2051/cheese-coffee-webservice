package com.cheesecoffee.dtos;

import com.cheesecoffee.common.Constants;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopeeParamsDto {
    private Map<String, String> params;
    private String apiName;
    private String secretKey;



    public String getUrl() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        long miliSecs = ts.getTime();
        params.put(Constants.PARAM_TIMESTAMP, String.valueOf(miliSecs));
        JSONObject object = new JSONObject(params);
        String s = object.toString();
        return Constants.ENDPOINT_SHOPEE + apiName + "|" + s;
    }
}
