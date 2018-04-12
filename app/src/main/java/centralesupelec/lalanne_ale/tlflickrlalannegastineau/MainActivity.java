package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ImageAdapter adapter;
    public static ArrayList<MyImage> arrayOfImages = new ArrayList<MyImage>();
    public static ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ImageAdapter(this, arrayOfImages);
        liste = (ListView)findViewById(R.id.maliste);
        liste.setAdapter(adapter);
        final Button button = (Button)findViewById(R.id.button2);
        final EditText edit = (EditText)findViewById(R.id.editText);
        button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String search = edit.getText().toString();
                        new HttpRequestTask().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=" + search + "&format=json");
                    }
                }
        );
    }
}
