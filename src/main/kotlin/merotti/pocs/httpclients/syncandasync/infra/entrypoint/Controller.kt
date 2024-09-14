package merotti.pocs.httpclients.syncandasync.infra.entrypoint

import kotlinx.coroutines.*
import merotti.pocs.httpclients.syncandasync.infra.httpClients.FeignDemo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import kotlin.system.measureTimeMillis


@RestController
@RequestMapping("/httpclients")
class Controller(
    private val feignClient: FeignDemo,
    private val restTemplate: RestTemplate
) {

    @GetMapping("/feignclient")
    suspend fun callFeignHttpClient(): List<CallReturnInfo> {
        return asyncCall {
            feignClient.uuid()
        }

    }

    @GetMapping("/resttemplate")
    suspend fun callRestTemplateClient(): List<CallReturnInfo> {
        return asyncCall {
            restTemplate.getForEntity(
                "http://localhost:8081/slowcall",
                String::class.java
            ).body.toString()
        }

    }

    @GetMapping("/webclient")
    suspend fun callWebClientClient(): List<CallReturnInfo> {
        return asyncCall {
            WebClient.create()
                .get()
                .uri("http://localhost:8081/slowcall")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
                .toString()
        }
    }

    private suspend fun asyncCall(httpClientCall: () -> String): List<CallReturnInfo> {
        val uuidList = coroutineScope {
            val uuids = mutableListOf<Deferred<CallReturnInfo>>()
            for (i in 1..100) {
                uuids.add(
                    async(Dispatchers.IO) {
                        var fetchedUUID: String
                        val elapsedTime = measureTimeMillis {
                            fetchedUUID = httpClientCall()
                        }
                        CallReturnInfo(
                            response = fetchedUUID,
                            responseTime = elapsedTime
                        )
                    }
                )
            }
            uuids.awaitAll()
        }
        return uuidList
    }

}

data class CallReturnInfo(
    val response: String,
    val responseTime: Long
)