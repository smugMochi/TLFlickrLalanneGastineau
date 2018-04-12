package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lalanne_ale on 27/03/18.
 */

public class ImageAdapter extends ArrayAdapter<MyImage> {

    public ImageAdapter(Context context, ArrayList<MyImage> listeImages) {
        super(context, 0, listeImages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyImage image = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gabarit_image, parent, false);
        }
        TextView textview = (TextView) convertView.findViewById(R.id.titre);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
        textview.setText(String.valueOf(image.getTitle()));
        new BitmapDownloaderTask(imageview).execute(image.getUrl());
        return convertView;
    }

}
