package com.forfun.streamy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.aaron.test.R;
import com.forfun.streamy.server.SocketRocket;
import com.forfun.streamy.server.StreamGetterServer;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener {

    private static Context mContext;
    private StreamGetterServer streamGetterServer;
    private SocketRocket socketRocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Button okayButton = (Button) findViewById(R.id.okay_button);
        okayButton.setOnClickListener(this);

        Button showSavebutton = (Button) findViewById(R.id.show_saved_stream_button);
        showSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showSavedIntent = new Intent(MainActivity.this, SavedStreamsActivity.class);
                startActivity(showSavedIntent);
            }
        });


        //        Log.d("networkCheck", "IS IT? : " + isNetworkAvailable());
    }

    @Override
    public void onResume() {
        super.onResume();

        EditText urlEditText = (EditText) findViewById(R.id.url_text_input);
        urlEditText.requestFocus();

        try{
            streamGetterServer = new StreamGetterServer(mContext);

            socketRocket = new SocketRocket();
            socketRocket.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(streamGetterServer != null) {
            streamGetterServer.stop();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        EditText urlEditText = (EditText) findViewById(R.id.url_text_input);
        String videoUrl = urlEditText.getText().toString();

        Switch saveSwitch = (Switch) findViewById(R.id.save_stream_switch);

        VideoPlayerHelper.createAndStartVideoIntent(videoUrl, this, saveSwitch.isChecked());
    }

}
