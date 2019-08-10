package com.cheesecoffee.services;

import com.cheesecoffee.dtos.ShopeeParamsDto;

import java.io.IOException;

public interface ShopeeService {
   String  generateUrl(ShopeeParamsDto dto) throws IOException;
}
