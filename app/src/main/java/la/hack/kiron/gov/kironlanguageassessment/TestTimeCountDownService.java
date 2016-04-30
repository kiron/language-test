package la.hack.kiron.gov.kironlanguageassessment;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class TestTimeCountDownService extends Service {

    private final static String TAG = TestTimeCountDownService.class.getSimpleName();

    private static final int TEST_TIME = 50; /* minutes */

    public static final String COUNTDOWN_BR = "gov.kiron.android.la.testtimecounddown_br";
    Intent intent = new Intent(COUNTDOWN_BR);

    CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        countDownTimer = new CountDownTimer(TEST_TIME * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                sendTime(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
                sendTime(0);
            }
        };

        countDownTimer.start();
    }

    private void sendTime(long seconds) {
        intent.putExtra("remainingTime", seconds);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {

        countDownTimer.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}