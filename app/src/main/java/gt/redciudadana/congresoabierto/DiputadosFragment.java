package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chromz on 3/23/2016.
 */
public class DiputadosFragment extends ListFragment {

    protected ArrayAdapter<String> adaptador;
    private ArrayList<String> personas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.diputados_fragment, container, false);

        personas = getArguments().getStringArrayList(Principal.PERSONAS);
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<String> urisImagenes = new ArrayList<>();
        JSONObject objeto = null;
        try {
            for (String persona : personas) {
                objeto = new JSONObject(persona);
                nombres.add(objeto.getString("nombre"));
                urisImagenes.add(objeto.getString("foto-funcionario")); // TESTING foto-funcionario
            }

        }catch (JSONException ex){
            Log.e("CandiFrag.java", "Json Error", ex);
        }
        ImgListAdapter adaptador = new ImgListAdapter(getActivity(), nombres, urisImagenes);
        /*adaptador = new ArrayAdapter<>(getActivity(),
                R.layout.lista_imagen, R.id.candidatos_text, nombres);*/

        setListAdapter(adaptador);
        return rootview;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), DiputadoPerfil.class);
        intent.putExtra(DiputadoPerfil.CANDIDATO, personas.get(position));
        startActivity(intent);
    }
}
