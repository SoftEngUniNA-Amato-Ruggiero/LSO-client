package furhatos.app.clientlso.flow.main

import furhatos.gestures.Gesture
import furhatos.gestures.Gestures

fun getGesture(openAIResponse: String?): Pair<Gesture, String?> {
    var response: String? = openAIResponse
    if (openAIResponse != null) {
        response = removeEmojis(openAIResponse)
    }

    return when {
        openAIResponse.isNullOrBlank() -> {
            Pair(Gestures.Blink, response)
        }

        //Happyness
        openAIResponse.contains("\uD83D\uDE00")
                || openAIResponse.contains("\uD83D\uDE03") -> {
            Pair(Gestures.Smile, response)
        }

        //Sadness
        openAIResponse.contains("\uD83D\uDE41") -> {
            Pair(Gestures.BrowFrown, response)
        }

        //TODO: Anger

        //TODO: Shy

        else -> {
            Pair(Gestures.Blink, response)
        }
    }
}

fun removeEmojis(input: String): String {
    val emojiPattern = "[\\p{So}\\p{Cn}]".toRegex()
    return input.replace(emojiPattern, "")
}