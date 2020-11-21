package com.blog.service;

import com.blog.entity.Post;
import com.blog.entity.Tag;
import com.blog.exception.BlogException;
import com.blog.model.request.CreatePostRequest;
import com.blog.model.response.CreatePostResponse;
import com.blog.model.response.GetPostResponse;
import com.blog.model.response.PostResponse;
import com.blog.repository.PostRepository;
import com.blog.repository.TagRepository;
import io.micronaut.core.annotation.Introspected;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Kishore
 */

@Introspected
@Slf4j
public class PostTagService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Inject
    public PostTagService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public CreatePostResponse createPost(CreatePostRequest createPostRequest) throws BlogException {
        log.info("In createPost");

        CreatePostResponse response;
        try {
            var post = new Post(createPostRequest.getTitle(), createPostRequest.getDescription(),
                    createPostRequest.getContent());
            post.setPostedAt(new Date(createPostRequest.getPostedDate()));

            for (String tagName : createPostRequest.getTags()) {
                if (!this.tagRepository.existsByName(tagName)) {
                    var tag = new Tag(tagName);

                    post.getTags().add(tag);

                    tag.getPosts().add(post);
                }
            }

            post = this.postRepository.save(post);

            var tagIdList = post.getTags().stream().map(Tag::getId).collect(Collectors.toList());

            response = new CreatePostResponse();
            response.setPostId(post.getId());
            response.setTagIdList(tagIdList);

        } catch (Exception e) {
            log.error("Error while creating the post");
            throw new BlogException(e);
        }

        log.info("Return from createPost");
        return response;
    }

    public GetPostResponse getPost(Long postId) throws BlogException {
        log.info("In createPost");

        GetPostResponse response;
        try {
            var optionalPost = this.postRepository.findById(postId);

            if (optionalPost.isEmpty()) {
                log.error("Invalid post id");
                throw new BlogException("Invalid post id, no post exist with the given id");
            }

            var post = optionalPost.get();

            var tagList = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());

            response = new GetPostResponse();
            response.setTitle(post.getTitle());
            response.setDescription(post.getDescription());
            response.setContent(post.getContent());
            response.setLastUpdated(post.getLastUpdatedAt());
            response.setPostedDate(post.getPostedAt());
            response.setTags(tagList);

        } catch (Exception e) {
            log.error("Error while creating the post");
            throw new BlogException(e);
        }

        log.info("Return from createPost");
        return response;
    }

    public List<GetPostResponse> listPost() throws BlogException {
        log.info("In listPost");

        List<GetPostResponse> result;
        try {
            var listPostResponse = this.postRepository.findAll();

            if (listPostResponse.isEmpty()) {
                log.error("Invalid post id");
                throw new BlogException("Invalid post id, no post exist with the given id");
            }

            result = listPostResponse.stream().map(post -> {
                var tagList = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());

                var response = new GetPostResponse();
                response.setTitle(post.getTitle());
                response.setDescription(post.getDescription());
                response.setContent(post.getContent());
                response.setLastUpdated(post.getLastUpdatedAt());
                response.setPostedDate(post.getPostedAt());
                response.setTags(tagList);

                return response;
            }).collect(Collectors.toList());


        } catch (Exception e) {
            log.error("Error while creating the post");
            throw new BlogException(e);
        }

        log.info("Return from createPost");
        return result;
    }

    public List<PostResponse> filterByTags(List<String> tags) throws BlogException {
        log.info("In filterByTags");

        List<PostResponse> response;
        try {
            var tagIDs = this.tagRepository.findByName(tags);

            var postList = this.postRepository.filterByTag(tagIDs);

            response = new ArrayList<>(postList.size());

            for (Post post : postList) {
                var tagsList = post.getTags().stream().map(Tag::getName).collect(Collectors.toList());

                var postResponse = new PostResponse();
                postResponse.setTitle(post.getTitle());
                postResponse.setPostedDate(post.getPostedAt());
                postResponse.setContent(post.getContent());
                postResponse.setDescription(post.getDescription());
                postResponse.setTags(tagsList);

                response.add(postResponse);

            }
        } catch (Exception e) {
            log.error("Error while creating the post");
            throw new BlogException(e);
        }

        log.info("Return from filterByTags");
        return response;
    }

    public List<String> listTags() throws BlogException {
        log.info("In listTags");

        List<String> tags;

        try {
            var tagResults = this.tagRepository.findAll();
            tags = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                    tagResults.listIterator(), Spliterator.IMMUTABLE), false)
                    .map(Tag::getName).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while retrieving tags - " + e);
            throw new BlogException(e);
        }

        log.info("Return from listTags");
        return tags;
    }
}
