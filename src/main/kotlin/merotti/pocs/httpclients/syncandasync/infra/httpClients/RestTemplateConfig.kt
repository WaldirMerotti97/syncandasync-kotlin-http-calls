package merotti.pocs.httpclients.syncandasync.infra.httpClients

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    fun createRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}