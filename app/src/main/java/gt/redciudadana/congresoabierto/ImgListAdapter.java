package gt.redciudadana.congresoabierto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Chromz on 3/24/2016.
 */
public class ImgListAdapter extends ArrayAdapter {

    private  final Activity context;
    private final ArrayList<String> nombres;
    private final ArrayList<String> imagenes;

    public ImgListAdapter(Activity context, ArrayList<String> nombres, ArrayList<String> imagenes) {
        super(context, R.layout.lista_imagen, nombres);

        this.context = context;
        this.nombres = nombres;
        this.imagenes = imagenes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rootView = convertView;
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.lista_imagen, null);
        }
        ImageView imageView = (ImageView) rootView.findViewById(R.id.persona);
        TextView texto = (TextView) rootView.findViewById(R.id.diputados_text);


        if (imagenes.get(position) != "null") {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(imagenes.get(position), imageView);
        }

        texto.setText(nombres.get(position));
        return rootView;
    }
}
