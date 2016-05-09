package gt.redciudadana.congresoabierto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Chromz on 5/8/2016.
 */
public class AsistenciaFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.asistencia_fragment, container, false);
        Bundle bundle = this.getArguments();
        String asistencia = null;
        if (bundle != null){
            asistencia = bundle.getString("Asistencia");
            asistencia = asistencia.substring(0, 2);
        }
        float dato1 = Float.parseFloat(asistencia);
        float dato2 = 100 - dato1;
        PieChart pieChart = (PieChart) rootview.findViewById(R.id.chart);
        ArrayList<Entry> entradas = new ArrayList<>();
        entradas.add(new Entry(dato1, 0));
        entradas.add(new Entry(dato2, 1));
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Asistencia");
        labels.add("Inasistencia");
        PieDataSet pieDataSet = new PieDataSet(entradas, " ");
        PieData data = new PieData(labels, pieDataSet);
        pieChart.setData(data);
        pieChart.setDescription("% Asistencia al congreso");
        pieChart.setDescriptionTextSize(7);
        pieDataSet.setColors(new int[] {R.color.verde, R.color.red}, getContext());

        return rootview;
    }
}