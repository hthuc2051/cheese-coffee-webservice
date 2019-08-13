package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.Constants;
import com.cheesecoffee.common.CustomUtils;
import com.cheesecoffee.dtos.ShopeeParamsDto;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShopeeServiceImpl implements ShopeeService {
    @Override
    public Map<String,String> generateUrl(ShopeeParamsDto dto) throws IOException{
        Map response = new HashMap();
        response.put("authorization",CustomUtils.byte2hex(CustomUtils.encryptHMACSHA256(dto.getUrl(),dto.getSecretKey())));
        response.put("time",dto.getParams().get(Constants.PARAM_TIMESTAMP));
        return response;
    }
}
