package furhatos.app.clientlso

fun getApiKey(): String {
    try {
        return System.getenv("OPENAI_API_KEY")
    } catch (e: Exception) {
        throw RuntimeException("API key not found")
    }
}