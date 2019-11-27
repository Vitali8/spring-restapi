package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TaskLists")
//@EqualsAndHashCode(exclude = ["tasks"])
data class TaskList(
        @Column(name = "name", length = 200)
        var name: String = "",

        @JsonIgnore
        @Column(name = "position", unique = true, nullable = false)
        var position: Double = 1.0,

        @Id // Primary Key
        @Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
) {
    @OrderBy("position ASC")
    @OneToMany(cascade = [CascadeType.ALL])
    var tasks: MutableList<Task> = mutableListOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as TaskList
        return id == that.id && java.lang.Double.compare(that.position, position) == 0 &&
                name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, position)
    }
}