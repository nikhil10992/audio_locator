package cse.a605.com.audio_locator;

import java.util.Comparator;

import cse.a605.com.audio_locator.dataobjects.AudioDataObject;

class AudioObjectComparator implements Comparator<AudioDataObject> {

    @Override
    public int compare(AudioDataObject audioDataObject1, AudioDataObject audioDataObject2) {
        int seq1 = 0, seq2 = 0;
            seq1 = audioDataObject1.getSequenceNumber();
            seq2 = audioDataObject2.getSequenceNumber();
        return seq1 - seq2;
    }
}
