//package com.mygroup.restApiTask
//
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.context.SpringBootTest
//
//@SpringBootTest
//class RestApiTaskApplicationTests {
//
//	@Test
//	fun contextLoads() {
//	}
//
//}
package com.mygroup.restApiTask

import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@SpringBootTest
@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Запускать тесты в алфавитном порядке
class RestApiTaskApplicationTests {
    private val taskListUrl = "http://localhost:8080/taskLists/"
    private val tasksUrl = "http://localhost:8080/tasks/"
    private val jsonContentType = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype) // Записываем http заголовок в переменную для удобства
    private lateinit var mockMvc: MockMvc // Объявляем изменяемую переменную с отложенной инициализацией в которой будем хранить mock объект

    private val firstTaskCreationDate = LocalDateTime.now()!!

    @Autowired
    private lateinit var webAppContext: WebApplicationContext // Объявляем изменяемую переменную с отложенной инициализацией в которую будет внедрен контекст приложения

    @Before // Этот метод будет запущен перед каждым тестом
    fun before() {
        mockMvc = webAppContextSetup(webAppContext).build() // Создаем объект с контекстом приложения
    }

    @Test
    fun `1 - Get empty list of columns`() {
        val request = get(taskListUrl).contentType(jsonContentType) // Создаем GET запрос по адресу http://localhost:8080/taskLists/ с http заголовком Content-Type: application/json

        mockMvc.perform(request) // Выполняем запрос
                .andExpect(status().isOk) // Ожидаем http статус 200 OK
                .andExpect(content().json("[]", true)) // ожидаем пустой JSON массив в теле ответа
    }

    // Далее по аналогии
    @Test
    fun `2 - Add first column`() {
        val passedJsonString = """
            {
                "name": "Doing"
            }
        """.trimIndent()

        val request = post(taskListUrl).contentType(jsonContentType).content(passedJsonString)

        val resultJsonString = """
            {
                "name": "Doing",
                "tasks": [],
                "position": 1.0,
                "id": 1
            }
        """.trimIndent()

        val result = mockMvc.perform(request)
        result.andExpect(status().isCreated)
                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `3 - Create and add first task to column`() {
        val passedJsonString = """
            {
                "name": "First task",
                "creation_date": "$firstTaskCreationDate",
                "task_list_id": 1
            }
        """.trimIndent()

        val request = post(tasksUrl).contentType(jsonContentType).content(passedJsonString)

        val resultJsonString = """
            {
                "name": "First task",
                "creation_date": "$firstTaskCreationDate",
                "description": "",
                "task_list_id": 1,
                "id": 1
            }
        """.trimIndent()

        val result = mockMvc.perform(request)
        result.andExpect(status().isCreated)
                .andExpect(content().json(resultJsonString, false))
    }

    @Test
    fun `4 - Update first task`() {
        val passedJsonString = """
            {
                "description": "Look at iPhone 4S - smart phone by Apple"
            }
        """.trimIndent()

        val request = put(tasksUrl + "1").contentType(jsonContentType).content(passedJsonString)

        val resultJsonString = """
            {
                "name": "First task",
                "creation_date": "$firstTaskCreationDate",
                "description": "Look at iPhone 4S - smart phone by Apple",
                "task_list_id": 1,
                "id": 1
            }
        """.trimIndent()

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
                .andExpect(content().json(resultJsonString, false))
    }

    @Test
    fun `5 - Get first task`() {
        val request = get(tasksUrl + "1").contentType(jsonContentType)

        val resultJsonString = """
            {
                "name": "First task",
                "creation_date": "$firstTaskCreationDate",
                "description": "Look at iPhone 4S - smart phone by Apple",
                "task_list_id": 1,
                "id": 1
            }
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isFound)
                .andExpect(content().json(resultJsonString, false))
    }

    @Test
    fun `6 - Get first column, with one task`() {
        val request = get(taskListUrl).contentType(jsonContentType)

        val resultJsonString = """
            [
                {
                    "name": "Doing",
                    "tasks": [1],
                    "id": 1
                }
            ]
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().json(resultJsonString, false))
    }

    @Test
    fun `7 - Get all tasks`() {
        val request = get(tasksUrl).contentType(jsonContentType)

        val resultJsonString = """
            [
                {
                    "name": "First task",
                    "creation_date": "$firstTaskCreationDate",
                    "description": "Look at iPhone 4S - smart phone by Apple",
                    "task_list_id": 1,
                    "id": 1
                }
            ]
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().json(resultJsonString, false))
    }

    @Test
    fun `8 - Add two new columns`() {
        fun newColumn(columnId: Long, columnName: String) {
            val passedJsonString = """
            {
                "name": "$columnName"
            }
        """.trimIndent()

            val request = post(taskListUrl).contentType(jsonContentType).content(passedJsonString)

            val resultJsonString = """
            {
                "name": "$columnName",
                "tasks": [],
                "id": $columnId
            }
        """.trimIndent()

            val result = mockMvc.perform(request)
            result.andExpect(status().isCreated)
                    .andExpect(content().json(resultJsonString, false))
        }
        newColumn(2, "Done")
        newColumn(3, "ToDo")
    }

    @Test
    fun `9 - Move columns`() {
        val request = put(taskListUrl + "3/move?columnIdA=1&columnIdB=2")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `10 - Delete first list of task`() {
        val request = delete(taskListUrl + "1").contentType(jsonContentType)

        mockMvc.perform(request).andExpect(status().isOk)
    }

}