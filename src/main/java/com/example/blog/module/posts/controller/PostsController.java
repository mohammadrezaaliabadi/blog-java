package com.example.blog.module.posts.controller;

import com.example.blog.module.posts.model.Posts;
import com.example.blog.module.posts.service.CategoryService;
import com.example.blog.module.posts.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private PostsService postsService;
    private CategoryService categoryService;

    public PostsController(PostsService postsService, CategoryService categoryService) {
        this.postsService = postsService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/rest", method = RequestMethod.GET)
    public @ResponseBody List<Posts> getPosts() {
        return postsService.findAllPosts();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Posts registerPost(@ModelAttribute Posts posts) throws IOException {
        return postsService.registerPost(posts);
    }

    @RequestMapping(value =  "/rest", method = RequestMethod.POST)
    public @ResponseBody Posts registerPostRest(@RequestBody Posts posts) throws IOException {
        return postsService.registerPost(posts);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        model.addAttribute("post", new Posts());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "posts/registerPosts";
    }

}
