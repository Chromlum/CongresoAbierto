package gt.redciudadana.congresoabierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chromz on 5/8/2016.
 */
public class BotonesPerfilFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.botones_perfil_fragment, container, false);

        return rootview;
    }
}
