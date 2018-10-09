package com.greatdreams.learn.webmvc.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ToolController {
    @RequestMapping("/tool/index.html")
    @ResponseBody
    fun home(): String {
        return "tool index"
    }
    @RequestMapping("/tool.html")
    @ResponseBody
    fun tool(): String {
        return "tool index"
    }

}