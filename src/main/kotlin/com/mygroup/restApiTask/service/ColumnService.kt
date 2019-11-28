package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.models.Column
import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.repositories.ColumnRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
@Transactional
class ColumnService(private val columnRepository: ColumnRepository) {
    fun all(): Iterable<Column> = columnRepository.findAll(Sort.by(Sort.Direction.ASC,"position"))

    fun get(id: Long): Optional<Column> = columnRepository.findById(id)

    fun add(column: Column): Column {
        var position = 0.0
        columnRepository.findTopByPositionNotNullOrderByPositionDesc().ifPresent { t: Column -> position = t.position }
        return columnRepository.save(column.copy(position = position + 1.0))
    }

    fun edit(id: Long, column: Column): Column {
        val currColumnState = get(id)
        currColumnState.ifPresent { t: Column -> t.name = column.name }
        return currColumnState.get()
    }

    fun addTask(id: Long, task: Task): Unit = get(id).ifPresent { t: Column -> t.tasks.add(task) }

    fun removeTask(id: Long, task: Task): Unit = get(id).ifPresent { t: Column -> t.tasks.remove(task) }

    fun remove(id: Long) = columnRepository.deleteById(id)

    fun insertBelow(targetId: Long, upperElementId: Long) {
        val lowerElement = findNextAfter(upperElementId).get()
        val upperElement = get(upperElementId).get()
        columnRepository.updatePositionById(targetId, position = (upperElement.position + lowerElement.position) / 2)
    }
    fun findNextAfter(id: Long) = columnRepository.findFirstByPositionGreaterThanOrderByPositionAsc(get(id).get().position)

    fun insertAbove(targetId: Long, lowerElementId: Long) {
        val lowerElementPosition = get(lowerElementId).get().position
        var upperElementPosition = 0.0
        findNextBefore(lowerElementId).ifPresent { t: Column -> upperElementPosition = t.position }
        columnRepository.updatePositionById(targetId, position = (upperElementPosition + lowerElementPosition) / 2)
    }
    fun findNextBefore(id: Long) = columnRepository.findFirstByPositionLessThanOrderByPositionAsc(get(id).get().position)
}