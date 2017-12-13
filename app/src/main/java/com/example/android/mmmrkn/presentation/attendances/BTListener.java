package com.example.android.mmmrkn.presentation.attendances;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import timber.log.Timber;

public class BTListener implements BluetoothProfile.ServiceListener {
    private static final int REQUEST_ENABLE_BT = 53126;

    private BluetoothHeadset mBluetoothHeadset;
    private Callback mCallback;

    BTListener ( Activity activity, Callback callback ) {
        this.mCallback = callback;
        if ( checkBTState ( activity ) ) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();
            bluetoothAdapter.getProfileProxy ( activity, this, BluetoothProfile.HEADSET );
        }
    }

    private boolean checkBTState ( Activity activity ) {
        BluetoothManager manager = (BluetoothManager) activity.getSystemService ( Context.BLUETOOTH_SERVICE );
        if ( manager == null ) {
            return false;
        }
        BluetoothAdapter mBluetoothAdapter = manager.getAdapter ();
        if ( mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled () ) {
            Intent enableBtIntent = new Intent ( BluetoothAdapter.ACTION_REQUEST_ENABLE );
            activity.startActivityForResult ( enableBtIntent, REQUEST_ENABLE_BT );
            return false;
        }
        return true;
    }

    @Override
    public void onServiceConnected ( int profile, BluetoothProfile proxy ) {
        if ( profile != BluetoothProfile.HEADSET ) {
            return;
        }
        mBluetoothHeadset = (BluetoothHeadset) proxy;
        List <BluetoothDevice> devices = mBluetoothHeadset.getConnectedDevices ();
        for ( BluetoothDevice device : devices ) {
            if ( mBluetoothHeadset.startVoiceRecognition ( device ) ) {
                mCallback.onBTDeviceConnected ();
                break;
            }
        }

    }

    @Override
    public void onServiceDisconnected ( int profile ) {
        if ( profile != BluetoothProfile.HEADSET ) {
            return;
        }
        Timber.e ( "device disconnected" );
        mBluetoothHeadset = null;
        mCallback.onBTDeviceDisconnected ();
    }

    void stop () {

        mCallback = null;
        if ( mBluetoothHeadset == null ) {
            throw new RuntimeException ( "connected devices don't exist" );
        }

        List <BluetoothDevice> devices = mBluetoothHeadset.getConnectedDevices ();
        if ( devices == null ) {
            throw new RuntimeException ( "connected devices don't exist" );
        }
        for ( BluetoothDevice device : devices ) {
            mBluetoothHeadset.stopVoiceRecognition ( device );
            mBluetoothHeadset = null;
            return;
        }
        throw new RuntimeException ( "used device don't exist" );
    }

    interface Callback {
        void onBTDeviceConnected ();
        void onBTDeviceDisconnected ();
    }
}