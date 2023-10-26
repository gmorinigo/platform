package com.example.platform.exceptions;

import com.example.platform.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException() {
        super(ErrorConstants.INVALID_DATE_FORMAT_ERROR_MESSAGE);
    }

    public String getErrorCode() {
        return ErrorConstants.PRODUCT_NOT_FOUNT_ERROR_CODE;
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
