package gov.kiron.android.la;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import gov.kiron.android.la.data.Level;
import gov.kiron.android.la.data.Question;
import gov.kiron.android.la.data.TestFactory;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button = (Button) findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra("test", TestFactory.createTest(Level.A1));
                startService(new Intent(MainActivity.this, TestTimeCountDownService.class));

                startActivity(intent);
            }
        });

//        Log.d(MainActivity.class.getSimpleName(), "Starte...");
//        Test test = TestFactory.createTest(Level.A1);
//        Log.d(MainActivity.class.getSimpleName(), "Test: " + test.getQuestions().size());

    }


//

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_submit) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    ArrayList<Question> questions;
}
