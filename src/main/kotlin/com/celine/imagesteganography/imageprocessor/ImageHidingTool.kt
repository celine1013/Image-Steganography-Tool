package com.celine.imagesteganography.imageprocessor

import com.celine.imagesteganography.util.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder

@Service
class ImageHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(file: MultipartFile): ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        val coverImage = FileUtil.openImage(srcPath, "batman2.jpg") //todo: allow user to pick image from a range
        val dataImage = FileUtil.openImage(file)
        return fileBos
    }

    //data: Binary values as list of char('0' or '1')
    private fun insertData(destImage: BufferedImage, srcImage: BufferedImage) {
        loop@for (y in 0 until destImage.height) {
            for (x in 0 until destImage.width) {
                //retrieving rgb colors of each pixel
                val color = Color(destImage.getRGB(x, y), true)
                val red = 0
                val green = 0
                val blue = 0
                //modify the color of the pixel
                destImage.setRGB(x, y, Color(red, green, blue, color.alpha).rgb)
            }
        }
    }

    fun extractMessageFromImage(file: MultipartFile):ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        val image = FileUtil.openImage(file)

        val sb = StringBuilder()
        val result = StringBuilder()
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                //retrieving rgb colors of each pixel
                val color = Color(image.getRGB(x, y), true)
                //retrieve last 4 bit of each color value
                appendColorBit(sb, result, FileUtil.getColorBit(color.red, 4))
                appendColorBit(sb, result, FileUtil.getColorBit(color.green, 4))
                appendColorBit(sb, result, FileUtil.getColorBit(color.blue, 4))
            }
        }

        return fileBos
    }

    //todo: read bits back to color value
    private fun appendColorBit(sb: StringBuilder, result: StringBuilder, value: String){
        sb.append(value)
        if(sb.length%8 == 0){
            result.append(Integer.parseInt(sb.toString(), 2).toChar())
            sb.clear()
        }
    }

    companion object{
        const val DATA_FLAG = "ZZEN9203"
        const val SEPERATOR = '-'
    }
}