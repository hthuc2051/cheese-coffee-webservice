package com.cheesecoffee.controllers;

import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.services.LazadaService;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class URLController {

    private final LazadaService lazadaService;
    private final ShopeeService shopeeService;


    @Autowired
    public URLController(LazadaService lazadaService, ShopeeService shopeeService) {
        this.lazadaService = lazadaService;
        this.shopeeService = shopeeService;
    }

    @PostMapping("/lazada")
    public String getUrlLazada(LazadaParamsDto dto) {
//        return lazadaService.generateUrl(dto);
        return "SKT";
    }

}
