package com.forfun.streamy;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import com.example.aaron.test.R;

/**
 * Created by daron on 12/18/15.
 */
public class VideoPlayerHelper {

    public static void createAndStartVideoIntent(String videoUrl, Context context, boolean saveUrl) {

        Intent videoIntent = new Intent(context, VideoPlayerActivity.class);

        if(!checkForValidUrl(videoUrl, context)) {
            return;
        }

        if(saveUrl) {
            StreamSaver.saveStream(videoUrl, context);
        }

        videoIntent.putExtra(context.getString(R.string.video_url_extra), videoUrl);
        context.startActivity(videoIntent);
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
