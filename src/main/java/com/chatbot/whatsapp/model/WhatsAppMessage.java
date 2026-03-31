package com.chatbot.whatsapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an incoming WhatsApp-style message payload.
 */
public class WhatsAppMessage {

    @JsonProperty("from")
    private String from;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("messageId")
    private String messageId;

    // Default constructor (required for Jackson deserialization)
    public WhatsAppMessage() {}

    public WhatsAppMessage(String from, String message, String timestamp, String messageId) {
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    // Getters and Setters
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    @Override
    public String toString() {
        return "WhatsAppMessage{from='" + from + "', message='" + message +
               "', timestamp='" + timestamp + "', messageId='" + messageId + "'}";
    }
}
