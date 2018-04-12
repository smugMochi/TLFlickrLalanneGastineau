package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Alexandre on 12/04/2018.
 */

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    Bitmap image;

    public BitmapDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            BufferedInputStream bin = new BufferedInputStream(in);
            Bitmap image = BitmapFactory.decodeStream(bin);
            urlConnection.disconnect();
            return image;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            // Disconnect the http url connection
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        if (imageViewReference != null){
            ImageView imageView = imageViewReference.get();
            if (imageView != null){
                imageView.setImageBitmap(image);
            }
        }
    }

}
