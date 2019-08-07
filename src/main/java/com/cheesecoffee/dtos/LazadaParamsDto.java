package com.cheesecoffee.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LazadaParamsDto {
    private Map<String, String> params;
    private String body;
    private String appSecret;
    private String signMethod;
    private String apiName;

}
