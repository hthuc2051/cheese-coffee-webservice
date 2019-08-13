package com.cheesecoffee.services.serviceImpl;

import com.cheesecoffee.common.CustomUtils;
import com.cheesecoffee.dtos.ShopeeParamsDto;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ShopeeServiceImpl implements ShopeeService {
    @Override
    public String generateUrl(ShopeeParamsDto dto) throws IOException{
        return CustomUtils.byte2hex(CustomUtils.encryptHMACSHA256(dto.getUrl(),dto.getSecretKey()));
    }

}
