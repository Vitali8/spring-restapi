package com.mygroup.restApiTask.system.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(name = "tasks") // Говорим как назвать таблицу в БД
@SequenceGenerator(name = "task_pos_seq", initialValue = 1, allocationSize = 1)
data class Task( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
//        @JsonProperty("name") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        var name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

//        @JsonProperty("description")
        @Column(name = "description", length = 1000)
        var description: String = "",

//        @JsonProperty("position")
        @Column(
                name = "position", unique = true, nullable = false/*,
                columnDefinition = "DOUBLE DEFAULT nextval('task_pos_seq')"*/
        )
        var position: Double = 1.0,

//        @JsonProperty("creationDate")
        @Column(name = "creationDate")
        var creationDate: LocalDateTime = LocalDateTime.now(),

        @Column(name = "task_list_id", nullable = false)
        var taskListId: Long = 0L,

        @Id // Сообщяем ORM что это поле - Primary Key
//        @JsonProperty("id")
        @Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
) {
//    @ManyToOne
//    lateinit var taskList: TaskList

//    constructor(taskList: TaskList) : this() {
//        this.taskList = taskList
//    }

    operator fun compareTo(o: Task) = position.compareTo(o.position)
}