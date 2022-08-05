package dev.tykan.jpashop.repository

import dev.tykan.jpashop.domain.OrderStatus

class OrderSearch(
    val memberName: String?, //회원 이름
    val orderStatus: OrderStatus?, //주문 상태[ORDER, CANCEL]
) {


}