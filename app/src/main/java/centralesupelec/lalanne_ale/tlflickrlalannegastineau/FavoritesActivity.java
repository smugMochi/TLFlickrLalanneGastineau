package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Alexandre on 13/04/2018.
 */

public class FavoritesActivity extends Activity {
    public static ImageAdapter adapter;
    public static ArrayList<MyImage> arrayOfImages = new ArrayList<MyImage>();
    public static ListView liste;
    public static MyDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        mydb = new MyDatabase(this);
        mydb.readData(arrayOfImages);
        adapter = new ImageAdapter(this, arrayOfImages);
        liste = (ListView)findViewById(R.id.maliste2);
        liste.setAdapter(adapter);
        liste.setClickable(true);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                MyImage imageFavorie = arrayOfImages.get(position);
                new FavoriteRemoveTask(adapter, position).execute(imageFavorie.getUrl());
                Toast.makeText(getApplicationContext(), "Image retirée des favoris", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
