package org.website.thienan.ricewaterthienan.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/san-pham")
    public String product() {
        return "client/product";
    }
}
