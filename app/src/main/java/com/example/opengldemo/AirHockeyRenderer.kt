package com.example.opengldemo;

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.example.opengldemo.utils.ShaderHelper
import com.example.opengldemo.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
const val POSITION_COMPONENT_COUNT = 2
const val BYTES_PER_FLOAT = 4
private const val TAG = "AirHockeyRenderer"
const val U_COLOR= "u_Color"
const val A_POSITION = "a_Position"
class AirHockeyRenderer(val context: Context) : GLSurfaceView.Renderer {
        lateinit var  vertexData:FloatBuffer
        var uColorLocation :Int =0;
       var aPositionLocation = 0
    init {

        /**
         * 效果图上的所有顶点的位置
         *
         * (-0.5f,0.5f)------------------(0.5f,0.5f)
         *
         * |             -(0f,0.25f)                |
         * |                                        |
         * (-0.5f,0f)----------------------(0.5f,0f)
         * |                                        |
         * |            -(0f,-0.25f)                |
         * (-0.5f,-0.5f)------------------(0.5f,-0.5f)
         */
        val tableVertices = floatArrayOf(
            // Triangle 1
          -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f,0.5f,

            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            -0.5f, 0f,
            0.5f, 0f,

            0f, -0.25f,
            0f, 0.25f
        )
        vertexData = ByteBuffer.allocateDirect(tableVertices.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()

    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        val fragmentShaderSource =
            TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)
        val vertexShaderSource =
            TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader)

        Log.i(TAG, "fragmentShaderSource:$fragmentShaderSource")

        Log.i(TAG, "vertexShaderSource:$vertexShaderSource")

        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val programId = ShaderHelper.linkProgram(fragmentShader, vertexShader)
        val isSuccess = ShaderHelper.validateProgram(programId)
        Log.i(TAG, "validateProgram:$isSuccess")
        uColorLocation = glGetUniformLocation(programId, U_COLOR)
        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0 , vertexData)
        glEnableVertexAttribArray(aPositionLocation)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //设置视口尺寸来告诉OpenGL可以用来渲染的surface的大小
        glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT);

        //draw Desktop

        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        //draw line
        glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f)
        glDrawArrays(GL_LINES, 6, 2)

        //draw point

        glUniform4f(uColorLocation , 0f, 0f, 1f, 1f)
        glDrawArrays(GL_POINTS, 8, 1)

        //draw mallet red

        glUniform4f(uColorLocation , 0f, 0f, 1f, 1f)
        glDrawArrays(GL_POINTS, 9, 1)

    }

}

