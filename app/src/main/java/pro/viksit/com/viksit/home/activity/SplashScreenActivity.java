package pro.viksit.com.viksit.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import pro.viksit.com.viksit.R;
import pro.viksit.com.viksit.dashboard.activity.DashboardActivity;

/**
 * Created by ravy on 17/03/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /*startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));*/
                startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                //SplashScreenActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
