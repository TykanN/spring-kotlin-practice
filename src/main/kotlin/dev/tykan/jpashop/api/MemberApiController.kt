package dev.tykan.jpashop.api

import dev.tykan.jpashop.domain.Member
import dev.tykan.jpashop.service.MemberService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@RestController
class MemberApiController(private val memberService: MemberService) {

    companion object {
        data class CreateMemberResponse(
            val id: Long
        )

        data class CreateMemeberRequest(
            @field:NotEmpty(message = "이름은 필수입니다.")
            val name: String
        )

        data class UpdateMemberResponse(
            val id: Long,
            val name: String
        )

        data class UpdateMemeberRequest(
            @field:NotEmpty(message = "이름은 필수입니다.")
            val name: String
        )

        data class Result<T>(
            val data: T
        )

        data class MemberDto(
            val id: Long,
            val name: String
        )
    }

    @GetMapping("/api/v1/members")
    fun membersV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/api/v2/members")
    fun membersV2(): Result<List<MemberDto>> {
        val members = memberService.findMembers()
        val collect: List<MemberDto> = members.map { MemberDto(it.id!!, it.name) }
        return Result(collect)
    }

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemeberRequest): CreateMemberResponse {
        val member = Member(name = request.name)
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody @Valid request: UpdateMemeberRequest
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val findMember = memberService.findOne(id) ?: throw NotFoundException()
        return UpdateMemberResponse(findMember.id!!, findMember.name)
    }
}