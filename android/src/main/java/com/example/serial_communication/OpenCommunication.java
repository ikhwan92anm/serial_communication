/*
 * OpenCommunication.java
 * Created by: Mahad Asghar on 12/08/2022.
 *
 *  Copyright © 2022 BjsSoftSolution. All rights reserved.
 */


package com.example.serial_communication;


import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android_serialport_api.SerialPortFinder;
import android_serialport_api.port.BaseReader;
import android_serialport_api.port.LogInterceptorSerialPort;
import android_serialport_api.port.SerialApiManager;


public class OpenCommunication  {
    private SerialApiManager spManager;
    private BaseReader baseReader;
    private String currentPort1;
    private String currentPort2;
    public String logChannelPort1 = "";
    public String logChannelPort2 = "";
    public String readChannelPort1 = "";
    public String readChannelPort2 = "";

    List<String> entries = new ArrayList<String>();
    List<String> entryValues = new ArrayList<String>();

    public void destroyResources() {
        spManager.destroyPort1();
        spManager.destroyPort2();
    }

    public void initDataPort1() {

        spManager = SerialApiManager.getInstances().setLogInterceptor(new LogInterceptorSerialPort() {
            @Override
            public void log(@SerialApiManager.Type final String type, final String port, final boolean isAscii, final String log) {
                // Log.d("SerialPortLog", new StringBuffer()
                //         .append("Serial Port ：").append(port)
                //         .append("\ndata format ：").append(isAscii ? "ascii" : "hexString")
                //         .append("\ntype：").append(type)
                //         .append("messages：").append(log).toString());
                //         .append(log).toString());
                // logChannel = (new StringBuffer()
                //         .append(" ").append(port)
                //         .append(" ").append(isAscii ? "ascii" : "hexString")
                //         .append(" ").append(type)
                //         .append("：").append(log)
                //         .append("\n").toString());
                //         .append(log).toString());

                // CustomEventHandler.sendEvent(  Map.of("LogChannel", logChannel, "readChannel", readChannel));
            }

        });
        baseReader = new BaseReader() {
            @Override
            protected void onParse(final String port, final boolean isAscii, final String read) {
                // Log.d("SerialPortRead", new StringBuffer()
                //         .append(port).append("/").append(isAscii ? "ascii" : "hex")
                //         .append(" read：").append(read).append("\n").toString());
                //         .append(read).toString());
                readChannelPort1 = (new StringBuffer()
                //         .append(port).append("/").append(isAscii ? "ascii" : "hex")
                //         .append(" read：").append(read).append("\n").toString());
                        .append(read).toString());

                CustomEventHandler.sendEvent(  Map.of("logChannelPort1", logChannelPort1, "readChannelPort1", readChannelPort1));

            }
        };
    }

    public void initDataPort2() {

        spManager = SerialApiManager.getInstances().setLogInterceptor(new LogInterceptorSerialPort() {
            @Override
            public void log(@SerialApiManager.Type final String type, final String port, final boolean isAscii, final String log) {
                // Log.d("SerialPortLog", new StringBuffer()
                //         .append("Serial Port ：").append(port)
                //         .append("\ndata format ：").append(isAscii ? "ascii" : "hexString")
                //         .append("\ntype：").append(type)
                //         .append("messages：").append(log).toString());
                //         .append(log).toString());
                // logChannelPort2 = (new StringBuffer()
                //         .append(" ").append(port)
                //         .append(" ").append(isAscii ? "ascii" : "hexString")
                //         .append(" ").append(type)
                //         .append("：").append(log)
                //         .append("\n").toString());
                //         .append(log).toString());

                // CustomEventHandler.sendEvent(  Map.of("LogChannel", logChannel, "readChannel", readChannel));
            }

        });
        baseReader = new BaseReader() {
            @Override
            protected void onParse(final String port, final boolean isAscii, final String read) {
                // Log.d("SerialPortRead", new StringBuffer()
                //         .append(port).append("/").append(isAscii ? "ascii" : "hex")
                //         .append(" read：").append(read).append("\n").toString());
                //         .append(read).toString());
                readChannelPort2 = (new StringBuffer()
                //         .append(port).append("/").append(isAscii ? "ascii" : "hex")
                //         .append(" read：").append(read).append("\n").toString());
                        .append(read).toString());

                CustomEventHandler.sendEvent(  Map.of("logChannelPort2", logChannelPort2, "readChannelPort2", readChannelPort2));

            }
        };
    }

    List<String> sendDeviceData() {
        SerialPortFinder mSerialPortFinder = new SerialPortFinder();
        entries = List.of(mSerialPortFinder.getAllDevices());
        entryValues = List.of(mSerialPortFinder.getAllDevicesPath());
        return entryValues;
    }

    public void openPort1(String name, boolean isAscii, int baudRate) {
        initDataPort1();
        String checkPort = name;
        if (TextUtils.isEmpty(checkPort)) {
            return;
        } else if (TextUtils.equals(checkPort, "other")) {
            checkPort = name;
            if (TextUtils.isEmpty(checkPort)) {
                return;
            }
        }

        if (TextUtils.equals(currentPort1, checkPort)) {
            return;
        }

        if (!TextUtils.isEmpty(currentPort1)) {
            // Close the CurrentPort serial port
            spManager.stopSerialPort1(currentPort1);
        }

        if(entryValues.contains(checkPort)){
            currentPort1 = checkPort;
            spManager.startSerialPort1(checkPort, isAscii, baseReader, baudRate);
            changeCodePort1(isAscii);
        }
    }

    public void openPort2(String name, boolean isAscii, int baudRate) {
        initDataPort2();
        String checkPort = name;
        if (TextUtils.isEmpty(checkPort)) {
            return;
        } else if (TextUtils.equals(checkPort, "other")) {
            checkPort = name;
            if (TextUtils.isEmpty(checkPort)) {
                return;
            }
        }

        if (TextUtils.equals(currentPort2, checkPort)) {
            return;
        }

        if (!TextUtils.isEmpty(currentPort2)) {
            // Close the CurrentPort serial port
            spManager.stopSerialPort2(currentPort2);
        }

        if(entryValues.contains(checkPort)){
            currentPort2 = checkPort;
            spManager.startSerialPort2(checkPort, isAscii, baseReader, baudRate);
            changeCodePort2(isAscii);
        }
    }

    public void closePort1() {
        if (!TextUtils.isEmpty(currentPort1)) {
            // currentPort1
            spManager.stopSerialPort1(currentPort1);
            currentPort1 = "";
        }
    }

    public void closePort2() {
        if (!TextUtils.isEmpty(currentPort2)) {
            // currentPort2
            spManager.stopSerialPort2(currentPort2);
            currentPort2 = "";
        }
    }

    public void sendPort1(String sendCommand) {
        if (TextUtils.isEmpty(currentPort1)) {
            return;
        }

        if (TextUtils.isEmpty(sendCommand)) {
            return;
        }
        // send data
        spManager.sendPort1(currentPort1, sendCommand);
    }

    public void sendPort2(String sendCommand) {
        if (TextUtils.isEmpty(currentPort2)) {
            return;
        }

        if (TextUtils.isEmpty(sendCommand)) {
            return;
        }
        // send data
        spManager.sendPort2(currentPort2, sendCommand);
    }

    private void changeCodePort1(boolean isAscii) {
        if (TextUtils.isEmpty(currentPort1)) {
            return;
        }
        spManager.setReadCodePort1(currentPort1, isAscii);
    }

    private void changeCodePort2(boolean isAscii) {
        if (TextUtils.isEmpty(currentPort2)) {
            return;
        }
        spManager.setReadCodePort2(currentPort2, isAscii);
    }
}
