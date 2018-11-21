package com.example.mikkel.workoutplanner.utils;

import com.example.mikkel.workoutplanner.Interfaces.Notification;

import java.util.ArrayList;

public class EventHandler
{
    private ArrayList<Notification> listeners = new ArrayList<>();

    public void subscribe(Notification listener)
    {
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    public void unsubscribe(Notification listener)
    {
        if(listeners.contains(listener))
            listeners.remove(listener);
    }

    public void notifyAllListeners(Object data)
    {
        for (int i = listeners.size() - 1; i >= 0 ; i--) {
            Notification listener =  listeners.get(i);
            if(listener != null)
                listener.onNotification(data);
            else
                listeners.remove(i);
        }
    }
}
