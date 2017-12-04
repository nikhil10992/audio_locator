package cse605.com.audiosamplerserver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.lang.Integer;

import static java.sql.Types.INTEGER;

/**
 * Created by deepak on 07-Nov-17.
 */

public class DirectionComputerUsingTimeDifference {
    double speedOfSound = 343.0D;
    double distBetweenReceivers = 0.4D;
    float xCoordinate = 5;
    float yCoordinate = 5;
    static int[] allMicInputPresent = new int[4];
    public static Queue<JSONObject> InputQ = new PriorityQueue<JSONObject>(100, new JSONObjComparator());
    public static boolean readInput(JSONObject data) throws JSONException {
        allMicInputPresent[Integer.parseInt(data.getString("id"))-1] = 1;
        InputQ.add(data);
        return true;
    }
    public JSONArray getInput(int sequenceNumber) throws JSONException {
        JSONArray inputArr = new JSONArray();
        for(int i = 1; i < 5; i++)
        {
            JSONObject obj = getMicInputById(i,sequenceNumber);
            if(obj == null)
            {
                return null;
            }
            inputArr.put(obj);
        }
        return inputArr;
    }

    public JSONObject getMicInputById(int id, int sequenceNumber) throws JSONException {
        //Read till you get the most updated value
        JSONObject temp = InputQ.poll();
        if(temp.getString("sequenceNumber").equals("1") && temp.getString("id").equals(id+""))
        {
            return temp;
        }
        while(InputQ.peek() != null && InputQ.peek().getString("id").equals(id + ""))
        {
            temp = InputQ.poll();
            if(temp.getString("sequenceNumber").equals("1"))
                return temp;
        }
        Log.e("getMicInput", "Error no data found");
        return null;
        /*
        while(InputQ.peek() != null && temp.getString("id").equals(id+""))
        {
            temp = InputQ.poll();
        }
        while(InputQ.peek() != null && temp.getString("id").equals(id+""))
        {
            if(temp.getString("sequenceNumber").equals("1"))
                temp = InputQ.poll();
        }
        if(temp.getString("id").equals(id+"") == false)
        {
            Log.e("getMicInput", "Error no data found");
            return null;
        }
        return temp;*/
    }

    public double[] calculateDirection(int sequenceNumber) throws JSONException {
        //Check if you have received all the input
        for(int i = 0; i < 4; i++)
        {
            if(allMicInputPresent[i] == 0)
            {
                Log.e("CalculateDirection","Error, not every input present");
                return null;
            }
        }
        JSONArray input = getInput(sequenceNumber);
        if(input == null)
        {
            return null;
        }
        double[] coordinates = new double[2];
        ArrayList<Float> angles = new ArrayList<Float>();
        for(int i = 0; i < 4; i = i + 2)
        {
            angles.add(findAngleOfArrival(input, i));
        }
        coordinates[0] = (xCoordinate*(Math.tan(angles.get(0)) + 1.0))/(Math.tan(angles.get(0) - Math.tan(angles.get(1))));
        coordinates[1] = (yCoordinate*  Math.tan(angles.get(0))*(1.0 + Math.tan(angles.get(1))))/(Math.tan(angles.get(0) - Math.tan(angles.get(1))));
        Log.d("XCoordinates", coordinates[0] + "");
        Log.d("YCoordinates", coordinates[1] + "");
        //reset flag
        allMicInputPresent = new int[4];
        return coordinates;
    }

    public Float findAngleOfArrival(JSONArray input, int pos) throws JSONException {
        double t1 = Double.parseDouble(input.getJSONObject(pos).getString("timestamp"));
        double t2 = Double.parseDouble(input.getJSONObject(pos + 1).getString("timestamp"));
        //Math.abs?
        Log.d("Calculations = ",(t1-t2)+"");
        double cal = ((t1 - t2)*speedOfSound/(distBetweenReceivers*1000));
        Log.d("Calculations = ",cal+"");
        double angle = Math.acos(cal);
        return (float)angle;
    }
}

class JSONObjComparator implements Comparator<JSONObject> {
    public int compare(JSONObject obj1, JSONObject obj2)
    {
        int id1 = 0, id2 = 0;
        try
        {
            id1 = Integer.parseInt(obj1.getString("id"));
            id2 = Integer.parseInt(obj2.getString("id"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return id1 - id2;
    }
    public boolean equals(JSONObject obj)
    {
        return true;
    }
}