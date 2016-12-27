package net.ailoli.video.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText pwd;
    private TextView notice;
    private Button login;
    private ProgressDialog progressDialog;
    boolean statues = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在加载");
        progressDialog.show();
//        progressDialog.show(MainActivity.this,"提示","正在加载");

        initview();
    }
    public Handler myhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    progressDialog.dismiss();
                    statues = true;
                    Toast.makeText(MainActivity.this,"接口验证成功",Toast.LENGTH_SHORT).show();
                    notice.setText("接口状态：正常");
                    break;
                case 0:
                    progressDialog.dismiss();
                    statues = false;
                    notice.setText("接口状态：异常");
                    Toast.makeText(MainActivity.this,"接口验证失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    public void initview(){
        pwd = (EditText) findViewById(R.id.pwdEdit);
        notice = (TextView) findViewById(R.id.notice);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new ButtonListenter());
        notice.setText("正在获取接口状态");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                progressDialog.dismiss();
                progressDialog.setMessage("正在验证接口是否可用...");
                progressDialog.show();

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.kiseon.net/").build();
                Message message = new Message();
                try {

                    Response response = okHttpClient.newCall(request).execute();
                    if(!response.isSuccessful()){
                        return;
                    }
                    if(response.code() == 200){
                        message.what = 1;
                        myhandler.sendMessage(message);
//                        progressDialog.dismiss();
                    }
                    else {
                        message.what = 0;
                        myhandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = 0;
                    myhandler.sendMessage(message);
                }

            }
        });
        thread.start();
    }

    class ButtonListenter implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(!statues){
                Toast.makeText(MainActivity.this,"接口获取失败无法登陆",Toast.LENGTH_SHORT).show();
                return;
            }
            if(v.getId() == R.id.login){
                if(pwd.getText().toString().equals("Ailoli")){
                    Toast.makeText(MainActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(MainActivity.this,Second.class);
                    startActivity(it);
                    return;
                }
                else {
                    Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(MainActivity.this,"点按钮啊，不是点这里！！！",Toast.LENGTH_SHORT).show();


        }
    }
}
