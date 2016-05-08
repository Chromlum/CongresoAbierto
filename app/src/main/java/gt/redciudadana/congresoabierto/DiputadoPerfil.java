package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class DiputadoPerfil extends AppCompatActivity {
    protected static final String CANDIDATO = "gt.candidatos.app.SELECCION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diputado_perfil);
        Intent intent = getIntent();
        try {
            if (intent.hasExtra(CANDIDATO)) {
                String jsonString = intent.getStringExtra(CANDIDATO);
                JSONObject jsonObject = new JSONObject(jsonString);
                String imagen = jsonObject.getString("foto-funcionario");
                String nombre = jsonObject.getString("nombre");
                String telefono = jsonObject.getString("telefono");
                String fb = jsonObject.getString("fb");
                String tw = jsonObject.getString("tw");
                String comision = jsonObject.getString("comision");
                String email = jsonObject.getString("email-funcionario");
                setTitle(nombre);
                TextView correoText = (TextView) findViewById(R.id.correo_text);
                correoText.setText(email);
                TextView comisionText = (TextView) findViewById(R.id.comision_text);
                comisionText.setText(comision);
                TextView telefonoText = (TextView) findViewById(R.id.telefono_text);
                telefonoText.setText(telefono);
                if (imagen != "null") {
                    ImageView imgView = (ImageView) findViewById(R.id.perfil_img);
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.displayImage(imagen, imgView);
                }
            }
        }catch (JSONException ex){
            Log.e("CandiPerfil", "Json parse error", ex);
        }
    }
}
