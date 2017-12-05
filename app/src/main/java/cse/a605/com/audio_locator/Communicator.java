package cse.a605.com.audio_locator;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shahsid104 on 12/3/2017.
 */

public class Communicator {
    private RequestQueue volleyRequestQueue;
    private String receiverURL;
    private String ERROR = "";
    public Communicator(Activity clientActivity, String receiverURL)
    {
        volleyRequestQueue = Volley.newRequestQueue(clientActivity);
        this.receiverURL = receiverURL;
    }

    public boolean sendSyncData(SyncDataObject obj){
            StringRequest sendDataRequest = new StringRequest(Request.Method.GET, receiverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Reponse received", response);
                            ERROR = "";
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            ERROR = error.toString();
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
            };
            sendDataRequest.setRetryPolicy(new DefaultRetryPolicy(0,0,0));
            volleyRequestQueue.add(sendDataRequest);
            return ERROR.equals("");
    }
}
