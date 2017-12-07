package cse.a605.com.audio_locator;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shahsid104 on 12/3/2017.
 */

public class SynchronizerClient {
    private HashMap<String, String> idToIP = new HashMap<>();
    MainActivity mainActivity;

    SynchronizerClient(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        setIdToIP();
        sendMessage();
    }

    public void setIdToIP() {
        idToIP.put(MainActivity.ids[0], "192.168.1.229");
        idToIP.put(MainActivity.ids[1], "192.168.1.186");
        idToIP.put(MainActivity.ids[2], "192.168.1.188");
        idToIP.put(MainActivity.ids[3], "192.168.1.187");

    }

    public void sendMessage()
    {
        for(Map.Entry<String, String> iterator : idToIP.entrySet())
        {
            String receiverIP = iterator.getValue();

            SyncDataObject messageObj = new SyncDataObject(iterator.getKey());

            Communicator communicator = new Communicator(mainActivity, messageObj, receiverIP);
            communicator.start();
        }
    }
}
