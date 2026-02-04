package tienda_back.chat;

import java.util.List;

public record ChatRequest(String message, List<ChatMessage> history) {}
