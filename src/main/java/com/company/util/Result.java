package com.company.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    String message;
    boolean result;

    public Result(String message) {
        this.message = message;
    }
}
