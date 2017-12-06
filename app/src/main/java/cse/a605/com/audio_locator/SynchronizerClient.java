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
    int messageCounter = 0;
    private final String PORT = "8080";
    Activity mainActivity;

    SynchronizerClient(Activity mainActivity)
    {
        this.mainActivity = mainActivity;
        setIdToIP();
        sendMessage();
    }

    public void setIdToIP() {
        idToIP.put(MainActivity.ids[0], "abc");
        idToIP.put(MainActivity.ids[1], "abc");
        idToIP.put(MainActivity.ids[2], "abc");
        idToIP.put(MainActivity.ids[3], "abc");

    }

    public void sendMessage()
    {
        /*Iterator it = idToIP.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry
        }*/

        for(Map.Entry<String, String> it : idToIP.entrySet())
        {
            SyncDataObject messageObj = new SyncDataObject(messageCounter++,String.valueOf(System.currentTimeMillis()));
            String receiverURL = "http://" + it.getValue() + ":" + PORT;

            Communicator communicator = new Communicator(mainActivity, receiverURL);
            communicator.sendSyncData(messageObj);

            Log.d("Updated Message Obj", messageObj.toString());

            it.getKey();
            it.getValue();
        }
    }
}
