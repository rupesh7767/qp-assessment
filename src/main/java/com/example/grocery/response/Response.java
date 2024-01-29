package com.example.grocery.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    private int statusCode;
    private String message;
    private String error;


    public Response(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


}
