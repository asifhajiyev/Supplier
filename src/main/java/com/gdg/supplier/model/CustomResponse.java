package com.gdg.supplier.model;

import lombok.Data;

@Data
public class CustomResponse {
    private int code;
    private String message;
}
// 400 -- Product doesn't exist or You are not our customer
// 2 -- Order send but not enough amount to send
// 1 -- Order send and product received
