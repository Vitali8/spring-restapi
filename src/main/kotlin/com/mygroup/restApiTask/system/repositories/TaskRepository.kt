package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.Task
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TaskRepository : MyBaseRepository<Task, Long> {
    @Modifying
    @Query("update tasks as t set t.position = :position where t.id = :id", nativeQuery = true)
    fun updatePositionById(@Param("id") id: Long, @Param("position") position: Double)

//    @Modifying
//    @Query("update tasks set position = :position where id = :id", nativeQuery = true)
//    fun editTaskById(@Param("id") id: Long, @Param("name") name: String)
}