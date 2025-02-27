package furhatos.app.templateadvancedskill

import org.json.JSONObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

import io.github.cdimascio.dotenv.Dotenv

private fun getApiKey(): String {
    try {
        val dotenv = Dotenv.load()
        return dotenv["OPENAI_API_KEY"]
    } catch (e: Exception) {
        return System.getenv("OPENAI_API_KEY") ?: throw RuntimeException("API key not found")
    }
}

fun askOpenAI(message: String): String {
    val apiKey = getApiKey()
    val url = "https://api.openai.com/v1/chat/completions"

    val client = OkHttpClient()
    val requestBody = JSONObject()
        .put("model", "gpt-4o-mini")
        .put("messages", listOf(
            JSONObject().put("role", "user").put("content", message)
        ))
        .toString()
        .toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "Bearer $apiKey")
        .addHeader("Content-Type", "application/json")
        .post(requestBody)
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw Exception("Unexpected response: ${response.code}")
        val responseBody = response.body?.string()
        val jsonResponse = JSONObject(responseBody)
        val reply = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
        return reply
    }
}