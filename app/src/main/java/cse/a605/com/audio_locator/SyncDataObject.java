package cse.a605.com.audio_locator;

/**
 * Created by shahsid104 on 12/3/2017.
 */

public class SyncDataObject {
    String messageId;
    String senderTimestamp;
    String receiverTimestamp;
    String senderReceivedTimestamp;

    SyncDataObject(String messageId) {
        this.messageId = messageId;
    }
}
