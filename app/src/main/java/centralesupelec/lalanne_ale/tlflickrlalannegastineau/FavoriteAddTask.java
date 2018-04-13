package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.os.AsyncTask;

/**
 * Created by Alexandre on 13/04/2018.
 */

public class FavoriteAddTask extends AsyncTask<String, Void, Void> {
    MyDatabase mydb = MainActivity.mydb;
    MyImage image;

    public FavoriteAddTask(MyImage i){
        image = i;
    }

    protected Void doInBackground(String... params) {
        if (!mydb.hasEntry(image.getUrl())){
            mydb.insertData(image);
        }
        return null;
    }
}
