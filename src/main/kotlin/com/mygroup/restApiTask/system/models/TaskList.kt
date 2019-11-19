package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(name = "taskLists") // Говорим как назвать таблицу в БД
data class TaskList( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
        @JsonProperty("name") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        val name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

//        @JsonProperty("description")
//        @Column(name = "description", length = 1000)
//        val description: String = "",

        @Id // Сообщяем ORM что это поле - Primary Key
        @JsonProperty("id")
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO) // Также говорим ему что оно - Autoincrement
        val id: Long = 0L
) {
    @OneToMany(mappedBy = "taskList")
    val tasks: MutableSet<Task> = HashSet()
}