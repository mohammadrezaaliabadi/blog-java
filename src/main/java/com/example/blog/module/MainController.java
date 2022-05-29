package com.example.blog.module;

import com.example.blog.module.posts.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private PostsService postsService;

    public MainController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = {"","index"})
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllPosts());
        return "index";
    }
}
