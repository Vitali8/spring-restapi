package com.mygroup.restApiTask.system.controllers

import com.mygroup.restApiTask.service.*
import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController // Сообщаем как обрабатывать http запросы и в каком виде отправлять ответы (сериализация в JSON и обратно)
@RequestMapping("taskLists") // Указываем префикс маршрута для всех экшенов
class TaskListsController(private val taskListService: TaskListService) { // Внедряем наш сервис в качестве зависимости
    @GetMapping // Говорим что экшен принимает GET запрос без параметров в url
    fun index() = taskListService.all() // И возвращает результат метода all нашего сервиса. Функциональная запись с выводом типа

    @PostMapping // Экшен принимает POST запрос без параметров в url
    @ResponseStatus(HttpStatus.CREATED) // Указываем специфический HttpStatus при успешном ответе
    fun create(@RequestBody taskList: TaskList) = taskListService.add(taskList) // Принимаем объект TaskList из тела запроса и передаем его в метод add нашего сервиса

    @PostMapping(path = ["createByName"])
    @ResponseStatus(HttpStatus.CREATED) // Указываем специфический HttpStatus при успешном ответе
    fun create(@RequestBody name: String) = taskListService.add(TaskList(name = name)) // Принимаем объект TaskList из тела запроса и передаем его в метод add нашего сервиса

    @GetMapping("{id}") // Тут мы говорим что это GET запрос с параметром в url (http://localhost/taskLists/{id})
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = taskListService.get(id) // Сообщаем что наш id типа Long и передаем его в метод get сервиса

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody taskList: TaskList) = taskListService.edit(id, taskList) // Здесь мы принимаем один параметр из url, второй из тела PUT запроса и отдаем их методу edit

    @PutMapping("{id}/move")
    fun move(@PathVariable id: Long, @RequestBody columnIdA: Long, @RequestBody columnIdB: Long) = taskListService.insertBetween(id, columnIdA, columnIdB)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = taskListService.remove(id)
}
