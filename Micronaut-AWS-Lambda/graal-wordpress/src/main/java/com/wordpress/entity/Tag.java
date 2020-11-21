package com.wordpress.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "tags")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }
}
