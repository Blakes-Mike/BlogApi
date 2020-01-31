package com.example.blog_api.demo.controllers;

import com.example.blog_api.demo.repos.BlogRepository;
import com.example.blog_api.demo.pojos.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    ResponseEntity<Object> objectResponseEntity;

    @PreAuthorize("permitAll()")
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

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateArticle(@RequestBody Blog blog) {
        System.out.println(blog.getId());
        System.out.println(blog.getTitle());
        System.out.println(blog.getContent());
        blogRepository.save(blog);
        objectResponseEntity = new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
        return objectResponseEntity;
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteArticle(@PathVariable String id) {
//        Integer.parseInt(id);
        try {
            blogRepository.deleteById(id);
            objectResponseEntity = new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            return objectResponseEntity;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            objectResponseEntity = new ResponseEntity<>("Record does not exist ", HttpStatus.OK);
            return objectResponseEntity;
        }
    }

    @RequestMapping(value = "{id}/getarticle")
    public ResponseEntity<Object> getArticle(@PathVariable String id) {
        Optional<Blog> article = blogRepository.findById(id);
        objectResponseEntity = new ResponseEntity<>(article, HttpStatus.OK);
        return objectResponseEntity;
    }

    @RequestMapping(value = "/getarticlebytitle", method = RequestMethod.POST)
    public ResponseEntity<Object> getArticleByTitle(@RequestParam String title) {
        objectResponseEntity = new ResponseEntity<>(blogRepository.findByTitle(title), HttpStatus.OK);
        System.out.println(title);
        System.out.println(blogRepository.findByTitle(title));
        return objectResponseEntity;
    }

    @RequestMapping(value = "/getarticlebytitlecontaining", method = RequestMethod.POST)
    public ResponseEntity<Object> getArticleByTitleContaining(@RequestParam String title) {
        try {
            Blog blogByTitleContaining = blogRepository.findBlogByTitleContaining(title);
            if (blogByTitleContaining != null) {
                objectResponseEntity = new ResponseEntity<>(blogByTitleContaining, HttpStatus.OK);
                System.out.println(objectResponseEntity);
                return objectResponseEntity;
            } else {
                return objectResponseEntity = new ResponseEntity<>("Didn't find any articles with titles containing that string ", HttpStatus.OK);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            return objectResponseEntity = new ResponseEntity<>("This string you're querying with is not unique", HttpStatus.OK);
        }
    }

    @RequestMapping("/numberofall")
    public ResponseEntity<Object> getNumberOfArticles() {
        return objectResponseEntity = new ResponseEntity<>("Total Number of Articles : " + blogRepository.getNumberOfAll(), HttpStatus.OK);
    }

    @RequestMapping("/allidsandtitles")
    public ResponseEntity<Object> getAllIdsAndTitles() {
        System.out.println("started doing this shit");
        List<Object[]> list = blogRepository.getAllIdsAndTitles();
        System.out.println(list);
        objectResponseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        return objectResponseEntity;
    }
}
