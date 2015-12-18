package com.forfun.streamy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.aaron.test.R;

public class SavedStreamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_streams);

        StreamsArrayAdapter streamsAdapter = new StreamsArrayAdapter(StreamSaver.getSavedStreams(this), this);

        ListView streamsListView = (ListView) findViewById(R.id.saved_streams_list);
        streamsListView.setAdapter(streamsAdapter);

        Button donebutton = (Button) findViewById(R.id.done_button);
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
