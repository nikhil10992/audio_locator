package cse.a605.com.audio_locator;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import cse.a605.com.audio_locator.dataobjects.AudioDataObject;
import cse.a605.com.audio_locator.dataobjects.SyncDataObject;


public class Server {
    MainActivity mainActivity;
    private DirectionComputer directionComputer;
    ServerSocket serverSocket;
    static final int socketServerPORT = 8080;
    static Integer runningMaxSequence = -1;
    static Integer synchronizedMaxSequence = -1;
    List<String> idsArr;

    public Server(MainActivity mainActivity){

        this.mainActivity = mainActivity;
        this.idsArr = Arrays.asList(mainActivity.ids);
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        directionComputer = new DirectionComputer(mainActivity,2);

    }

    public void onDestroy(){

        if (serverSocket != null) {

            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread{

        int count = 0;
        @Override
        public void run() {

            try{

                serverSocket = new ServerSocket(socketServerPORT);

                while(true){

                    Socket socket = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String payload = bufferedReader.readLine();
                    JSONObject jsonObject = new JSONObject(payload);

                    if(jsonObject.getString("TYPE").equalsIgnoreCase("Sync"))
                    {
                        SyncDataObject SyncObj = new Gson().fromJson(payload, SyncDataObject.class);
                        SyncDataCompute computingOffset = new SyncDataCompute(mainActivity, SyncObj);
                        long offset = computingOffset.computeSoundOffset();
                        computingOffset.addOffset(offset, SyncObj.deviceID);
                    }

                    else {
                        //Map ids
                        AudioDataObject audioDataObject = new Gson().fromJson(payload, AudioDataObject.class);
                        runningMaxSequence = Math.max(audioDataObject.getSequenceNumber(), runningMaxSequence);

                        if (audioDataObject.getSequenceNumber() < synchronizedMaxSequence) continue;
                    /*if(audioDataObject.getSequenceNumber() > maxSequenceNumber  )
                        maxSequenceNumber = audioDataObject.getSequenceNumber();
                    */

                        //Check if necessary inputs are recieved, fire the compute thread if so
                        directionComputer.addToQueue(audioDataObject);
                        if (directionComputer.checkQueueForSameSequenceNumber()) {
                            directionComputer.calculateDirection();
                        }
                  /*  if(directionComputer.getMinimumCountInQueues() > 15){
                        directionComputer.dequeueFromQueue();
                        if(directionComputer.checkQueueForSameSequenceNumber())
                            directionComputer.calculateDirection(mainActivity.offsets);
                    }
                  */
                        count++;
//                    message += "\n DeviceId = "+ audioDataObject.getId() +" SequenceNumber " + audioDataObject.getSequenceNumber() + " Timestamp = " + audioDataObject.getTimestamp();
//                    mainActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mainActivity.textViewMsg.setText(message);
//                        }
//                    });
                    }
                }

            }catch (Exception e){

                e.printStackTrace();
            }
        }

    }


    public String getIpAddress() {

        String ip = "";

        try {

            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();

            while (enumNetworkInterfaces.hasMoreElements()) {

                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();

                while (enumInetAddress.hasMoreElements()) {

                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}