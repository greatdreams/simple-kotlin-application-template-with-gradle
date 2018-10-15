package com.greatdreams.learn.mybatis.spring.springboot.controller

import com.greatdreams.learn.mybatis.mapper.StudentMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @Autowired
    lateinit var studentMapper: StudentMapper

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }

    @RequestMapping("/student")
    fun student(): String {
        val stu = studentMapper.selectStudentByID(1)
        return "${stu.id} ${stu.name} ${stu.telephone}"
    }
}
