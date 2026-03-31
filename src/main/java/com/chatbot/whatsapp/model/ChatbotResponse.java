package com.chatbot.whatsapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the chatbot's response to a WhatsApp message.
 */
public class ChatbotResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("to")
    private String to;

    @JsonProperty("reply")
    private String reply;

    @JsonProperty("originalMessage")
    private String originalMessage;

    @JsonProperty("timestamp")
    private String timestamp;

    // Default constructor
    public ChatbotResponse() {}

    public ChatbotResponse(String status, String to, String reply, String originalMessage, String timestamp) {
        this.status = status;
        this.to = to;
        this.reply = reply;
        this.originalMessage = originalMessage;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public String getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(String originalMessage) { this.originalMessage = originalMessage; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "ChatbotResponse{status='" + status + "', to='" + to + "', reply='" + reply + "'}";
    }
}
