package net.ailoli.video.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2016/12/17/017.
 */

public class Second extends Activity{
    private Button startbtn;
    private VideoView videoView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        initiview();
    }
    public void initiview(){
        startbtn = (Button) findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new ButtonListener());
        videoView = (videoView)findViewById(R.id.videoview);
    }
    public class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
//            Toast.makeText(Second.this,"123",Toast.LENGTH_SHORT).show();
//            Log.d("koko","123");
            Thread thread = new Thread(new NetWorkThread());
            thread.start();
        }
    }
    public class myhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    }
    public class NetWorkThread implements Runnable{
        @Override
        public void run() {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://www.kiseon.net/").build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                Log.d("fanhui:",response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
