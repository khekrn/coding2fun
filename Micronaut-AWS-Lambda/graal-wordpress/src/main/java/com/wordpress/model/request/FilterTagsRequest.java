package com.wordpress.model.request;

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
public final class FilterTagsRequest {

    private List<String> tags;
}
