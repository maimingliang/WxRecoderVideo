package com.maiml.wxrecodervideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.maiml.wechatrecodervideolibrary.recoder.WechatRecoderActivity;

/**
 * @Author maimingliang@gmail.com
 *
 * Created by maimingliang on 2016/9/25.
 */
public class MainActivity extends Activity {


    private static final int REQ_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WechatRecoderActivity.launchActivity(MainActivity.this,REQ_CODE);

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(RESULT_OK == resultCode){


            if(requestCode == REQ_CODE){
                String videoPath = data.getStringExtra(WechatRecoderActivity.VIDEO_PATH);

                play(videoPath);
            }

        }
    }

    private void play(String videoPath) {

        startActivity(new Intent(this,PlayActivity.class).putExtra("path",videoPath));
    }
}
