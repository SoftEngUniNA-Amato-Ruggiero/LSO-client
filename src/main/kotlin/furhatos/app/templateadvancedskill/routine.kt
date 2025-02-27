package furhatos.app.templateadvancedskill


import furhatos.app.templateadvancedskill.personalitytest.PersonalityTest
import furhatos.app.templateadvancedskill.personalitytest.PersonalityTestRunner

//TODO: Move to flow package, replace println and readln with furhat api calls

fun routine() {
    println("Ciao! Posso farti qualche domanda per conoscerti meglio? [Y/N]")
    val personalityModifier: String

    val input = readln()
    if (input.equals("Y", ignoreCase = true)) {
        val personality = getPersonality()
        personalityModifier = getPersonalityModifier(personality)
    } else {
        println("Va bene, allora risponderó in modo neutrale.")
        personalityModifier = ""
    }

    println(getResponseFromOpenAI("Ciao!", personalityModifier))
    do {
        val message = readln()
        if (message.equals("exit", ignoreCase = true)) {
            break
        }
        val response = getResponseFromOpenAI(message, personalityModifier)
        println(response)
    } while (true)
}

private fun getResponseFromOpenAI(message: String, personalityModifier: String): String {
    return askOpenAI("$message, $personalityModifier")
}

private fun getPersonalityModifier(personality: Personality): String {
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

private fun getPersonality(): Personality {
    val test = PersonalityTest()
    PersonalityTestRunner.run(test) //TODO: Run test from robot
    val personality = getPersonalityFromServer(test.getAnswers())

    for (trait in Personality.Traits.values()) {
        println("${trait.name}: ${personality.get(trait)}")
    }

    return personality
}

private fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val personalityJson = it.getPersonalityJson(answers)
        return Personality(personalityJson)
    }
}