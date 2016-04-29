package la.hack.kiron.gov.kironlanguageassessment.data;

import android.media.Image;

import java.util.List;

/**
 * Created by Christian on 29.04.2016.
 */
public class DataFactory {

    public static Answer createAnswer(final int id, final String text) {
        return new Answer() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public String getContent() {
                return text;
            }
        };
    }

    public static Content createContent(final String text) {
        return new Content() {
            @Override
            public String getContent() {
                return text;
            }

            @Override
            public boolean hasPicture() {
                return false;
            }

            @Override
            public Image getPicture() {
                return null;
            }
        };
    }

    public static Question createQuestion(final int id, final Content content, final List<Answer> answers) {
        return new Question() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public Level getLevel() {
                return null;
            }

            @Override
            public Content getContent() {
                return content;
            }

            @Override
            public List<Answer> getAnswers() {
                return answers;
            }
        };
    }
}
