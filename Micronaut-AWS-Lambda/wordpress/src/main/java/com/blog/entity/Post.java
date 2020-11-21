package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kishore
 */

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String content;

    @Column(name = "posted_at")
    private Date postedAt = new Date();

    @Column(name = "last_updated_at")
    private Date lastUpdatedAt = new Date();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "post_tags", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    public Post() {
    }

    public Post(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
