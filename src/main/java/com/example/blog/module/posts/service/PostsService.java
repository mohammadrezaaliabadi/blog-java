package com.example.blog.module.posts.service;

import com.example.blog.MyBeanCopy;
import com.example.blog.module.posts.model.Posts;
import com.example.blog.module.posts.repository.PostsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostsService {

    private PostsRepository postsRepository;

    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Transactional
    public Posts registerPost(Posts posts) throws IOException, InvocationTargetException, IllegalAccessException {

        if (!posts.getFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
            byte[] bytes = posts.getFile().getBytes();
            String name = UUID.randomUUID() + "." + Objects.requireNonNull(posts.getFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            posts.setCover(name);
        }
        if (posts.getId() != null) {
            Posts exist = postsRepository.getOne(posts.getId());
            MyBeanCopy myBeanCopy = new MyBeanCopy();
            myBeanCopy.copyProperties(exist,posts);
            return postsRepository.save(exist);
        }
        return this.postsRepository.save(posts);
    }

    public Posts findById(Long id) {
        return postsRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(Long id) {
        postsRepository.deleteById(id);
    }

    public List<Posts> findAllPosts() {
        return this.postsRepository.findAll();
    }

    public Page<Posts> findAllPosts(Pageable pageable){
        return postsRepository.findAll(pageable);
    }

    public Page<Posts> findBySearch(Posts posts, Pageable pageable){
        return postsRepository.findBySearch(posts,(posts.getCategories()!= null ? (long) posts.getCategories().size() : 0),pageable);
    }

}
