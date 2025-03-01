package furhatos.app.clientlso.flow.main

import furhatos.app.clientlso.Client
import furhatos.app.clientlso.flow.Parent
import furhatos.app.clientlso.personality.Personality
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.State
import furhatos.app.clientlso.personality.PersonalityTest
import furhatos.flow.kotlin.furhat

val personalityTestRunner: State = state(Parent) {
    onEntry {
        val test = PersonalityTest()
        furhat.say(PersonalityTest.TEST_DESCRIPTION)
        for ((attribute, question) in PersonalityTest.QUESTIONS) {
            val answer = furhat.ask(question)
            val score = answer.toString().toInt()
            test.setAnswer(attribute, score)
        }
        val personality = getPersonalityFromServer(test.getAnswers())
        goto(conversation(personality))
    }
}

private fun getPersonalityFromServer(answers: Map<PersonalityTest.Attributes, Int>): Personality {
    Client().use {
        val personalityJson = it.getPersonalityJson(answers)
        return Personality(personalityJson)
    }
}