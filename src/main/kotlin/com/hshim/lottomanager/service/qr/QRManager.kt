package com.hshim.lottomanager.service.qr

import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.multi.GenericMultipleBarcodeReader
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

@Component
class QRManager {

    fun decode(file: MultipartFile): List<String> {
        val processedImage = processImage(file.inputStream)
        val luminanceSource = BufferedImageLuminanceSource(processedImage)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        val hintMap = mapOf(
            DecodeHintType.TRY_HARDER to true,
            DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE)
        )
        val multiReader = GenericMultipleBarcodeReader(MultiFormatReader().apply { setHints(hintMap) })

        return try {
            multiReader.decodeMultiple(binaryBitmap).mapNotNull { it.text }.toList()
        } catch (e: NotFoundException) {
            emptyList()
        }
    }

    private fun processImage(inputStream: InputStream): BufferedImage {
        val originalImage = ImageIO.read(inputStream)
        val grayscaleImage = toGrayscale(originalImage)
        return adaptiveThreshold(grayscaleImage)
    }

    private fun toGrayscale(image: BufferedImage): BufferedImage {
        val width = image.width
        val height = image.height
        val grayscaleImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
        val g2d = grayscaleImage.createGraphics()
        g2d.drawImage(image, 0, 0, null)
        g2d.dispose()
        return grayscaleImage
    }

    private fun adaptiveThreshold(image: BufferedImage): BufferedImage {
        val width = image.width
        val height = image.height
        val binaryImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val gray = image.getRGB(x, y) and 0xff
                val localThreshold = calculateLocalThreshold(image, x, y)
                val binaryValue = if (gray < localThreshold) 0 else 255
                binaryImage.setRGB(x, y, (0xff shl 24) or (binaryValue shl 16) or (binaryValue shl 8) or binaryValue)
            }
        }
        return binaryImage
    }

    private fun calculateLocalThreshold(image: BufferedImage, x: Int, y: Int): Int {
        val radius = 5
        val startX = maxOf(0, x - radius)
        val endX = minOf(image.width - 1, x + radius)
        val startY = maxOf(0, y - radius)
        val endY = minOf(image.height - 1, y + radius)

        var sum = 0
        var count = 0
        for (j in startY..endY) {
            for (i in startX..endX) {
                sum += image.getRGB(i, j) and 0xff
                count++
            }
        }
        return sum / count
    }
}