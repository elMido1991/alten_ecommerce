package com.ecommerce.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authentication-server")
public interface AuthServiceClient {
    @GetMapping("/validate")
    Boolean validateToken(@RequestParam String token);
}