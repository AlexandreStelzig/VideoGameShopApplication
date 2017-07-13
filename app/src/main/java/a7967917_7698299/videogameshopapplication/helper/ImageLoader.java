package a7967917_7698299.videogameshopapplication.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import a7967917_7698299.videogameshopapplication.R;

/**
 * Created by alex on 7/7/2017.
 */

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {


    public interface AsyncResponse  {
        void processFinish(Bitmap output);
    }

    public AsyncResponse delegate = null;

    ImageView imageView;
    Context context;

    public ImageLoader(ImageView imageView, AsyncResponse delegate, Context context) {
        this.imageView = imageView;
        this.delegate = delegate;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            mIcon11 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.no_image);
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        delegate.processFinish(result);
    }

}
