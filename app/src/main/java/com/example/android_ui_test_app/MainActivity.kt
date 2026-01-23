package com.example.android_ui_test_app

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.scanbot.sdk.ui_v2.document.DocumentScannerActivity
import io.scanbot.sdk.ui_v2.document.configuration.AcknowledgementMode
import io.scanbot.sdk.ui_v2.document.configuration.DocumentScanningFlow

class MainActivity : AppCompatActivity() {

    private lateinit var documentScannerResult: ActivityResultLauncher<DocumentScanningFlow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        documentScannerResult =
            registerForActivityResult(DocumentScannerActivity.ResultContract()) {
                    document  ->

            }

        val scannerButton = findViewById<Button>(R.id.btn_open_scanner)

        scannerButton.setOnClickListener {
            runSinglePageScanner()
        }
    }

    private fun runSinglePageScanner() {
        val config = DocumentScanningFlow().apply {
            this.outputSettings.pagesScanLimit = 1
            this.screens.camera.acknowledgement.acknowledgementMode = AcknowledgementMode.NONE
        }
        documentScannerResult.launch(config)
    }
}