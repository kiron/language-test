package la.hack.kiron.gov.kironlanguageassessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import la.hack.kiron.gov.kironlanguageassessment.data.Question;
import la.hack.kiron.gov.kironlanguageassessment.data.Test;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Test test = (Test)getIntent().getExtras().get("test");

        TextView titleTextView = (TextView)findViewById(R.id.title);
        TextView questionTextView = (TextView)findViewById(R.id.question);

        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        RadioButton radioButton4 = (RadioButton)findViewById(R.id.radioButton4);

        Question question = test.getQuestions().get(0);

        titleTextView.setText("Question 1 of 1");
        questionTextView.setText(question.getContent().getContent());

        radioButton1.setText(question.getAnswers().get(0).getContent());
        radioButton2.setText(question.getAnswers().get(1).getContent());
        radioButton3.setText(question.getAnswers().get(2).getContent());
        radioButton4.setText(question.getAnswers().get(3).getContent());

        final Button button = (Button) findViewById(R.id.start_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Eine Frage von der
//            }
//        });
    }
}
