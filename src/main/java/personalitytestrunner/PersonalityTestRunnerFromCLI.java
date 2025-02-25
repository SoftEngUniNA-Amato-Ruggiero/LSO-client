package personalitytestrunner;

import personalitytest.PersonalityTest;

import java.lang.System.Logger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PersonalityTestRunnerFromCLI implements PersonalityTestRunner {
    private static final Logger logger = System.getLogger(PersonalityTestRunnerFromCLI.class.getName());

    @Override
    public void run(PersonalityTest test) {
        runFromCLI(test);
    }

    public static void runFromCLI(PersonalityTest test) {
        try (Scanner scanner = new Scanner(System.in)) {
            gatherResponsesFromCLI(test, scanner);
        } catch (IllegalArgumentException e) {
            logger.log(Logger.Level.ERROR, e.getMessage(), e);
        } catch (InputMismatchException e){
            logger.log(Logger.Level.ERROR, "Token does not match the Integer regular expression, or is out of range.", e);
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Unexpected error occurred.");
            logger.log(Logger.Level.ERROR, e.getMessage(), e);
        }
    }

    private static void gatherResponsesFromCLI(PersonalityTest test, Scanner scanner) {
        System.out.println(PersonalityTest.TEST_DESCRIPTION);
        for (PersonalityTest.Attributes attribute : PersonalityTest.Attributes.values()) {
            System.out.println(PersonalityTest.getQuestions().get(attribute));
            test.setAnswer(attribute, scanner.nextInt());
        }
    }
}
