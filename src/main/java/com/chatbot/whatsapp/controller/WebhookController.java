package com.chatbot.whatsapp.controller;

import com.chatbot.whatsapp.model.ChatbotResponse;
import com.chatbot.whatsapp.model.MessageLog;
import com.chatbot.whatsapp.model.WhatsAppMessage;
import com.chatbot.whatsapp.service.ChatbotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller that simulates a WhatsApp Webhook endpoint.
 *
 * Endpoints:
 *   POST /webhook          → Receive and process WhatsApp messages
 *   GET  /webhook/health   → Health check
 *   GET  /webhook/logs     → View all logged messages
 *   GET  /webhook/stats    → View statistics
 */
@RestController
@RequestMapping("/webhook")
@CrossOrigin(origins = "*") // Allow testing from any origin / Postman / Render
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ChatbotService chatbotService;

    public WebhookController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * POST /webhook
     * Accepts a WhatsApp-style JSON message and returns a predefined bot reply.
     *
     * Sample Request Body:
     * {
     *   "from": "+91-9876543210",
     *   "message": "Hi",
     *   "timestamp": "2026-03-31T10:00:00",
     *   "messageId": "msg_001"
     * }
     */
    @PostMapping
    public ResponseEntity<?> receiveMessage(@RequestBody WhatsAppMessage incomingMessage) {

        // --- Validate request ---
        if (incomingMessage == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Request body cannot be empty"));
        }
        if (incomingMessage.getMessage() == null || incomingMessage.getMessage().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "The 'message' field is required and cannot be blank"));
        }
        if (incomingMessage.getFrom() == null || incomingMessage.getFrom().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "The 'from' field is required"));
        }

        logger.info("Webhook triggered by: {} | Message: {}", incomingMessage.getFrom(), incomingMessage.getMessage());

        // --- Generate reply ---
        String reply = chatbotService.generateReply(incomingMessage.getMessage());

        // --- Log the message ---
        chatbotService.logMessage(incomingMessage, reply);

        // --- Build response ---
        ChatbotResponse response = new ChatbotResponse(
            "success",
            incomingMessage.getFrom(),
            reply,
            incomingMessage.getMessage(),
            LocalDateTime.now().format(FORMATTER)
        );

        return ResponseEntity.ok(response);
    }

    /**
     * GET /webhook/health
     * Health check endpoint — useful for Render and deployment monitoring.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP ✅");
        health.put("service", "WhatsApp Chatbot Backend");
        health.put("timestamp", LocalDateTime.now().format(FORMATTER));
        health.put("messagesProcessed", chatbotService.getMessageCount());
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }

    /**
     * GET /webhook/logs
     * Returns all logged messages (most recent first).
     */
    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getLogs() {
        List<MessageLog> logs = chatbotService.getAllLogs();
        Map<String, Object> result = new HashMap<>();
        result.put("totalMessages", logs.size());
        result.put("logs", logs);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /webhook/stats
     * Returns basic stats about the chatbot's activity.
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMessagesProcessed", chatbotService.getMessageCount());
        stats.put("uptime", "Server is live");
        stats.put("supportedCommands", List.of("Hi", "Hello", "Hey", "Bye", "Goodbye",
                "Help", "Hours", "Contact", "Thank you", "Price"));
        stats.put("timestamp", LocalDateTime.now().format(FORMATTER));
        return ResponseEntity.ok(stats);
    }

    /**
     * Global fallback for unsupported methods or paths within /webhook.
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<Map<String, String>> fallback() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of(
                    "error", "Method not allowed",
                    "hint", "Use POST /webhook to send a message, GET /webhook/health for status"
                ));
    }
}
