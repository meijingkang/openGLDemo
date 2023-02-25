package com.example.opengldemo

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.opengldemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val glSurfaceView: GLSurfaceView by lazy {
        GLSurfaceView(this) //创建GLSurfaceView
    }
    private var rendererSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //1.检查系统是否支持OpenGL ES 2.0
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        val supportsEs2 =
            activityManager.deviceConfigurationInfo.reqGlEsVersion >= 0x20000 //模拟器上不支持,所以还要以下的判断条件了
                    || (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                    && (Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")));

        if (supportsEs2) {
            //2.配置渲染的表面rendering surface
            glSurfaceView.setEGLContextClientVersion(2) //Request an OpenGL ES 2.0 compatible context
            //3.设置自定义的render渲染器
            glSurfaceView.setRenderer(FirstOpenGLProjectRenderer())
            rendererSet = true
        } else {
            /*
            * This is where you could create an OpenGL ES 1.x compatible
            * renderer if you wanted to support both ES 1 and ES 2. Since we're
            * not doing anything, the app will crash if the device doesn't
            * support OpenGL ES 2.0. If we publish on the market, we should
            * also add the following to AndroidManifest.xml:
            *
            * <uses-feature android:glEsVersion="0x00020000"
            * android:required="true" />
            *
            * This hides our app from those devices which don't support OpenGL
            * ES 2.0.
            */
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG)
                .show()
            return
        }
        //最后展示glSurfaceView
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            glSurfaceView.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            glSurfaceView.onResume()
        }
    }



}