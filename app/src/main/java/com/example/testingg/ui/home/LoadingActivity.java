package com.example.testingg.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testingg.R;
import com.example.testingg.ui.home.PreventionsActivity;

public class LoadingActivity extends AppCompatActivity {

    private static final int LOADING_DURATION = 3000; // Duration for the loading animation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        // Start a handler to delay the transition to the result activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the result and imageUri from the intent
                Intent intent = getIntent();
                String result = intent.getStringExtra("result");
                String imageUri = intent.getStringExtra("imageUri");

                // Start the prediction results activity
                Intent resultIntent = new Intent(LoadingActivity.this, PreventionsActivity.class);
                resultIntent.putExtra("result", result);
                resultIntent.putExtra("imageUri", imageUri);
                startActivity(resultIntent);
                finish();
            }
        }, 2000);
    }
}
