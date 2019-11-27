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

    fun edit(id: Long, taskList: TaskList): TaskList = taskListRepository.save(taskList.copy(id = id))

    fun remove(id: Long) = taskListRepository.deleteById(id)

    fun insertBelow(targetId: Long, upperElementId: Long) {
        val targetElement = get(targetId).get()
        val lowerElement = findNextAfter(upperElementId).get()
        val upperElement = get(upperElementId).get()
        targetElement.position = (upperElement.position + lowerElement.position) / 2
        edit(targetId, targetElement)
    }

    fun findNextAfter(id: Long) = taskListRepository.findFirstByPositionGreaterThanOrderByPositionAsc(get(id).get().position)
}