package com.maiml.wxrecodervideo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * @Author maimingliang@gmail.com
 *
 * Created by maimingliang on 2016/9/25.
 */
public class PlayActivity extends Activity {


    private VideoView videoView;
    private String videoPath;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        videoView = (VideoView) findViewById(R.id.videoview);

        videoPath = getIntent().getStringExtra("path");

        play(videoPath);
//        mVideoView.setVideoPath(videoPath);
     }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void play(final String path) {


        mediaController = new MediaController(this);
        videoView.setVideoPath(path);
        // 设置VideView与MediaController建立关联
        videoView.setMediaController(mediaController);
//        // 设置MediaController与VideView建立关联
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.INVISIBLE);
        // 让VideoView获取焦点
//        videoView.requestFocus();
        // 开始播放
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                Log.e("tag"," width = " +mp.getVideoWidth());
                Log.e("tag"," height = " +mp.getVideoHeight());
//                mp.start();
//                mp.setLooping(true);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                videoView.setVideoPath(path);
//                videoView.start();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {


                return false;
            }
        });
    }



}
