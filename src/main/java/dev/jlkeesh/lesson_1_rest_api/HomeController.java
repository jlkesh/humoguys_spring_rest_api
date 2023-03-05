package dev.jlkeesh.lesson_1_rest_api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;

@Controller
public class HomeController {

    @GetMapping("/api")
    @ResponseBody
    public String timeIs() {
        return "Time is " + LocalTime.now();
    }



}
