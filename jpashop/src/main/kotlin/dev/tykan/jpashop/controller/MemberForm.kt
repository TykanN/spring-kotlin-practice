package dev.tykan.jpashop.controller

import javax.validation.constraints.NotEmpty

class MemberForm(
    @field:NotEmpty(message = "회원 이름은 필수입니다.")
    var name: String = "",
    var city: String = "",
    var street: String = "",
    var zipcode: String = "",
) {
}