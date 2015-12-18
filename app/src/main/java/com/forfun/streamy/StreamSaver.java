package com.forfun.streamy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.aaron.test.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by daron on 12/17/15.
 */
public class StreamSaver {

    final static String SAVED_STREAMS_KEY = "savedStreams";
    final static String SAVED_STREAMS_SET_KEY = "savedStreamsSet";

    public static void saveStream(final String videoUrl, Context context) {

        SharedPreferences sharedPrefs = context.getSharedPreferences(SAVED_STREAMS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();

        if(sharedPrefs.contains(SAVED_STREAMS_SET_KEY)) {
            Log.d("CONTAINS", "YES");
            Set<String> streamsSet = new HashSet<String>(sharedPrefs.getStringSet(SAVED_STREAMS_SET_KEY, new HashSet<String>()));
            Log.d("CONTAINS", "SIZE: " + streamsSet.size());
            streamsSet.add(videoUrl);
            prefsEditor.remove(SAVED_STREAMS_SET_KEY);
            prefsEditor.putStringSet(SAVED_STREAMS_SET_KEY, streamsSet);

        } else {
            Log.d("CONTAINS", "NO");
            prefsEditor.putStringSet(SAVED_STREAMS_SET_KEY, new HashSet<String>(){{
                add(videoUrl);
            }});
        }

        prefsEditor.commit();
    }

    public static boolean deleteStream(String videoUrl, Context context) {

        SharedPreferences sharedPrefs = context.getSharedPreferences(SAVED_STREAMS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();

        boolean contained = false;

        if(sharedPrefs.contains(SAVED_STREAMS_SET_KEY)) {
            Set<String> streamsSet = new HashSet<String>(sharedPrefs.getStringSet(SAVED_STREAMS_SET_KEY, new HashSet<String>()));
            Log.d("CONTAINS", "SIZE: " + streamsSet.size());
            contained = streamsSet.remove(videoUrl);
            Log.d("CONTAINS", "SIZE: " + streamsSet.size());
            prefsEditor.remove(SAVED_STREAMS_SET_KEY);
            prefsEditor.putStringSet(SAVED_STREAMS_SET_KEY, streamsSet);
            prefsEditor.commit();
        }

        return contained;
    }

    public static ArrayList<String> getSavedStreams(Context context) {

        SharedPreferences sharedPrefs = context.getSharedPreferences(SAVED_STREAMS_KEY, Context.MODE_PRIVATE);

        if(sharedPrefs.contains(SAVED_STREAMS_SET_KEY)) {
            ArrayList<String> streamsSet = new ArrayList<String>(sharedPrefs.getStringSet(SAVED_STREAMS_SET_KEY, new HashSet<String>()));
            return streamsSet;
        }

        return null;
    }

}
