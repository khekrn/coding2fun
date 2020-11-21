package com.blog.model.response;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kishore
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public final class Response<T> {

    private String code;
    private T model;
    private String message;

}
