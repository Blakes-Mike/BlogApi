package com.example.blog_api.demo.repos;

import com.example.blog_api.demo.pojos.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogRepository extends CrudRepository<Blog, String> {
//    @Override
//    Iterable<Blog> findAllById(Iterable<String> strings);

//    findAllByTitle();

    Blog findByTitle(String title);

    Blog findBlogByTitleContaining(String string);

    @Query("SELECT COUNT(blog) FROM Blog blog")
    int getNumberOfAll();

    String FIND_ARTICLES = "SELECT d.id, d.title FROM blog d";

    @Query(value = FIND_ARTICLES, nativeQuery = true)
    List<Object[]> getAllIdsAndTitles();

}
