package com.cheesecoffee.controllers;

import com.cheesecoffee.dtos.LazadaParamsDto;
import com.cheesecoffee.dtos.ShopeeParamsDto;
import com.cheesecoffee.services.LazadaService;
import com.cheesecoffee.services.ShopeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class URLController {
    Logger logger = Logger.getLogger(URLController.class.getName());
    private final LazadaService lazadaService;
    private final ShopeeService shopeeService;


    @Autowired
    public URLController(LazadaService lazadaService, ShopeeService shopeeService) {
        this.lazadaService = lazadaService;
        this.shopeeService = shopeeService;
    }

    @PostMapping(value = "/lazada",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String getUrlLazada(LazadaParamsDto dto) {
        logger.log(Level.WARNING,dto.toString());
        System.out.println(dto.toString());
        return lazadaService.generateUrl(dto);
    }

    @PostMapping("/lazada1")
    public String getUrlLazada1(@RequestBody LazadaParamsDto dto) {
        logger.log(Level.WARNING,dto.toString());
        System.out.println(dto.toString());
        return lazadaService.generateUrl(dto);
    }


    @GetMapping("/test")
    public String testUrl() {
        return lazadaService.test();
    }

    @PostMapping("/test2")
    public String testUrl(@RequestBody String s) {
        System.out.println(s);
        logger.log(Level.WARNING,s);

        return lazadaService.test();
    }

    @PostMapping("/test3")
    public String getUrlShopee(@RequestBody ShopeeParamsDto dto) {
        System.out.println(dto.toString());
        logger.log(Level.WARNING,dto.toString());
        return lazadaService.test();
    }
}
