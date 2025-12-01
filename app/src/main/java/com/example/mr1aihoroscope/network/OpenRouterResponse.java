package com.example.mr1aihoroscope.network;

import java.util.List;

public class OpenRouterResponse {
    public List<Choice> choices;

    public static class Choice {
        public Message message;
    }

    public static class Message {
        public String content;
    }
}
