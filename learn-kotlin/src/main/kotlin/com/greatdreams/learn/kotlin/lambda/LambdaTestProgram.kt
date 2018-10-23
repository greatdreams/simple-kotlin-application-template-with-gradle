package com.greatdreams.learn.kotlin.lambda

val sum = { x: Int, y: Int -> x + y }

val minus = fun Int.(other: Int): Int = this - other

class HTML {
    fun body() {
        println("body")
    }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

data class Student(var name: String, var id: String)


class ClassRoom {
    val studentList = ArrayList<Student>()

    fun student(init: Student.() -> Unit): Student {
        val stu = Student("123", "456")
        stu.init()
        studentList.add(stu)
        return stu
    }
}

fun classRoom(student: ClassRoom.() -> Student): ClassRoom {
    val className = ClassRoom()
    className.student()
    return className
}

object LambdaTestProgram {
    fun main() {
        println(sum(10, 100))
        println(sum::class.java)
        sum::class.java.genericInterfaces.forEach { type ->
            println(type::class.java)
        }
        println(100.minus(10))

        html {
            body()
        }

        val classRoom = classRoom {
            student {
                name = "hello wolrd"
                id = "10001"
            }
            student {
                name = "hello wolrd"
                id = "10002"
            }
        }
        println(classRoom.studentList.size)
        classRoom.studentList.forEach {
            student ->
            println("Student(${student.id}, ${student.name})")
        }
    }
}