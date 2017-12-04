package cse605.com.audiosamplerserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView textViewIp,textViewMsg;
    Server server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewIp = (TextView) findViewById(R.id.text_view_ip);
        textViewMsg = (TextView)findViewById(R.id.text_view_msg);
        //Init the class
        //Start the server
        server = new Server(MainActivity.this);

        //Display the IP address of the server device
        textViewIp.setText(server.getIpAddress());

    }
}
