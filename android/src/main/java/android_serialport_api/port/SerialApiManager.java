/*
 * SerialApiManager.java
 * Created by: Mahad Asghar on 18/08/2022.
 *
 *  Copyright © 2022 BjsSoftSolution. All rights reserved.
 */



package android_serialport_api.port;
import android.util.Log;

import androidx.annotation.StringDef;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class SerialApiManager {

    private final String TAG = "ADan_SerialPortManager";
    private static SerialApiManager instance;
    private HashMap serialPorts1;
    private HashMap serialPorts2;
    private LogInterceptorSerialPort logInterceptor;

    public static final String port = "port";
    public static final String read = "read";
    public static final String write = "write";
    public static final String append = "append";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({port, read, write, append})
    public @interface Type {
    }

    private SerialApiManager() {
        serialPorts1 = new HashMap();
        serialPorts2 = new HashMap();
    }

    public static SerialApiManager getInstances() {
        if (instance == null) {
            synchronized (SerialApiManager.class) {
                if (instance == null) {
                    instance = new SerialApiManager();
                }
            }
        }
        return instance;
    }

    public SerialApiManager setLogInterceptor(LogInterceptorSerialPort logInterceptor) {
        this.logInterceptor = logInterceptor;
        Iterator iter = serialPorts1.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            SerialApi1 serialPort = (SerialApi1) entry.getValue();
            serialPort.setLogInterceptor(logInterceptor);
        }
        return this;
    }

    /**
     *
     * @param port    Serial Number
     * @param isAscii coded format true:ascii false HexString
     * @param reader  Read data monitor
     */
    public boolean startSerialPort1(String port, boolean isAscii, BaseReader reader, int baudRate) {
        return startSerialPort1(port, isAscii, baudRate, 0, reader);
    }

    public boolean startSerialPort2(String port, boolean isAscii, BaseReader reader, int baudRate) {
        return startSerialPort2(port, isAscii, baudRate, 0, reader);
    }

    /**
     *
     * @param port      Serial Number
     * @param baudRate Baud rate
     * @param flags    sign
     * @param reader   Read data monitor
     */
    public boolean startSerialPort1(String port, boolean isAscii, int baudRate, int flags, BaseReader reader) {
        SerialApi1 serial;
        if (serialPorts1.containsKey(port)) {
            serial = (SerialApi1) serialPorts1.get(port);
        } else {
            Log.d("SerialPort1",port);
            serial = new SerialApi1(port, isAscii, baudRate, flags);
            serialPorts1.put(port, serial);
        }
        serial.setLogInterceptor(logInterceptor);
        return serial.open(reader);
    }

    public boolean startSerialPort2(String port, boolean isAscii, int baudRate, int flags, BaseReader reader) {
        SerialApi2 serial;
        if (serialPorts2.containsKey(port)) {
            serial = (SerialApi2) serialPorts2.get(port);
        } else {
            Log.d("SerialPort2",port);
            serial = new SerialApi2(port, isAscii, baudRate, flags);
            serialPorts2.put(port, serial);
        }
        serial.setLogInterceptor(logInterceptor);
        return serial.open(reader);
    }
    /**
     * @param port
     * @param reader
     */
    public void setReaderPort1(String port, BaseReader reader) {
        if (serialPorts1.containsKey(port)) {
            SerialApi1 serial = (SerialApi1) serialPorts1.get(port);
            serial.setReader(reader);
        }
    }

    public void setReaderPort2(String port, BaseReader reader) {
        if (serialPorts2.containsKey(port)) {
            SerialApi2 serial = (SerialApi2) serialPorts2.get(port);
            serial.setReader(reader);
        }
    }

    public void setReadCodePort1(String port, boolean isAscii) {
        if (serialPorts1.containsKey(port)) {
            SerialApi1 serial = (SerialApi1) serialPorts1.get(port);
            serial.setReadCode(isAscii);
        }
    }

    public void setReadCodePort2(String port, boolean isAscii) {
        if (serialPorts2.containsKey(port)) {
            SerialApi2 serial = (SerialApi2) serialPorts2.get(port);
            serial.setReadCode(isAscii);
        }
    }

    public void sendPort1(String port, String cmd) {
        if (serialPorts1.containsKey(port)) {
            SerialApi1 serial = (SerialApi1) serialPorts1.get(port);
            serial.write(cmd);
        }
    }

    public void sendPort2(String port, String cmd) {
        if (serialPorts2.containsKey(port)) {
            SerialApi2 serial = (SerialApi2) serialPorts2.get(port);
            serial.write(cmd);
        }
    }

    // not being used for now, but it exists in original plugin
    /**
    public void send(String port, boolean isAscii, String cmd) {
        if (serialPorts.containsKey(port)) {
            SerialApi serial = (SerialApi) serialPorts.get(port);
            serial.write(isAscii, cmd);
        }
    }
    */

    /**
     * Close a serial port
     *
     * @param port
     */
    public void stopSerialPort1(String port) {
        if (serialPorts1.containsKey(port)) {
            SerialApi1 serial = (SerialApi1) serialPorts1.get(port);
            serial.close();
            serialPorts1.remove(serial);
            System.gc();
        }
    }

    public void stopSerialPort2(String port) {
        if (serialPorts2.containsKey(port)) {
            SerialApi2 serial = (SerialApi2) serialPorts2.get(port);
            serial.close();
            serialPorts2.remove(serial);
            System.gc();
        }
    }

    /**
     * Has it been turned on
     *
     * @param port
     * @return
     */
    public boolean isStartPort1(String port) {
        if (serialPorts1.containsKey(port)) {
            SerialApi1 serial = (SerialApi1) serialPorts1.get(port);
            return serial.isOpen();
        }
        return false;
    }

    public boolean isStartPort2(String port) {
        if (serialPorts2.containsKey(port)) {
            SerialApi2 serial = (SerialApi2) serialPorts2.get(port);
            return serial.isOpen();
        }
        return false;
    }

    /**
     * Destruction of resources
     */
    public void destroyPort1() {
        Log.e(TAG, "SerialPort destroyed");
        try {
            Iterator iter = serialPorts1.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                SerialApi1 serial = (SerialApi1) entry.getValue();
                serial.close();
                serialPorts1.remove(serial);
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroyPort2() {
        Log.e(TAG, "SerialPort destroyed");
        try {
            Iterator iter = serialPorts1.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                SerialApi2 serial = (SerialApi2) entry.getValue();
                serial.close();
                serialPorts1.remove(serial);
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
