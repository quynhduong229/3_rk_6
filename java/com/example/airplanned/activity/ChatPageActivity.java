package com.example.airplanned.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.airplanned.R;

import com.example.airplanned.model.GlobalVariables;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatPageActivity extends AppCompatActivity {

    Button b1, b2;
    EditText e2;
    TextView t1;
    ImageView back;

    private WebSocketClient cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);


        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        back = (ImageView) findViewById(R.id.iv_chatPage_back);
        e2 = (EditText) findViewById(R.id.et2);
        t1 = (TextView) findViewById(R.id.tx1);
        t1.setMovementMethod(new ScrollingMovementMethod());


        GlobalVariables gb = (GlobalVariables) getApplicationContext();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),HomePageActivity.class));
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                /**
                 * If running this on an android device, make sure it is on the same network as your
                 * computer, and change the ip address to that of your computer.
                 * If running on the emulator, you can use localhost.
                 */
                String w = "ws://coms-309-063.cs.iastate.edu:8080/support/" + gb.getCurrent().getEmailId();;


                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {

                            Log.d("", "run() returned: " + message);

                            String s = gb.getCurrent().getEmailId();
                            //String b = s.substring(0, s.indexOf('@') - 1);
                            t1.append(message+ "\n");
                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(e2.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });
    }
}
