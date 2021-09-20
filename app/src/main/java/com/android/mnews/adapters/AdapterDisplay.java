package com.android.mnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mnews.ActivityError;
import com.android.mnews.ActivityWeb;
import com.android.mnews.R;
import com.android.mnews.constants.Errors;
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
                        .placeholder(R.drawable.background_of_placeholder)
                        .into(thumbnailImageView);
            } else {
                thumbnailImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                thumbnailImageView.setBackgroundResource(R.drawable.background_of_placeholder);
                thumbnailImageView.setImageResource(R.drawable.ic_icon_image_not_found);
            }

            //Title of Post
            TextView titleTextView = eachRow.findViewById(R.id.activity_display_cardview_title_ID);
            titleTextView.setText(data.get(position).getTitle());

            //Source/Author TextView
            TextView author = eachRow.findViewById(R.id.activity_display_cardview_author_ID);
            if(data.get(position).getAuthor() != null && !data.get(position).getAuthor().isEmpty()) {
                author.setVisibility(View.VISIBLE);
                author.setText(data.get(position).getSource());
            }

            //Read More Button (On Click Go To ActivityWeb)
            ImageButton readMoreImageButton = eachRow.findViewById(R.id.activity_display_cardview_readmore_ID);
            readMoreImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String link = data.get(position).getUrl();
                    if(link == null){
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(context, ActivityWeb.class);
                        intent.putExtra("POST_LINK",link);
                        context.startActivity(intent);
                    }


                }
            });
        }
        catch (Exception e){
            Intent intent = new Intent(context, ActivityError.class);
            intent.putExtra(Errors.ERROR_KEY,Errors.ERROR_IN_ADAPTER_DISPLAY);
            context.startActivity(intent);
        }

        return eachRow;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //TODO : Remove
        Log.d("MEHUL","Adapter object to be destroyed");
    }
}
