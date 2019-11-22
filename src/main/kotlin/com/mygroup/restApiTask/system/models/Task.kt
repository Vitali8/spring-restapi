package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(name = "tasks") // Говорим как назвать таблицу в БД
@TableGenerator(name = "tab_id")
data class Task( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
        @JsonProperty("name") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        var name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

        @JsonProperty("description")
        @Column(name = "description", length = 1000)
        var description: String = "",

        @JsonProperty("position")
        @Column(name = "position", unique = true)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_seq")
        @SequenceGenerator(name = "pos_seq")
        var position: Double = 1.0,

        @JsonProperty("creationDate")
        @Column(name = "creationDate")
        var creationDate: LocalDateTime = LocalDateTime.now(),

        @Id // Сообщяем ORM что это поле - Primary Key
        @JsonProperty("id")
        @Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.TABLE, generator = "tab_id")
        val id: Long = 0L
) {
    @ManyToOne
    lateinit var taskList: TaskList

    constructor(taskList: TaskList) : this() {
        this.taskList = taskList
    }
}