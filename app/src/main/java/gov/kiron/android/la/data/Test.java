package gov.kiron.android.la.data;

import java.io.Serializable;
import java.util.List;

/**
 * A test
 */
public interface Test extends Serializable {

    List<Question> getQuestions();
}
