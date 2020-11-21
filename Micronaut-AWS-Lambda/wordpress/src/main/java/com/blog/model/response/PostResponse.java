package com.blog.model.response;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Kishore
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Introspected
public final class PostResponse {

    private String title;
    private String description;
    private String content;
    private Date postedDate;
    private List<String> tags;
}
