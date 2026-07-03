package org.example.dtn2509_order_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException
{
    int code;
    int httpStatus;

    public ApplicationException(int code, int httpStatus, String message)
    {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public ApplicationException(String message)
    {
        super(message);
        this.code = 400;
        this.httpStatus = 400;
    }

}
