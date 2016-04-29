package la.hack.kiron.gov.kironlanguageassessment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.webkit.JsResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import la.hack.kiron.gov.kironlanguageassessment.data.Answer;
import la.hack.kiron.gov.kironlanguageassessment.data.Content;
import la.hack.kiron.gov.kironlanguageassessment.data.DataFactory;
import la.hack.kiron.gov.kironlanguageassessment.data.Level;
import la.hack.kiron.gov.kironlanguageassessment.data.Question;
import la.hack.kiron.gov.kironlanguageassessment.data.Test;

/**
 * Created by Christian on 29.04.2016.
 */
public class TestFactory {


    public static Test createTest(Level level) {

        String json = getJSON();

        final List<Question> questions;

        try {
            JSONArray jsonQuestions = new JSONArray(json);

            questions = new ArrayList<>(jsonQuestions.length());

            for(int i=0; i<jsonQuestions.length(); i++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);

                int questionId = jsonQuestion.getInt("id");
                String questionContent = jsonQuestion.getString("content");

                JSONArray jsonAnswers = jsonQuestion.getJSONArray("answers");

                List<Answer> answers = new ArrayList<>(jsonAnswers.length());

                for(int j=0; j<jsonAnswers.length(); j++) {
                    JSONObject jsonAnswer = jsonAnswers.getJSONObject(j);
                    int answerId = jsonAnswer.getInt("id");
                    String answerContent = jsonAnswer.getString("content");
                    answers.add(DataFactory.createAnswer(answerContent));
                }

                Content content = DataFactory.createContent(questionContent);

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
            e.printStackTrace();
        }

        return null;
    }

    private static String getJSON() {
        return "[\n" +
                "  {\n" +
                "    \"id\": 11,\n" +
                "    \"content\": \"Hi! I ______ John. I study at Kiron.\",\n" +
                "    \"answers\": [\n" +
                "      {\n" +
                "        \"id\": 16,\n" +
                "        \"content\": \"are\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 17,\n" +
                "        \"content\": \"am\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 18,\n" +
                "        \"content\": \"is\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 19,\n" +
                "        \"content\": \"name\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 12,\n" +
                "    \"content\": \"This is Florian. He is ______ best friend in Germany.\",\n" +
                "    \"answers\": [\n" +
                "      {\n" +
                "        \"id\": 20,\n" +
                "        \"content\": \"mine\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 21,\n" +
                "        \"content\": \"my\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 22,\n" +
                "        \"content\": \"mie\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 23,\n" +
                "        \"content\": \"close\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 13,\n" +
                "    \"content\": \"This _____(1)  John. _____(2) studies at Kiron.\",\n" +
                "    \"answers\": [\n" +
                "      {\n" +
                "        \"id\": 24,\n" +
                "        \"content\": \"(1) is / (2) she\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 25,\n" +
                "        \"content\": \"(1) are / (2) he\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 26,\n" +
                "        \"content\": \"(1) is / (2) he\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 27,\n" +
                "        \"content\": \"(1) are / (2) they\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 14,\n" +
                "    \"content\": \"A: Do you live in Germany?\\r\\n\\r\\nB: No, I ______ live in Germany.\",\n" +
                "    \"answers\": [\n" +
                "      {\n" +
                "        \"id\": 28,\n" +
                "        \"content\": \"not\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 29,\n" +
                "        \"content\": \"can\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 30,\n" +
                "        \"content\": \"am\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 31,\n" +
                "        \"content\": \"don\\u0027t\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 15,\n" +
                "    \"content\": \"A: Today is John’s party!\\r\\n\\r\\nB: Yes, great, I will see you there _____ 3 o’clock!\",\n" +
                "    \"answers\": [\n" +
                "      {\n" +
                "        \"id\": 32,\n" +
                "        \"content\": \"in\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 33,\n" +
                "        \"content\": \"on\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 34,\n" +
                "        \"content\": \"at\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 35,\n" +
                "        \"content\": \"of\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]\n" +
                "\n" +
                "\n";
    }

    private static String loadJSONFromAsset(InputStream inputStream) {
        String json = null;
        try {
            int size = inputStream.available();

            byte[] buffer = new byte[size];

            inputStream.read(buffer);

            inputStream.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            Log.e(TestFactory.class.getSimpleName(), "Error", ex);
            return null;
        }
        return json;

    }
}
