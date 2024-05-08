package org.website.thienan.ricewaterthienan.controller.apiv2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.website.thienan.ricewaterthienan.controller.UrlApi;

@RestController
@RequestMapping(value = UrlApi.API_V2)
public class TestAPIV2 {

    @GetMapping("/test")
    public String test() {
        return "test api v2";
    }
}
