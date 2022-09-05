package dev.tykan.springtest.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {
    @GetMapping("/")
    fun leaf(model: Model): String {
        return "home"
    }

    @GetMapping("/hello-mvc")
    fun helloMvc(@RequestParam("name") name: String, model: Model): String {
        model.addAttribute("name", name)
        return "hello-template"
    }


    @GetMapping("/hello-string")
    @ResponseBody
    fun helloString(@RequestParam("name") name: String): String {
        return "hello $name"
    }

    @GetMapping("/hello-api")
    @ResponseBody
    fun helloApi(@RequestParam("name") name: String): Hello {
        val hello = Hello()
        hello.name = name
        return hello
    }

    companion object {

        class Hello {
            var name: String = ""
        }

    }

}