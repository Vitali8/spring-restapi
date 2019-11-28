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

//TODO: Update (rewrite) tests according to the new refactored code, logic and structure
@SpringBootTest
@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestApiTaskApplicationTests {
    private val taskListUrl = "http://localhost:8080/taskLists/"
    private val tasksUrl = "http://localhost:8080/tasks/"
    private val jsonContentType = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype) // Записываем http заголовок в переменную для удобства
    private lateinit var mockMvc: MockMvc // Объявляем изменяемую переменную с отложенной инициализацией в которой будем хранить mock объект

    // Data for tests
    private val taskCreationDate = LocalDateTime.now().withNano(0)!!

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
                "id": 1,
                "tasks": []
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
                "creation": "$taskCreationDate"
            }
        """.trimIndent()

        val request = post(taskListUrl + "1/tasks").contentType(jsonContentType).content(passedJsonString)

        val resultJsonString = """
            {
                "name": "First task",
                "description": "",
                "creation": "$taskCreationDate",
                "id": 1
            }
        """.trimIndent()

        val result = mockMvc.perform(request)
        result.andExpect(status().isCreated)
                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `4 - Update first task`() {
        val passedJsonString = """
            {
                "name": "First task",
                "creation": "$taskCreationDate",
                "description": "Look at iPhone 4S - smart phone by Apple",
                "position": 50.0,
                "id": 1
            }
        """.trimIndent()
        val request = put(taskListUrl + "1/tasks/1").contentType(jsonContentType).content(passedJsonString)

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
//                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `5 - Get first task`() {
        val request = get(taskListUrl + "1/tasks/1").contentType(jsonContentType)

        val resultJsonString = """
            {
                "name": "First task",
                "creation": "$taskCreationDate",
                "description": "Look at iPhone 4S - smart phone by Apple",
                "id": 1
            }
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isFound)
                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `6 - Get first column, with one task`() {
        val request = get(taskListUrl + 1).contentType(jsonContentType)

        val resultJsonString = """
            {
                "name": "Doing",
                "position":1.0,
                "tasks": [{
                    "name": "First task",
                    "creation": "$taskCreationDate",
                    "description": "Look at iPhone 4S - smart phone by Apple",
                    "position": 1.0,
                    "id": 1
                }],
                "id": 1
            }
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isFound)
                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `7 - Add new tasks`() {
        fun newTask(id: Int, name: String) {
            val passedJsonString = """
            {
                "name": "$name",
                "creation": "$taskCreationDate",
                "position": $id.0
            }
        """.trimIndent()
            val request = post(taskListUrl + "1/tasks").contentType(jsonContentType).content(passedJsonString)
            val resultJsonString = """
                {
                    "name": "$name",
                    "creation": "$taskCreationDate",
                    "description": "",
                    "position": $id.0,
                    "id": $id
                }
            """.trimIndent()

            mockMvc.perform(request)
                    .andExpect(status().isCreated)
                    .andExpect(content().json(resultJsonString, true))
        }
        newTask(2, "Second Task")
        newTask(3, "Third Task")
    }

    @Test
    fun `8 - Add new columns`() {
        fun newColumn(columnId: Long, columnName: String) {
            val passedJsonString = """
            {
                "name": "$columnName",
                "position": $columnId.0
            }
        """.trimIndent()

            val request = post(taskListUrl).contentType(jsonContentType).content(passedJsonString)

            val resultJsonString = """
            {
                "name": "$columnName",
                "position": $columnId.0,
                "tasks": [],
                "id": $columnId
            }
        """.trimIndent()

            val result = mockMvc.perform(request)
            result.andExpect(status().isCreated)
                    .andExpect(content().json(resultJsonString, true))
        }
        newColumn(2, "Done")
        newColumn(3, "ToDo")
        newColumn(4, "ForDelete")
    }

    @Test
    fun `9 - Move columns`() {
        val request = put(taskListUrl + "1/move/2")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `9A - Delete last column`() {
        val request = delete(taskListUrl + "4").contentType(jsonContentType)

        mockMvc.perform(request).andExpect(status().isOk)
    }

}