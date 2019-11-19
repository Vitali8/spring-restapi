package com.mygroup.restApiTask.system

import org.springframework.data.repository.*

interface ProductRepository : CrudRepository<Product, Long> // Дает нашему слою работы с данными весь набор CRUD операций