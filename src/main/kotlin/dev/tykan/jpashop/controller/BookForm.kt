package dev.tykan.jpashop.controller

class BookForm(
    val id: Long = 0,

    val name: String = "",
    val price: Int = 0,
    val stockQuantity: Int = 0,

    val author: String = "",
    val isbn: String = "",
) {
}