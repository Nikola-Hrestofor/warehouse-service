package com.example.warehouseservice.api

import com.example.techcardservice.config.FeignConfig
import com.example.warehouseservice.api.dto.ProductDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "management", path = "/product", url = "\${spring.services.management.host}", configuration = [FeignConfig::class])
interface ManagementService {
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ProductDto
}