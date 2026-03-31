package com.chatbot.whatsapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a log entry for an incoming + outgoing message pair.
 */
public class MessageLog {

    @JsonProperty("logId")
    private String logId;

    @JsonProperty("from")
    private String from;

    @JsonProperty("receivedMessage")
    private String receivedMessage;

    @JsonProperty("botReply")
    private String botReply;

    @JsonProperty("timestamp")
    private String timestamp;

    public MessageLog() {}

    public MessageLog(String logId, String from, String receivedMessage, String botReply, String timestamp) {
        this.logId = logId;
        this.from = from;
        this.receivedMessage = receivedMessage;
        this.botReply = botReply;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getLogId() { return logId; }
    public void setLogId(String logId) { this.logId = logId; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getReceivedMessage() { return receivedMessage; }
    public void setReceivedMessage(String receivedMessage) { this.receivedMessage = receivedMessage; }

    public String getBotReply() { return botReply; }
    public void setBotReply(String botReply) { this.botReply = botReply; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
