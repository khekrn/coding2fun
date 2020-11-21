package com.blog.model.request;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Kishore
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public final class CreatePostRequest {

    private String title;
    private String description;
    private String content;
    private List<String> tags;
    private Long postedDate;
}
