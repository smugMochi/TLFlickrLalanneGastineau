package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ImageAdapter adapter;
    public static ArrayList<MyImage> arrayOfImages = new ArrayList<MyImage>();
    public static ListView liste;
    public static MyDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new MyDatabase(this);
        adapter = new ImageAdapter(this, arrayOfImages);
        liste = (ListView)findViewById(R.id.maliste);
        liste.setAdapter(adapter);
        final Button button = (Button)findViewById(R.id.button2);
        final EditText edit = (EditText)findViewById(R.id.editText);
        final Button button2 = (Button)findViewById(R.id.button3);
        button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String search = edit.getText().toString();
                        new HttpRequestTask().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=" + search + "&format=json");
                    }
                }
        );
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = edit.getText().toString();
                    new HttpRequestTask().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=" + search + "&format=json");
                    handled = true;
                }
                return handled;
            }
        });

        button2.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Intent callactivity = new Intent(MainActivity.this, FavoritesActivity.class);
                        startActivity(callactivity);
                    }
                }
        );

        liste.setClickable(true);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                MyImage imageFavorie = arrayOfImages.get(position);
                new FavoriteAddTask(imageFavorie).execute();
                Toast.makeText(getApplicationContext(), "Image ajoutée aux favoris", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
