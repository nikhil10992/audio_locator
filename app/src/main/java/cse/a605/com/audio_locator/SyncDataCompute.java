package cse.a605.com.audio_locator;

import android.util.Log;

import cse.a605.com.audio_locator.dataobjects.AudioDataObject;
import cse.a605.com.audio_locator.dataobjects.SyncDataObject;

public class SyncDataCompute {
    private SyncDataObject computeObj;
    private long travelTime;
    private long expectedTimestamp;
    private long offset;
    private MainActivity mainActivity;
    private final String tag = "SyncDataCompute";

    public SyncDataCompute(MainActivity mainActivity, SyncDataObject obj)
    {
        this.mainActivity = mainActivity;
        this.computeObj = obj;
    }

    public void addOffset(long offset, String deviceId)
    {
        this.mainActivity.offsets.put(deviceId, offset);
    }

    public long computeSoundOffset()
    {
        this.expectedTimestamp = this.mainActivity.syncTimestampSound + (2/330);
        this.offset = this.expectedTimestamp - Long.parseLong(computeObj.timestamp);

        Log.d("SYNCDATA",  " :ID: " + computeObj.deviceID + " :TrT: " + travelTime +
                " :ET: " + expectedTimestamp + " :OFF: " + offset
                + " :RT: " + computeObj.timestamp);

        return this.offset;
    }
}
