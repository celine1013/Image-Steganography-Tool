package com.celine.imagesteganography.util

import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

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
        val lastIndex = result.length - 1
        val startIndex = lastIndex - (bitLength - 1)
        return result.substring(startIndex..lastIndex)
    }

    //todo: resize image so stego is always smaller than original
    fun resizeImage(file: MultipartFile, compressToWidth: Int, compressToHeight: Int): BufferedImage {
        val image = openImage(file)
        Log.d("Image Compression Start", image.width.toString())
        var compressed = resizeImage(image)
//        while((compressed.width > compressToWidth)||compressed.height > compressToHeight){
//            compressed = compressImage(compressed)
//        }
        Log.d("Image Compression Start", compressed.width.toString())
        return compressed
    }

    private fun resizeImage(image: BufferedImage): BufferedImage {
        val compressedFile = File("compressedImage.png")
        val fos = FileOutputStream(compressedFile)

        //image writers
        val imageWriters = ImageIO.getImageWritersByFormatName("png")
        if (!imageWriters.hasNext())
            throw IllegalStateException("Writers Not Found!!")
        val writer = imageWriters.next()
        val imageOutputStream = ImageIO.createImageOutputStream(fos)
        writer.output = imageOutputStream

        //set compress metrics
        val imageParam = writer.defaultWriteParam
        imageParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
        imageParam.compressionQuality = 0.9f

        writer.write(null, IIOImage(image, null, null), imageParam)
        fos.close()
        imageOutputStream.close()
        writer.dispose()
        return ImageIO.read(compressedFile)
    }

    fun readColorValue(color: Int): Int {
        val colorBits = Integer.toBinaryString(color)
        val stegColor = colorBits.substring(4..7).padEnd(8, '0')
        return Integer.parseInt(stegColor, 2)
    }

    fun mixColorBits(firstColorByte: Int, secondColorValue: Int): Int {
        //take the first 4 bits of two values and mix then into 1 byte
        val firstBitString = Integer.toBinaryString(firstColorByte).padStart(8, '0')
        val secondBitString = Integer.toBinaryString(secondColorValue).padStart(8, '0')
        val mixedBitString = firstBitString.substring(0..3) + secondBitString.substring(0..3)
        return Integer.parseInt(mixedBitString, 2)
    }

    const val MAX_RGB_VALUE = 255
}