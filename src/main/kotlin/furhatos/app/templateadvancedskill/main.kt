package furhatos.app.templateadvancedskill

import furhatos.app.templateadvancedskill.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill
import personalitytest.PersonalityTest
import personalitytestrunner.PersonalityTestRunnerFromCLI

class MyAdvancedSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    // Skill.main(args)
    // TODO: Move the following methods to some flow instead of main, and uncomment line above
    val personality = startingFlow()

    //TODO: Implement behavior based on personality
    println("Ciao! Sono un robot. Beep-boop.")
    val message = "Ciao robot!"
    println(getResponseFromRobot(personality, message))
}

private fun getResponseFromRobot(personality: Personality, message: String): String {
    //Extrovertion < 4: talk less
    //Agreeableness < 4: be aggressive
    //Conscientiousness < 4: change subject often without user input
    //Emotional Stability < 4: be apprehensive, ask user not to leave
    //Openness < 4: discourage any idea of the user

    val personalityModifier: String
    if (personality.get(Personality.Traits.EXTROVERSION) < 4) {
        personalityModifier = "Rispondi in modo timido"
    } else {
        personalityModifier = "Dai una risposta molto socievole"
    }
    return askOpenAI("${message} ${personalityModifier}")
}

fun startingFlow(): Personality {
    val test = PersonalityTest()
    PersonalityTestRunnerFromCLI().run(test) //TODO: Run test from robot
    val personality = getPersonalityFromServer(test.answers)

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