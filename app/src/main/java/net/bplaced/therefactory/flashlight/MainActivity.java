package net.bplaced.therefactory.flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    CameraManager.TorchCallback toggleTorchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
            super.onTorchModeChanged(cameraId, enabled);
            try {
                mCameraManager.setTorchMode(cameraId, !enabled);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    };
    private CameraManager.TorchCallback enableTorchCallback = new CameraManager.TorchCallback() {
        @Override
        public void onTorchModeChanged(@NonNull String cameraId, boolean enabled) {
            super.onTorchModeChanged(cameraId, enabled);
            try {
                mCameraManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        turnOnFlashLight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        toggleFlashLight(null);
    }

    private void fireOnTorchModeChanged(CameraManager.TorchCallback torchCallback) {
        mCameraManager.registerTorchCallback(torchCallback, null);
        mCameraManager.unregisterTorchCallback(torchCallback);
    }

    private void turnOnFlashLight() {
        fireOnTorchModeChanged(enableTorchCallback);
    }

    public void toggleFlashLight(View view) {
        fireOnTorchModeChanged(toggleTorchCallback);
    }
}
