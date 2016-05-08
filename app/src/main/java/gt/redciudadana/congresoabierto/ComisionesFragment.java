package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Chromz on 3/23/2016.
 */
public class ComisionesFragment extends Fragment {

    ArrayAdapter<String> adaptador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.listado_fragment, container, false);
        ArrayList<String> comisiones = new ArrayList<>();
        String[] comisionesData = getResources().getStringArray(R.array.comisiones);

        for (String comision: comisionesData) {
            comisiones.add(comision);
        }

        adaptador = new ArrayAdapter<String>(getActivity(),
                R.layout.lista_textview, R.id.lista_txt, comisiones);

        ListView listView = (ListView) rootview.findViewById(R.id.lista_frag);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 21) {
                    Intent intent = new Intent(getActivity(), DiputadoPerfil.class);
                    startActivity(intent);
                }
            }
        });


        return rootview;


    }
}
