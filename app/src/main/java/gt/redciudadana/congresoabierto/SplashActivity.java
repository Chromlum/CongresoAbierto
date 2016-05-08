package gt.redciudadana.congresoabierto;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "QrWZOJbe9p9NRrK1B9gloQP2l";
    private static final String TWITTER_SECRET = "Nb3krFfcK9DELKXshpCPVvFZrZsuqfFL615BMkiJPCGP2v0imi";
    protected static final String DATOSJSON = "gt.candidatos.app.DATOSJSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        if (isNetworkAvailable()) {
            ObtenerCandidatos datosProceso = new ObtenerCandidatos();
            datosProceso.execute();
        }else{
            File file = new File(getApplicationContext().getFilesDir(), "data.json");
            if (!file.exists()) {
                try {
                    String archivo = "data.json";
                    FileOutputStream fos = openFileOutput(archivo, Context.MODE_PRIVATE);
                    AssetManager assetManager = getAssets();
                    InputStream stream = assetManager.open("data.json");
                    BufferedReader datos = new BufferedReader(new InputStreamReader(stream));
                    String str;
                    while ((str = datos.readLine()) != null) {
                        fos.write(str.getBytes());
                    }
                    fos.close();
                }catch (IOException ex){
                    Log.e(Principal.class.getName(), "ERROR DE ESCRITURA", ex);
                }
            }
            loadFile();
        }

    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return  info != null && info.isConnected();
    }

    private void loadFile(){
        try {
            FileInputStream fis = openFileInput("data.json");
            BufferedReader datos = new BufferedReader(new InputStreamReader(fis));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = datos.readLine()) != null){
                buffer.append(str + "\n");
            }
            String datosJSON = buffer.toString();
            JSONObject rootObject = new JSONObject(datosJSON);
            JSONArray resultados = rootObject.getJSONArray("data");

            Intent intent = new Intent(this, Principal.class);
            intent.putExtra(DATOSJSON, datosJSON);
            startActivity(intent);
            finish();

        }catch (IOException ex){
            Log.e(Principal.class.getName(), "ERROR DE LECUTRA", ex);
        }catch (JSONException jsonEx){
            Log.e("InicioFragment.java", "JSON PARSE ERROR", jsonEx);
        }

    }

    public class ObtenerCandidatos extends AsyncTask<Void, Void,Void> {

        // Titulo de la accion
        private final String LOG_TAG = ObtenerCandidatos.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {



            // Conexion
            HttpURLConnection urlConnection = null;
            // Buffer
            BufferedReader reader = null;

            try{

                // Datos de la direccion de la API
                final String URL_BASE = "http://www.miguatemala.org/data/data.json";

                // Se crea la direccion de la API
                Uri uri = Uri.parse(URL_BASE)
                        .buildUpon()
                        .build();
                URL url = new URL(uri.toString());
                // Crear conexion y abrirla
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    // No hay datos
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String archivo = "data.json";
                FileOutputStream fos = openFileOutput(archivo, Context.MODE_PRIVATE);
                String line;
                while ((line = reader.readLine()) != null){
                    fos.write(line.getBytes());
                    buffer.append(line + "\n");
                }
                fos.close();
                if (buffer.length() == 0 ){
                    return null;
                }
                loadFile();
                return null;
            }catch (MalformedURLException ex){
                Log.e(LOG_TAG, "Error", ex);
                return null;
            }catch(IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;
            }finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (final IOException ex){
                        Log.e(LOG_TAG, "Error al cerrar la transferencia", ex);
                    }
                }
            }
        }
    }
}
