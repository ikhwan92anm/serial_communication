/*
 * CustomEventHandler.java
 * Created by: Mahad Asghar on 12/08/2022.
 *
 *  Copyright © 2022 BjsSoftSolution. All rights reserved.
 */

package com.example.serial_communication;

import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.content.BroadcastReceiver;
import io.flutter.plugin.common.EventChannel;
import java.util.Map;

class CustomEventHandler extends BroadcastReceiver implements EventChannel.StreamHandler {
    static EventChannel.EventSink events;
    static final Handler mainHandler = new Handler(Looper.getMainLooper());


    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.events = events;

    }

    @Override
    public void onCancel(Object arguments) {
        events = null;
    }

    static void sendEvent(final Map<String,String> response) {
        // Runnable runnable = () -> events.success(response);
        // mainHandler.post(runnable);
        if (response != null && events != null && mainHandler != null) {
            mainHandler.post(new Runnable() {
              @Override
              public void run() {
                try {
                    events.success(response);
                } catch (Exception e) {
                    Log.e("CustomEventHandler", "Exception sendEvent: " + e.toString());
                }    
              }
            });
        }  
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (events != null) {
            events.success("Hello");
        }
    }

}