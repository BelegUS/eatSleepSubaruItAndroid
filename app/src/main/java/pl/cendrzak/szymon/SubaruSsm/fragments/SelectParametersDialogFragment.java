package pl.cendrzak.szymon.SubaruSsm.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.cendrzak.szymon.SubaruSsm.AvailableCarParameters;
import pl.cendrzak.szymon.SubaruSsm.R;

public class SelectParametersDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        AvailableCarParameters availableCarParameters = new AvailableCarParameters();
        final HashMap<String,String> availableParametersMap = availableCarParameters.getAvailableParameters();
        final List<String> parametersNames = new ArrayList<String>(availableParametersMap.keySet());

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select parameters to show");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, parametersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedParameterName = (String) parametersNames.get(which);
                String selectedParameterValue = availableParametersMap.get(selectedParameterName);
                Log.d("KittenLog", "Name" + selectedParameterName);
                Log.d("KittenLog", "Value" + selectedParameterValue);

            }
        });

        return builder.create();
    }

}
