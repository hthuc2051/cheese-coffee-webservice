package com.cheesecoffee.controllers;

import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.dtos.ShopeeParamsDto;
import com.cheesecoffee.services.LazadaService;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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
    public String getUrlLazada(@RequestBody LazadaParamsDto dto) {
        return lazadaService.generateUrl(dto);
    }


    @PostMapping("/shopee")
    public String getUrlShopee(@RequestBody ShopeeParamsDto dto) throws IOException {
        return shopeeService.generateUrl(dto);
    }

    //TODO: Later
//    @PostMapping("/tiki")
//    public String getUrlTiki(@RequestBody ShopeeParamsDto dto) {
//        return "";
//    }
//
//    @PostMapping("/sendo")
//    public String getUrlSendo(@RequestBody ShopeeParamsDto dto) {
//        return "";
//    }


}
