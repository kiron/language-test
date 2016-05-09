package ngo.kiron.language_assessment.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Optional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ngo.kiron.language_assessment.app.data.Question;
import ngo.kiron.language_assessment.app.data.Test;
import ngo.kiron.language_assessment.app.data.TestFactory;

public class QuestionActivity extends AppCompatActivity {

    private static final String TAG = QuestionActivity.class.getSimpleName();

    public static final String SAVED_INDEX = "saved_index";
    public static final String SAVED_TEST = "saved_test";
    public static final String SAVED_USERNAME = "saved_username";
    private int index;
    private Test test;

    private RadioGroup answerGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private TextView titleTextView;
    private TextView questionTextView;
    private ImageView questionImageView;
    private Button nextButton;
    private Button previousButton;
    private TextView timerTextView;

    private Map<Character,Integer> answerRadioButtonMap;
    private String username;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.index = savedInstanceState.getInt(SAVED_INDEX);
        this.test = (Test) savedInstanceState.get(SAVED_TEST);
        this.username = savedInstanceState.getString(SAVED_USERNAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        titleTextView = (TextView) findViewById(R.id.title);
        questionTextView = (TextView) findViewById(R.id.question);
        questionImageView = (ImageView) findViewById(R.id.image_question);
        questionImageView.setVisibility(View.VISIBLE);

        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);

        answerRadioButtonMap = new HashMap<>();
        answerRadioButtonMap.clear();
        answerRadioButtonMap.put('A', radioButton1.getId());
        answerRadioButtonMap.put('B', radioButton2.getId());
        answerRadioButtonMap.put('C', radioButton3.getId());
        answerRadioButtonMap.put('D', radioButton4.getId());

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


        if (savedInstanceState != null) {
            this.index = savedInstanceState.getInt(SAVED_INDEX);
            this.test = (Test) savedInstanceState.get(SAVED_TEST);
            this.username = savedInstanceState.getString(SAVED_USERNAME);
        } else {
            this.index = 0;
            this.test = (Test) getIntent().getExtras().get(SAVED_TEST);
            this.username = getIntent().getExtras().getString(SAVED_USERNAME);
        }

        loadQuestion(test.getQuestions(), this.index);

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

                char answerLetter = getCheckedItemByInt(checkedId);

                if (answerLetter == '-') {
                    currentQuestion.setSelectedAnswer(Optional.<Character>absent());
                } else {
                    currentQuestion.setSelectedAnswer(Optional.of(answerLetter));
                }

            }
        });

        Log.d(TAG, "Username: " + username);
    }

    private char getCheckedItemByInt(int checkedInt) {
        if (checkedInt == R.id.radioButton1) {
            return 'A';
        } else if (checkedInt == R.id.radioButton2) {
            return 'B';
        } else if (checkedInt == R.id.radioButton3) {
            return 'C';
        } else if (checkedInt == R.id.radioButton4) {
            return 'D';
        } else {
            return '-';
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

        if(question.getContent().hasPicture()) {
            questionImageView.setImageBitmap(question.getContent().getPicture());
            questionImageView.setVisibility(View.VISIBLE);
        } else {
            questionImageView.setVisibility(View.INVISIBLE);
        }


        // Set selected answer (if there is one)
        Question currentQuestion = test.getQuestions().get(index);

        if (currentQuestion.getSelectedAnswer().isPresent()) {
            answerGroup.check(answerRadioButtonMap.get(currentQuestion.getSelectedAnswer().get()));
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
        Log.i(TAG, "Registered broadcast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i(TAG, "Unregistered broadcast receiver");
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SAVED_INDEX, this.index);
        savedInstanceState.putSerializable(SAVED_TEST, this.test);
        savedInstanceState.putSerializable(SAVED_USERNAME, this.username);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            try {
                TestFactory.writeJSONToServer(test, username);
            } catch (IOException e) {
                Log.e(TAG, "Error while submitting test data to server.", e);
            }

            Intent intent = new Intent(QuestionActivity.this, SummaryActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
