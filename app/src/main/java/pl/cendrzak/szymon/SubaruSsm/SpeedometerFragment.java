package pl.cendrzak.szymon.SubaruSsm;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;

public class SpeedometerFragment extends Fragment {
    private SpeedometerGauge speedometer;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public SpeedometerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.speedometer_fragment_layout, container,
                false);

        // Customize SpeedometerFragment
        speedometer = (SpeedometerGauge) view.findViewById(R.id.speedometer);

        // Add label converter
        speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        // Initialize the Engine Speed button with a listener for a click events
        Button engineSpeedButton = (Button) view.findViewById(R.id.engineSpeedButton);
        engineSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubaruSsm subaruSsm = (SubaruSsm)getActivity();
                subaruSsm.currentRequestedParameter = SubaruParameter.ENGINE_SPEED;

                // configure value range and ticks
                speedometer.setMaxSpeed(8000);
                speedometer.setMajorTickStep(1000);
                speedometer.setMinorTicks(100);

                // Configure value range colors
                speedometer.addColoredRange(0, 3500, Color.GREEN);
                speedometer.addColoredRange(3500, 5500, Color.YELLOW);
                speedometer.addColoredRange(5500, 8000, Color.RED);

                subaruSsm.sendMessage(subaruSsm.subaruQueryConstructor.getQueryForParameter(subaruSsm.currentRequestedParameter));
            }
        });

        // Initialize the Engine Temp button with a listener for a click events
        Button engineTempButton = (Button) view.findViewById(R.id.engineTempButton);
        engineTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubaruSsm subaruSsm = (SubaruSsm)getActivity();
                subaruSsm.currentRequestedParameter = SubaruParameter.ENGINE_TEMP;

                // configure value range and ticks
                speedometer.setMaxSpeed(200);
                speedometer.setMajorTickStep(20);
                speedometer.setMinorTicks(1);

                // Configure value range colors
                speedometer.addColoredRange(0, 75, Color.RED);
                speedometer.addColoredRange(75, 125, Color.GREEN);
                speedometer.addColoredRange(125, 200, Color.RED);

                subaruSsm.sendMessage(subaruSsm.subaruQueryConstructor.getQueryForParameter(subaruSsm.currentRequestedParameter));
            }
        });

        // Initialize the Engine Load button with a listener for a click events
        Button engineLoadButton = (Button) view.findViewById(R.id.engineLoadButton);
        engineLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubaruSsm subaruSsm = (SubaruSsm)getActivity();
                subaruSsm.currentRequestedParameter = SubaruParameter.ENGINE_LOAD;

                // configure value range and ticks
                speedometer.setMaxSpeed(300);
                speedometer.setMajorTickStep(10);
                speedometer.setMinorTicks(1);

                // Configure value range colors
                speedometer.addColoredRange(0, 35, Color.GREEN);
                speedometer.addColoredRange(35, 70, Color.YELLOW);
                speedometer.addColoredRange(70, 100, Color.RED);

                subaruSsm.sendMessage(subaruSsm.subaruQueryConstructor.getQueryForParameter(subaruSsm.currentRequestedParameter));
            }
        });

        // Initialize the Stop button with a listener for a click events
        Button endConnectionButton = (Button) view.findViewById(R.id.endConnectionButton);
        endConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubaruSsm subaruSsm = (SubaruSsm)getActivity();
                subaruSsm.currentRequestedParameter = SubaruParameter.END_CONNECTION;

                subaruSsm.sendMessage(subaruSsm.subaruQueryConstructor.getQueryForParameter(subaruSsm.currentRequestedParameter));
            }
        });

        return view;
    }

}
