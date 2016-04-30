package la.hack.kiron.gov.kironlanguageassessment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Optional;

import java.util.List;

import la.hack.kiron.gov.kironlanguageassessment.data.Answer;
import la.hack.kiron.gov.kironlanguageassessment.data.Question;
import la.hack.kiron.gov.kironlanguageassessment.data.Test;

public class QuestionActivity extends AppCompatActivity {

    private static final int TEST_TIME = 50; /* minutes */

    private int index;
    private Test test;

    private RadioGroup answerGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private TextView titleTextView;
    private TextView questionTextView;
    private Button nextButton;
    private Button previousButton;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        titleTextView = (TextView)findViewById(R.id.title);
        questionTextView = (TextView)findViewById(R.id.question);

        answerGroup = (RadioGroup)findViewById(R.id.answerGroup);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton)findViewById(R.id.radioButton4);

        nextButton = (Button)findViewById(R.id.next_button);
        previousButton = (Button)findViewById(R.id.previousButton);

        timerTextView = (TextView)findViewById(R.id.timer);

        new CountDownTimer(TEST_TIME * 60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(millisUntilFinished / 1000 / 60 + ":" + millisUntilFinished / 1000 % 60);
            }

            public void onFinish() {
                timerTextView.setText("00:00");
            }
        }.start();

        this.test = (Test)getIntent().getExtras().get("test");

        loadQuestion(test.getQuestions(), 0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                index++;
                loadQuestion(test.getQuestions(), index);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                index--;
                loadQuestion(test.getQuestions(), index);
            }
        });

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Question currentQuestion = test.getQuestions().get(index);

                int indexOfCheckedAnswer = getCheckedItemById(checkedId);

                if(indexOfCheckedAnswer >=0 && indexOfCheckedAnswer <= 3) {
                    currentQuestion.setSelectedAnswer(Optional.of(Integer.valueOf(checkedId)));
                } else {
                    currentQuestion.setSelectedAnswer(Optional.<Integer>absent());
                }

            }
        });
    }

    private int getCheckedItemById(int checkedId) {
        if(checkedId == R.id.radioButton1) {
            return 0;
        } else if(checkedId == R.id.radioButton2) {
            return 1;
        } else if(checkedId == R.id.radioButton3) {
            return 2;
        } else if(checkedId == R.id.radioButton4) {
            return 3;
        } else {
            return -1;
        }
    }

    private void loadQuestion(List<Question> questions, int index) {
        Question question = questions.get(index);

        titleTextView.setText("Question " + (index+1) + " of " + questions.size());
        questionTextView.setText(question.getContent().getContent());

        radioButton1.setText(question.getAnswers().get(0).getContent());
        radioButton2.setText(question.getAnswers().get(1).getContent());
        radioButton3.setText(question.getAnswers().get(2).getContent());
        radioButton4.setText(question.getAnswers().get(3).getContent());

        // Set selected answer (if there is one)
        Question currentQuestion = test.getQuestions().get(index);

        if(currentQuestion.getSelectedAnswer().isPresent()) {
            answerGroup.check(currentQuestion.getSelectedAnswer().get());
        } else {
            answerGroup.clearCheck();
        }

        nextButton.setEnabled(index < questions.size()-1);
        previousButton.setEnabled(index > 0);
    }
}
