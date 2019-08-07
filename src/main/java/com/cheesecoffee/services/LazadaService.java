package com.cheesecoffee.services;

import com.cheesecoffee.dtos.LazadaParamsDto;

public interface LazadaService {
     String generateUrl(LazadaParamsDto dto);
     String test();
}
