package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LoginDTOBadRequest {

    public String type;
    public String title;
    public String status;
    public String traceId;
    public ErrorsRegister errors;

//    "type": "https://tools.ietf.org/html/rfc7231#section-6.5.1",
//            "title": "One or more validation errors occurred.",
//            "status": 400,
//            "traceId": "00-b9bfd3280e7e9846808e8cbb38c8563d-c55a1e4665412d4f-00",
//            "errors": {
//        "Email": [
//        "The Email field is not a valid e-mail address."
//    ]
//    }

    @Data
    public class ErrorsRegister{
        public List<String> Email;
        public List<String> Password;
    }
}

