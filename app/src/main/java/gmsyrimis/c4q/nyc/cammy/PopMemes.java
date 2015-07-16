package gmsyrimis.c4q.nyc.cammy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PopMemes extends Activity {
    public static String IMAGE_URI_KEY = "uri";
    private ListView memeListView;
    private List<Integer> memeResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_memes);

        //start async in the background thread to save pictures in the db
        ImageAsync imageAsync = new ImageAsync();
        imageAsync.execute();

        //a list of images to save in db
        memeListView = (ListView) findViewById(R.id.meme_list_view);

        memeResources = new ArrayList<Integer>();
        memeResources.add(R.drawable.gridone);
        memeResources.add(R.drawable.gridtwo);
        memeResources.add(R.drawable.gridthree);
        memeResources.add(R.drawable.girdfour);
        memeResources.add(R.drawable.gridfive);
        memeResources.add(R.drawable.gridsix);
        memeResources.add(R.drawable.gridseven);
        memeResources.add(R.drawable.grideight);
        memeResources.add(R.drawable.gridnine);
        memeResources.add(R.drawable.gridten);
        memeResources.add(R.drawable.grideleven);
        memeResources.add(R.drawable.gridtwelve);
        memeResources.add(R.drawable.gridthirteen);
        memeResources.add(R.drawable.gridforteen);
        memeResources.add(R.drawable.gridfifteen);
        memeResources.add(R.drawable.gridsixteen);

        memeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageTemplate meme = (ImageTemplate) parent.getItemAtPosition(position);
                Intent gotoPopMeme = new Intent(PopMemes.this, PopMemeEditor.class);
                gotoPopMeme.putExtra(IMAGE_URI_KEY, meme.getImage());
                startActivity(gotoPopMeme);
            }
        });
    }

    class ImageAsync extends AsyncTask<Void, Void, List<ImageTemplate>> {

        @Override
        protected List<ImageTemplate> doInBackground(Void... params) {
            insertData();

            return loadData();
        }

        @Override
        protected void onPostExecute(List<ImageTemplate> images)
        {
            showData(images);
        }
    }

    private void insertData() {
        ImageSQLOpenHelper myImageSQLOpenHelper = ImageSQLOpenHelper.getInstance(this);
        int number = 1;
        for (int drawable : memeResources) {
            // get image from drawable
            Bitmap image = BitmapFactory.decodeResource(getResources(), drawable);

            //covert image to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            //insert a row in the db
            myImageSQLOpenHelper.insertImage(new ImageTemplate("Template " + number, imageInByte));
            number++;
        }
    }

    private List<ImageTemplate> loadData() {
        ImageSQLOpenHelper myImageSQLOpenHelper = ImageSQLOpenHelper.getInstance(this);
        return myImageSQLOpenHelper.getAllImages();
    }

    private void showData(List<ImageTemplate> images) {
        ImageTemplateAdapter adapter = new ImageTemplateAdapter(this, R.layout.image_adapter, images);
        memeListView.setAdapter(adapter);
    }
}
