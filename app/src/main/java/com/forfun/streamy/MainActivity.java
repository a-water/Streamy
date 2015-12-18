package com.forfun.streamy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaron.test.R;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button okayButton = (Button) findViewById(R.id.okay_button);
        okayButton.setOnClickListener(this);

        Button showSavedbutton = (Button) findViewById(R.id.show_saved_stream_button);
        showSavedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showSavedIntent = new Intent(MainActivity.this, SavedStreamsActivity.class);
                startActivity(showSavedIntent);
            }
        });

//        Log.d("networkCheck", "IS IT? : " + isNetworkAvailable());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        createAndStartVideoIntent();
    }

    private void createAndStartVideoIntent() {
        Intent videoIntent = new Intent(MainActivity.this, VideoPlayer.class);
        EditText urlEditText = (EditText) findViewById(R.id.url_text_input);
        String videoUrl = urlEditText.getText().toString();
        if(!VideoPlayer.checkForValidUrl(videoUrl, this)) {
            return;
        }

        CheckBox checkBox = (CheckBox) findViewById(R.id.save_checkbox);
        if(checkBox.isChecked()) {
            StreamSaver.saveStream(videoUrl, this);
        }

        videoIntent.putExtra(getString(R.string.video_url_extra), videoUrl);
        startActivity(videoIntent);
    }



}
