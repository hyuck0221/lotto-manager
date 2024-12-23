package com.hshim.lottomanager.service.qr

import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.multi.GenericMultipleBarcodeReader
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@Component
class QRManager {
    fun decode(file: File): List<String> {
        val processedImage = processImage(file)
        val luminanceSource = BufferedImageLuminanceSource(processedImage)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        val multiReader = GenericMultipleBarcodeReader(MultiFormatReader())
        return try {
            multiReader.decodeMultiple(binaryBitmap).mapNotNull { it.text }.toList()
        } catch (e: NotFoundException) {
            emptyList()
        }
    }

    private fun processImage(file: File): BufferedImage {
        val originalImage = ImageIO.read(file)
        val width = originalImage.width
        val height = originalImage.height

        val grayscaleImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
        val g2d = grayscaleImage.createGraphics()
        g2d.drawImage(originalImage, 0, 0, null)
        g2d.dispose()

        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = grayscaleImage.getRGB(x, y)
                val alpha = (rgb shr 24) and 0xff
                val gray = rgb and 0xff
                val thresholded = if (gray < 128) 0 else 255
                val newRgb = (alpha shl 24) or (thresholded shl 16) or (thresholded shl 8) or thresholded
                grayscaleImage.setRGB(x, y, newRgb)
            }
        }

        return grayscaleImage
    }
}