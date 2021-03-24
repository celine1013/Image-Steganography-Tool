package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.util.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
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
        ImageIO.write(coverImage, "jpg", fileBos)
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
                if(dataIndex == data.lastIndex)break@loop
                dataIndex++
                val green = FileUtil.modifyColor(color.green, data[dataIndex])
                if(dataIndex == data.lastIndex)break@loop
                dataIndex++
                val blue = FileUtil.modifyColor(color.blue, data[dataIndex])
                if(dataIndex == data.lastIndex)break@loop
                dataIndex++

                //modify the color of the pixel
                image.setRGB(x, y, Color(red, green, blue, color.alpha).rgb)
            }
        }
    }

    fun extractMessageFromImage(file: MultipartFile):String{

        return ""
    }
    companion object{
        const val DATA_FLAG = "ZZEN9203"
    }
}