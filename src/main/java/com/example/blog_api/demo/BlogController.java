package com.example.blog_api.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    private static Map<String, Blog> blogRepo = new HashMap<>();

    static {
        Blog post = new Blog();
        post.setTitle("Why Michael is an awesome person");
        post.setContent("In Ubuntu systems running MySQL 5.7 (and later versions), the root MySQL user is set to authenticate using the auth_socket plugin by default rather than with a password. This allows for some greater security and usability in many cases, but it can also complicate things when you need to allow an external program (e.g., phpMyAdmin) to access the user.\n" +
                "\n" +
                "In order to use a password to connect to MySQL as root, you will need to switch its authentication method from auth_socket to mysql_native_password. To do this, open up the MySQL prompt from your terminal:");
        blogRepo.put(post.getTitle(), post);
    }

    ResponseEntity<Object> objectResponseEntity;

    @RequestMapping("/")
    public ResponseEntity<Object> landing() {
        objectResponseEntity = new ResponseEntity<>("This is the  first endpoint", HttpStatus.OK);
        return objectResponseEntity;
    }

    @RequestMapping("/getall")
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
