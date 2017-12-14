package cse.a605.com.audio_locator;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SynchronizerClient {
    private HashMap<String, String> idToIP = new HashMap<>();
    MainActivity mainActivity;
    Timer t = new Timer();

    SynchronizerClient(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public void generateSound()
    {

    }
}
