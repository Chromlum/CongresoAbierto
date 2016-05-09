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
    protected QueryManager personasData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.listado_fragment, container, false);
        ArrayList<String> departamentos = new ArrayList<>();
        personasData = new QueryManager(getArguments().getStringArrayList(Principal.DATA_KEY));

        String[] departamentosData = getResources().getStringArray(R.array.departamentos);
        for (String comision: departamentosData) {
            departamentos.add(comision);
        }
        adaptador = new ArrayAdapter<String>(getActivity(),
                R.layout.lista_textview, R.id.lista_txt, departamentos);

        ListView listView = (ListView) rootview.findViewById(R.id.lista_frag);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                ArrayList<String> personas = personasData.buscarPorDistrito(query);
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
