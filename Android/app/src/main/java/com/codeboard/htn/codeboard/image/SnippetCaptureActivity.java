package com.codeboard.htn.codeboard.image;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.codeboard.htn.codeboard.CodeBoard;
import com.codeboard.htn.codeboard.editor.CodeEditorActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.codeboard.htn.codeboard.R;

import java.io.IOException;

public final class SnippetCaptureActivity extends AppCompatActivity {

    private static final String SCRIPT_KEY = "SCRIPT";
    private static final int  REQUEST_CAMERA_PERMISSION_ID = 101;
    private final TextRecognizer TRANSLATOR = new TextRecognizer.Builder(CodeBoard.getContext()).build();
    private StringBuilder scriptBuilder;

    private CameraSource cameraSource;
    private TextView textView;
    private SurfaceView cameraView;
    private FloatingActionButton acceptTranslateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_snippet);

        cameraView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView);
        acceptTranslateBtn = findViewById(R.id.translationAccept);

        acceptTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareScript();
            }
        });

        initCameraSource();
    }

    private void prepareScript() {
        Intent editorIntent = new Intent(this, CodeEditorActivity.class);
        editorIntent.putExtra(SCRIPT_KEY, scriptBuilder.toString());
        startActivity(editorIntent);
    }

    private void initCameraSource() {
        if (!TRANSLATOR.isOperational()) {
            displayError("HARDCODED ERROR MESSAGE");
        }

        cameraSource = new CameraSource.Builder(CodeBoard.getContext(), TRANSLATOR)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {

                    if (ActivityCompat.checkSelfPermission(CodeBoard.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SnippetCaptureActivity.this, new String[]{ Manifest.permission.CAMERA },
                                REQUEST_CAMERA_PERMISSION_ID);
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        TRANSLATOR.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
                cameraSource.stop();
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            scriptBuilder = new StringBuilder();
                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                scriptBuilder.append(item.getValue());
                                scriptBuilder.append("\n");
                            }
                            textView.setText(scriptBuilder.toString());
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CAMERA_PERMISSION_ID) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                if (ActivityCompat.checkSelfPermission(CodeBoard.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cameraSource.start(cameraView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayError(String msg) {

    }
}
