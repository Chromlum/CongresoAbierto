package gt.redciudadana.congresoabierto;

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
 * Created by Chromz on 2/8/2016.
 */
public class DepartamentosFragment extends Fragment {

    protected ArrayAdapter<String> adaptador;
    protected QueryManager candiDatos;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.listado_fragment, container, false);
        ArrayList<String> departamentos = new ArrayList<>();
        candiDatos = new QueryManager(getArguments().getStringArrayList(Principal.DATA_KEY));
        departamentos.add("Alta Verapaz");
        departamentos.add("Baja Verapaz");
        departamentos.add("Chimaltenango");
        departamentos.add("Distrito Central");
        departamentos.add("Distrito Guatemala");
        departamentos.add("El Progreso");
        departamentos.add("Escuintla");
        departamentos.add("Huehuetenango");
        departamentos.add("Izabal");
        departamentos.add("Jalapa");
        departamentos.add("Jutiapa");
        departamentos.add("Petén");
        departamentos.add("Quetzaltenango");
        departamentos.add("Quiché");
        departamentos.add("Retalhuleu");
        departamentos.add("Sacatepéquez");
        departamentos.add("San Marcos");
        departamentos.add("Santa Rosa");
        departamentos.add("Sololá");
        departamentos.add("Totonicapán");
        departamentos.add("Zacapa");
        departamentos.add("Listado Nacional");
        adaptador = new ArrayAdapter<String>(getActivity(),
                R.layout.lista_textview, R.id.lista_txt, departamentos);

        ListView listView = (ListView) rootview.findViewById(R.id.lista_frag);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                ArrayList<String> personas = candiDatos.buscarPorDistrito(query);
                TextView textView = (TextView) getActivity().findViewById(R.id.bannerText);
                textView.setText(query);
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
