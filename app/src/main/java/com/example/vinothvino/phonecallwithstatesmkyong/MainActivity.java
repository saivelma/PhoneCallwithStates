//Sorry!! The source code of this program is taken from javacodegeeks
//Don't confused by looking the AppName..
package com.example.vinothvino.phonecallwithstatesmkyong;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.PhoneStateListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText e1;
    Button bcall,bdial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = (EditText) findViewById(R.id.editText);
        bcall = (Button) findViewById(R.id.button);
        bdial = (Button) findViewById(R.id.button2);

        MyPhoneStateListener phoneListener = new MyPhoneStateListener();

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        bcall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    String number = e1.getText().toString();
                    String uri = "tel:" + number;

                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    startActivity(callIntent);

                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "your call has failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
        });

        bdial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    String uri = "tel:" + e1.getText().toString();

                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse(uri));
                    startActivity(dialIntent);
                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),"your call has failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }
        });
    }

    private class MyPhoneStateListener extends PhoneStateListener{

        private boolean onCall = false;

        public void onCallStateChanged(int state,String incomingnumber){

            switch (state){

                case TelephonyManager.CALL_STATE_RINGING:

                    Toast.makeText(getApplicationContext(),"Ringing....",Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:

                    Toast.makeText(getApplicationContext(),"onCall....",Toast.LENGTH_LONG).show();
                    onCall= true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:



                    if(onCall == true){

                        Toast.makeText(getApplicationContext(),"Restarting app",Toast.LENGTH_LONG).show();
                        //restarting application
                        Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);

                        onCall= false;
                    }

                    break;
                default:
                    break;
            }
        }

    }
}
