package merotti.pocs.httpclients.syncandasync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SyncandasyncApplication

fun main(args: Array<String>) {
    runApplication<SyncandasyncApplication>(*args)
}
