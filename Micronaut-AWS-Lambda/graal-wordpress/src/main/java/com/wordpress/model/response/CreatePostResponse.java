package com.wordpress.model.response;

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
public final class CreatePostResponse {

    private Long postId;
    private List<Long> tagIdList;
}
