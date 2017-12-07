package cse.a605.com.audio_locator;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


public class Server {
    MainActivity mainActivity;
    private DirectionComputer directionComputer;
    ServerSocket serverSocket;
    static final int socketServerPORT = 8080;
    static String[] ids = {"ce1a11bb6459491f","7d2195c0675bcd9f","bed43046e2258412","5756a1757d60c6ca"};
    List<String> idsArr = Arrays.asList(ids);
    public Server(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        directionComputer = new DirectionComputer(mainActivity,4);
        //For testing
       // directionComputer.initDummyData();
        //if(directionComputer.checkQueueForSameSequenceNumber()){
         //   directionComputer.calculateDirection();
        // }
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
            DataInputStream dataInputStream = null;
            try{
                serverSocket = new ServerSocket(socketServerPORT);

                while(true){
                    Socket socket = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String payload = bufferedReader.readLine();
                    JSONObject jsonObject = new JSONObject(payload);
//                    Log.d("SHITSTORM", jsonObject.getString("sequenceNumber") + "|" + jsonObject.getString("deviceId") + "|" +
//                            jsonObject.getString("timestamp"));

                    //Map ids
                    int index = idsArr.indexOf(jsonObject.getString("deviceId"));
                    jsonObject.put("id",index + 1 + "");
                    AudioDataObject audioDataObject = new AudioDataObject(jsonObject.getString("deviceId"),jsonObject.getString("timestamp"),
                            Integer.parseInt(jsonObject.getString("sequenceNumber")),
                            Integer.parseInt(jsonObject.getString("id")));
                    //Check if necessary inputs are recieved, fire the compute thread if so
                    directionComputer.addToQueue(audioDataObject);
                    if(directionComputer.checkQueueForSameSequenceNumber()){
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
                    if(count == 100)
                        break;
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