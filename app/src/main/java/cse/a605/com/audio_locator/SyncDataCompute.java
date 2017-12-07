package cse.a605.com.audio_locator;

import android.util.Log;

/**
 * Created by shahsid104 on 12/5/2017.
 */

public class SyncDataCompute {
    private SyncDataObject computeObj;
    private long travelTime;
    private long expectedTimestamp;
    private long offset;
    private final String tag = "SyncDataCompute";

    public SyncDataCompute(SyncDataObject obj)
    {
        this.computeObj = obj;
    }

    public long computeOffset()
    {
        this.travelTime = (Long.parseLong(computeObj.senderReceivedTimestamp) - Long.parseLong(computeObj.senderTimestamp)) / 2;
        this.expectedTimestamp = Long.parseLong(computeObj.senderTimestamp) + travelTime;
        this.offset = this.expectedTimestamp - Long.parseLong(computeObj.receiverTimestamp);

        Log.d(tag, "Travel time : " + String.valueOf(travelTime));
        Log.d(tag, "Expected Timestamp time : " + String.valueOf(expectedTimestamp));
        Log.d(tag, "Final Offset : " + String.valueOf(offset));

        return this.offset;

    }
}
