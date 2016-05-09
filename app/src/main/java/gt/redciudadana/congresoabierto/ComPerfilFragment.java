package gt.redciudadana.congresoabierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Chromz on 5/8/2016.
 */
public class ComPerfilFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.comisiones_perfil_fragment, container, false);
        Bundle bundle = this.getArguments();
        String comi = bundle.getString("Comisiones");
        String[] comisionesArray = comi.split(",");
        ArrayAdapter<String> adaptador = null;
        ArrayList<String> comisiones = new ArrayList<>();
        for (int i = 0; i < comisionesArray.length; i++){
            comisiones.add(comisionesArray[i]);
        }
        /*String[] comisionesData = getResources().getStringArray(R.array.comisiones);
        for (int i = 0; i < comisionesArray.length; i++){
            comisiones.add(comisionesData[Integer.parseInt(comisionesArray[i])]);
        }*/
        adaptador = new ArrayAdapter<String>(getActivity(),
                R.layout.lista_textview, R.id.lista_txt, comisiones);
        ListView lista = (ListView)rootview.findViewById(R.id.comisiones_list);
        lista.setAdapter(adaptador);
        return rootview;
    }
}
