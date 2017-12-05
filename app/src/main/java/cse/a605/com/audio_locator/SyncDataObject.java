package cse.a605.com.audio_locator;

/**
 * Created by shahsid104 on 12/3/2017.
 */

public class SyncDataObject {
    int messageId;
    String senderTimestamp;
    String receiverTimestamp;

    SyncDataObject(int messageId, String senderTimestamp)
    {
        this.messageId = messageId;
        this.senderTimestamp = senderTimestamp;
    }
}
