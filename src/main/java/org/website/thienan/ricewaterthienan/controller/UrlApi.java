package org.website.thienan.ricewaterthienan.controller;

public class UrlApi {
    public static final String  API_V1 = "api/v1" ;
    public static final  String[]  PUBLIC_API = {
            "/api/v1/index","/api/v1/auth/login", "/api/v1/auth/log_up","/api/v1/auth/refresh-token",
            "/api/v1/health","/ap1/v1/branch/findAll","/ap1/v1/branch/findAllActive",
            "/ap1/v1/branch/findById/**", "/ap1/v1/branch/findByName/**",
            "/api/v1/brand/findAll","/api/v1/brand/findAllActive",
            "/api/v1/brand/findById/**","/api/v1/brand/findByName/**",
            "/api/v1/categories/findAll","/api/v1/categories/findAllActive",
            "/api/v1/categories/findById/**","/api/v1/categories/findByName/**",
            "/api/v1/category-post/findAll","/api/v1/category-post/findAllActive",
            "/api/v1/category-post/findById/**","/api/v1/category-post/findByName/**",
            "/api/v1/orders/findByKeyWord/**","/api/v1/orders/findAllActive/**","/api/v1/orders/findByStatus/**",
            "/api/v1/orders/findById/**","/api/v1/orders/findByName/**","/api/v1/orders/findByPhone/**",
            "/api/v1/post/findAll/**","/api/v1/post/findAllTitle/**", "/api/v1/post/findById/**",
            "/api/v1/product/findAll/**","/api/v1/product/findAllFilter/**", "/api/v1/product/findById/**",
            "/api/v1/role_detail/findAll","/api/v1/role_detail/findAllActive",
            "/api/v1/role_detail/findById/**","/api/v1/role_detail/findByName/**",
            "/api/v1/setting/findAll","/api/v1/setting/findById/**",
            "/api/v1/type/findAll/**","/api/v1/type/findAllTitle/**", "/api/v1/type/findById/**",
            "/api/v1/chat/**","/api/v1/firebase/**","/api/v1/chat",
            "/swagger-ui/**","/v3/api-docs/**","/v3/api-docs/API-Service-thienan"
    } ;
}
