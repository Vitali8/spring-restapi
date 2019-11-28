package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.repositories.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
@Transactional
class TaskService(private val taskRepository: TaskRepository) { // Внедряем репозиторий в качестве зависимости
    fun all(): Iterable<Task> = taskRepository.findAll() // Возвращаем коллекцию сущностей, функциональная запись с указанием типа

    fun get(id: Long): Optional<Task> = taskRepository.findById(id)

    fun add(task: Task): Task {
        var position = 0.0
        taskRepository.findTopByPositionNotNullOrderByPositionDesc().ifPresent { t: Task -> position = t.position }
        return taskRepository.save(task.copy(position = position + 1.0))
    }

    fun edit(id: Long, task: Task): Task = taskRepository.save(task.copy(id = id))
    // Сохраняем копию объекта с указанным id в БД.
    // Идиоматика Kotlin говорит что НЕ изменяемый - всегда лучше чем изменяемый
    // (никто не поправит значение в другом потоке)
    // и предлагает метод copy для копирования объектов (специальных классов для хранения данных) с возможностью замены значений

    fun remove(id: Long) = taskRepository.deleteById(id)

    fun insertBelow(targetId: Long, upperElementId: Long) {
        val upperElement = get(upperElementId).get()
        val lowerElement = findNextAfter(upperElementId)
        val targetPosition =
                if (lowerElement.isPresent) (upperElement.position + lowerElement.get().position) / 2
                else upperElement.position + 1
        taskRepository.updatePositionById(targetId, position = targetPosition)
    }

    fun findNextAfter(id: Long) = taskRepository.findFirstByPositionGreaterThanOrderByPositionAsc(get(id).get().position)

    fun insertAbove(targetId: Long, lowerElementId: Long) {
        val lowerElementPosition = get(lowerElementId).get().position
        var upperElementPosition = 0.0
        findNextBefore(lowerElementId).ifPresent { t: Task -> upperElementPosition = t.position }
        taskRepository.updatePositionById(targetId, position = (upperElementPosition + lowerElementPosition) / 2)
    }

    fun findNextBefore(id: Long) = taskRepository.findFirstByPositionLessThanOrderByPositionAsc(get(id).get().position)
}