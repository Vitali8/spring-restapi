package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.TaskListService
import com.mygroup.restApiTask.service.TaskService
import com.mygroup.restApiTask.system.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("tasks")
class TasksController(private val taskService: TaskService) {
    @Autowired
    private lateinit var taskListService: TaskListService

    @GetMapping("/tasks")
    fun index() = taskService.all()

    @GetMapping("/taskLists/{taskListId}/tasks/")
    fun getTasks(@PathVariable(value = "taskListId") taskListId: Long): MutableList<Task> = taskListService.get(taskListId).get().tasks

    @PostMapping("/taskLists/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTask(@PathVariable(value = "id") id: Long, @RequestBody task: Task): Task {
        val column = taskListService.get(id).get()
        column.tasks.add(task)
        return taskService.add(task)
    }

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = taskService.get(id)

    @GetMapping("/taskLists/{taskListId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getTask(@PathVariable(value = "taskListId") taskListId: Long, @PathVariable(value = "taskId") taskId: Long) = taskService.get(taskId)

    @PutMapping("/tasks/{id}")
    fun update(@PathVariable id: Long, @RequestBody task: Task) = taskService.edit(id, task)

    @PutMapping("/taskLists/{taskListId}/tasks/{taskId}")
    fun updateTask(@PathVariable(value = "taskListId") taskListId: Long,
                   @PathVariable(value = "taskId") taskId: Long,
                   @RequestBody updatedTask: Task) = taskService.edit(taskId, updatedTask)

    @PutMapping("/tasks/{targetTaskId}/move/{upperTaskId}")
    fun move(@PathVariable targetTaskId: Long, @PathVariable upperTaskId: Long) = taskService.insertBelow(targetTaskId, upperTaskId)

    @DeleteMapping("/tasks/{id}")
    fun delete(@PathVariable id: Long) = taskService.remove(id)

    @DeleteMapping("/taskLists/{taskListId}/tasks/{taskId}")
    fun deleteTask(@PathVariable(value = "taskListId") taskListId: Long,
                   @PathVariable(value = "taskId") taskId: Long) = taskService.remove(taskId)
}
