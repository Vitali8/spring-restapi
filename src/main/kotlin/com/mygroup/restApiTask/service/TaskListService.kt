package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.models.TaskList
import com.mygroup.restApiTask.system.repositories.TaskListRepository
import org.springframework.stereotype.Service
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
class TaskListService(private val taskListRepository: TaskListRepository) { // Внедряем репозиторий в качестве зависимости
    fun all(): Iterable<TaskList> = taskListRepository.findAll() // Возвращаем коллекцию сущностей, функциональная запись с указанием типа

    fun get(id: Long): Optional<TaskList> = taskListRepository.findById(id)

    fun add(taskList: TaskList): TaskList = taskListRepository.save(taskList)

    fun edit(id: Long, taskList: TaskList): TaskList = taskListRepository.save(taskList.copy(id = id)) // Сохраняем копию объекта с указанным id в БД. Идиоматика Kotlin говорит что НЕ изменяемый - всегда лучше чем изменяемый (никто не поправит значение в другом потоке) и предлагает метод copy для копирования объектов (специальных классов для хранения данных) с возможностью замены значений

    fun remove(id: Long) = taskListRepository.deleteById(id)

    fun insertBetween(targetId: Long, upperElementId: Long, lowerElementId: Long) {
        val targetElement = get(targetId).get()
        val lowerElement = get(lowerElementId).get()
        val upperElement = get(upperElementId).get()
        targetElement.position = (upperElement.position + lowerElement.position) / 2
    }
}