package com.greatdreams.learn.groovy

class MainProgram {
    static void main(String... args) {
        println("Groovy world")

        def text = 'Hello, $firstname, $lastname'
        def binding = ["firstname": "huawei", "lastname": "Huawei Wang"]
        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(text).make(binding)

        println(template.toString())
    }
}
