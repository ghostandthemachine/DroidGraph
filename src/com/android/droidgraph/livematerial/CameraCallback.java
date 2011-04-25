package com.android.droidgraph.livematerial;

import android.hardware.Camera;

public interface CameraCallback {
        public abstract void onPreviewFrame(byte[] data, Camera camera);
        public abstract void onShutter();
        public abstract void onRawPictureTaken(byte[] data, Camera camera);
        public abstract void onJpegPictureTaken(byte[] data, Camera camera);
        public abstract String onGetVideoFilename();
}