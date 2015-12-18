package com.forfun.streamy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.aaron.test.R;

import java.io.IOException;

public class VideoPlayer extends Activity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    String videoUrl;
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        loadingSpinner.setVisibility(View.VISIBLE);

        Intent openVideoIntent = getIntent();
        videoUrl = openVideoIntent.getStringExtra(getString(R.string.video_url_extra));

        final VideoView videoView = (VideoView)findViewById(R.id.video_player);
        videoView.setVideoURI(Uri.parse(videoUrl));
        MediaController mediaControls = new MediaController(this);
        mediaControls.setAnchorView(videoView);
        videoView.setMediaController(mediaControls);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setOnCompletionListener(this);

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

        Log.e("VID ERROR", "ERROR: " + what + " " + extra);

        if(what == mediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
            Toast.makeText(getApplicationContext(), getString(R.string.unsupported_media_error), Toast.LENGTH_LONG);
            finish();
        } else {
            try{
                mediaPlayer.reset();
                mediaPlayer.setDataSource(videoUrl);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.unknown_media_error), Toast.LENGTH_LONG);
                finish();
            }
        }

        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        Toast.makeText(getApplicationContext(), getString(R.string.media_complete), Toast.LENGTH_LONG);
        finish();
    }

    public static boolean checkForValidUrl(String url, Context context) {

        boolean isValid = true;
        String toastText = "";

        if(!Patterns.WEB_URL.matcher(url).matches()) {
            toastText = context.getString(R.string.url_invalid_toast);
            isValid = false;
        } else if(url.startsWith("https")) {
            toastText = context.getString(R.string.url_invalid_toast_https);
            isValid = false;
        }
        if(!isValid) {
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
        }

        return isValid;
    }



}
