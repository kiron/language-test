package gov.kiron.android.la;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Optional;

import java.util.List;

import gov.kiron.android.la.data.Question;
import gov.kiron.android.la.data.Test;

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = QuestionActivity.class.getSimpleName();

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

        titleTextView = (TextView) findViewById(R.id.title);
        questionTextView = (TextView) findViewById(R.id.question);

        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);

        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previousButton);

        timerTextView = (TextView) findViewById(R.id.timer);

//        new CountDownTimer(TEST_TIME * 60 * 1000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                timerTextView.setText(millisUntilFinished / 1000 / 60 + ":" + millisUntilFinished / 1000 % 60);
//            }
//
//            public void onFinish() {
//                timerTextView.setText("00:00");
//            }
//        }.start();

        this.test = (Test) getIntent().getExtras().get("test");

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

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Question currentQuestion = test.getQuestions().get(index);

                int indexOfCheckedAnswer = getCheckedItemById(checkedId);

                if (indexOfCheckedAnswer >= 0 && indexOfCheckedAnswer <= 3) {
                    currentQuestion.setSelectedAnswer(Optional.of(Integer.valueOf(checkedId)));
                } else {
                    currentQuestion.setSelectedAnswer(Optional.<Integer>absent());
                }

            }
        });
    }

    private int getCheckedItemById(int checkedId) {
        if (checkedId == R.id.radioButton1) {
            return 0;
        } else if (checkedId == R.id.radioButton2) {
            return 1;
        } else if (checkedId == R.id.radioButton3) {
            return 2;
        } else if (checkedId == R.id.radioButton4) {
            return 3;
        } else {
            return -1;
        }
    }

    private void loadQuestion(List<Question> questions, int index) {
        Question question = questions.get(index);

        titleTextView.setText("Question " + (index + 1) + " of " + questions.size());
        questionTextView.setText(question.getContent().getContent());

        radioButton1.setText(question.getAnswers().get(0).getContent());
        radioButton2.setText(question.getAnswers().get(1).getContent());
        radioButton3.setText(question.getAnswers().get(2).getContent());
        radioButton4.setText(question.getAnswers().get(3).getContent());

        // Set selected answer (if there is one)
        Question currentQuestion = test.getQuestions().get(index);

        if (currentQuestion.getSelectedAnswer().isPresent()) {
            answerGroup.check(currentQuestion.getSelectedAnswer().get());
        } else {
            answerGroup.clearCheck();
        }

        nextButton.setEnabled(index < questions.size() - 1);
        previousButton.setEnabled(index > 0);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(TestTimeCountDownService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        //stopService(new Intent(this, TestTimeCountDownService.class));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long remainingTime = intent.getLongExtra("remainingTime", 0);
            //Log.i(TAG, "Countdown seconds remaining: " +  millisUntilFinished / 1000);
            String min = String.format("%02d", remainingTime / 60);
            String sec = String.format("%02d", remainingTime % 60);
            timerTextView.setText(min + ":" + sec);

        }
    }
}
