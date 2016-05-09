package ngo.kiron.language_assessment.app.data;

import java.io.Serializable;
import java.util.List;

/**
 * A test
 */
public interface Test extends Serializable {

    List<Question> getQuestions();
}
