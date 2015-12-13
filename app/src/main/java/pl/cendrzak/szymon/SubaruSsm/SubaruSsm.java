/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.cendrzak.szymon.SubaruSsm;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cardiomood.android.speedometer.SpeedometerView;

import java.util.Arrays;

/**
 * This is the main Activity that displays the current chat session.
 */
public class SubaruSsm extends Activity {
    // Debugging
    private static final String TAG = "SubaruSsm";

    // Message types sent from the BluetoothConnectionService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothConnectionService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
    private Button engineSpeedButton;
    private Button engineTempButton;
    private Button engineLoadButton;
    private Button endConnectionButton;
    private SpeedometerView speedometer;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothConnectionService mChatService = null;
    //Data requested from Subaru
    private SubaruParameter currentRequestedParameter;
    //Subaru Query Constructor instance to generate queries to be sent to Subaru
    private SubaruQueryConstructor subaruQueryConstructor = new SubaruQueryConstructor();
    //Subaru Data Processor instance to process data received from Subaru
    private SubaruDataProcessor subaruDataProcessor = new SubaruDataProcessor(subaruQueryConstructor);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Customize SpeedometerView
        speedometer = (SpeedometerView) findViewById(R.id.speedometer);

        // Add label converter
        speedometer.setLabelConverter(new SpeedometerView.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothConnectionService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {

        // Initialize the Engine Speed button with a listener for a click events
        engineSpeedButton = (Button) findViewById(R.id.engineSpeedButton);
        engineSpeedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRequestedParameter = SubaruParameter.ENGINE_SPEED;

                // configure value range and ticks
                speedometer.setMaxSpeed(8000);
                speedometer.setMajorTickStep(1000);
                speedometer.setMinorTicks(100);

                // Configure value range colors
                speedometer.addColoredRange(0, 3500, Color.GREEN);
                speedometer.addColoredRange(3500, 5500, Color.YELLOW);
                speedometer.addColoredRange(5500, 8000, Color.RED);

                sendMessage(subaruQueryConstructor.getQueryForParameter(currentRequestedParameter));
            }
        });

        // Initialize the Engine Temp button with a listener for a click events
        engineTempButton = (Button) findViewById(R.id.engineTempButton);
        engineTempButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRequestedParameter = SubaruParameter.ENGINE_TEMP;

                // configure value range and ticks
                speedometer.setMaxSpeed(200);
                speedometer.setMajorTickStep(20);
                speedometer.setMinorTicks(1);

                // Configure value range colors
                speedometer.addColoredRange(0, 75, Color.RED);
                speedometer.addColoredRange(75, 125, Color.GREEN);
                speedometer.addColoredRange(125, 200, Color.RED);

                sendMessage(subaruQueryConstructor.getQueryForParameter(currentRequestedParameter));
            }
        });

        // Initialize the Engine Load button with a listener for a click events
        engineLoadButton = (Button) findViewById(R.id.engineLoadButton);
        engineLoadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRequestedParameter = SubaruParameter.ENGINE_LOAD;

                // configure value range and ticks
                speedometer.setMaxSpeed(300);
                speedometer.setMajorTickStep(10);
                speedometer.setMinorTicks(1);

                // Configure value range colors
                speedometer.addColoredRange(0, 35, Color.GREEN);
                speedometer.addColoredRange(35, 70, Color.YELLOW);
                speedometer.addColoredRange(70, 100, Color.RED);

                sendMessage(subaruQueryConstructor.getQueryForParameter(currentRequestedParameter));
            }
        });

        // Initialize the Stop button with a listener for a click events
        endConnectionButton = (Button) findViewById(R.id.endConnectionButton);
        endConnectionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRequestedParameter = SubaruParameter.END_CONNECTION;

                sendMessage(subaruQueryConstructor.getQueryForParameter(currentRequestedParameter));
            }
        });

        // Initialize the BluetoothConnectionService to perform bluetooth connections
        mChatService = new BluetoothConnectionService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) {
            sendMessage(subaruQueryConstructor.getQueryForParameter(SubaruParameter.END_CONNECTION));
            mChatService.stop();
        }
    }

    /**
     * Sends a message.
     * @param commandToSend  An array of bytes to send.
     */
    private void sendMessage(byte[] commandToSend) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothConnectionService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (commandToSend.length > 0) {
            // Tell the BluetoothConnectionService to write
            mChatService.write(commandToSend);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    // The Handler that gets information back from the BluetoothConnectionService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:

                switch (msg.arg1) {
                case BluetoothConnectionService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    break;
                case BluetoothConnectionService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothConnectionService.STATE_LISTEN:
                case BluetoothConnectionService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                break;
            case MESSAGE_READ:
                byte[] tempBuf = (byte[]) msg.obj;
                byte[] readArray = Arrays.copyOfRange(tempBuf, 0, msg.arg1);
                subaruDataProcessor.appendToReceivedData(readArray);
                for(int i=0; i<msg.arg1; i++) {
                    if(currentRequestedParameter == null) {
                        break;
                    }
                    int castedByte = subaruDataProcessor.processDataForParameter(currentRequestedParameter);
                    if(castedByte != -1) {
                        Log.d("Subaru value:",Integer.toString(castedByte));
                        Log.d("Requested:", currentRequestedParameter.toString());
                        speedometer.setSpeed(castedByte, 0, 0);
                    }
                }
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BluetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        }
        return false;
    }
}