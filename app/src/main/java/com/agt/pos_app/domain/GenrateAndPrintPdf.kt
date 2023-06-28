package com.agt.pos_app.domain

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agt.pos_app.data.module.Order
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun generateAndPrintInvoice(context: Context,tableNo: String, items: List<Order>) {
    val document = createPdfDocument(items, context,tableNo)
    val outputFile = savePdfFile(context, document)

    if (outputFile != null) {
        printPdf(context, outputFile)
    } else {
        Toast.makeText(context, "Failed to Print Invoice ", Toast.LENGTH_SHORT).show()
        Log.e("PDF Generation", "Failed to create PDF file")
    }
}

private fun createPdfDocument(items: List<Order>, context: Context, tableNo : String): PrintedPdfDocument {
    val printAttributes = PrintAttributes.Builder()
        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
        .build()

    val document = PrintedPdfDocument(context, printAttributes)

    val pageInfo = PdfDocument.PageInfo.Builder(
        printAttributes.mediaSize?.widthMils ?: 0,
        printAttributes.mediaSize?.heightMils ?: 0,
        1
    ).create()

    val page = document.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    // Define the content layout and styling
    val x = 500f
    var y = 500f

    paint.color = Color.BLACK
    paint.textSize = 522f

    canvas.drawText("Invoice Table :$tableNo ", x, y, paint)

    y += 600f

    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = dateFormat.format(Date())

    canvas.drawText("Date: $date", x, y, paint)

    y += 600f

    for (item in items) {
        val line = "${item.orderItem.name} x ${item.qnt} = ${item.orderItem.price * item.qnt}"
        canvas.drawText(line, x, y, paint)
        y += 600f
    }

    document.finishPage(page)

    return document
}

private fun savePdfFile(context: Context, document: PrintedPdfDocument): File? {
    val outputDir = context.cacheDir
    val outputFile = File(outputDir, "invoice.pdf")

    try {
        val outputStream = FileOutputStream(outputFile)
        document.writeTo(outputStream)
        document.close()
        outputStream.close()
        return outputFile
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return null
}

private fun printPdf(context: Context, pdfFile: File) {
    val printManager = ContextCompat.getSystemService(context, PrintManager::class.java)
    val jobName = "Invoice Print Job"

    printManager?.print(jobName, object : PrintDocumentAdapter() {

        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback.onLayoutCancelled()
                return
            }

//            val layoutResult = PrintDocumentAdapter.LayoutResultCallback(
//                PrintDocumentAdapter.LayoutResultCallback.LayoutResult.PAGE_COUNT_UNKNOWN,
//                newAttributes
//            )
            callback.onLayoutFinished(
                PrintDocumentInfo.Builder(jobName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build(), false
            )
        }

        override fun onWrite(
            pages: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback?
        ) {
            try {
                val inputStream = FileInputStream(pdfFile)
                val outputStream = FileOutputStream(destination?.fileDescriptor)
                val buffer = ByteArray(16384)
                var size: Int

                while (inputStream.read(buffer)
                        .also { size = it } >= 0 && cancellationSignal?.isCanceled == false
                ) {
                    outputStream.write(buffer, 0, size)
                }

                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                inputStream.close()
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }, null)
}