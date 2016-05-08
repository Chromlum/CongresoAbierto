package gt.redciudadana.congresoabierto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected ArrayList<String> datosPersonas;
    protected static final String DATA_KEY ="gt.redciudadana.app.DATOS";
    protected static final String PERSONAS ="gt.redciudadana.app.PERSONAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Icono
        /*
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.topicon); */

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Se inicializa en el fragmento de inicio
        Fragment fragment = new InicioFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentoActual, fragment).commit();
        setTitle("Inicio");

        datosPersonas = new ArrayList<>();
        Intent intent = getIntent();
        String datosJSON = intent.getStringExtra(SplashActivity.DATOSJSON);
        try {
            JSONObject rootObject = new JSONObject(datosJSON);
            JSONArray resultados = rootObject.getJSONArray("data");
            for (int i = 0; i < resultados.length(); i++) {
                datosPersonas.add(resultados.getJSONObject(i).toString());
            }
        }catch (JSONException ex){
            Log.e(getClass().getSimpleName(), "JSON ERROR", ex);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView textView = (TextView)findViewById(R.id.bannerText);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragmentoActual);
            if (currentFragment instanceof InicioFragment){
                textView.setText(R.string.bienvenida);
                setTitle(R.string.inicio);
            }else if (currentFragment instanceof RedFragment){
                textView.setText(R.string.contacto);
                setTitle(R.string.contacto);
            }else if (currentFragment instanceof DepartamentosFragment){
                textView.setText(R.string.representante_dialog);
            }else if(currentFragment instanceof FeedFragment){
                textView.setText(R.string.feed);
                setTitle(R.string.tuvoz);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        TextView textView = (TextView)findViewById(R.id.bannerText);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DATA_KEY, datosPersonas);

        if (id == R.id.nav_inicio) {
            fragmentClass = InicioFragment.class;
            setTitle(R.string.inicio);
            textView.setText(R.string.bienvenida);
        } else if (id == R.id.nav_noticias) {
            fragmentClass = FeedFragment.class;
            setTitle(R.string.tuvoz);
            textView.setText(R.string.feed);
        } else if (id == R.id.nav_representante) {
            fragmentClass = DepartamentosFragment.class;
            setTitle(R.string.representante);
            textView.setText(R.string.representante_dialog);
        } else if (id == R.id.nav_comisiones) {
            fragmentClass = ComisionesFragment.class;
            setTitle(R.string.comisiones);
            textView.setText(R.string.comisiones_dialog);
        } else if (id == R.id.nav_contacto) {
            fragmentClass = RedFragment.class;
            setTitle(R.string.contacto);
            textView.setText(R.string.contacto);
        } else if (id == R.id.nav_info){
            fragmentClass = RedFragment.class;
            setTitle("Red Ciudadana");
            textView.setText(R.string.acercaDe);
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
        }catch(Exception e){
            Log.e("Principal.java", "Instance Error",e);
        }
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaccion.replace(R.id.fragmentoActual, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void clickMenu(View v){
        ImageButton boton = (ImageButton) v;
        int btnID = boton.getId();
        Fragment fragment = null;
        Class fragmentClass = null;
        TextView textView = (TextView)findViewById(R.id.bannerText);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DATA_KEY, datosPersonas);

        if (btnID == R.id.btnRepr){
            fragmentClass = DepartamentosFragment.class;
            setTitle(R.string.representante);
            textView.setText(R.string.representante_dialog);
        }else if (btnID == R.id.btnComisiones){
            fragmentClass = ComisionesFragment.class;
            setTitle(R.string.comisiones);
            textView.setText(R.string.comisiones_dialog);
        }else if (btnID == R.id.btnNoticias){
            fragmentClass = FeedFragment.class;
            setTitle(R.string.tuvoz);
            textView.setText(R.string.feed);
        }else if (btnID == R.id.btnContacto){
            fragmentClass = EmailFragment.class;
            setTitle(R.string.contacto);
            textView.setText(R.string.contacto);
        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
            transaccion.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            transaccion.replace(R.id.fragmentoActual, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        }catch (Exception e){
            Log.e("Principal.java", "Instance Error",e);
        }
    }
}
