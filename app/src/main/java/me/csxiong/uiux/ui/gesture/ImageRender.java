package me.csxiong.uiux.ui.gesture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.widget.ProgressBar;

import java.nio.FloatBuffer;

import me.csxiong.camera.opengl.AbsEglRenderer;
import me.csxiong.camera.opengl.FBOEntity;
import me.csxiong.camera.opengl.GlUtil;
import me.csxiong.camera.opengl.Texture2dProgram;
import me.csxiong.camera.opengl.TextureHelper;
import me.csxiong.camera.opengl.VertexHelper;
import me.csxiong.library.base.APP;
import me.csxiong.uiux.R;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;

/**
 * @Desc : 图片Render
 * @Author : csxiong - 2020-03-14
 */
public class ImageRender implements AbsEglRenderer {

    private TextureProgram program;

    private FBOEntity fbo;

    @Override
    public void onSurfaceCreated() {
        //创建基础的定点位置
        //创建基础的program
        program = new TextureProgram();
        Bitmap bitmap = BitmapFactory.decodeResource(APP.get().getResources(), R.mipmap.icon_face);
        fbo = TextureHelper.createFboWithImg(bitmap);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //改变ViewPort
        //修改为全拼命的ViewPort
        GLES20.glViewport(0, 0, width, height);
        program.updateTextureSize(height, width, fbo.height, fbo.width);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClearColor(1, 1, 1, 1);
//        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        program.draw(GlUtil.IDENTITY_MATRIX, GlUtil.IDENTITY_MATRIX, fbo.textureId);
    }
}
