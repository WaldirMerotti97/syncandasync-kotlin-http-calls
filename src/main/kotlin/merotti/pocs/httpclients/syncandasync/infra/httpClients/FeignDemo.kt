package merotti.pocs.httpclients.syncandasync.infra.httpClients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(name = "myapiclient", url = "http://localhost:8081")
interface FeignDemo {
    @GetMapping("/slowcall")
    fun uuid(): String
}