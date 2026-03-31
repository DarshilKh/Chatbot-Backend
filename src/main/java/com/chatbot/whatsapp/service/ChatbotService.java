package com.chatbot.whatsapp.service;

import com.chatbot.whatsapp.model.MessageLog;
import com.chatbot.whatsapp.model.WhatsAppMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Core chatbot service that handles:
 * - Generating predefined replies based on incoming messages
 * - Logging all messages in memory
 */
@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // In-memory message log (would be a DB in production)
    private final List<MessageLog> messageLogs = Collections.synchronizedList(new ArrayList<>());

    /**
     * Generates a bot reply based on the incoming message text.
     * Supports case-insensitive keyword matching with friendly fallback.
     */
    public String generateReply(String incomingMessage) {
        if (incomingMessage == null || incomingMessage.isBlank()) {
            return "Please send a valid message.";
        }

        String msg = incomingMessage.trim().toLowerCase();

        // --- Predefined replies ---
        if (msg.equals("hi") || msg.equals("hello") || msg.equals("hey")) {
            return "Hello! 👋 How can I help you today?";
        }
        if (msg.equals("bye") || msg.equals("goodbye") || msg.equals("see you")) {
            return "Goodbye! 👋 Have a great day!";
        }
        if (msg.contains("help")) {
            return "Sure! Here's what I can help with:\n1. Type 'Hi' to greet\n2. Type 'Bye' to say goodbye\n3. Type 'Hours' for business hours\n4. Type 'Contact' for contact info";
        }
        if (msg.contains("hour") || msg.contains("timing") || msg.contains("open")) {
            return "🕘 Our business hours are:\nMon–Fri: 9 AM – 6 PM\nSat: 10 AM – 4 PM\nSun: Closed";
        }
        if (msg.contains("contact") || msg.contains("email") || msg.contains("phone")) {
            return "📞 Contact us at:\nPhone: +91-9876543210\nEmail: support@example.com";
        }
        if (msg.contains("thank")) {
            return "You're welcome! 😊 Is there anything else I can help you with?";
        }
        if (msg.contains("price") || msg.contains("cost") || msg.contains("rate")) {
            return "💰 Please visit our website or contact our team at support@example.com for pricing details.";
        }

        // Default fallback
        return "🤖 Sorry, I didn't understand that. Type 'Help' to see what I can do!";
    }

    /**
     * Logs an incoming message along with the bot's reply.
     */
    public void logMessage(WhatsAppMessage message, String reply) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        MessageLog log = new MessageLog(
            logId,
            message.getFrom(),
            message.getMessage(),
            reply,
            timestamp
        );

        messageLogs.add(log);

        // Also log to console/file via SLF4J
        logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        logger.info("📩 [{}] New message received", logId);
        logger.info("   FROM    : {}", message.getFrom());
        logger.info("   MESSAGE : {}", message.getMessage());
        logger.info("   REPLY   : {}", reply);
        logger.info("   TIME    : {}", timestamp);
        logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * Returns all stored message logs (most recent first).
     */
    public List<MessageLog> getAllLogs() {
        List<MessageLog> reversed = new ArrayList<>(messageLogs);
        Collections.reverse(reversed);
        return reversed;
    }

    /**
     * Returns the total count of messages processed.
     */
    public int getMessageCount() {
        return messageLogs.size();
    }
}
