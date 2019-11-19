package com.mygroup.restApiTask.system.repositories

import com.mygroup.restApiTask.system.models.Task
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<Task, Long>