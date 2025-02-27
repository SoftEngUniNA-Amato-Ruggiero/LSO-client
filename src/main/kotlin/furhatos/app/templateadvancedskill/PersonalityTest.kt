package furhatos.app.templateadvancedskill

import java.util.EnumMap

class PersonalityTest {
    enum class Attributes {
        EXTROVERTED, CRITICAL, DEPENDABLE, ANXIOUS, COMPLEX, RESERVED, SYMPATHETIC, DISORGANIZED, CALM, CONVENTIONAL
    }

    companion object {
        const val MINIMUM_SCORE = 1
        const val MAXIMUM_SCORE = 7

        const val TEST_DESCRIPTION = "Indica in un punteggio da $MINIMUM_SCORE a $MAXIMUM_SCORE quanto la tua personalitá è..."

        private val questions: Map<Attributes, String> = EnumMap<Attributes, String>(Attributes::class.java).apply {
            put(Attributes.EXTROVERTED, "Estroversa, esuberante.")
            put(Attributes.CRITICAL, "Polemica, litigiosa.")
            put(Attributes.DEPENDABLE, "Affidabile, auto-disciplinata.")
            put(Attributes.ANXIOUS, "Ansiosa, che si agita facilmente.")
            put(Attributes.COMPLEX, "Aperta alle nuove esperienze, con molti interessi.")
            put(Attributes.RESERVED, "Riservata, silenziosa.")
            put(Attributes.SYMPATHETIC, "Comprensiva, affettuosa.")
            put(Attributes.DISORGANIZED, "Disorganizzata, distratta.")
            put(Attributes.CALM, "Tranquilla, emotivamente stabile.")
            put(Attributes.CONVENTIONAL, "Tradizionalista, abitudinaria.")
        }

        fun getQuestions(): Map<Attributes, String> = questions
    }

    private val answers: MutableMap<Attributes, Int> = EnumMap(Attributes::class.java)

    fun getAnswers(): Map<Attributes, Int> = answers

    //TODO: Run test from robot and delete this method
    fun run() {
        println(TEST_DESCRIPTION)
        for ((attribute, question) in questions) {
            println(question)
            val score = readlnOrNull()?.toIntOrNull() ?: throw IllegalArgumentException("Invalid input.")
            setAnswer(attribute, score)
        }
    }

    fun setAnswer(attribute: Attributes, score: Int) {
        answers[attribute] = validateScore(score)
    }

    private fun validateScore(score: Int): Int {
        require(score in MINIMUM_SCORE..MAXIMUM_SCORE) {
            "Score must be between $MINIMUM_SCORE and $MAXIMUM_SCORE."
        }
        return score
    }

}