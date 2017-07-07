package a7967917_7698299.videogameshopapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by alex on 7/7/2017.
 */

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {


    public interface AsyncResponse  {
        void processFinish(Bitmap output);
    }

    public AsyncResponse delegate = null;

    ImageView imageView;

    public ImageLoader(ImageView imageView, AsyncResponse delegate) {
        this.imageView = imageView;
        this.delegate = delegate;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

            try {
                InputStream in = new java.net.URL("http://used.agwest.com/images/default-image-agwest-thumb.jpg").openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e2) {
                Log.e("Error", e2.getMessage());
                e2.printStackTrace();
            }

        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        delegate.processFinish(result);
    }

}
