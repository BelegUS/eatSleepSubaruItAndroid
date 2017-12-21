package pl.cendrzak.szymon.SubaruSsm.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.cendrzak.szymon.SubaruSsm.AvailableCarParameters;
import pl.cendrzak.szymon.SubaruSsm.AvailableCars;
import pl.cendrzak.szymon.SubaruSsm.NewSubaruValueListener;
import pl.cendrzak.szymon.SubaruSsm.R;
import pl.cendrzak.szymon.SubaruSsm.SubaruParameter;
import pl.cendrzak.szymon.SubaruSsm.SubaruSsm;
import pl.cendrzak.szymon.SubaruSsm.SubaruValue;

public class SpeedometerFragment extends Fragment {
    private SpeedometerGauge speedometer;
    private TextView currentValueText;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    AvailableCarParameters availableCarParameters;

    HashMap<String,SubaruParameter> availableParametersMap;
    List<String> parametersNames;

    public SpeedometerFragment() {
        availableCarParameters = new AvailableCarParameters();
        availableParametersMap = availableCarParameters.getImprezaParameters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SubaruSsm subaruSsm = (SubaruSsm)getActivity();
        View view = inflater.inflate(R.layout.speedometer_fragment_layout, container,
                false);

        currentValueText = (TextView) view.findViewById(R.id.current_value_text);

        // Customize SpeedometerFragment
        speedometer = (SpeedometerGauge) view.findViewById(R.id.speedometer);

         speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
             @Override
             public String getLabelFor(double progress, double maxProgress) {
                 return String.valueOf((int) Math.round(progress));
             }
         });
        speedometer.setLabelTextSize(30);

        //Set Spinner for Value selecting
        final Spinner selectValueSpinner = (Spinner) view.findViewById(R.id.select_value_spinner);

        final Spinner selectCarSpinner = (Spinner) view.findViewById(R.id.select_car_spinner);

        final List<String> carsNames = new ArrayList<String>(new AvailableCars().get().keySet());
        selectCarSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_selectable_list_item, new ArrayList<String>(carsNames)));

        selectCarSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCarKey = (String) carsNames.get(position);
                if (selectedCarKey.equals("impreza")) {
                    availableParametersMap = availableCarParameters.getImprezaParameters();
                } else {
                    availableParametersMap = availableCarParameters.getSvxParameters();
                }
                parametersNames = new ArrayList<String>(availableParametersMap.keySet());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_selectable_list_item, parametersNames);
                selectValueSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectValueSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedParameterKey = (String) parametersNames.get(position);
                subaruSsm.queryCarForParameter(availableParametersMap.get(selectedParameterKey));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Initialize the Stop button with a listener for a click events
        Button endConnectionButton = (Button) view.findViewById(R.id.endConnectionButton);
        endConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                subaruSsm.queryCarForParameter(EngineQuery.getEndConnectionQuery());
            }
        });

        subaruSsm.setOnNewSubaruValueListener(new NewSubaruValueListener() {
            @Override
            public void onNewSubaruValue(SubaruValue subaruValue) {
                speedometer.setSpeed(subaruValue.value);
                currentValueText.setText(subaruValue.value.toString());
            }
        });

        return view;
    }

}
