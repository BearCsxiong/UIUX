package me.csxiong.uiux.ui.gesture;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

import me.csxiong.camera.opengl.GlThread;
import me.csxiong.camera.opengl.GlUtil;
import me.csxiong.camera.opengl.VertexHelper;
import me.csxiong.library.utils.XResUtils;
import me.csxiong.uiux.R;

public class TextureProgram {

    private int programHandle;
    private int muMVPMatrixLoc;
    private int muTexMatrixLoc;
    private int maPositionLoc;
    private int maTextureCoordLoc;

    private float[] vertexFloats = Arrays.copyOf(VertexHelper.GL_VERTEX_FLOATS, VertexHelper.GL_VERTEX_FLOATS.length);

    private float[] textureFloats = Arrays.copyOf(VertexHelper.TEXTURE_VERTEX_FLOATS, VertexHelper.TEXTURE_VERTEX_FLOATS.length);

    private FloatBuffer vertexFB;

    private FloatBuffer textureFB;

    @GlThread
    public TextureProgram() {
        programHandle = GlUtil.createProgram(XResUtils.getString(R.string.vertex_shader), XResUtils.getString(R.string.fragment_shader));
        muMVPMatrixLoc = GLES20.glGetUniformLocation(programHandle, "mMVPMatrix");
        muTexMatrixLoc = GLES20.glGetUniformLocation(programHandle, "mTexMatrix");
        maPositionLoc = GLES20.glGetAttribLocation(programHandle, "aPosition");
        maTextureCoordLoc = GLES20.glGetAttribLocation(programHandle, "aTexCoordinate");

        vertexFB = ByteBuffer.allocateDirect(vertexFloats.length * VertexHelper.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexFloats);
        vertexFB.position(0);


        textureFB = ByteBuffer.allocateDirect(textureFloats.length * VertexHelper.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureFloats);
        textureFB.position(0);
    }

    public void updateTextureSize(int viewPortHeight, int viewPortWidth, int height, int width) {
        //更新纹理尺寸 保证纹理在ViewPort中居中显示
        //图片尺寸
        float ratio = height >= width ? height / (float) width : width / (float) height;
        float viewPortRatio = viewPortHeight / (float) viewPortWidth;

        int startIndex = height >= width ? 1 : 0;
        for (int i = startIndex; i < vertexFloats.length; i += 2) {
            vertexFloats[i] = vertexFloats[i] / (ratio * viewPortRatio);
        }
    }

    public void draw(float[] mvpMatrix, float[] texMatrix, int textureId) {
        vertexFB.put(vertexFloats);
        vertexFB.position(0);

        textureFB.put(textureFloats);
        textureFB.position(0);

        GLES20.glUseProgram(programHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE20, textureId);

        GLES20.glUniformMatrix4fv(muMVPMatrixLoc, 1, false, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(muTexMatrixLoc, 1, false, texMatrix, 0);

        GLES20.glEnableVertexAttribArray(maPositionLoc);
        GLES20.glVertexAttribPointer(maPositionLoc, 2, GLES20.GL_FLOAT, false, 4 * 2, vertexFB);

        GLES20.glEnableVertexAttribArray(maTextureCoordLoc);
        GLES20.glVertexAttribPointer(maTextureCoordLoc, 2, GLES20.GL_FLOAT, false, 4 * 2, textureFB);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

}
