package dev.tykan.jpashop

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JpashopApplication {
    // LAZY 로딩되는 엔티티 필드를 무시하고 Json으로 뿌릴 수 있다.
    @Bean
    fun hibernate5Module(): Hibernate5Module {
        val module = Hibernate5Module()
        // 강제로 LAZY 로딩 시키기
//         module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true)
        return module
    }
}

fun main(args: Array<String>) {
    runApplication<JpashopApplication>(*args)
}
