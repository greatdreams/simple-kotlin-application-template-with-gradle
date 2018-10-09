package com.greatdreams.learn.webmvc.controller;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {
    @RequestMapping("/")
    @ResponseBody
    public Publisher<String> home() {
        return Mono.just("Hello , webflux");
    }
    @RequestMapping("/help.html")
    @ResponseBody
    public Publisher<String> help() {
        return Mono.just("Hello , webflux, ask for a help");
    }

    @RequestMapping("/login.html")
    @ResponseBody
    public String login() {
        return "login page";
    }
}
