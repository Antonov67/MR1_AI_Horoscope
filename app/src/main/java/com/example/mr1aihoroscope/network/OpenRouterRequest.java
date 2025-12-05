package com.example.mr1aihoroscope.network;

import java.util.List;

public class OpenRouterRequest {
    private String model;
    private List<Message> messages;

    public OpenRouterRequest(List<Message> messages) {
        this.model = "tngtech/deepseek-r1t2-chimera:free";
        this.messages = messages;
    }

    

    public static class Message {
        private String role;
        private String content;

        public Message(String content) {
            this.role = "user";
            this.content = content;
        }
    }
}
