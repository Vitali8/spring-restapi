package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.TaskList
import org.springframework.data.repository.CrudRepository

interface TaskListRepository : CrudRepository<TaskList, Long>