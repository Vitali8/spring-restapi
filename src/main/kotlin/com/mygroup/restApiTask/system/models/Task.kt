package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Tasks")
@SequenceGenerator(name = "task_pos_seq", initialValue = 1, allocationSize = 1)
data class Task(
        @javax.persistence.Column(name = "name", length = 200)
        var name: String = "",

        @javax.persistence.Column(name = "description", length = 1000)
        var description: String = "",

        @JsonIgnore
        @javax.persistence.Column(name = "position", unique = true, nullable = false)
        var position: Double = 0.0,

        @javax.persistence.Column(name = "creation")
        var creation: LocalDateTime = LocalDateTime.now(),

        @Id
        @javax.persistence.Column(name = "id", updatable = false, nullable = false)
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Task
        return id == that.id && java.lang.Double.compare(that.position, position) == 0 &&
                name == that.name &&
                description == that.description &&
                creation == that.creation
    }

    override fun hashCode(): Int = Objects.hash(id, name, description, creation, position)
}