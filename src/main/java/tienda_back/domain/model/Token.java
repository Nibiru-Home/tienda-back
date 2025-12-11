package tienda_back.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Token {

    private UUID id;
    private String value;
    private UUID userId;
    private Instant createdAt;


    public Token(UUID id, String value, UUID userId,
                 Instant createdAt) {
        this.id = id;
        this.value = value;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(createdAt);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", value=" + value + ", userId=" + userId + ", createdAt=" + createdAt + "]";
    }
}

