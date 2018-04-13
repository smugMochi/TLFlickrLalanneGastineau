package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Alexandre on 13/04/2018.
 */

public class FavoriteRemoveTask extends AsyncTask<String, Void, Void> {
    MyDatabase mydb = MainActivity.mydb;
    ImageAdapter adapter;
    int position;

    public FavoriteRemoveTask(ImageAdapter a, int pos) {
        adapter = a;
        position = pos;
    }

    protected Void doInBackground(String... params) {
        String url = params[0];
        mydb.removeData(url);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        adapter.remove(adapter.getItem(position));
        adapter.notifyDataSetChanged();

    }
}
