package com.tridang.assignment.constant;/*
 * @author Tri Dang
 */

import lombok.Getter;

@Getter
public enum MerriamValidationStatus {
    VALIDATED("Validated"),
    NOT_VALIDATED("Not Validated");

    private String status;

    MerriamValidationStatus(String status) {
        this.status = status;
    }
}
