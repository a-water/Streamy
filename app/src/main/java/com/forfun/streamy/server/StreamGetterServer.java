package com.forfun.streamy.server;

import android.content.Context;
import android.util.Log;

import com.example.aaron.test.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class StreamGetterServer extends NanoHTTPD {

    private static final int PORT = 8080;
    private Context mainContext;

    public StreamGetterServer(Context context) throws IOException {
        super(PORT);
        mainContext = context;
        start();
    }

    @Override
    public Response serve(IHTTPSession session) {

        InputStream is = mainContext.getResources().openRawResource(R.raw.index);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String htmlBody = "";
        String readLine = null;

        try {
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null) {
                htmlBody += readLine;
            }

            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        String msg = "<html><body><h1>Enter a url</h1>\n";
//        msg += "<a href=http://developer.android.com/guide/appendix/media-formats.html>Supported Formats</a>\n";
//        Map<String, String> parms = session.getParms();
//        if (parms.get("username") == null) {
//            msg += "<form action='?' method='get'>\n";
//            msg += "<p>Your name: <input type='text' name='username'></p>\n";
//            msg += "</form>\n";
//        } else {
//            msg += "<p>Hello, " + parms.get("username") + "!</p>";
//        }
        return newFixedLengthResponse(htmlBody);
    }

}
