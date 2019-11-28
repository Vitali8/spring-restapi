package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.Column
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ColumnRepository : MyBaseRepository<Column, Long> {
    @Modifying
    @Query("update columns as t set t.position = :position where t.id = :id", nativeQuery = true)
    fun updatePositionById(@Param("id") id: Long, @Param("position") position: Double)
}