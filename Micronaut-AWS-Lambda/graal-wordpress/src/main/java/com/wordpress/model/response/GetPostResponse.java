package com.wordpress.model.response;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Kishore
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public final class GetPostResponse {

    private String title;
    private String description;
    private String content;
    private Date postedDate;
    private Date lastUpdated;

    private List<String> tags;
}
