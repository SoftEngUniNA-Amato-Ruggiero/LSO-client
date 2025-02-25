package personalitytest;

import java.util.EnumMap;
import java.util.Map;

public class PersonalityTest {
    public enum Attributes {
        EXTROVERTED, CRITICAL, DEPENDABLE, ANXIOUS, COMPLEX, RESERVED, SYMPATHETIC, DISORGANIZED, CALM, CONVENTIONAL
    }

    static final int MINIMUM_SCORE = 1;
    static final int MAXIMUM_SCORE = 7;

    public static final String TEST_DESCRIPTION = "Indicare in un punteggio da " +
            MINIMUM_SCORE + " a " + MAXIMUM_SCORE +
            " quanto i seguenti tratti di personalitá coincidono con la propria.";

    private static final Map<Attributes, String> questions = new EnumMap<>(Attributes.class);

    static {
        questions.put(Attributes.EXTROVERTED, "Estroversa, esuberante.");
        questions.put(Attributes.CRITICAL, "Polemica, litigiosa.");
        questions.put(Attributes.DEPENDABLE, "Affidabile, auto-disciplinata.");
        questions.put(Attributes.ANXIOUS, "Ansiosa, che si agita facilmente.");
        questions.put(Attributes.COMPLEX, "Aperta alle nuove esperienze, con molti interessi.");
        questions.put(Attributes.RESERVED, "Riservata, silenziosa.");
        questions.put(Attributes.SYMPATHETIC, "Comprensiva, affettuosa.");
        questions.put(Attributes.DISORGANIZED, "Disorganizzata, distratta.");
        questions.put(Attributes.CALM, "Tranquilla, emotivamente stabile.");
        questions.put(Attributes.CONVENTIONAL, "Tradizionalista, abitudinaria.");
    }

    private final Map<Attributes, Integer> answers = new EnumMap<>(Attributes.class);

    public static Map<Attributes, String> getQuestions() { return questions; }
    public Map<Attributes, Integer> getAnswers() {
        return answers;
    }

    public void setAnswer(Attributes attribute, Integer score) throws IllegalArgumentException {
        answers.put(attribute, validateScore(score));
    }

    private Integer validateScore(Integer score) throws IllegalArgumentException {
        if (score < MINIMUM_SCORE || score > MAXIMUM_SCORE) {
            throw new IllegalArgumentException("Score must be between " + MINIMUM_SCORE +  " and " + MAXIMUM_SCORE + ".");
        }
        return score;
    }
}
