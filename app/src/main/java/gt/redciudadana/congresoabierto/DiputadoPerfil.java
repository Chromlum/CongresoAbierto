package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class DiputadoPerfil extends AppCompatActivity {

    protected static final String CANDIDATO = "gt.candidatos.app.SELECCION";
    private String imagen;
    private String nombre;
    private String telefono;
    private String partido;
    private String foto_partido;
    private String fb;
    private String tw;
    private String yt;
    private String comision;
    private String email;
    private String asistencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diputado_perfil);
        Intent intent = getIntent();
        try {
            if (intent.hasExtra(CANDIDATO)) {
                String jsonString = intent.getStringExtra(CANDIDATO);
                JSONObject jsonObject = new JSONObject(jsonString);
                imagen = jsonObject.getString("foto-funcionario");
                nombre = jsonObject.getString("nombre");
                telefono = jsonObject.getString("telefono");
                partido = jsonObject.getString("partido-actual");
                foto_partido = jsonObject.getString("foto-partido-actual");
                fb = jsonObject.getString("fb");
                tw = jsonObject.getString("tw");
                comision = jsonObject.getString("comision");
                email = jsonObject.getString("email-funcionario");
                asistencia = jsonObject.getString("asistencia");
                setTitle("Diputados");

                TextView nombreDiputado = (TextView) findViewById(R.id.txtNombreDiputado);
                TextView nombrePartido = (TextView) findViewById(R.id.txtBancada);
                nombreDiputado.setText(nombre);
                nombrePartido.setText(partido);

                ImageLoader imageLoader = ImageLoader.getInstance();
                if (imagen != "null") {
                    ImageView imgView = (ImageView) findViewById(R.id.imgFotoDiputadoPerfil);
                    imageLoader.displayImage(imagen, imgView);
                }
                if (foto_partido != "null"){
                    ImageView imagenPartido = (ImageView) findViewById(R.id.partidoImg);
                    imageLoader.displayImage(foto_partido, imagenPartido);
                }
                Button btnLlamar = (Button) findViewById(R.id.boton_llamar);

                Fragment fragment = new BotonesPerfilFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.diputado_frame, fragment).commit();
            }
        } catch (JSONException ex) {
            Log.e("CandiPerfil", "Json parse error", ex);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void onClick(View v){
        Button boton = (Button) v;
        int btnID = boton.getId();
        Bundle bundle = new Bundle();
        bundle.putString("Correo", email);
        bundle.putString("Asistencia", asistencia);
        bundle.putString("Comisiones", comision);
        Fragment fragment = null;
        Class fragmentClass = null;

        if(btnID == R.id.boton_llamar){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefono));
            startActivity(intent);
        } else if (btnID == R.id.btnEnvCorreo){
            try{
                fragmentClass = EmailFragment.class;
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                transaccion.replace(R.id.diputado_frame, fragment)
                        .addToBackStack(fragment.getClass().getName())
                        .commit();
            }catch (Exception e){
                Log.v("Error Fragment", "ERROR",e);
            }
        }else if (btnID == R.id.btnAsistencia){
            try{
                fragmentClass = AsistenciaFragment.class;
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                transaccion.replace(R.id.diputado_frame, fragment)
                        .addToBackStack(fragment.getClass().getName())
                        .commit();
            }catch (Exception e){
                Log.v("Error Fragment", "ERROR",e);
            }
        }else if (btnID == R.id.btnComi){
            try{
                fragmentClass = ComisionesFragment.class;
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                transaccion.replace(R.id.diputado_frame, fragment)
                        .addToBackStack(fragment.getClass().getName())
                        .commit();
            }catch (Exception e){
                Log.v("Error Fragment", "ERROR",e);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}