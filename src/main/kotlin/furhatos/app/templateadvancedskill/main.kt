package furhatos.app.templateadvancedskill

import furhatos.app.templateadvancedskill.flow.Init
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import personalitytest.PersonalityTest
import personalitytestrunner.PersonalityTestRunnerFromCLI

class MyAdvancedSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    val test = PersonalityTest()
    PersonalityTestRunnerFromCLI().run(test)
    val answers = test.answers
    val behavior = Client().use { it.requestBehavior(answers) }

    print(behavior)

//    Skill.main(args)
}