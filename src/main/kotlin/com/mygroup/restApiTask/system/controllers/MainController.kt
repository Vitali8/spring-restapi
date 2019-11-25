package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.*
import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController // Сообщаем как обрабатывать http запросы и в каком виде отправлять ответы (сериализация в JSON и обратно)
//@RequestMapping("taskLists") // Указываем префикс маршрута для всех экшенов
class MainController() { // Внедряем наш сервис в качестве зависимости
    @Autowired
    private lateinit var taskListService: TaskListService
    @Autowired
    private lateinit var taskService: TaskService

    // И возвращает результат метода all нашего сервиса. Функциональная запись с выводом типа
    @GetMapping("/taskLists") // Говорим что экшен принимает GET запрос без параметров в url
    fun indexColumns(): Iterable<TaskList> {
        val taskLists = taskListService.all()
        for (taskList in taskLists) {
            taskList.tasks.addAll(taskService.findAllByColumn(taskList.id))
        }
        return taskLists
    }

    @PostMapping("/taskLists/") // Экшен принимает POST запрос без параметров в url
    @ResponseStatus(HttpStatus.CREATED) // Указываем специфический HttpStatus при успешном ответе
    fun createColumn(@RequestBody taskList: TaskList) = taskListService.add(taskList) // Принимаем объект TaskList из тела запроса и передаем его в метод add нашего сервиса

    // Сообщаем что наш id типа Long и передаем его в метод get сервиса
    @GetMapping("/taskLists/{id}") // Тут мы говорим что это GET запрос с параметром в url (http://localhost/taskLists/{id})
    @ResponseStatus(HttpStatus.FOUND)
    fun readColumn(@PathVariable id: Long): TaskList {
        val taskList = taskListService.get(id).get()
        taskList.tasks.addAll(taskService.findAllByColumn(taskList.id))
        return taskList
    }

    @PutMapping("/taskLists/{id}")
    fun updateColumn(@PathVariable id: Long, @RequestBody taskList: TaskList) = taskListService.edit(id, taskList) // Здесь мы принимаем один параметр из url, второй из тела PUT запроса и отдаем их методу edit

    @PutMapping("/taskLists/{id}/move")
    fun moveColumn(@PathVariable id: Long, @RequestBody columnIdA: Long, @RequestBody columnIdB: Long) = taskListService.insertBetween(id, columnIdA, columnIdB)

    @DeleteMapping("/taskLists/{id}")
    fun deleteColumn(@PathVariable id: Long) = taskListService.remove(id)

    // Tasks.

    @GetMapping("/tasks") // Говорим что экшен принимает GET запрос без параметров в url
    fun index() = taskService.all() // И возвращает результат метода all нашего сервиса. Функциональная запись с выводом типа

    @GetMapping("/taskLists/{taskListId}/tasks/")
    fun getTasks(@PathVariable(value = "taskListId") taskListId: Long): MutableList<Task> {
        return taskService.findAllByColumn(taskListId)
    }

    @PostMapping("/taskLists/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun addTask(@PathVariable(value = "id") id: Long, @RequestBody task: Task): Task {
        task.taskListId = id
        return taskService.add(task)
    }

    @GetMapping("/tasks/{id}") // Тут мы говорим что это GET запрос с параметром в url (http://localhost/tasks/{id})
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = taskService.get(id) // Сообщаем что наш id типа Long и передаем его в метод get сервиса
    @GetMapping("/taskLists/{taskListId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getTask(@PathVariable(value = "taskListId") taskListId: Long, @PathVariable(value = "taskId") taskId: Long) = taskService.get(taskId)

    /* Здесь мы принимаем один параметр из url, второй из тела PUT запроса и отдаем их методу edit */
    @PutMapping("/tasks/{id}")
    fun update(@PathVariable id: Long, @RequestBody task: Task) = taskService.edit(id, task)
    @PutMapping("/taskLists/{taskListId}/tasks/{taskId}")
    fun updateTask(@PathVariable(value = "taskListId") taskListId: Long,
                   @PathVariable(value = "taskId")  taskId:  Long,
                   @RequestBody updatedTask: Task) = taskService.edit(taskId, updatedTask)

    @PutMapping("/tasks/{id}/move")
    fun move(@PathVariable id: Long, @RequestBody taskIdA: Long, @RequestBody taskIdB: Long) = taskService.insertBetween(id, taskIdA, taskIdB)

    @DeleteMapping("/tasks/{id}")
    fun delete(@PathVariable id: Long) = taskService.remove(id)
    @DeleteMapping("/taskLists/{taskListId}/tasks/{taskId}")
    fun deleteTask(@PathVariable(value = "taskListId") taskListId: Long,
                   @PathVariable(value = "taskId")  taskId:  Long) = taskService.remove(taskId)
}
