package dev.tykan.springtest.controller

import dev.tykan.springtest.domain.Member
import dev.tykan.springtest.service.MemberService
import org.hibernate.annotations.NotFound
import org.jetbrains.annotations.NotNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Controller
class MemberController(private val memberService: MemberService) {
    @GetMapping("/members/new")
    fun createForm(): String {
        return "/members/createMemberForm"
    }


    @PostMapping("/members/new")
    fun create(form: MemberForm): String {
        val member = Member(name = form.name)


        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members = memberService.findMembers()
        model.addAttribute("members", members)
        return "members/memberList"
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginDto: LoginDto,
        br: BindingResult
    ): ResponseEntity<Any> {
        if (br.hasErrors()) {
            return ResponseEntity.status(400).body(br.allErrors.map { e -> e.defaultMessage })
        }
        return ResponseEntity.ok("검증 완료")
    }

    @PostMapping("/login2")
    fun login2(
        @RequestParam(name = "nickname") nickname: String, @RequestParam password: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok("검증 완료")
    }
}

data class LoginDto(
    @field:NotBlank(message = "닉네임은 필수입니다.")
    val nickname: String?,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    val password: String?,
)






