package gt.redciudadana.congresoabierto;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chromz on 3/24/2016.
 */
public class QueryManager {

    private ArrayList<JSONObject> dataPersonas;



    public QueryManager(ArrayList<String> dataPersonas){
        this.dataPersonas = new ArrayList<>();
        try {
            for (String string : dataPersonas) {
                this.dataPersonas.add(new JSONObject(string));
            }
        }catch (JSONException ex){
            Log.e(QueryManager.class.getSimpleName(), "Query Manager Error", ex);
        }
    }

    public ArrayList<String> buscarPorComision(String query){
        ArrayList<String> personas = new ArrayList<>();
        try {
            for (JSONObject jsonObject : dataPersonas) {
                String comision = jsonObject.getString("comision");
                String[] comisiones = comision.split(",");
                for (int i = 0; i < comisiones.length; i++){
                    if (comisiones[i].equals(query)) {
                        personas.add(jsonObject.toString());
                        break;
                    }
                }
            }
            return personas;
        }catch (JSONException ex){
            Log.e(QueryManager.class.getSimpleName(), "JSON ERROR", ex);
            return personas;
        }
    }

    public ArrayList<String> buscarPorDistrito(String query){
        ArrayList<String> personas = new ArrayList<>();
        try {
            for (JSONObject jsonObject : dataPersonas) {
                if (jsonObject.getString("distrito").equals(query)) {
                    personas.add(jsonObject.toString());
                }
            }
            return personas;
        }catch (JSONException ex){
            Log.e(QueryManager.class.getSimpleName(),
                    "Algun CandiDato no tiene los campos requeridos", ex);
            return personas;
        }
    }

    public ArrayList<JSONObject> getdataPersonas() {
        return dataPersonas;
    }

    public void setDataPersonas(ArrayList<JSONObject> dataPersonas) {
        this.dataPersonas = dataPersonas;
    }

}
