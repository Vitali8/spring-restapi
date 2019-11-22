package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(name = "taskLists") // Говорим как назвать таблицу в БД
@TableGenerator(name = "tab_id")
data class TaskList( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
        @JsonProperty("name") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        var name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

        @JsonProperty("position")
        @Column(name = "position", unique = true)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_seq")
        @SequenceGenerator(name = "pos_seq")
        var position: Double = 1.0,

        @Id // Сообщяем ORM что это поле - Primary Key
        @JsonProperty("id")
        @Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.TABLE, generator="tab_id")
        val id: Long = 0L
) {
    @OneToMany(mappedBy = "taskList", cascade = [CascadeType.ALL])
    @OrderBy("position ASC")
    var tasks: SortedSet<Task> = TreeSet()
}