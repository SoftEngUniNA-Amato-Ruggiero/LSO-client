package furhatos.app.templateadvancedskill.personalitytest

object PersonalityTestRunner {

    fun run(test: PersonalityTest) {
        PersonalityTestRunnerFromCli.runFromCli(test)
    }

    //TODO: Define PersonalityTestRunnerFromFurhatApi

    object PersonalityTestRunnerFromCli {
        fun runFromCli(test: PersonalityTest) {
            println(PersonalityTest.TEST_DESCRIPTION)
            for ((attribute, question) in PersonalityTest.QUESTIONS) {
                println(question)
                val score = readlnOrNull()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid input.")
                test.setAnswer(attribute, score)
            }
        }
    }
}