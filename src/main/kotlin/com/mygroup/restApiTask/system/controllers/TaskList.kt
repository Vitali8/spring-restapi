package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.TaskListService
import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("taskLists")
class TaskListController(private val taskListService: TaskListService) {

    @GetMapping
    fun indexColumns() = taskListService.all()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createColumn(@RequestBody taskList: TaskList) = taskListService.add(taskList)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun readColumn(@PathVariable id: Long) = taskListService.get(id).get()

    @PutMapping("/{id}")
    fun updateColumn(@PathVariable id: Long, @RequestBody taskList: TaskList) = taskListService.edit(id, taskList)

    @PutMapping("/{targetColumnId}/move/{upperColumnId}")
    fun moveColumn(@PathVariable targetColumnId: Long, @PathVariable upperColumnId: Long) = taskListService.insertBelow(targetColumnId, upperColumnId)

    @DeleteMapping("/{id}")
    fun deleteColumn(@PathVariable id: Long) = taskListService.remove(id)

}
