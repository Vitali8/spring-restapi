package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.Task
import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TaskRepository : CrudRepository<Task, Long> {

//    @Query(value = "SELECT id, name, description, creation_date, task_list_id FROM tasks", nativeQuery = true)
//    override fun findAll(): MutableIterable<Task>
//
//    @Query(value = "SELECT id, name, description, creation_date, task_list_id FROM tasks WHERE id = :id", nativeQuery = true)
//    override fun findById(@Param("id") id: Long): Optional<Task>
    @Query("select * from tasks where task_list_id = :taskListId", nativeQuery = true)
    fun findAllByColumn(@Param("taskListId") taskListId: Long): MutableList<Task>

    @Query("select * from tasks where id = :id and task_list_id = :taskListId limit 1", nativeQuery = true)
    fun findByIdAndColumn(@Param("id") id: Long, @Param("taskListId") taskListId: Long): Task
}