package furhatos.app.clientlso.flow.main

import furhatos.gestures.Gesture
import furhatos.gestures.Gestures

val happyEmojis = setOf(
    "😊", "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😇", "🙂", "😍", "🥰", "😎", "😋"
)

val sadEmojis = setOf(
    "☹️", "🙁", "😟", "😞", "😢", "😭", "😿", "😔", "😕", "😔"
)

val angryEmojis = setOf(
    "😠", "😡", "🤬", "👿", "💢", "😤", "😾"
)

val shyEmojis = setOf(
    "😣", "😖", "😫", "😩", "😳", "😶", "🙈", "🙉", "🙊", "😬", "😅", "🤭"
)

val disgustedEmojis = setOf(
    "🤢", "🤮", "😷", "😵", "😵‍💫", "😷", "🤒", "🤕"
)

val surprisedEmojis = setOf(
    "😲", "😳", "😮", "😯", "😧", "😨", "😱", "😵", "🤯", "😲"
)

fun getGesture(openAIResponse: String?): Pair<Gesture, String?> {
    var response: String? = openAIResponse
    if (openAIResponse != null) {
        response = removeEmojis(openAIResponse)
    }

    return when {
        openAIResponse.isNullOrBlank() -> {
            Pair(Gestures.Blink, response)
        }

        happyEmojis.any{openAIResponse.contains(it)} -> {
            Pair(Gestures.Smile, response)
        }

        sadEmojis.any{openAIResponse.contains(it)} -> {
            Pair(Gestures.ExpressSad, response)
        }

        angryEmojis.any{openAIResponse.contains(it)} -> {
            Pair(Gestures.ExpressAnger, response)
        }

        shyEmojis.any{openAIResponse.contains(it)} -> {
            Pair(Gestures.ExpressFear, response)
        }

        disgustedEmojis.any{openAIResponse.contains(it)} -> {
            Pair(Gestures.ExpressDisgust, response)
        }

        else -> {
            Pair(Gestures.Blink, response)
        }
    }
}

fun removeEmojis(input: String): String {
    val emojiPattern = "[\\p{So}\\p{Cn}]".toRegex()
    return input.replace(emojiPattern, "")
}