package com.example.techcardservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(FeignConfig::class)
class AppConfig {
}