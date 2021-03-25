package com.celine.imagesteganography.util

import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object FileUtil {

    fun openImage(srcPath: String, imageName: String): BufferedImage {
        return ImageIO.read(File(srcPath + File.separator + imageName))
    }

    fun openImage(file: MultipartFile): BufferedImage {
        return ImageIO.read(file.inputStream)
    }

    fun toBinary(textMessage: String): MutableList<Char> {
        val result = mutableListOf<Char>()
        for (char in textMessage.toCharArray()) {
            val binaryString = Integer.toBinaryString(char.toInt()).padStart(8, '0')
            binaryString.forEach { result.add(it) }
        }
        return result
    }

    fun modifyColor(orig: Int, variant: Char): Int {
        val origBit = Integer.toBinaryString(orig).toMutableList()
        origBit[origBit.lastIndex] = variant
        val sb = StringBuilder()
        origBit.forEach { sb.append(it) }
        return Integer.parseInt(sb.toString(), 2)
    }

    fun getColorBit(color: Int, bitLength: Int): String {
        val result = Integer.toBinaryString(color)
        val lastIndex = result.length
        val startIndex = lastIndex - bitLength
        return result.substring(startIndex, lastIndex)
    }

    const val MAX_RGB_VALUE = 255
}