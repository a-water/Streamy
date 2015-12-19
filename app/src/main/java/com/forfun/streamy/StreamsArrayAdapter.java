package com.forfun.streamy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.aaron.test.R;

import java.util.ArrayList;

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
        Button streamButton = (Button)view.findViewById(R.id.stream_button);
        if(position == 0){
            streamButton.requestFocus();
        }

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                StreamSaver.deleteStream(list.get(position), context);
                notifyDataSetChanged();
            }
        });

        streamButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String videoUrl = list.get(position);
                VideoPlayerHelper.createAndStartVideoIntent(videoUrl, context, false);
            }
        });

        return view;
    }
}