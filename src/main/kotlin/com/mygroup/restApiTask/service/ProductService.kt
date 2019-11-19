package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.*
import org.springframework.stereotype.Service
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
class ProductService(private val productRepository: ProductRepository) { // Внедряем репозиторий в качестве зависимости
    fun all(): Iterable<Product> = productRepository.findAll() // Возвращаем коллекцию сущностей, функциональная запись с указанием типа

    fun get(id: Long): Optional<Product> = productRepository.findById(id)

    fun add(product: Product): Product = productRepository.save(product)

    fun edit(id: Long, product: Product): Product = productRepository.save(product.copy(id = id)) // Сохраняем копию объекта с указанным id в БД. Идиоматика Kotlin говорит что НЕ изменяемый - всегда лучше чем изменяемый (никто не поправит значение в другом потоке) и предлагает метод copy для копирования объектов (специальных классов для хранения данных) с возможностью замены значений

    fun remove(id: Long) = productRepository.deleteById(id)
}