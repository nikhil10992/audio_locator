package cse.a605.com.audio_locator.dataobjects;

public class AudioDataObject {
    private String timestamp;
    private int sequenceNumber;
    private String deviceId;

    public AudioDataObject(String _deviceId, String _timestamp, int _sequenceNumber) {
        this.timestamp = _timestamp;
        this.sequenceNumber = _sequenceNumber;
        this.deviceId = _deviceId;
    }

    public String getTimestamp() { return timestamp;}

    public String getDeviceId() {
        return deviceId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

}
