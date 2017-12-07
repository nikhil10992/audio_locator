package cse.a605.com.audio_locator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a605.cse.audio_locator.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public TextView textViewIp,textViewMsg;
    public Button startSyncButton;
    public static HashMap<String, Long> offsets = new HashMap<>();
    Server server;
    SynchronizerClient client;
    static String[] ids = {"bd54565b67801eed","7d2195c0675bcd9f","bed43046e2258412","5756a1757d60c6ca"};

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
