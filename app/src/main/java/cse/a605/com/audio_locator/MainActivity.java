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
    static String[] ids = {"602dfa77bf37d4e6","bd54565b67801eed","d39b47ad3df0a08e","240495edef394ee1"};

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
