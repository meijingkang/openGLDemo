package com.example.opengldemo

import android.opengl.EGLConfig
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.opengles.GL10

class FirstOpenGLProjectRenderer : GLSurfaceView.Renderer {

    /**
     * 当Surface被创建的时候,GLSurfaceView会调用这个方法,这发生在应用程序第一次运行的时候,并且当设备被唤醒
     * 或者用户从其他activity切换回来时,这个方法也可能会被调用,意味着这个方法会调用多次
     */
    override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    }

    /**
     * 在Surface被创建以后,每次Surface尺寸发生变化时,都会被GLSurfaceView调用.
     * 在横竖屏切换的时候,Surface尺寸会发生变化
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //设置视口尺寸来告诉OpenGL可以用来渲染的surface的大小
        glViewport(0, 0, width, height)
    }

    /**
     * 当绘制一帧时,这个方法会被GLSurfaceView调用,在这个方法中,我们一定要绘制一些东西,即使只是清空屏幕;
     * 因为,在这个方法返回后,渲染缓冲区会被交换并显示在屏幕上,如果什么都没有画,可能会看到闪烁效果
     */
    override fun onDrawFrame(gl: GL10?) {
        //调用下面方法清空屏幕,这会清除屏幕上的所有颜色,并调用之前glClearColor定义的颜色来填充整个屏幕
        glClear(GL_COLOR_BUFFER_BIT)
    }
}