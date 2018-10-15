package com.greatdreams.learn.mybatis.mapper

import com.greatdreams.learn.mybatis.model.Student

interface StudentMapper {
    fun selectStudentByID(id: Int): Student
}