package pl.cendrzak.szymon.SubaruSsm.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.cendrzak.szymon.SubaruSsm.AvailableCarParameters;
import pl.cendrzak.szymon.SubaruSsm.R;
import pl.cendrzak.szymon.SubaruSsm.SubaruSsm;

public class SelectParametersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SubaruSsm subaruSsm = (SubaruSsm)getActivity();
        View view = inflater.inflate(R.layout.select_parameters_layout, container,
                false);

        LinearLayout viewLayout = (LinearLayout)view.findViewById(R.id.select_parameters_layout);
        Spinner spinner = new Spinner(view.getContext());
        spinner.setLayoutParams(new Spinner.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        AvailableCarParameters availableCarParameters = new AvailableCarParameters();
        HashMap<String,String> availableParametersMap = availableCarParameters.getAvailableParameters();
        List<String> parametersNames = new ArrayList<String>(availableParametersMap.keySet());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, parametersNames);
        spinner.setAdapter(adapter);


        viewLayout.addView(spinner);

        return view;
    }

}
