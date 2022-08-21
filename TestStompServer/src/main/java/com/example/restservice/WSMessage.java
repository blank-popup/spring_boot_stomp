package com.example.restservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WSMessage {
    private String channel;
    private String username;
    private String message;
}
