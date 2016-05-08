package gov.kiron.android.la.data;

import android.os.StrictMode;
import android.util.Log;

import com.google.common.base.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 29.04.2016.
 */
public class TestFactory {

    private static final String TAG = TestFactory.class.getSimpleName();

    private static String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }




    private static String getJSONFromServer() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL("http://athen052.server4you.de:8080/questions");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String json = "";
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            json = readStream(in);
        } catch (IOException e) {
            Log.e(TAG, "Error while retrieving data from server.", e);
        }
        finally {
            urlConnection.disconnect();
        }
        return json;
    }

    public static Test createTest(Level level) {

        String json = null;
        try {
            json = getJSONFromServer();
        } catch (IOException e) {
            Log.e(TAG, "Error while retrieving data from server.", e);
        }

        final List<Question> questions;

        try {
            JSONArray jsonQuestions = new JSONArray(json);

            questions = new ArrayList<>(jsonQuestions.length());

            for(int i=0; i<jsonQuestions.length(); i++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);

                int questionId = jsonQuestion.getInt("id");
                String questionContent = jsonQuestion.getString("content");

                String pictureURL = jsonQuestion.getString("pictureURL");

                if(pictureURL.equals("null")) {
                    pictureURL = null;
                }


                JSONArray jsonAnswers = jsonQuestion.getJSONArray("answers");

                List<Answer> answers = new ArrayList<>(jsonAnswers.length());

                for(int j=0; j<jsonAnswers.length(); j++) {
                    JSONObject jsonAnswer = jsonAnswers.getJSONObject(j);
                    int answerId = jsonAnswer.getInt("id");
                    String answerContent = jsonAnswer.getString("content");
                    answers.add(DataFactory.createAnswer(answerId, answerContent));
                }

                Content content = DataFactory.createContent(questionContent, Optional.fromNullable(pictureURL));

                Question question = DataFactory.createQuestion(questionId, content, answers);

                questions.add(question);
            }

            return new Test() {
                @Override
                public List<Question> getQuestions() {
                    return questions;
                }
            };

        } catch (JSONException e) {
            Log.e(TAG, "Error while retrieving the test data.", e);
        }

        return null;
    }

    public static void writeJSONToServer(Test test, String username) throws IOException {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Question q : test.getQuestions()) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(q.getId());
            sb.append(":");
            sb.append(q.getSelectedAnswer().or('-'));
        }
        URL url = new URL("http://athen052.server4you.de:8080/submit/" +
                sb.toString() +
                "?username=" + username);
        Log.i("DEBUG", "write to JSON: " + sb.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        String json = readStream(in);
        urlConnection.disconnect();
    }
}
