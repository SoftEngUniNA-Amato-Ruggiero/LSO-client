package furhatos.app.clientlso

import io.github.cdimascio.dotenv.Dotenv

fun getApiKey(): String {
    try {
        return Dotenv.load()["OPENAI_API_KEY"]
    } catch (e: Exception) {
        try {
            return System.getenv("OPENAI_API_KEY")
        } catch (e: Exception) {
            throw RuntimeException("API key not found")
        }
    }
}