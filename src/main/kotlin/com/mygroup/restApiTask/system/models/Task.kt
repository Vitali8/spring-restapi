package com.mygroup.restApiTask.system.models

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Tasks")
@SequenceGenerator(name = "task_pos_seq", initialValue = 1, allocationSize = 1)
data class Task(
        @Column(name = "name", length = 200)
        var name: String = "",

        @Column(name = "description", length = 1000)
        var description: String = "",

//        @JsonProperty("position")
        @Column(name = "position", unique = true, nullable = false)
        var position: Double = 1.0,

        @Column(name = "creation")
        var creation: LocalDateTime = LocalDateTime.now(),

        @Id
        @Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
) {
//    @JoinColumn(name = "column_id", referencedColumnName = "id", nullable = false)
//    @ManyToOne
//    lateinit var taskList: TaskList

//    constructor(taskList: TaskList) : this() {
//        this.taskList = taskList
//    }

//    operator fun compareTo(o: Task) = position.compareTo(o.position)
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Task
        return id == that.id && java.lang.Double.compare(that.position, position) == 0 &&
                name == that.name &&
                description == that.description &&
                creation == that.creation
    }

    override fun hashCode(): Int = Objects.hash(id, name, description, creation, position)
}