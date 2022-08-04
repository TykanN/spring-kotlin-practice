package dev.tykan.jpashop.service

import dev.tykan.jpashop.domain.item.Item
import dev.tykan.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems(): MutableList<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item? = itemRepository.findOne(itemId)
}