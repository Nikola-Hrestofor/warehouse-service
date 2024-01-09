package com.example.warehouseservice.api

import com.example.techcardservice.config.FeignConfig
import com.example.warehouseservice.api.dto.ComponentDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "tech-card", path = "/api/v1/directory", url = "\${spring.services.tech-card.host}", configuration = [FeignConfig::class])
interface TechCardService {
    @GetMapping("/component/{id}")
    fun getComponentById(@PathVariable id: Long): ComponentDto
}