package la.hack.kiron.gov.kironlanguageassessment.data;

import java.util.List;

/**
 * Created by jules on 29.04.16.
 */
public class DummyQuestion implements Question {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Level getLevel() {
        return null;
    }

    @Override
    public Content getContent() {
        return null;
    }

    @Override
    public List<Answer> getAnswers() {
        return null;
    }
}
