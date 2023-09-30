package com.example.klasha.api.models.app;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SafeTokenResponse {
    private String code;
    private String description;
    private List<Error> errors;
}
