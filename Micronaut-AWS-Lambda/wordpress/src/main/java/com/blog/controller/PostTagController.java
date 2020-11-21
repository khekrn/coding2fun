package com.blog.controller;

import com.blog.exception.BlogException;
import com.blog.model.request.CreatePostRequest;
import com.blog.model.request.FilterTagsRequest;
import com.blog.model.response.CreatePostResponse;
import com.blog.model.response.GetPostResponse;
import com.blog.model.response.PostResponse;
import com.blog.model.response.Response;
import com.blog.service.PostTagService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Kishore
 */

@Controller("/v1")
@Slf4j
public class PostTagController {

    private final PostTagService postTagService;

    @Inject
    public PostTagController(PostTagService postTagService) {
        this.postTagService = postTagService;
    }

    @Post(value = "/new-post", produces = MediaType.APPLICATION_JSON)
    public Response<CreatePostResponse> createPost(@Body CreatePostRequest createPostRequest) {
        log.info("In createPost");

        var response = new Response<CreatePostResponse>();

        try {
            var apiResponse = this.postTagService.createPost(createPostRequest);
            updateResponse(response, apiResponse, "200", "");
        } catch (BlogException e) {
            log.error("Error while creating post - " + e);
            updateResponse(response, null, "500", e.getMessage());
        }

        log.info("Return from createPost");
        return response;
    }

    @Get(value = "/list-tags", produces = MediaType.APPLICATION_JSON)
    public Response<List<String>> listTags() {
        log.info("In listTags");

        var response = new Response<List<String>>();
        try {
            var apiResponse = this.postTagService.listTags();
            updateResponse(response, apiResponse, "200", "");
        } catch (BlogException e) {
            log.error("Error while fetching post - " + e);
            updateResponse(response, null, "500", e.getMessage());
        }

        log.info("Return from listTags");
        return response;
    }

    @Get(value = "/get-post", produces = MediaType.APPLICATION_JSON)
    public Response<GetPostResponse> getPost(@QueryValue(value = "id") Long postId) {
        log.info("In getPost");

        var response = new Response<GetPostResponse>();
        try {
            var apiResponse = this.postTagService.getPost(postId);
            updateResponse(response, apiResponse, "200", "");
        } catch (BlogException e) {
            log.error("Error while fetching post - " + e);
            updateResponse(response, null, "500", e.getMessage());
        }

        log.info("Return from getPost");
        return response;
    }

    @Get(value = "/list-post", produces = MediaType.APPLICATION_JSON)
    public Response<List<GetPostResponse>> listPost() {
        log.info("In listPost");

        var response = new Response<List<GetPostResponse>>();
        try {
            var apiResponse = this.postTagService.listPost();
            updateResponse(response, apiResponse, "200", "");
        } catch (BlogException e) {
            log.error("Error while fetching all post - " + e);
            updateResponse(response, null, "500", e.getMessage());
        }

        log.info("Return from listPost");
        return response;
    }

    @Post(value = "/search/tags", produces = MediaType.APPLICATION_JSON)
    public Response<List<PostResponse>> searchByTags(@Body FilterTagsRequest filterTagsRequest) {
        log.info("In searchByTags");

        var response = new Response<List<PostResponse>>();
        try {
            var apiResponse = this.postTagService.filterByTags(filterTagsRequest.getTags());
            updateResponse(response, apiResponse, "200", "");
        } catch (BlogException e) {
            log.error("Error while fetching all post - " + e);
            updateResponse(response, null, "500", e.getMessage());
        }

        log.info("Return from searchByTags");
        return response;
    }

    @Get("/greet")
    public String greet() {
        return "Hello Micronaut !!";
    }

    private <T> void updateResponse(Response<T> response, T model, String code, String errorMessage) {
        response.setCode(code);
        response.setMessage(errorMessage);
        if (model != null) {
            response.setModel(model);
        }
    }
}
