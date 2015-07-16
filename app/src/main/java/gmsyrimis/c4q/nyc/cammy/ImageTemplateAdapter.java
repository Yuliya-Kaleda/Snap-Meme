package gmsyrimis.c4q.nyc.cammy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by July on 7/12/15.
 */
public class ImageTemplateAdapter extends ArrayAdapter<ImageTemplate> {
    private Context context;
    private int layoutResourceId;
    private List<ImageTemplate> data;

    public ImageTemplateAdapter(Context context, int layoutResourceId, List<ImageTemplate> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();

            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        ImageTemplate picture = data.get(position);
        holder.txtTitle.setText(picture.getName());

        //convert byte to bitmap taken from ImageTemplate class
        byte[] outImage=picture.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }

    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}

