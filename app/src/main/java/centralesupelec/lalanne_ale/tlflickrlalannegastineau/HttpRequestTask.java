package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by lalanne_ale on 27/03/18.
 */

public class HttpRequestTask extends AsyncTask<String, Void, Bitmap> {

    ArrayList<MyImage> arrayOfImages = MainActivity.arrayOfImages;
    ImageAdapter adapter = MainActivity.adapter;
    String server_response;

    @Override
    protected void onPreExecute() {
        arrayOfImages.clear();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.i("TASK", "tesk started");

        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                server_response = readStream(urlConnection.getInputStream());
            }
            urlConnection.disconnect();
        }
        catch (MalformedURLException e) {
                e.printStackTrace();
            }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        String sep = "\"items\": \\[" + "\n" + "\t   ";
        String sep2 = "\n" + "        \\]";
        String rawJsonText = server_response.split(sep)[1].split(sep2)[0];
        String sep3 = "\\},\n" + "\t   \\{";
        String[] rawJsonList = rawJsonText.split(sep3);
        ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
        try {
            jsonList.add(new JSONObject(rawJsonList[0] + "\n}"));
            for (int i = 1; i < rawJsonList.length - 1; i++) {
                jsonList.add(new JSONObject("{\n" + rawJsonList[i] + "\n}"));
            }
            jsonList.add(new JSONObject("{\n" + rawJsonList[rawJsonList.length - 1]));
            for (JSONObject j : jsonList) {
                JSONObject tmp = new JSONObject(j.getString("media"));
                arrayOfImages.add(new MyImage(j.getString("title"), tmp.getString("m")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }


    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
