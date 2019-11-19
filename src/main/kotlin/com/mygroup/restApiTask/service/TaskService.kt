package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.repositories.TaskRepository
import org.springframework.stereotype.Service
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
class TaskService(private val taskRepository: TaskRepository) { // Внедряем репозиторий в качестве зависимости
    fun all(): Iterable<Task> = taskRepository.findAll() // Возвращаем коллекцию сущностей, функциональная запись с указанием типа

    fun get(id: Long): Optional<Task> = taskRepository.findById(id)

    fun add(task: Task): Task = taskRepository.save(task)

    fun edit(id: Long, task: Task): Task = taskRepository.save(task.copy(id = id)) // Сохраняем копию объекта с указанным id в БД. Идиоматика Kotlin говорит что НЕ изменяемый - всегда лучше чем изменяемый (никто не поправит значение в другом потоке) и предлагает метод copy для копирования объектов (специальных классов для хранения данных) с возможностью замены значений

    fun remove(id: Long) = taskRepository.deleteById(id)
}