package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.ColumnService
import com.mygroup.restApiTask.system.models.Column
import com.mygroup.restApiTask.system.models.Task
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("columns")
class ColumnController(private val columnService: ColumnService) {

    @GetMapping
    fun indexColumns() = columnService.all()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createColumn(@RequestBody column: Column) = columnService.add(column)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun readColumn(@PathVariable id: Long) = columnService.get(id)

    // edit column (in fact, only name)
    @PutMapping("/{id}")
    fun updateColumn(@PathVariable id: Long, @RequestBody column: Column): Column = columnService.edit(id, column)

    //edit column's tasks (just add/remove)
    @PutMapping("/{id}/addTask")
    @ResponseStatus(HttpStatus.OK)
    fun addTaskToColumn(@PathVariable id: Long, @RequestBody task: Task) = columnService.addTask(id, task)

    @PutMapping("/{id}/removeTask")
    @ResponseStatus(HttpStatus.OK)
    fun removeTaskFromColumn(@PathVariable id: Long, @RequestBody task: Task) = columnService.removeTask(id, task)

    //edit order of columns
    @PutMapping("/{targetColumnId}/moveBefore/{otherColumnId}")
    fun moveColumnBefore(@PathVariable targetColumnId: Long, @PathVariable otherColumnId: Long) = columnService.insertAbove(targetColumnId, otherColumnId)

    @PutMapping("/{targetColumnId}/moveAfter/{otherColumnId}")
    fun moveColumnAfter(@PathVariable targetColumnId: Long, @PathVariable otherColumnId: Long) = columnService.insertBelow(targetColumnId, otherColumnId)

    @DeleteMapping("/{id}")
    fun deleteColumn(@PathVariable id: Long) = columnService.remove(id)

}
