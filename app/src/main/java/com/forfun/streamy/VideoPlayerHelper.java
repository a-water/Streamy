package com.forfun.streamy;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import com.example.aaron.test.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daron on 12/18/15.
 */
public class VideoPlayerHelper {

    public static void createAndStartVideoIntent(String videoUrl, Context context, boolean saveUrl) {

        Intent videoIntent = new Intent(context, VideoPlayerActivity.class);

        if(isStreamUpUrl(videoUrl)) {
            videoUrl = getStreamUpUrl(videoUrl);
        } else if(!checkForValidUrl(videoUrl, context)) {
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

    public static boolean isStreamUpUrl(String url) {

        if(!url.startsWith("http") && (url.contains("-stream") || url.contains("-channel"))) {
            return true;
        }

        return false;
    }

    public static String getStreamUpUrl(String channel) {

        String replaceKey = "{{CHANNEL}}";
        String baseStreamUpUrl = "http://streamup.global.ssl.fastly.net/app/" + replaceKey + "/chunklist.m3u8";

        return baseStreamUpUrl.replace(replaceKey, channel);
    }

}
