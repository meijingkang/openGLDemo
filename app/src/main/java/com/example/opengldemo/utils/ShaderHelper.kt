package com.example.opengldemo.utils

import android.opengl.GLES20.*
import android.util.Log

class ShaderHelper {
    companion object{
        private const val TAG = "ShaderHelper"
        fun compileVertexShader(shaderCode: String): Int {
            return compileShader(GL_VERTEX_SHADER, shaderCode)
        }
        fun compileFragmentShader(shaderCode: String): Int {
            return compileShader(GL_FRAGMENT_SHADER, shaderCode)
        }
        private fun compileShader(type: Int, shaderCode: String): Int {
            val shaderObjectId = glCreateShader(type)
            if (shaderObjectId == 0) {
                Log.i(TAG, "compileShader: create shaderObjectId error")
                return 0 ;
            }
            glShaderSource(shaderObjectId, shaderCode)
            glCompileShader(shaderObjectId)
            val compileStatus = IntArray(1)
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
            Log.i(TAG, "compileShader: ${glGetShaderInfoLog(shaderObjectId)} , compileStatus: ${compileStatus[0]}")

            if (compileStatus[0] == 0) {
                glDeleteShader(shaderObjectId)
                throw java.lang.RuntimeException("编译出错")
            }
            return shaderObjectId
            

        }

        fun linkProgram(fragmentShader: Int, vertexShader: Int): Int {

            val glProgramId = glCreateProgram();
            if (glProgramId == 0) {
                Log.i(TAG, "linkProgram: error")
                throw java.lang.RuntimeException("linkProgram error")
            }
            glAttachShader(glProgramId, fragmentShader)
            glAttachShader(glProgramId, vertexShader)
            glLinkProgram(glProgramId)
            val linkStatus = IntArray(1)
            glGetProgramiv(glProgramId, GL_LINK_STATUS, linkStatus, 0)
            if (linkStatus[0] == 0) {
                throw java.lang.RuntimeException("link error")
            }
            Log.i(TAG, "linkProgram: success")
            return glProgramId
        }

        fun validateProgram(programId: Int): Boolean {

            glValidateProgram(programId)
            val validateStatus = IntArray(1)
            glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0)
            return validateStatus[0] != 0
        }
    }
}