package jpabasic.ex1hellojpa

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import org.junit.jupiter.api.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException


data class Slack(
    @JsonProperty("text")
    val msg: String
)


interface RetroService {
    @POST("/services/T03T6J8379C/B03TD73E9R8/zGTS8ImQT4r1XrjcNxuhEEWd")
    fun getTest(@Body slack: Slack): Call<String>
}

class RetroClient {
    companion object {
        private const val baseUrl: String = "https://hooks.slack.com"


        private fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        }

        val apiService = getInstance().create(RetroService::class.java)
    }
}

class RetrofitTest {
    @Test
    fun call() {
        try {
            val test: Call<String> = RetroClient.apiService.getTest(Slack("안녕 Retrofit"))
            val a: Response<String> = test.execute()
            println(a.body())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JsonParseException) {
            e.printStackTrace()
        }
    }
}