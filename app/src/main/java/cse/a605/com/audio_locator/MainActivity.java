package cse.a605.com.audio_locator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.a605.cse.audio_locator.R;

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
