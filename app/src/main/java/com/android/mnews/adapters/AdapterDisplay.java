package com.android.mnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mnews.ActivityWeb;
import com.android.mnews.R;
import com.android.mnews.persistence.Timer;
import com.bumptech.glide.Glide;
import static com.android.mnews.MainActivity.data;

public class AdapterDisplay extends BaseAdapter {

    Context context;

    public AdapterDisplay(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View eachRow = inflater.inflate(R.layout.background_of_activity_display_cardview, parent, false);

        try {
            //Image Thumbnail
            ImageView thumbnailImageView = eachRow.findViewById(R.id.activity_display_cardview_thumbnail_ID);
            if (data.get(position).getImage() != null) {
                Glide.with(context)
                        .load(data.get(position).getImage())
                        .placeholder(R.drawable.ic_icon_loading_bar)
                        .into(thumbnailImageView);
            } else {
                thumbnailImageView.setImageResource(R.drawable.ic_icon_loading_bar);
            }

            //Title of Post
            TextView titleTextView = eachRow.findViewById(R.id.activity_display_cardview_title_ID);
            titleTextView.setText(data.get(position).getTitle());
//            titleTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Timer timer = new Timer(context);
//                    int min = timer.getDuration();
//                    Toast.makeText(context, "mins = "+min, Toast.LENGTH_SHORT).show();
//                }
//            });

            //source button
            TextView author = eachRow.findViewById(R.id.activity_display_cardview_author_ID);
            if(data.get(position).getAuthor() != null && !data.get(position).getAuthor().isEmpty()) {
                author.setVisibility(View.VISIBLE);
                author.setText(data.get(position).getAuthor());
            }

            //Read More Button (On Click Go To ActivityWeb)
            ImageButton readMoreImageButton = eachRow.findViewById(R.id.activity_display_cardview_readmore_ID);
            readMoreImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityWeb.class);
                    intent.putExtra("POST_LINK",data.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }

        catch (Exception e){
            Toast.makeText(context, "in getView "+e, Toast.LENGTH_SHORT).show();
        }

        return eachRow;
    }
}
