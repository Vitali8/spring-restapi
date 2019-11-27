package com.mygroup.restApiTask.system.repositories

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import java.util.*

@NoRepositoryBean
interface MyBaseRepository<T, ID> : PagingAndSortingRepository<T, ID> {

    //    @Query("SELECT * FROM tasks WHERE position > :position ORDER BY position LIMIT 1", nativeQuery = true)
    fun findFirstByPositionGreaterThanOrderByPositionAsc(@Param("position") position: Double): Optional<T>
}