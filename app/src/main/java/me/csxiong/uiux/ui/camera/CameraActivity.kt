package me.csxiong.uiux.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import me.csxiong.ipermission.IPermission
import me.csxiong.ipermission.PermissionResult
import me.csxiong.ipermission.PermissionResultCallBack
import me.csxiong.library.base.BaseActivity
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ActivityCameraBinding

@Route(path = "/main/camera",name = "相机预览界面")
class CameraActivity : BaseActivity<ActivityCameraBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_camera
    }

    override fun initView() {
        IPermission(this)
                .request(Manifest.permission.CAMERA)
                .excute(object : PermissionResultCallBack {

                    override fun onPermissionResult(results: MutableList<PermissionResult>?) {
                        startCamera()
                    }

                    override fun onPreRequest(requestList: MutableList<String>?) {
                    }
                })
    }

    @SuppressLint("RestrictedApi")
    fun takePicture(){

    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(mViewBinding.viewFinder.createSurfaceProvider())
                    }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this@CameraActivity, cameraSelector, preview)
            } catch (e: Exception) {

            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun initData() {
    }
}