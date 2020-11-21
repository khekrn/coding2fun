package com.wordpress.exception;

/**
 * @author Kishore
 */

public final class BlogException extends Exception{
    public BlogException(String message) {
        super(message);
    }

    public BlogException(Throwable e) {
        super(e);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }
}
