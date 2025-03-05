package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.getApiKey
import furhatos.app.clientlso.personality.Personality
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Goodbye

import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest

/** Open AI API Key **/
private val serviceKey = getApiKey()

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
            } as String?
            furhat.ask(robotResponse ?: "Scusa, non ho capito. Puoi ripetere?")
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
        .message(ChatMessage.SystemMessage.of(personalityModifier ?: "Rispondi brevemente"))

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

private fun getPersonalityModifier(personality: Personality): String {
    var personalityModifier = "Rispondi brevemente come una persona"

    if (personality.get(Personality.Traits.EXTROVERSION) < 4) {
        personalityModifier += " timida,"
    } else {
        personalityModifier += " estroversa,"
    }

    if (personality.get(Personality.Traits.AGREEABLENESS) < 4) {
        personalityModifier += " scortese, "
    } else {
        personalityModifier += " simpatica, "
    }

    if (personality.get(Personality.Traits.CONSCIENTIOUSNESS) < 4) {
        personalityModifier += " distratta, "
    } else {
        personalityModifier += " affidabile, "
    }

    if (personality.get(Personality.Traits.EMOTIONAL_STABILITY) < 4) {
        personalityModifier += " instabile, "
    } else {
        personalityModifier += " stabile, "
    }

    if (personality.get(Personality.Traits.OPENNESS) < 4) {
        personalityModifier += " noiosa."
    } else {
        personalityModifier += " creativa."
    }
    return personalityModifier
}
