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

@SpringBootTest
@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RestApiTaskApplicationTests {
    private val columnUrl = "http://localhost:8080/columns/"
    private val tasksUrl = "http://localhost:8080/tasks/"

    private val jsonContentType = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype)
    private lateinit var mockMvc: MockMvc

    // Date for tests
    private val taskCreationDate = "2019-11-28T18:05:45" /*LocalDateTime.now().withNano(0).toString()*/

    @Autowired
    private lateinit var webAppContext: WebApplicationContext

    @Before
    fun before() {
        mockMvc = webAppContextSetup(webAppContext).build()
    }

    @Test
    fun `A - Get empty list of columns`() {
        val request = get(columnUrl).contentType(jsonContentType)

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().json("[]", true))
    }

    @Test
    fun `B - Add first column`() {
        val passedJsonString = """
            {
                "name": "Doing"
            }
        """.trimIndent()

        val request = post(columnUrl).contentType(jsonContentType).content(passedJsonString)

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
    fun `C - Create and add first task to column`() {
        val passedJsonString = """
            {
                "name": "First task",
                "creation": "$taskCreationDate"
            }
        """.trimIndent()

        val request = post(columnUrl + "1/tasks").contentType(jsonContentType).content(passedJsonString)

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
    fun `D - Update first task`() {
        val passedJsonString = """
            {
                "name": "First task (Upd)",
                "creation": "$taskCreationDate",
                "description": "Look at iPhone 4S - smart phone by Apple",
                "position": 50.0,
                "id": 1
            }
        """.trimIndent()
        val request = put(columnUrl + "1/tasks/1").contentType(jsonContentType).content(passedJsonString)

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
//                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `E - Get first task`() {
        val request = get(columnUrl + "1/tasks/1").contentType(jsonContentType)

        val resultJsonString = """
            {
                "name": "First task (Upd)",
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
    fun `F - Get first column, with one task`() {
        val request = get(columnUrl + 1).contentType(jsonContentType)

        val resultJsonString = """
            {
                "name": "Doing",
                "tasks": [{
                    "name": "First task (Upd)",
                    "creation": "$taskCreationDate",
                    "description": "Look at iPhone 4S - smart phone by Apple",
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
    fun `G - Add new tasks`() {
        fun newTask(id: Int, name: String) {
            val passedJsonString = """
            {
                "name": "$name",
                "creation": "$taskCreationDate"
            }
        """.trimIndent()
            val request = post(columnUrl + "1/tasks").contentType(jsonContentType).content(passedJsonString)
            val resultJsonString = """
                {
                    "name": "$name",
                    "creation": "$taskCreationDate",
                    "description": "",
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
    fun `H - Add new columns`() {
        fun newColumn(columnId: Long, columnName: String) {
            val passedJsonString = """
            {
                "name": "$columnName"
            }
        """.trimIndent()

            val request = post(columnUrl).contentType(jsonContentType).content(passedJsonString)

            val resultJsonString = """
            {
                "name": "$columnName",
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
        newColumn(4, "New Task")
    }

    @Test
    fun `J - Rename column #4`() {
        val passedJsonString = """
            {
                "name": "For Delete"
            }
        """.trimIndent()

        val request = put(columnUrl + "4").contentType(jsonContentType).content(passedJsonString)

        val resultJsonString = """
            {
                "name": "For Delete",
                "tasks": [],
                "id": 4
            }
        """.trimIndent()

        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().json(resultJsonString, true))
    }

    @Test
    fun `K - Move task #3 before task #1`() {
        val request = put(tasksUrl + "3/moveBefore/1")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `L - Move task #1 after task #2`() {
        val request = put(tasksUrl + "1/moveAfter/2")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `M - Check order of tasks after reordering`() {
        val request = get(columnUrl + "1")

        val expectedJsonString = """
        {
            "name": "Doing",
            "tasks": [
                {
                    "name": "Third Task",
                    "creation": "$taskCreationDate",
                    "description": "",
                    "id": 3
                },
                {
                    "name": "Second Task",
                    "creation": "$taskCreationDate",
                    "description": "",
                    "id": 2
                },
                {
                    "name": "First task (Upd)",
                    "creation": "$taskCreationDate",
                    "description": "Look at iPhone 4S - smart phone by Apple",
                    "id": 1
                }
            ],
            "id": 1
        }
        """.trimIndent()

        val result = mockMvc.perform(request)
        result.andExpect(status().isFound)
                .andExpect(content().json(expectedJsonString, true))
    }

    @Test
    fun `N - Move column #1 after column #2`() {
        val request = put(columnUrl + "1/moveAfter/2")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `O - Move column #4 before column #2`() {
        val request = put(columnUrl + "4/moveBefore/2")

        val result = mockMvc.perform(request)
        result.andExpect(status().isOk)
    }

    @Test
    fun `P - Check order of columns`() {
        val request = get(columnUrl).contentType(jsonContentType)

        val expectedJsonString = """
            [
                {
                    "name": "For Delete",
                    "tasks": [],
                    "id": 4
                },
                {
                    "name": "Done",
                    "tasks": [],
                    "id": 2
                },
                {
                    "name": "Doing",
                    "tasks": [
                        {
                            "name": "Third Task",
                            "creation": "$taskCreationDate",
                            "description": "",
                            "id": 3
                        },
                        {
                            "name": "Second Task",
                            "creation": "$taskCreationDate",
                            "description": "",
                            "id": 2
                        },
                        {
                            "name": "First task (Upd)",
                            "creation": "$taskCreationDate",
                            "description": "Look at iPhone 4S - smart phone by Apple",
                            "id": 1
                        }
                    ],
                    "id": 1
                },
                {
                    "name": "ToDo",
                    "tasks": [],
                    "id": 3
                }
            ]
        """.trimIndent()

        // creation date and time is modifying for some reason...
        mockMvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJsonString, true))
    }

    @Test
    fun `Q - Move task #3 to column #4 and check it`() {
        mockMvc.perform(put(columnUrl + "1/tasks/3/moveToColumn/4").contentType(jsonContentType))
                .andExpect(status().isOk)

        val expectedJsonString = """
        {
            "name": "For Delete",
            "tasks": [
                {
                    "name": "Third Task",
                    "creation": "$taskCreationDate",
                    "description": "",
                    "id": 3
                }
            ],
            "id": 4
        }
        """.trimIndent()

        mockMvc.perform(get(columnUrl + "4").contentType(jsonContentType))
                .andExpect(status().isFound)
                .andExpect(content().json(expectedJsonString, true))
    }

    @Test
    fun `R - Delete column #4`() {
        val request = delete(columnUrl + "4").contentType(jsonContentType)

        mockMvc.perform(request).andExpect(status().isOk)
    }

}