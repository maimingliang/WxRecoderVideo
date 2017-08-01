# [WxRecoderVideo](https://github.com/maimingliang/WxRecoderVideo)

#更新


最近在学习FFmpeg，撸了一个[短视频编辑器Cut,有兴趣可以看看](http://www.jianshu.com/p/7e76c1b06933)

录制出来的视频的大小还是比较大，但可以通过ffmpeg 来对视频压缩,

怎么压缩? 

在合成ts后对视频进行了压缩，10s压缩后大概在2M内。压缩代码如下：

```code

protected Boolean compress(boolean mergeFlag) {

		if (!mergeFlag) {
			return mergeFlag;
		}

		String cmd = "ffmpeg -y -i " + mMediaObject.getOutputTempVideoPath() + " -strict -2 -vcodec libx264 -preset ultrafast " +
				"-crf 25 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 360x640 -aspect 9:16 " + mMediaObject.getOutputVideoPath();

		boolean compressFlag = UtilityAdapter.FFmpegRun("", cmd) == 0;

		File file = new File(mMediaObject.getOutputTempVideoPath());
		if(compressFlag){ //压缩成功删除临时文件
			if (file.exists()) {
				file.delete();
			}

			file = new File(mMediaObject.getTsPath());
			if (file.exists()) {
				file.delete();
			}

		}
		return compressFlag;

	}



```



>-crf：这是最重要的一个选项，用于指定输出视频的质量，取值范围是0-51，默认值为23，
数字越小输出视频的质量越高。这个选项会直接影响到输出视频的码率。一般来说，
压制480p我会用20左右，压制720p我会用16-18，1080p我没尝试过。个人觉得，
一般情况下没有必要低于16。最好的办法是大家可以多尝试几个值，每个都压几分钟，看
看最后的输出质量和文件大小，自己再按需选择。





## 简介
基于[VCamera](https://www.vitamio.org/Download/)，仿微信录制短视频

![这里写图片描述](https://github.com/maimingliang/WxRecoderVideo/blob/master/recoder.gif)

## 使用

1)  在build.gradle，添加wechatRecoderVideoLibrary module 。


### 配置manifest

```xml
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

* 配置拍摄视频缓存路径
```xml


       // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/recoder/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/recoder/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/WeChatJuns/");
        }

//		VCamera.setVideoCachePath(FileUtils.getRecorderPath());
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(this);
```

* 注册activity

```xml
<application
    ...
   <activity android:name="com.maiml.wechatrecodervideolibrary.recoder.WechatRecoderActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:hardwareAccelerated="true"
            android:launchMode="singleTop"
            android:screenOrientation="portrait"
            android:theme="@style/CameraTheme"
            />
</application
```


* 调用 WechatRecoderActivity
```code
   WechatRecoderActivity.launchActivity(MainActivity.this,REQ_CODE);
```
*在 onActivityResult Method 接收结果
```code
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
```

##参数配置

###自定义dialog
>拍摄完成需要对视频进行转码，转码过程中弹出的dialog。

让你的Activity implements OnDialogListener 例如：

```code

 public class MainActivity extends AppCompatActivity implements OnDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		WechatRecoderActivity.launchActivity(MainActivity.this,REQ_CODE);
     }

    /**
     * 处理自定义Dialog 的显示
     * @param context 自定义dialog 依赖的Context，注意：自定义dialog的Context 需要使用这个
     *
     */
    @Override
    public void onShowDialog(Context context) {

    }
    /**
     * 处理自定义Dialog 的隐藏
     * @param context 自定义dialog 依赖的Context，注意：自定义dialog的Context 需要使用这个
     *
     */
    @Override
    public void onHideDialog(Context context) {

    }
}

```

###配置参数

|name|format|description|
|:---:|:---:|:---:|
| recoderTimeMax| integer |录制的最长时间
| recoderTimeMin| integer |录制的最短时间
| titleBarCancelTextColor| integer |titleBar取消字体的颜色
| pressBtnColor| integer|按住拍字体的颜色
| pressBtnBg| integer|圆环的颜色
| lowMinTimeProgressColor| integer|Progress小于录制最短时间的颜色
| progressColor| integer|Progress大于录制最短时间的颜色

注意：颜色值均为 十六进制值，例如：0xFFFC2828

![这里写图片描述](https://github.com/maimingliang/WxRecoderVideo/blob/master/img_des1.png)

![这里写图片描述](https://github.com/maimingliang/WxRecoderVideo/blob/master/img_des2.png)


##自定义参数

```code

	 RecoderAttrs attrs = new RecoderAttrs.Builder()
                            .pressBtnColorBg(0xff00ff00)
                            .titleBarCancelTextColor(0xff00ff00)
                            .pressBtnTextColor(0xff00ff00)
                            .build();
        WechatRecoderActivity.launchActivity(MainActivity.this,attrs,REQ_CODE);

```

##Thanks

[VCamera](http://wscdn.miaopai.com/download/VCameraRecorder3.1.pdf)

#关于我

[简书maimingliang](http://www.jianshu.com/users/141bda5f1c5c/latest_articles)
