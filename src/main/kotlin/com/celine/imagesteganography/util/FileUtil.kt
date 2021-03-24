package com.celine.imagesteganography.util

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.StringBuilder
import java.util.*
import javax.imageio.ImageIO

object FileUtil {

    fun openImage(srcPath: String, imageName: String): BufferedImage {
        return ImageIO.read(File(srcPath + File.separator + imageName))
    }

    fun toBinary(textMessage:String): MutableList<Char> {
        val result = mutableListOf<Char>()
        for(char in textMessage.toCharArray()){
            val binaryString = Integer.toBinaryString(char.toInt())
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

    const val MAX_RGB_VALUE = 255
}