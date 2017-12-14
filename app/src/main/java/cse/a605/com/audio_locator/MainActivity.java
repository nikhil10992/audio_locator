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
    public static long syncTimestampSound;
    Server server;
    SoundSynchronizerClient client;
    static String[] ids = {"7d2195c0675bcd9f","bed43046e2258412"};
    static HashMap<String, Integer> idToIndex = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIp = findViewById(R.id.text_view_ip);
        textViewMsg = findViewById(R.id.text_view_msg);
        startSyncButton = findViewById(R.id.startSyncButton);

        setIdToIndex();

        startSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client = new SoundSynchronizerClient();
                client.send_chirp();
                syncTimestampSound = System.currentTimeMillis();
                Server.synchronizedMaxSequence = Server.runningMaxSequence;
            }
        });
        //Start the server
        server = new Server(MainActivity.this);

        //Display the IP address of the server device
        textViewIp.setText(server.getIpAddress());
    }

    void setIdToIndex()
    {
        idToIndex.put("7d2195c0675bcd9f", 0);
        idToIndex.put("bed43046e2258412", 1);
    }
}
