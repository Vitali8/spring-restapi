package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.ColumnService
import com.mygroup.restApiTask.service.TaskService
import com.mygroup.restApiTask.system.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("tasks")
class TasksController(private val taskService: TaskService) {
    @Autowired
    private lateinit var columnService: ColumnService

    // general task
    @GetMapping("/tasks")
    fun index() = taskService.all()

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = taskService.get(id)

    @PutMapping("/tasks/{targetTaskId}/moveBefore/{beforeTaskId}")
    fun moveBefore(@PathVariable targetTaskId: Long, @PathVariable beforeTaskId: Long) = taskService.insertAbove(targetTaskId, beforeTaskId)

    @PutMapping("/tasks/{targetTaskId}/moveAfter/{afterTaskId}")
    fun moveAfter(@PathVariable targetTaskId: Long, @PathVariable afterTaskId: Long) = taskService.insertBelow(targetTaskId, afterTaskId)

    // Tasks per column
    @GetMapping("/columns/{columnId}/tasks/")
    fun getTasks(@PathVariable(value = "columnId") columnId: Long): MutableList<Task> = columnService.get(columnId).get().tasks

    @PostMapping("/columns/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTask(@PathVariable(value = "id") id: Long, @RequestBody task: Task): Task {
        val column = columnService.get(id).get()
        column.tasks.add(task)
        return taskService.add(task)
    }

    @GetMapping("/columns/{columnId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getTask(@PathVariable(value = "columnId") columnId: Long, @PathVariable(value = "taskId") taskId: Long) = taskService.get(taskId)

    @PutMapping("/columns/{columnId}/tasks/{taskId}")
    fun updateTask(@PathVariable(value = "columnId") columnId: Long,
                   @PathVariable(value = "taskId") taskId: Long,
                   @RequestBody updatedTask: Task): Task {
        val currTask = taskService.get(taskId).get()
        return taskService.edit(taskId, updatedTask.copy(position = currTask.position))
    }

    @PutMapping("/columns/{fromColumnId}/tasks/{taskId}/moveToColumn/{toColumnId}")
    @ResponseStatus(HttpStatus.OK)
    fun moveToColumn(@PathVariable(value = "fromColumnId") fromColumnId: Long,
                     @PathVariable(value = "taskId") taskId: Long,
                     @PathVariable(value = "toColumnId") toColumnId: Long) {
        val task = taskService.get(taskId).get()
        columnService.removeTask(fromColumnId, task)
        columnService.addTask(toColumnId, task)
    }

    @DeleteMapping("/columns/{columnId}/tasks/{taskId}")
    fun deleteTask(@PathVariable(value = "columnId") columnId: Long,
                   @PathVariable(value = "taskId") taskId: Long) {
        columnService.removeTask(columnId, taskService.get(taskId).get())
        taskService.remove(taskId)
    }
}
