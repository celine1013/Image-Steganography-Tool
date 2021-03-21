package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.util.ImageUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

@Service
class TextHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(msg: String): ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        //todo: encode the msg to bit/bytes

        //todo: open an image
        val coverImage = ImageUtil.openImage(srcPath, "example.png")

        //todo: modify the pixels


        //return image output stream
        ImageIO.write(coverImage, "png", fileBos)
        return fileBos
    }
}