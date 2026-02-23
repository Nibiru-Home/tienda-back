package tienda_back.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PreDestroy;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenAiChatService {
    private static final URI RESPONSES_URI = URI.create("https://api.openai.com/v1/responses");

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final ExecutorService executor;
    private final String apiKey;
    private final String model;

    public OpenAiChatService(ObjectMapper objectMapper,
            String apiKey,
            String model) {
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.model = model;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        this.executor = Executors.newCachedThreadPool();
    }

    public SseEmitter streamChat(ChatRequest request) {
        SseEmitter emitter = new SseEmitter(0L);

        if (request == null || request.message() == null || request.message().isBlank()) {
            emitter.completeWithError(new IllegalArgumentException("Message is required"));
            return emitter;
        }

        if (apiKey == null || apiKey.isBlank()) {
            emitter.completeWithError(new IllegalStateException("OPENAI_API_KEY is missing"));
            return emitter;
        }

        List<ChatMessage> history = request.history() == null ? List.of() : request.history();

        executor.submit(() -> {
            try {
                ObjectNode payload = buildPayload(history, request.message());
                HttpRequest httpRequest = HttpRequest.newBuilder(RESPONSES_URI)
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .header("Accept", "text/event-stream")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payload)))
                        .build();

                HttpResponse<java.io.InputStream> response = httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofInputStream());

                if (response.statusCode() / 100 != 2) {
                    String errorBody = new String(response.body().readAllBytes(), StandardCharsets.UTF_8);
                    emitter.completeWithError(new IllegalStateException(
                            "OpenAI error " + response.statusCode() + ": " + errorBody));
                    return;
                }

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder data = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if (line.isEmpty()) {
                            handleEvent(data.toString(), emitter);
                            data.setLength(0);
                            continue;
                        }
                        if (line.startsWith("data:")) {
                            if (data.length() > 0) {
                                data.append('\n');
                            }
                            data.append(line.substring(5).trim());
                        }
                    }
                    emitter.complete();
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }

    private ObjectNode buildPayload(List<ChatMessage> history, String message) {
        ArrayNode input = objectMapper.createArrayNode();
        for (ChatMessage msg : history) {
            ObjectNode item = objectMapper.createObjectNode();
            item.put("role", msg.role());
            item.put("content", msg.content());
            input.add(item);
        }
        ObjectNode user = objectMapper.createObjectNode();
        user.put("role", "user");
        user.put("content", message);
        input.add(user);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("model", model);
        payload.set("input", input);
        payload.put("stream", true);
        return payload;
    }

    private void handleEvent(String data, SseEmitter emitter) throws Exception {
        if (data == null || data.isBlank()) {
            return;
        }
        if ("[DONE]".equals(data)) {
            emitter.complete();
            return;
        }
        JsonNode node = objectMapper.readTree(data);
        String type = node.path("type").asText();
        if ("response.output_text.delta".equals(type)) {
            String delta = node.path("delta").asText();
            if (delta != null && !delta.isEmpty()) {
                emitter.send(delta);
            }
        } else if ("response.error".equals(type)) {
            String message = node.path("error").path("message").asText("OpenAI error");
            emitter.completeWithError(new IllegalStateException(message));
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdownNow();
    }
}
