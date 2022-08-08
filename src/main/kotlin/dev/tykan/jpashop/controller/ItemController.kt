package dev.tykan.jpashop.controller

import dev.tykan.jpashop.domain.item.Book
import dev.tykan.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ItemController(private val itemService: ItemService) {

    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(form: BookForm): String {
        val book = Book(form.author, form.isbn)
        book.name = form.name
        book.price = form.price
        book.addStock(form.stockQuantity)

        itemService.saveItem(book)

        return "redirect:/items"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemList"
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item = itemService.findOne(itemId) as Book
        val form = BookForm(item.id!!, item.name, item.price, item.stockQuantity, item.author, item.isbn)
        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@PathVariable itemId: String, @ModelAttribute("form") form: BookForm): String {
//        val book = Book(form.author, form.isbn)
//        book.id = form.id
//        book.name = form.name
//        book.price = form.price
//        book.addStock(form.stockQuantity)
//
//        itemService.saveItem(book)

        itemService.updateItem(form.id, form.name, form.price, form.stockQuantity)
        return "redirect:/items"
    }
}