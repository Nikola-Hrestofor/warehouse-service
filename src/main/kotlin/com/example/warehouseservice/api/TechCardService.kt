package com.example.warehouseservice.api

import com.example.techcardservice.config.FeignConfig
import com.example.warehouseservice.api.dto.CardDto
import com.example.warehouseservice.api.dto.ComponentDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "tech-card", path = "/api/v1", url = "\${spring.services.tech-card.host}", configuration = [FeignConfig::class])
interface TechCardService {
    @GetMapping("/directory/component/{id}")
    fun getComponentById(@PathVariable id: Long): ComponentDto

    @GetMapping("/cards/{id}")
    fun getCardById(@PathVariable id: Long): CardDto
}