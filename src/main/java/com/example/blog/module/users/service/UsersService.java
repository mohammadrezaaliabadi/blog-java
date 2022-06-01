package com.example.blog.module.users.service;

import com.example.blog.MyBeanCopy;
import com.example.blog.module.users.model.Users;
import com.example.blog.module.users.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users findById(Long id){
        return usersRepository.findById(id).get();
    }
    @Transactional
    public Users registerUser(Users users) throws IOException, InvocationTargetException, IllegalAccessException {
        if (!users.getFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath();
            byte[] bytes = users.getFile().getBytes();
            String name = UUID.randomUUID() + "." + Objects.requireNonNull(users.getFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            users.setCover(name);
        }
        if (!users.getPassword().isEmpty())
            users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));

        if(users.getId() != null) {
            Users exist = usersRepository.getOne(users.getId());
            MyBeanCopy myBeanCopy = new MyBeanCopy();
            myBeanCopy.copyProperties(exist,users);
            return usersRepository.save(exist);
        }

        return this.usersRepository.save(users);
    }

    public List<Users> findAllUsers() {
        return this.usersRepository.findAll();
    }

    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
