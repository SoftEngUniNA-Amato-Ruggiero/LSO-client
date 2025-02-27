package furhatos.app.templateadvancedskill

import furhatos.app.templateadvancedskill.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class MyAdvancedSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    // Skill.main(args)

    // TODO: Move the following code to some flow instead of main, replace println and readln with calls to furhat api, and uncomment line above
    println("Ciao! Posso farti qualche domanda per conoscerti meglio? [Y/N]")
    val personalityModifier: String

    val input = readln()
    if (input == "Y") {
        val personality = getPersonality()
        personalityModifier = setPersonalityModifier(personality)
    } else {
        println("Va bene, allora risponderó in modo neutrale.")
        personalityModifier = ""
    }

    println(askRobot("Ciao!", personalityModifier))
    do {
        val message = readln()
        val response = askRobot(message, personalityModifier)
        println(response)
    } while (true)
}

private fun askRobot(message: String, personalityModifier: String): String {
    return askOpenAI("$message, $personalityModifier")
}

private fun setPersonalityModifier(personality: Personality): String {
    var personalityModifier = "Senza usare emoji, dai una breve risposta "

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

fun getPersonality(): Personality {
    val test = PersonalityTest()
    test.run() //TODO: Run test from robot
    val personality = getPersonalityFromServer(test.getAnswers())

    for (trait in Personality.Traits.values()) {
        println("${trait.name}: ${personality.get(trait)}")
    }

    return personality
}

fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val behaviorJson = it.requestBehavior(answers)
        return Personality(behaviorJson)
    }
}