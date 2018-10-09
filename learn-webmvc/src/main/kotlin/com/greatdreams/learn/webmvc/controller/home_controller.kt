package com.greatdreams.learn.webmvc.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HomeController {
    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Hello , Spring Webmvc"
    }

    @RequestMapping("/help.html")
    @ResponseBody
    fun help(): String {
        return "Hello , Spring Webmvc, ask for a help"
    }
}
