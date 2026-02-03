package com.example.android_ui_test_app

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.scanbot.common.onSuccess
import io.scanbot.sdk.ScanbotSDK
import io.scanbot.sdk.pdfgeneration.PageSize
import io.scanbot.sdk.pdfgeneration.PdfConfiguration
import io.scanbot.sdk.ui_v2.document.DocumentScannerActivity
import io.scanbot.sdk.ui_v2.document.configuration.AcknowledgementMode
import io.scanbot.sdk.ui_v2.document.configuration.DocumentScanningFlow
import java.io.File

class MainActivity : AppCompatActivity() {

    private val scanbotSdk by lazy { ScanbotSDK(this) }
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

        val createPDFButton = findViewById<Button>(R.id.create_pdf)
        createPDFButton.setOnClickListener {
            createPDF()
        }
    }

    private fun runSinglePageScanner() {
        val config = DocumentScanningFlow().apply {
            this.outputSettings.pagesScanLimit = 1
            this.screens.camera.acknowledgement.acknowledgementMode = AcknowledgementMode.NONE
        }
        documentScannerResult.launch(config)
    }

    private fun createPDF() {
//        val uri = Uri.parse(
//            "android.resource://$packageName/drawable/sample_document.jpg"
//        )

        val externalDir = getExternalFilesDir("scanbot-pdfs") // app-specific external folder
        if (externalDir != null && !externalDir.exists()) {
            externalDir.mkdirs()
        }

        // 5️⃣ Create the output PDF file
        val outputFile = File(externalDir, "scanbot_document_${System.currentTimeMillis()}.pdf")


        val bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.sample_document
        )

        scanbotSdk.documentApi.createDocument().onSuccess { document ->
            document.addPage(bitmap)
            val pdfGenerator = scanbotSdk.createPdfGenerator()
            val pdfConfig = PdfConfiguration.default().copy(pageSize = PageSize.A4)
            val pdf = pdfGenerator.generate(document, outputFile, pdfConfig)
        }
    }
}