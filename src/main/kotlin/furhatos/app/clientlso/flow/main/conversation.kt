package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.getApiKey
import furhatos.app.clientlso.personality.Personality
import furhatos.app.clientlso.personality.Personality.Traits
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Goodbye

import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest

/** Open AI API Key **/
private val serviceKey = getApiKey()

val TRAIT_DESCRIPTION: Map<Traits, Pair<String, String>> = mapOf(
    Traits.EXTROVERSION to ("timida," to "estroversa, "),
    Traits.AGREEABLENESS to ("scortese, " to "simpatica, "),
    Traits.CONSCIENTIOUSNESS to ("distratta, " to "affidabile, "),
    Traits.EMOTIONAL_STABILITY to ("instabile, " to "stabile, "),
    Traits.OPENNESS to ("noiosa." to "creativa.")
)

var personalityModifier: String? = null

val openAI: SimpleOpenAI = SimpleOpenAI.builder()
    .apiKey(serviceKey)
    .build()

val conversation: (Personality?) -> State = { personality ->
    state(Parent) {
        onEntry {
            if (personality != null) {
                personalityModifier = getPersonalityModifier(personality)
            }

            furhat.say("Di cosa vogliamo parlare oggi?")
            furhat.listen()
        }

        onReentry {
            furhat.listen()
        }

        onResponse<Goodbye> {
            furhat.say("Va bene, ciao!")
            goto(Idle)
        }

        onResponse {
            furhat.say(async = true) {
                +Gestures.GazeAway
                random {
                    +"Ah."
                    +"Allora..."
                    +""
                }
            }

            val robotResponse = call {
                getDialogCompletion()
            } as String

            print(robotResponse)
            val (gesture, response) = getGesture(robotResponse)
            furhat.ask {
                +gesture
                +(response ?: "Scusa, non ho capito. Puoi ripetere?")
            }
            reentry()
        }

        onNoResponse {
            furhat.say("Mi dispiace, non ho sentito nulla.")
            reentry()
        }

        onResponse<Goodbye> {
            furhat.say("Arrivederci!")
            goto(Idle)
        }
    }
}

fun getDialogCompletion(): String {
    val chatRequestBuilder = ChatRequest.builder()
        .model("gpt-4o-mini")
        .message(
            ChatMessage.SystemMessage.of(
                personalityModifier ?: "Rispondi usando emoji in modo creativo."
            )
        )

    Furhat.dialogHistory.all.takeLast(10).forEach {
        when (it) {
            is DialogHistory.ResponseItem -> {
                chatRequestBuilder.message(ChatMessage.UserMessage.of(it.response.text))
            }

            is DialogHistory.UtteranceItem -> {
                chatRequestBuilder.message(ChatMessage.AssistantMessage.of(it.toText()))
            }
        }
    }

    val futureChat = openAI.chatCompletions().create(chatRequestBuilder.build())
    val chatResponse = futureChat.join()
    return chatResponse.firstContent().toString()
}

private fun getPersonalityModifier(personality: Personality): String? {
    val traitString = StringBuilder()
    for (trait in TRAIT_DESCRIPTION.keys) {
        traitString.append(getPersonalityTraitScore(personality, trait))
    }
    return "Usa emoji in modo creativo e rispondi brevemente come una persona $traitString"
}

private fun getPersonalityTraitScore(personality: Personality, trait: Personality.Traits): String {
    val (negative, positive) = TRAIT_DESCRIPTION[trait] ?: error("Trait not found")
    return if (personality.get(trait) < 4) negative else positive
}
