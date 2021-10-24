package com.tridang.assignment.model;/*
 * @author Tri Dang
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestStatus {
    private String code;
    private String message;

    public RestStatus() {}

    public RestStatus(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
