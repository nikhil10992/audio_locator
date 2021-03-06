package cse.a605.com.audio_locator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a605.cse.audio_locator.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public TextView textViewIp,textViewMsg;
    public Button startSyncButton;
    public HashMap<String, Long> offsets;
    Server server;
    SynchronizerClient client;
    static String[] ids = {"ce1a11bb6459491f","7d2195c0675bcd9f","bed43046e2258412","5756a1757d60c6ca"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewIp = (TextView) findViewById(R.id.text_view_ip);
        textViewMsg = (TextView)findViewById(R.id.text_view_msg);
        startSyncButton = (Button) findViewById(R.id.startSyncButton);

        startSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client = new SynchronizerClient(MainActivity.this);
            }
        });
        //Init the class
        //Start the server
        server = new Server(MainActivity.this);

        //Display the IP address of the server device
        textViewIp.setText(server.getIpAddress());
    }
}
