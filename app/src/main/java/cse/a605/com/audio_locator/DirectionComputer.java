package cse.a605.com.audio_locator;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by premh on 08-Nov-17.
 */

public class DirectionComputer {
    private static final float SPEED_OF_SOUND = 330.0F;
    private static final float DISTANCE_BETWEEN_RECIEVERS = 0.4F;
    public static ArrayList<Queue<AudioDataObject>> priorityQueueArrayList;
    private Context context;
    private int numberOfListeningDevice;
    public DirectionComputer(Context _context, int _numberOfListeningDevice){
        this.context = _context;
        this.numberOfListeningDevice = _numberOfListeningDevice;
        priorityQueueArrayList = new ArrayList<>();
        init();
    }
    private void init(){
        for(int i=0;i<numberOfListeningDevice;i++){
            Queue<AudioDataObject> queue = new PriorityQueue<>(100,new AudioObjectComparator());
            priorityQueueArrayList.add(queue);
        }
    }

    public void initDummyData(){
        addToQueue(new AudioDataObject("1510107523680",1,1));
        addToQueue(new AudioDataObject("1510107523640",1,2));
        addToQueue(new AudioDataObject("1510107525450",1,3));
        addToQueue(new AudioDataObject("1510107525425",1,4));
    }
    public void addToQueue(AudioDataObject audioDataObject){
        int id = audioDataObject.getId();
        Queue<AudioDataObject> queue = priorityQueueArrayList.get(id-1);
        queue.add(audioDataObject);
    }

    public boolean checkQueueForSameSequenceNumber(){
        int prev_seq = -1;          //Init
        for(Queue<AudioDataObject> queue : priorityQueueArrayList){
            if(queue.peek() == null) return false;
            if(prev_seq == -1)
                prev_seq = queue.peek().getSequenceNumber();            //First sequenceNumber
            else if(queue.peek().getSequenceNumber() != prev_seq)
                return false;           //SequenceNumber not same in any of the priority Queue
        }
        return true;
    }

    public int getMinimumCountInQueues(){
        int min_count = Integer.MAX_VALUE;
        for(Queue<AudioDataObject> queue : priorityQueueArrayList){
            if(queue.size() < min_count){
                min_count = queue.size();
            }
        }
        return min_count;
    }

    public void dequeueFromQueue(){
        int[] seqNumbers = new int[numberOfListeningDevice];
        for(int i=0;i<numberOfListeningDevice;i++){
            Queue<AudioDataObject> queue = priorityQueueArrayList.get(i);
            seqNumbers[i] = queue.peek().getSequenceNumber();
        }
        int[] maxSeqWithQueueId = findMaxSequenceNumber(seqNumbers);
        int maxSequenceNumber = maxSeqWithQueueId[0];
        int indexOfQueueWithMax = maxSeqWithQueueId[1];
        for(int i=0;i<numberOfListeningDevice;i++){
            if(i==indexOfQueueWithMax) continue;
            Queue<AudioDataObject> queue = priorityQueueArrayList.get(i);
            int sequenceNumber = queue.peek().getSequenceNumber();
            while(sequenceNumber <= maxSequenceNumber && queue.peek()!=null){
                queue.poll();
            }
        }
    }
    private int[] findMaxSequenceNumber(int[] sequenceNumbers){
        int[] result = new int[2];
        int maxSequenceNumber = Integer.MIN_VALUE;
        int index = -1;
        for(int i = 0;i<sequenceNumbers.length;i++)
        {
            int seq = sequenceNumbers[i];
            if(seq > maxSequenceNumber) {
                maxSequenceNumber = seq;
                index  = i;
            }
        }
        result[0] = maxSequenceNumber;
        result[1] = index;
        return result;
    }

    public void calculateDirection(){
        final int X_CORDINATE = 5;
        final int Y_CORDINATE = 5;
        ArrayList<Float> thetaAngles = new ArrayList<>();
        double finalCordinates[] = new double[2];
        for(int i = 0; i < numberOfListeningDevice; i = i + 2)
        {
            AudioDataObject a1 = priorityQueueArrayList.get(i).poll();
            AudioDataObject a2 = priorityQueueArrayList.get(i+1).poll();
            thetaAngles.add(findAngleOfArrival(a1,a2));
        }
        finalCordinates[0] = ( X_CORDINATE * ( Math.tan(thetaAngles.get(0)) + 1 ) ) / ( Math.tan(thetaAngles.get(0)) - Math.tan(thetaAngles.get(1)) );
        finalCordinates[0] = ( Y_CORDINATE * Math.tan(thetaAngles.get(0)) * (1 + Math.tan(thetaAngles.get(1))) ) / ( Math.tan(thetaAngles.get(0)) - Math.tan(thetaAngles.get(1)) );
        Log.d("XCoordinates", finalCordinates[0] + "");
        Log.d("YCoordinates", finalCordinates[1] + "");
    }

    public Float findAngleOfArrival(AudioDataObject audioDataObject1, AudioDataObject audioDataObject2){
        double t1 = Double.parseDouble(audioDataObject1.getTimestamp());
        double t2 = Double.parseDouble(audioDataObject2.getTimestamp());
        //Math.abs?
        Log.d("Calculations = ",(t1-t2)+"");
        double diff = Math.abs(t1-t2);
        if(diff > 3)
            diff = diff/1000;
        double cal = (diff*SPEED_OF_SOUND/(DISTANCE_BETWEEN_RECIEVERS*1000));
        Log.d("Calculations Degrees = ",cal+"");
        double angle = Math.toDegrees(Math.acos(cal));
        Log.d("Calculations Angles = ",angle+" And Device = "+ audioDataObject1.getId() + " and "+ audioDataObject2.getId());
        return (float)angle;
    }
}
