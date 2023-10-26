package com.example.platform.exceptions;

import com.example.platform.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ErrorConstants.BRAND_NOT_FOUNT_ERROR_CODE;
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
