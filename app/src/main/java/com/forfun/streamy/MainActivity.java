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
        Intent videoIntent = new Intent(MainActivity.this, videoPlayer.class);
        EditText urlEditText = (EditText) findViewById(R.id.url_text_input);
        String videoUrl = urlEditText.getText().toString();
        if(!checkForValidUrl(videoUrl)) {
            return;
        }
        videoIntent.putExtra(getString(R.string.video_url_extra), videoUrl);
        startActivity(videoIntent);
    }

    private boolean checkForValidUrl(String url) {

        boolean isValid = true;
        String toastText = "";

        if(!Patterns.WEB_URL.matcher(url).matches()) {
            toastText = getString(R.string.url_invalid_toast);
            isValid = false;
        } else if(url.startsWith("https")) {
            toastText = getString(R.string.url_invalid_toast_https);
            isValid = false;
        }
        if(!isValid) {
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
        }

        return isValid;
    }

}
