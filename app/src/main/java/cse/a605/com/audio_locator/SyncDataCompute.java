package cse.a605.com.audio_locator;

import android.util.Log;

public class SyncDataCompute {
    private SyncDataObject computeObj;
    private long travelTime;
    private long expectedTimestamp;
    private long offset;
    private MainActivity mainActivity;
    private AudioDataObject obj;
    private final String tag = "SyncDataCompute";

    public SyncDataCompute(SyncDataObject obj)
    {
        this.computeObj = obj;
    }

    public SyncDataCompute(MainActivity mainActivity, AudioDataObject obj){
        this.mainActivity = mainActivity;
        this.obj = obj;
    }

    public void findOffset()
    {
        String deviceId = obj.getDeviceId();
        mainActivity.offsets.put(deviceId, computeSoundOffset());

    }

    public long computeSoundOffset()
    {
        this.expectedTimestamp = this.mainActivity.syncTimestampSound + (1/330);
        this.offset = this.expectedTimestamp - Long.parseLong(obj.getTimestamp());

        Log.d("SYNCDATA",  " :ID: " + computeObj.deviceID + " :TrT: " + travelTime +
                " :ET: " + expectedTimestamp + " :OFF: " + offset
                + " :RT: " + computeObj.timestamp);

        return this.offset;
    }
}
