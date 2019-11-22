package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TaskListRepository : CrudRepository<TaskList, Long>{

//    @Query(value = "SELECT id, name FROM task_lists", nativeQuery = true)
//    override fun findAll(): MutableList<TaskList>
//
//    @Query(value = "SELECT id, name FROM task_lists WHERE id = :id", nativeQuery = true)
//    override fun findById(@Param("id") id: Long): Optional<TaskList>

}