package cse.a605.com.audio_locator.dataobjects;

public class SyncDataObject {
    public String deviceID;
    public String timestamp;
    public String type = "Christian";

    SyncDataObject(String messageId, String timestamp)
    {
        this.deviceID = messageId;
        this.timestamp = timestamp;
    }
}
