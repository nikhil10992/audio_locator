package cse.a605.com.audio_locator;

public class SyncDataObject {
    String deviceID;
    String timestamp;
    String type = "Christian";

    SyncDataObject(String messageId, String timestamp)
    {
        this.deviceID = messageId;
        this.timestamp = timestamp;
    }
}
