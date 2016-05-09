package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chromz on 3/23/2016.
 */
public class ComisionesFragment extends Fragment {

    protected ArrayAdapter<String> adaptador;
    protected QueryManager personasData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.listado_fragment, container, false);
        ArrayList<String> comisiones = new ArrayList<>();
        personasData = new QueryManager(getArguments().getStringArrayList(Principal.DATA_KEY));
        final String[] comisionesData = getResources().getStringArray(R.array.comisiones);

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
                ArrayList<String> personas = personasData
                        .buscarPorComision(String.valueOf(position));
                TextView textView = (TextView) getActivity().findViewById(R.id.bannerText);
                textView.setText(comisionesData[position]);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(Principal.PERSONAS, personas);
                Class fragmentClass = null;
                Fragment fragment = null;
                fragmentClass = DiputadosFragment.class;
                try{
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragment.setArguments(bundle);
                }catch(Exception e){
                    Log.e("FragMentDep", "Instance Error",e);
                }
                FragmentTransaction transaccion = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                transaccion.replace(R.id.fragmentoActual, fragment)
                        .addToBackStack(fragment.getClass().getName())
                        .commit();
            }
        });


        return rootview;


    }
}
