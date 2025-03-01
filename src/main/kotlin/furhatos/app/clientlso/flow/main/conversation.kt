package furhatos.app.clientlso.flow.main


import furhatos.app.clientlso.askOpenAI
import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.personality.Personality
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse

val conversation: (Personality?) -> State = { personality ->
    state(Parent) {
        val personalityModifier = getPersonalityModifier(personality)
        var input: String

        onEntry {
            furhat.listen()
        }

        onResponse {
            input = it.text
            furhat.say(getResponseFromOpenAI(input, personalityModifier))
            reentry()
        }
    }
}

private fun getResponseFromOpenAI(message: String, personalityModifier: String?): String {
    val question =
        if (personalityModifier != null) {
            "$message, $personalityModifier"
        } else {
            message
        }
    return askOpenAI(question)
}

private fun getPersonalityModifier(personality: Personality?): String? {
    if (personality == null) {
        return null
    }

    var personalityModifier = "Senza usare emoji, dai una risposta breve come se fossi una persona"

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
