package cubex.mahesh.firebasesample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.RED);
        setContentView(R.layout.activity_main);

        Handler h = new Handler( );
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
          startActivity(new Intent(MainActivity.this,
                  LoginActivity.class));
            }
        }, 3000);

    }
}
