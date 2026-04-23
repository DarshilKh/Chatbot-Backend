# 💬 WhatsApp Chatbot Backend Simulation

A Spring Boot REST API that simulates a WhatsApp chatbot webhook. Built as part of an internship assignment.

## 📸 Screenshots

![Chat Demo](screenshots/wp%201.jpeg)
![API Tester](screenshots/wp%202.jpeg)
![Dashboard](screenshots/wp%203.jpeg)

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.2.3 |
| Build Tool | Maven |
| Testing | JUnit 5 + Spring Boot Test |
| Deployment | Render / Docker / Local |

---

## 📁 Project Structure

```
whatsapp-chatbot/
├── src/
│   ├── main/java/com/chatbot/whatsapp/
│   │   ├── WhatsAppChatbotApplication.java   ← Entry point
│   │   ├── controller/
│   │   │   └── WebhookController.java        ← REST endpoints
│   │   ├── model/
│   │   │   ├── WhatsAppMessage.java          ← Request model
│   │   │   ├── ChatbotResponse.java          ← Response model
│   │   │   └── MessageLog.java               ← Log model
│   │   └── service/
│   │       └── ChatbotService.java           ← Business logic
│   └── resources/
│       └── application.properties
├── Dockerfile
├── pom.xml
└── README.md
```

---

## ▶️ Running Locally

### Prerequisites
- Java 21+
- Maven 3.8+

### Steps

```bash
# 1. Clone the repo
git clone https://github.com/YOUR_USERNAME/whatsapp-chatbot.git
cd whatsapp-chatbot

# 2. Build the project
mvn clean package

# 3. Run it
java -jar target/whatsapp-chatbot.jar
```

Server starts at: `http://localhost:8080`

---

## 🔌 API Endpoints

### `POST /webhook` — Receive a message

**Request Body:**
```json
{
  "from": "+91-9876543210",
  "message": "Hi",
  "timestamp": "2026-03-31T10:00:00",
  "messageId": "msg_001"
}
```

**Response:**
```json
{
  "status": "success",
  "to": "+91-9876543210",
  "reply": "Hello! 👋 How can I help you today?",
  "originalMessage": "Hi",
  "timestamp": "2026-03-31 10:00:00"
}
```

---

### `GET /webhook/health` — Health Check

```json
{
  "status": "UP ✅",
  "service": "WhatsApp Chatbot Backend",
  "messagesProcessed": 5,
  "version": "1.0.0"
}
```

---

### `GET /webhook/logs` — Message Logs

Returns all logged messages (most recent first).

---

### `GET /webhook/stats` — Bot Statistics

Returns total messages processed and supported commands.

---

## 🤖 Predefined Replies

| User Says | Bot Replies |
|-----------|------------|
| Hi / Hello / Hey | Hello! 👋 How can I help you today? |
| Bye / Goodbye | Goodbye! 👋 Have a great day! |
| Help | Lists available commands |
| Hours / Timing | Business hours |
| Contact / Email | Contact information |
| Thank you | You're welcome! 😊 |
| Price / Cost | Redirects to pricing contact |
| Anything else | Friendly fallback message |

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🐳 Docker Deployment

```bash
# Build Docker image
docker build -t whatsapp-chatbot .

# Run container
docker run -p 8080:8080 whatsapp-chatbot
```

---

## 📬 Sample cURL Commands

```bash
# Send "Hi"
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from":"+91-9876543210","message":"Hi","timestamp":"2026-03-31T10:00:00","messageId":"msg_001"}'

# Send "Bye"
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from":"+91-9876543210","message":"Bye","timestamp":"2026-03-31T10:00:00","messageId":"msg_002"}'

# Health check
curl http://localhost:8080/webhook/health

# View logs
curl http://localhost:8080/webhook/logs
```

---

## 👨‍💻 Author

Darshil Khandelwal

Built with ❤️ using Java & Spring Boot for internship assignment submission.
