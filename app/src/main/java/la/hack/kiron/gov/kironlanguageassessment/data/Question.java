package la.hack.kiron.gov.kironlanguageassessment.data;

import java.util.List;

/**
 * Created by Christian on 29.04.2016.
 */
public interface Question {

    int getId();

    Level getLevel();

    Content getContent();

    List<Answer> getAnswers();
}

