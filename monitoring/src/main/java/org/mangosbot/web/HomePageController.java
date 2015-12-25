package org.mangosbot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomePageController {

    @RequestMapping("/home.html")
    public void home(Model model) {
    }

    @RequestMapping("/problems.html")
    public void problems(Model model) {
    }

    @RequestMapping("/bot.html")
    public void bot(@RequestParam String name, Model model) {
    }
}
