package la.hack.kiron.gov.kironlanguageassessment.data;

import java.util.List;

/**
 * A single question (part of a test)
 */
public interface Question {

    /**
     * Question's id (unique among all questions)
     */
    int getId();

    /**
     * Skill level
     */
    Level getLevel();

    /**
     * Question's content
     */
    Content getContent();

    /**
     * Answers that belong to this question
     */
    List<Answer> getAnswers();
}

