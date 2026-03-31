package com.chatbot.whatsapp;

import com.chatbot.whatsapp.service.ChatbotService;
import com.chatbot.whatsapp.model.WhatsAppMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WhatsAppChatbotApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ChatbotService chatbotService;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    @DisplayName("Context loads successfully")
    void contextLoads() {
        assertThat(chatbotService).isNotNull();
    }

    @Test
    @DisplayName("POST /webhook - Hi should return Hello")
    void testHiReturnsHello() {
        WhatsAppMessage msg = new WhatsAppMessage("+91-9999999999", "Hi", "2026-03-31T10:00:00", "msg_001");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/webhook", msg, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Hello");
    }

    @Test
    @DisplayName("POST /webhook - Bye should return Goodbye")
    void testByeReturnsGoodbye() {
        WhatsAppMessage msg = new WhatsAppMessage("+91-9999999999", "Bye", "2026-03-31T10:00:00", "msg_002");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/webhook", msg, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Goodbye");
    }

    @Test
    @DisplayName("POST /webhook - Unknown message returns fallback")
    void testUnknownMessageReturnsFallback() {
        WhatsAppMessage msg = new WhatsAppMessage("+91-9999999999", "xyzunknown", "2026-03-31T10:00:00", "msg_003");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/webhook", msg, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("didn't understand");
    }

    @Test
    @DisplayName("GET /webhook/health - should return UP")
    void testHealthEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/webhook/health", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("UP");
    }

    @Test
    @DisplayName("GET /webhook/logs - should return log list")
    void testLogsEndpoint() {
        // Send a message first
        WhatsAppMessage msg = new WhatsAppMessage("+91-9999999999", "Hi", "2026-03-31T10:00:00", "msg_004");
        restTemplate.postForEntity(baseUrl + "/webhook", msg, String.class);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/webhook/logs", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("logs");
    }

    @Test
    @DisplayName("ChatbotService - reply generation logic")
    void testReplyGeneration() {
        assertThat(chatbotService.generateReply("hi")).contains("Hello");
        assertThat(chatbotService.generateReply("Hi")).contains("Hello");
        assertThat(chatbotService.generateReply("bye")).contains("Goodbye");
        assertThat(chatbotService.generateReply("help")).contains("help");
        assertThat(chatbotService.generateReply("hours")).contains("business hours");
        assertThat(chatbotService.generateReply("gibberish_xyz")).contains("didn't understand");
    }
}
