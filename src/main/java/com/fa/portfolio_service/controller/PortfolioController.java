package com.fa.portfolio_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    @RequestMapping(value = "/portfolio")
    public String getPortfolio(){
        return  "from dummy api response";
    }
}
