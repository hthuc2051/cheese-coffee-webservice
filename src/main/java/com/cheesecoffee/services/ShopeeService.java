package com.cheesecoffee.services;

import com.cheesecoffee.dtos.ShopeeParamsDto;

import java.io.IOException;
import java.util.Map;

public interface ShopeeService {
    Map<String, String> generateUrl(ShopeeParamsDto dto) throws IOException;
}
