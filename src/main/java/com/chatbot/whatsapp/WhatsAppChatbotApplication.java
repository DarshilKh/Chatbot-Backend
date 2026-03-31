package com.chatbot.whatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhatsAppChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatsAppChatbotApplication.class, args);
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   WhatsApp Chatbot Backend is Running! 🚀        ║");
        System.out.println("║   POST http://localhost:8080/webhook              ║");
        System.out.println("║   GET  http://localhost:8080/webhook/health       ║");
        System.out.println("║   GET  http://localhost:8080/webhook/logs         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
