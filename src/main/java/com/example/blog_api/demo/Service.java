package com.example.blog_api.demo;

import com.example.blog_api.demo.pojos.Blog;
import com.example.blog_api.demo.repos.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class Service {

    @Autowired
    private BlogRepository blogRepository;

    ResponseEntity<Object> objectResponseEntity;

    public ResponseEntity<Object> getArticles() {
        List<Blog> list = new ArrayList<>();
        blogRepository.findAll().forEach(list::add);
        System.out.println(blogRepository);
        objectResponseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return objectResponseEntity;
    }

    @RequestMapping(value = "/getall", method = RequestMethod.POST)
    public ResponseEntity<Object> addArticle(@RequestBody Blog blog) {
        System.out.println(blog.getId());
        System.out.println(blog.getTitle());
        System.out.println(blog.getContent());
        blogRepository.save(blog);
        objectResponseEntity = new ResponseEntity<>("Created Successfully", HttpStatus.OK);
        return objectResponseEntity;
    }


}
