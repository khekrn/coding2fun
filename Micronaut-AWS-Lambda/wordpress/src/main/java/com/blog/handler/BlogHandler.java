package com.blog.handler;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kishore
 */

@Slf4j
public class BlogHandler extends MicronautLambdaHandler {

    public BlogHandler() throws ContainerInitializationException {
        super();
    }

    public BlogHandler(ApplicationContextBuilder applicationContextBuilder) throws ContainerInitializationException {
        super(applicationContextBuilder);
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        return super.handleRequest(input, context);
    }
}
