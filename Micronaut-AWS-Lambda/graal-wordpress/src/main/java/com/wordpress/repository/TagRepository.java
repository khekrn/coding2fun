package com.wordpress.repository;

import com.wordpress.entity.Tag;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Kishore
 */

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    @Query("Select t.id from Tag t where t.name in(:names)")
    List<Long> findByName(List<String> names);
}
