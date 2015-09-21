package andres_sjsu.instaViewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andres_sjsu.mylistdemo.R;

/**
 * Created by andres on 9/19/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
//What data do we need from the activity

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        InstagramPhoto photo = getItem(position);
        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent , false);
        }
        /*
                    photo.username = photoJSON.getJSONObject("user").getString("username");
                    photo.caption = photoJSON.getJSONObject("caption").getString("text");
                    photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                    photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                    photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

         */


        TextView tvuserName = (TextView)convertView.findViewById(R.id.userName);
        TextView tvCatption = (TextView)convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
        TextView tvlikes = (TextView)convertView.findViewById(R.id.likes);
        ImageView ivprofilePicture = (ImageView)convertView.findViewById(R.id.profilePicture);

        TextView tvCreationTime = (TextView)convertView.findViewById(R.id.createdTime);


       //long timeInterval = System.currentTimeMillis() / 1000 - Long.parseLong(photo.createdTime);
        long timeInterval = System.currentTimeMillis() / 1000 - Long.parseLong(photo.createdTime);
        tvCreationTime.setText(Utils.getTimeStringFromInterval(timeInterval));

        int CountLikes = photo.likesCount;
        String Likes = Integer.toString(CountLikes);

        tvuserName.setText(photo.username);
        tvCatption.setText(photo.caption);
        tvlikes.setText(Likes + " likes");

        ivPhoto.setImageResource(0);
        ivprofilePicture.setImageResource(0);

        //PlaceHoder image, to wait to load img from instagram..

        //Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        //Picasso.with(getContext()).load(photo.profilePictureUrl).into(ivprofilePicture);
        Picasso.with(getContext()).load(photo.imageUrl).fit().centerCrop().placeholder(R.color.dim_foreground_disabled_material_dark).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profilePictureUrl).into(ivprofilePicture);
        // Picasso.with(getContext()).load(photo.profilePictureUrl).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(ivprofilePicture);


        return convertView;
    }
}
