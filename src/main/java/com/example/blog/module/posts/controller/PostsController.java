package com.example.blog.module.posts.controller;

import com.example.blog.module.posts.model.Posts;
import com.example.blog.module.posts.service.CategoryService;
import com.example.blog.module.posts.service.PostsService;
import com.example.blog.module.users.service.UsersService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private PostsService postsService;
    private CategoryService categoryService;

    private UsersService usersService;

    public PostsController(PostsService postsService, CategoryService categoryService, UsersService usersService) {
        this.postsService = postsService;
        this.categoryService = categoryService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String posts(@ModelAttribute("p") Posts posts,
                        Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("posts", postsService.findBySearch(posts,pageable));
        return "posts/posts";
    }


    //    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    public @ResponseBody List<Posts> search(@ModelAttribute Posts posts) {
//        return postsService.findBySearch(posts);
//    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String posts(Model model, @PageableDefault(size = 5) Pageable pageable) {
//        model.addAttribute("posts", postsService.findAllPosts(pageable));
//        return "posts/posts";
//    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        model.addAttribute("post", new Posts());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("post") @Valid Posts posts,Model model, BindingResult bindingResult, Principal principal) throws IOException, InvocationTargetException, IllegalAccessException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "posts/registerPosts";
        }
        posts.setUsers(usersService.findByEmail(principal.getName()));
        postsService.registerPost(posts);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("post", postsService.findById(id));
        model.addAttribute("categories", categoryService.findAllCategories());
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        postsService.deleteById(id);
        return "redirect:/posts";
    }


    @RequestMapping(value = "/rest/getPosts", method = RequestMethod.GET)
    public @ResponseBody List<Posts> getPosts() {
        return postsService.findAllPosts();
    }

    @RequestMapping(value = "/rest/register", method = RequestMethod.POST)
    public @ResponseBody
    Posts registerPost(@RequestBody Posts posts) throws IOException, InvocationTargetException, IllegalAccessException {
        return postsService.registerPost(posts);
    }

}
