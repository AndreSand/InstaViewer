package andres_sjsu.instaViewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import andres_sjsu.mylistdemo.R;
import cz.msebera.android.httpclient.Header;

/*
Inta API: https://instagram.com/developer/clients/manage/?registered=CodePathLab1

Client ID:  	3ca194ebf6364a65bbd98085f37d7cde

API endpoint
-/Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
API Response:
	-Type: {"data” => [X] => “type”} (“image or “video”)
	-URL: {"data” => [X] => “images” => “standard_resolution or “url”}
	-Caption: {"data” => [X] => “caption” => “txt”}
	-Author Name: {"data” => [X] => “user” => “username”}

 */
public class PhotosActivity extends Activity {

    private static final String CLIENT_ID = "3ca194ebf6364a65bbd98085f37d7cde";
    private static String URL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;
//    public List<InstagramPhoto> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        photos = new ArrayList<>();

        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhotos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        fetchPopularPhotos();



    }


    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL, null, new JsonHttpResponseHandler() {

            //200 Response
            @Override
            public void onSuccess(int statusCode,Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                swipeContainer.setRefreshing(false);
                aPhotos.clear();
                //iterate the JSON response, create JSON array, itereate each of the phto items and deceode the items into a java object
                JSONArray photosJSON= null;

                try
                    {photosJSON= response.getJSONArray("data"); //array of posts
                        for (int i=0; i< photosJSON.length(); i++) {
                            JSONObject photoJSON = photosJSON.getJSONObject(i);

                            InstagramPhoto photo= new InstagramPhoto();
                            photo.username = photoJSON.getJSONObject("user").getString("username");
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                            photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                            photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                            photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

                            photo.profilePictureUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                            //photo.createdTime = photoJSON.getJSONObject("created_time").getInt("created_time");

                            photo.createdTime = photoJSON.getString("created_time");

                            // Parse comments
                            JSONObject commentsObj = photoJSON.getJSONObject("comments");//.getString("text");
                            photo.commentCount = commentsObj.getInt("count");
                             JSONArray commentsJSON = commentsObj.getJSONArray("data");



                    photos.add(photo);


                        }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                //callback
                aPhotos.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                swipeContainer.setRefreshing(false);
                Log.e("ERROR", "error downloading json");
                Toast.makeText(PhotosActivity.this,"donwload error", Toast.LENGTH_SHORT).show();

            }


        });

    }
}