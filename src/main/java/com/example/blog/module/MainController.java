package com.example.blog.module;

import com.example.blog.module.posts.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private PostsService postsService;
    @RequestMapping(value = {"","index"})
    public String index(Model model){
        model.addAttribute("home","This is Home!!!");
        return "index";
    }
}
