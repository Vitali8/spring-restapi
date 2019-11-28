package com.mygroup.restApiTask.system.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Columns")
//@EqualsAndHashCode(exclude = ["tasks"])
data class Column(
        @javax.persistence.Column(name = "name", unique = true, length = 200)
        var name: String = "",

        @JsonIgnore
        @javax.persistence.Column(name = "position", unique = true, nullable = false)
        var position: Double = 0.0,

        @Id // Primary Key
        @javax.persistence.Column(name = "id", updatable = false, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
) {
    @OrderBy("position ASC")
    @OneToMany(cascade = [CascadeType.ALL])
    var tasks: MutableList<Task> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Column
        return id == that.id && java.lang.Double.compare(that.position, position) == 0 &&
                name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name, position)
    }
}