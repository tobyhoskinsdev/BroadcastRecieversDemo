package examples.aaronhoskins.com.broadcastrecieversdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String SEND_BROADCAST = "examples.aaronhoskins.com.broadcastrecieversdemo.SEND_BROADCAST";
    MyBroadcastReceiver myBroadcastReceiver;
    IntentFilter intentFilter;
    EditText etUserInput;
    BroadcastReceiver receiver;
    IntentFilter intentFilterAiplane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserInput = findViewById(R.id.etUserInput);

        intentFilter = new IntentFilter();//instantiated intent filter
        myBroadcastReceiver = new MyBroadcastReceiver();//instantiated receiver
        intentFilter.addAction(SEND_BROADCAST); //Add a action to intent filter

        intentFilterAiplane = new
                IntentFilter("android.intent.action.AIRPLANE_MODE_CHANGED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("TAG_AIRPLANE", "onReceive: Airplane Mode has Changed");

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        //register the receiver
        registerReceiver(myBroadcastReceiver,intentFilter);
        registerReceiver(receiver, intentFilterAiplane);
        //register locally
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister the receiver
        unregisterReceiver(myBroadcastReceiver);
        //unregister Locally
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver);
    }

    public void onClick(View view) {
        String userInput = etUserInput.getText().toString();
        switch(view.getId()) {
            case R.id.btnSendBroadcast:
                if(userInput != null) {
                    Intent intent = new Intent(SEND_BROADCAST);//create intent to send the broadcast
                    intent.putExtra("message", userInput);//insert our message into the intent
                    sendBroadcast(intent);//send standard broadcast
                }
                break;
            case R.id.btnSendBroadcastLocal:
                if(userInput != null) {
                    Intent intent = new Intent(SEND_BROADCAST);//create intent to send the broadcast
                    intent.putExtra("message", userInput);//insert our message into the intent
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);//send local broadcast
                }
                break;
            case R.id.btnSendBroadcastWithPerm:
                if(userInput != null) {
                    Intent intent = new Intent(SEND_BROADCAST);//create intent to send the broadcast
                    intent.putExtra("message", userInput);//insert our message into the intent
                    sendBroadcast(intent, "my.permission");//send broadcast with permissions requirement

                }
        }
    }
}
