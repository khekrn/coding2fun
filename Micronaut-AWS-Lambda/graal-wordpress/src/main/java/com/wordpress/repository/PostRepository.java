package com.wordpress.repository;

import com.wordpress.entity.Post;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kishore
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true, value = "select * from post where id in(select distinct(post_tags.post_id) " +
            "from post_tags where post_tags.tag_id in(:id))")
    List<Post> filterByTag(List<Long> id);
}
