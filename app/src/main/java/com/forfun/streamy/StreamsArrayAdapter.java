package com.forfun.streamy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aaron.test.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by aaron on 12/17/15.
 */
public class StreamsArrayAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list;
    private Context context;



    public StreamsArrayAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 1;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.saved_stream_list_item, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_text);
        listItemText.setText(list.get(position));

        Button deleteButton = (Button)view.findViewById(R.id.delete_button);
        Button streamButtonn = (Button)view.findViewById(R.id.stream_button);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position);
                StreamSaver.deleteStream(list.get(position), context);
                notifyDataSetChanged();
            }
        });

        streamButtonn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(context, VideoPlayer.class);

                String videoUrl = list.get(position);
                if(!VideoPlayer.checkForValidUrl(videoUrl, context)) {
                    return;
                }

                videoIntent.putExtra(context.getString(R.string.video_url_extra), videoUrl);
                context.startActivity(videoIntent);

            }
        });

        return view;
    }
}