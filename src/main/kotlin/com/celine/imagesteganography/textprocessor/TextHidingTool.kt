package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.util.FileUtil
import com.celine.imagesteganography.util.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import javax.imageio.ImageIO

@Service
class TextHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(msg: String): ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        val coverImage = FileUtil.openImage(srcPath, "batman2.jpg") //todo: allow user to pick image from a range
        val encodedData = FileUtil.toBinary("$DATA_FLAG{$msg}")
        insertData(coverImage, encodedData)
        ImageIO.write(coverImage, "png", fileBos)//has to be a png to prevent auto-file-compression
        return fileBos
    }

    //data: Binary values as list of char('0' or '1')
    private fun insertData(image: BufferedImage, data: MutableList<Char>) {
        var dataIndex = 0
        loop@for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                //retrieving rgb colors of each pixel
                val color = Color(image.getRGB(x, y), true)
                val red = FileUtil.modifyColor(color.red, data[dataIndex])
                dataIndex++
                if(dataIndex == data.size){
                    image.setRGB(x, y, Color(red, color.green, color.blue, color.alpha).rgb)
                    break@loop
                }
                val green = FileUtil.modifyColor(color.green, data[dataIndex])
                dataIndex++
                if(dataIndex == data.size){
                    image.setRGB(x, y, Color(red, green, color.blue, color.alpha).rgb)
                    break@loop
                }
                val blue = FileUtil.modifyColor(color.blue, data[dataIndex])
                dataIndex++
                if(dataIndex == data.size){
                    image.setRGB(x, y, Color(red, green, blue, color.alpha).rgb)
                    break@loop
                }
                //modify the color of the pixel
                image.setRGB(x, y, Color(red, green, blue, color.alpha).rgb)
            }
        }
    }

    fun extractMessageFromImage(file: MultipartFile):String{
        //todo:solve image compression problem
        val image = FileUtil.openImage(file)

        val sb = StringBuilder()
        val result = StringBuilder()
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                //retrieving rgb colors of each pixel
                val color = Color(image.getRGB(x, y), true)
                //retrieve last bit of each color value
                appendColorBit(sb, result, FileUtil.getColorBit(color.red, 1))
                appendColorBit(sb, result, FileUtil.getColorBit(color.green, 1))
                appendColorBit(sb, result, FileUtil.getColorBit(color.blue, 1))
            }
        }
        val rawMessage = result.toString()
        val flagRegex = "$DATA_FLAG\\{[\\w|\\s]+}".toRegex()
        val extractedMessage = flagRegex.find(rawMessage)?.value?:"No hidden message found"
        Log.d("Message", extractedMessage)
        return extractedMessage
    }

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