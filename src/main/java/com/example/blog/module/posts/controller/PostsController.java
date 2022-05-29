package com.example.blog.module.posts.controller;

import com.example.blog.module.posts.model.Posts;
import com.example.blog.module.posts.service.PostsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = "/rest", method = RequestMethod.GET)
    public List<Posts> getPosts() {
        return postsService.findAllPosts();
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.POST)
    public Posts registerPost(@ModelAttribute Posts posts) throws IOException {
        return postsService.registerPost(posts);
    }

    @RequestMapping(value =  "/rest", method = RequestMethod.POST)
    public Posts registerPostRest(@RequestBody Posts posts) throws IOException {
        return postsService.registerPost(posts);
    }


}
