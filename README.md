# J-Court Service

**A microservice for judges to review offenses and render decisions on penalties**

## Rules

### Penalty Creation & Modification

- A new penalty **can be created** only if **no penalty already exists** for the given offense.
- A penalty **cannot be updated** once it reaches a **terminal status**:
    - `CONFIRMED`
    - `SENT`
- AI-suggested penalties are allowed **only** as `DRAFT`:
    - When no penalty exists for the offense, **or**
    - When the existing penalty is **not** in a terminal status

## Integrated Services

| Service       | Repository / Location                              | Purpose                         |
|---------------|-----------------------------------------------------|---------------------------------|
| J-Notification| https://github.com/Dadamuhames/notification-service | Sending notifications           |
| J-GCP         |                          | GCP integration  |

## Integrated Language Models (LLMs)

| Provider | Model                 | Usage                              |
|----------|-----------------------|------------------------------------|
| Ollama   | `nomic-embed-text`    | Embeddings / vector search         |
| Groq     | `openai/gpt-oss-120b` | Penalty suggestion / text generation |