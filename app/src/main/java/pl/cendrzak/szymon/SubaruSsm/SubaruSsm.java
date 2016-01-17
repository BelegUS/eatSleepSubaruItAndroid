package pl.cendrzak.szymon.SubaruSsm;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.cendrzak.szymon.SubaruSsm.fragments.SpeedometerFragment;

/**
 * This is the main Activity that displays the current chat session.
 */
public class SubaruSsm extends ActionBarActivity {

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

    // Navigation Drawer
    private ListView mNavigationList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mNavigationToggle;
    private CharSequence mTitle;
    NavigationAdapter navigationAdapter;
    List<NavigationItem> navigationItemList;

    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothConnectionService mConnectionService = null;
    //Data requested from Subaru
    public SubaruParameter currentRequestedParameter;
    //Subaru Data Processor instance to process data received from Subaru
    public SubaruDataProcessor subaruDataProcessor;
    //Listeners for Subaru Data received and processed
    ArrayList<NewSubaruValueListener> newSubaruValueListeners = new ArrayList<NewSubaruValueListener> ();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mNavigationList = (ListView) findViewById(R.id.navigation_drawer);
        navigationItemList = new ArrayList<NavigationItem>();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addNavigationItems();
        setupNavigationDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button scanButton = (Button) findViewById(R.id.scan);
        scanButton.setOnClickListener(new ScanButtonClickListener());

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void addNavigationItems() {
        navigationItemList.add(new NavigationItem("gauge", "Gauge view", R.drawable.abc_ic_menu_copy_mtrl_am_alpha));
        navigationItemList.add(new NavigationItem("graph", "Graph view", R.drawable.abc_ic_commit_search_api_mtrl_alpha));
        navigationItemList.add(new NavigationItem("export", "Export to file", R.drawable.abc_ic_menu_copy_mtrl_am_alpha));
        navigationItemList.add(new NavigationItem("exit", "Exit", R.drawable.abc_ic_clear_mtrl_alpha));

        navigationAdapter = new NavigationAdapter(this, R.layout.custom_drawer_item, navigationItemList);

        mNavigationList.setAdapter(navigationAdapter);
        mNavigationList.setOnItemClickListener(new NavigationItemClickListener());
    }

    public void setOnNewSubaruValueListener(NewSubaruValueListener listener)
    {
        // Store the listener object
        this.newSubaruValueListeners.add(listener);
    }

    public void showItem(int position) {

        NavigationItem item = (NavigationItem)mNavigationList.getItemAtPosition(position);
        Fragment fragment;
        Bundle args = new Bundle();

        switch (item.getId()) {
//            case "gauge":
//                fragment = new SpeedometerFragment();
//                args.putString(SpeedometerFragment.ITEM_NAME, navigationItemList.get(position)
//                        .getItemName());
//                args.putInt(SpeedometerFragment.IMAGE_RESOURCE_ID, navigationItemList.get(position)
//                        .getImgResID());
//                break;
            case "exit":
                finish();
            default:
                fragment = new SpeedometerFragment();
                args.putString(SpeedometerFragment.ITEM_NAME, navigationItemList.get(position)
                        .getItemName());
                args.putInt(SpeedometerFragment.IMAGE_RESOURCE_ID, navigationItemList.get(position)
                        .getImgResID());

                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                        .commit();
                break;
        }

        mNavigationList.setItemChecked(position, true);
        setTitle(navigationItemList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mNavigationList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void setupNavigationDrawer() {
        mNavigationToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mNavigationList.bringToFront();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mNavigationToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mNavigationToggle);
    }

    private class NavigationItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            showItem(position);
        }
    }

    private class ScanButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            // Launch the DeviceListActivity to see devices and do scan
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                // Otherwise, setup the chat session
            } else {
                if (mConnectionService == null) {
                    setupCarConnection();
                }
                showDeviceListIntent();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mNavigationToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mNavigationToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mConnectionService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mConnectionService.getState() == BluetoothConnectionService.STATE_NONE) {
                // Start the Bluetooth chat services
                mConnectionService.start();
            }
        }
    }

    private void setupCarConnection() {
        // Initialize the BluetoothConnectionService to perform bluetooth connections
        mConnectionService = new BluetoothConnectionService(this, mHandler);

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
        if (mConnectionService != null) {
            sendEndConnectionQuery();
            mConnectionService.stop();
        }
    }

    public void sendEndConnectionQuery()
    {
        if (mConnectionService.getState() != BluetoothConnectionService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Tell the BluetoothConnectionService to write
        mConnectionService.write(SubaruQuery.getEndConnectionQuery());

        // Reset out string buffer to zero and clear the edit text field
        mOutStringBuffer.setLength(0);

    }

    /**
     * Sends a Subaru query.
     *
     * @param subaruQuery Instance of SubaruQuery to be sent.
     */
    public void sendSubaruQuery(SubaruQuery subaruQuery) {
        // Check that we're actually connected before trying anything
        if (mConnectionService.getState() != BluetoothConnectionService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        this.subaruDataProcessor.setRequestedSubaruQuery(subaruQuery);

        byte[] queryToSend = subaruQuery.getQuery();
        // Check that there's actually something to send
        if (queryToSend.length > 0) {
            // Tell the BluetoothConnectionService to write
            mConnectionService.write(queryToSend);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    private void showDeviceListIntent()
    {
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    // The Handler that gets information back from the BluetoothConnectionService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case MESSAGE_STATE_CHANGE:
//
//                    switch (msg.arg1) {
//                        case BluetoothConnectionService.STATE_CONNECTED:
////                            mTitle.setText(R.string.title_connected_to);
////                            mTitle.append(mConnectedDeviceName);
//                            break;
//                        case BluetoothConnectionService.STATE_CONNECTING:
////                            mTitle.setText(R.string.title_connecting);
//                            break;
//                        case BluetoothConnectionService.STATE_LISTEN:
//                        case BluetoothConnectionService.STATE_NONE:
////                            mTitle.setText(R.string.title_not_connected);
//                            break;
//                    }
//                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_READ:
                    byte[] tempBuf = (byte[]) msg.obj;
                    byte[] readArray = Arrays.copyOfRange(tempBuf, 0, msg.arg1);
                    subaruDataProcessor.appendToReceivedData(readArray);
                    for (int i = 0; i < msg.arg1; i++) {
                        if (currentRequestedParameter == null) {
                            break;
                        }
                        int castedByte = subaruDataProcessor.processData();
                        if (castedByte != -1) {
                            SubaruValue subaruValue = new SubaruValue();
                            subaruValue.value = castedByte;
                            for (NewSubaruValueListener newSubaruValueListener : newSubaruValueListeners) {
                                newSubaruValueListener.onNewSubaruValue(subaruValue);
                            }
                        }
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    String mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
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
                    mConnectionService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session and search for connection
                    setupCarConnection();
                    showDeviceListIntent();
                } else {
                    // User did not enable Bluetooth or an error occurred
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
        // Activate the navigation drawer toggle
        if (mNavigationToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}