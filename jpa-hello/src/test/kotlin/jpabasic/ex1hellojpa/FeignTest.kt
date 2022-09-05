package jpabasic.ex1hellojpa

import com.fasterxml.jackson.core.JsonParseException
import feign.Feign
import feign.RequestLine
import feign.jackson.JacksonEncoder
import org.junit.jupiter.api.Test
import org.springframework.web.bind.annotation.RequestBody
import java.io.IOException

data class SlackMessage(
    val text: String
)

interface SlackClient {
    @RequestLine("POST /services/T03T6J8379C/B03TD73E9R8/zGTS8ImQT4r1XrjcNxuhEEWd")
    fun post(@RequestBody msg: SlackMessage): String

    companion object {

        private fun getInstance(): SlackClient {
            return Feign.builder()
                .encoder(JacksonEncoder())
                .target(SlackClient::class.java, "https://hooks.slack.com")
        }

        val client = getInstance()
    }

}

class FeignTest() {


    @Test
    fun call() {
        try {
            val test: String = SlackClient.client.post(SlackMessage("asdad"))
            println(test)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JsonParseException) {
            e.printStackTrace()
        }

    }
}