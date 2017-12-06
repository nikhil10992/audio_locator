package cse.a605.com.audio_locator;

/**
 * Created by shahsid104 on 12/5/2017.
 */

public class SyncDataCompute {
    private SyncDataObject computeObj;
    private long travelTime;
    private long expectedTimestamp;
    private long offset;

    public SyncDataCompute(SyncDataObject obj)
    {
        this.computeObj = obj;
    }

    public long computeOffset()
    {
        this.travelTime = Long.parseLong(computeObj.senderReceivedTimestamp) - Long.parseLong(computeObj.senderTimestamp) / 2;
        this.expectedTimestamp = Long.parseLong(computeObj.senderTimestamp) + travelTime;
        this.offset = Math.abs(this.expectedTimestamp - Long.parseLong(computeObj.receiverTimestamp));
        return this.offset;
    }
}
