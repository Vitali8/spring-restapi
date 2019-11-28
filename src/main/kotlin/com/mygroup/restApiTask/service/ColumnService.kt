package com.mygroup.restApiTask.service

import com.mygroup.restApiTask.system.models.Column
import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.repositories.ColumnRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ColumnService(private val columnRepository: ColumnRepository) {
    fun all(): Iterable<Column> = columnRepository.findAll(Sort.by(Sort.Direction.ASC, "position"))

    fun get(id: Long): Optional<Column> = columnRepository.findById(id)

    fun add(column: Column): Column {
        var position = 0.0
        columnRepository.findTopByPositionNotNullOrderByPositionDesc().ifPresent { position = it.position }
        return columnRepository.save(column.copy(position = position + 1.0))
    }

    fun edit(id: Long, column: Column): Column {
        val currColumnState = get(id)
        currColumnState.ifPresent { t: Column -> t.name = column.name }
        return currColumnState.get()
    }

    fun addTask(id: Long, task: Task): Unit = get(id).ifPresent { it.tasks.add(task) }

    fun removeTask(id: Long, task: Task): Unit = get(id).ifPresent { it.tasks.remove(task) }

    fun remove(id: Long) = columnRepository.deleteById(id)

    fun insertBelow(targetId: Long, upperElementId: Long) {
        val upperElement = get(upperElementId).get()
        val lowerElement = findNextAfter(upperElementId)
        val targetPosition =
                if (lowerElement.isPresent) (upperElement.position + lowerElement.get().position) / 2
                else upperElement.position + 1
        columnRepository.updatePositionById(targetId, position = targetPosition)
    }

    fun findNextAfter(id: Long) = columnRepository.findFirstByPositionGreaterThanOrderByPositionAsc(get(id).get().position)

    fun insertAbove(targetId: Long, lowerElementId: Long) {
        val lowerElementPosition = get(lowerElementId).get().position
        var upperElementPosition = 0.0
        findNextBefore(lowerElementId).ifPresent { upperElementPosition = it.position }
        columnRepository.updatePositionById(targetId, (upperElementPosition + lowerElementPosition) / 2)
    }

    fun findNextBefore(id: Long) = columnRepository.findFirstByPositionLessThanOrderByPositionAsc(get(id).get().position)
}