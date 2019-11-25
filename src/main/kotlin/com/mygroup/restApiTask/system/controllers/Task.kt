//package com.mygroup.restApiTask.system.controllers
//
//import com.mygroup.restApiTask.service.*
//import com.mygroup.restApiTask.system.models.Task
//import org.springframework.http.HttpStatus
//import org.springframework.web.bind.annotation.*
//
//@RestController // Сообщаем как обрабатывать http запросы и в каком виде отправлять ответы (сериализация в JSON и обратно)
//@RequestMapping("tasks") // Указываем префикс маршрута для всех экшенов
//class TasksController(private val taskService: TaskService) { // Внедряем наш сервис в качестве зависимости
//    @GetMapping // Говорим что экшен принимает GET запрос без параметров в url
//    fun index() = taskService.all() // И возвращает результат метода all нашего сервиса. Функциональная запись с выводом типа
//
//    @PostMapping("/") // Экшен принимает POST запрос без параметров в url
//    @ResponseStatus(HttpStatus.CREATED) // Указываем специфический HttpStatus при успешном ответе
//    fun create(@RequestBody task: Task) = taskService.add(task) // Принимаем объект Task из тела запроса и передаем его в метод add нашего сервиса
//
//    @GetMapping("{id}") // Тут мы говорим что это GET запрос с параметром в url (http://localhost/tasks/{id})
//    @ResponseStatus(HttpStatus.FOUND)
//    fun read(@PathVariable id: Long) = taskService.get(id) // Сообщаем что наш id типа Long и передаем его в метод get сервиса
//
//    /* Здесь мы принимаем один параметр из url, второй из тела PUT запроса и отдаем их методу edit */
//    @PutMapping("{id}")
//    fun update(@PathVariable id: Long, @RequestBody task: Task) = taskService.edit(id, task)
//
//    @PutMapping("{id}/move")
//    fun move(@PathVariable id: Long, @RequestBody taskIdA: Long, @RequestBody taskIdB: Long) = taskService.insertBetween(id, taskIdA, taskIdB)
//
//    @DeleteMapping("{id}")
//    fun delete(@PathVariable id: Long) = taskService.remove(id)
//}
