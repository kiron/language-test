package gov.kiron.android.la.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.common.base.Optional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static Content createContent(final String text, final Optional<String> pictureUrl) {
        return new Content() {
            @Override
            public String getContent() {
                return text;
            }

            @Override
            public boolean hasPicture() {
                return pictureUrl.isPresent();
            }

            @Override
            public Bitmap getPicture() {

                URL url = null;
                try {
                    url = new URL(pictureUrl.get());
                    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public static Question createQuestion(final int id, final Content content, final List<Answer> answers) {
        return new Question() {

            Optional<Character> selectedAnswer = Optional.absent();

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

            public void setSelectedAnswer(Optional<Character> index) {
                this.selectedAnswer = index;
            }

            @Override
            public Optional<Character> getSelectedAnswer() {
                return selectedAnswer;
            }


        };
    }
}
