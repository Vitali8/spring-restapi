package com.mygroup.restApiTask.system.repositories

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.util.*

@NoRepositoryBean
interface MyBaseRepository<T, ID> : PagingAndSortingRepository<T, ID> {

    fun findFirstByPositionGreaterThanOrderByPositionAsc(@Param("position") position: Double): Optional<T>
    fun findFirstByPositionLessThanOrderByPositionAsc(@Param("position") position: Double): Optional<T>

    fun findTopByPositionNotNullOrderByPositionDesc() : Optional<T>
}