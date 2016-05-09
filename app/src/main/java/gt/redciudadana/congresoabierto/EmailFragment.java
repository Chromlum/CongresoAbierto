package gt.redciudadana.congresoabierto;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Chromz on 5/8/2016.
 */
public class EmailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.email_fragment, container, false);
        Bundle bundle = this.getArguments();
        String correo = null;
        if (bundle != null){
            correo = bundle.getString("Correo");
        }
        final Button boton = (Button) rootView.findViewById(R.id.boton_enviar);
        final EditText et0 = (EditText) rootView.findViewById(R.id.asunto);
        final EditText et1 = (EditText) rootView.findViewById(R.id.mensaje);
        final EditText et2 = (EditText) rootView.findViewById(R.id.reci);
        et2.setText(correo);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{et2.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, et0.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, et1.getText().toString());
                try{
                    startActivity(Intent.createChooser(intent,"Enviar Correo..."));
                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(EmailFragment.this.getActivity(),
                            "Error no hay aplicación de correo", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
