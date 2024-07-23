/*
 * SerialCommunicationPlugin.java
 * Created by: Mahad Asghar on 18/08/2022.
 *
 *  Copyright © 2022 BjsSoftSolution. All rights reserved.
 */


package com.example.serial_communication;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** SerialCommunicationPlugin */
public class SerialCommunicationPlugin implements FlutterPlugin, MethodCallHandler {

  private MethodChannel methodChannel;
  private EventChannel eventChannel;
  OpenCommunication communication = new OpenCommunication();
  private CustomEventHandler receiver;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    setupChannels(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getApplicationContext());
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "embedded_serial");
    methodChannel.setMethodCallHandler(this);

  }


  private void setupChannels(BinaryMessenger messenger, Context context) {
    eventChannel = new EventChannel(messenger, "log_tv");
    receiver = new CustomEventHandler();
    eventChannel.setStreamHandler(receiver);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    teardownChannels();
  }


  private void teardownChannels() {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
    receiver.onCancel(null);
    eventChannel = null;
    receiver = null;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    Map<String, String> argments = ((Map<String, String>) call.arguments());
    switch (call.method) {
      case "embeddedSerial/availablePorts":
        final List<String> list = new ArrayList<String>();
        list.addAll(communication.sendDeviceData());
        result.success(list);
        break;
      case "embeddedSerial/openPort1":
        communication.openPort1(argments.get("serialPort"), Boolean.parseBoolean(argments.get("dataFormat")),Integer.parseInt(argments.get("baudRate")));
        break;
      case "embeddedSerial/openPort2":
        communication.openPort2(argments.get("serialPort"), Boolean.parseBoolean(argments.get("dataFormat")),Integer.parseInt(argments.get("baudRate")));
        break;
      case "embeddedSerial/closePort1":
        communication.closePort1();
        break;
      case "embeddedSerial/closePort2":
        communication.closePort2();
        break;
      case "embeddedSerial/sendPort1":
        communication.sendPort1(argments.get("message"));
        break;
      case "embeddedSerial/sendPort2":
        communication.sendPort2(argments.get("message"));
        break;
      case "embeddedSerial/clearLog":
        communication.logChannelPort1 = "";
        communication.logChannelPort2 = "";
        communication.readChannelPort1 = "";
        communication.readChannelPort2 = "";
        receiver.sendEvent(  Map.of("logChannelPort1", communication.logChannelPort1, "readChannelPort1", communication.readChannelPort1, "logChannelPort2", communication.logChannelPort2, "readChannelPort2", communication.readChannelPort2));
        break;
      case "embeddedSerial/clearReadPort1":
        communication.logChannelPort1 = "";
        communication.logChannelPort2 = "";
        communication.readChannelPort1 = "";
        receiver.sendEvent(  Map.of("logChannelPort1", communication.logChannelPort1, "readChannelPort1", communication.readChannelPort1, "logChannelPort2", communication.logChannelPort2, "readChannelPort2", communication.readChannelPort2));
        break;
      case "embeddedSerial/clearReadPort2":
        communication.logChannelPort1 = "";
        communication.logChannelPort2 = "";
        communication.readChannelPort2 = "";
        receiver.sendEvent(  Map.of("logChannelPort1", communication.logChannelPort1, "readChannelPort1", communication.readChannelPort1, "logChannelPort2", communication.logChannelPort2, "readChannelPort2", communication.readChannelPort2));
        break;
      case "embeddedSerial/destroy":
        communication.destroyResources();
        break;
      default:
        result.notImplemented();
    }
  }
}




