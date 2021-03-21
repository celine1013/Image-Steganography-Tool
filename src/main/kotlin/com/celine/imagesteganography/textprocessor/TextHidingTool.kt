package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.util.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
class TextHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(msg: String): ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        //todo: encode the msg to bit/bytes

        val coverImage = FileUtil.openImage(srcPath, "batman2.jpg") //todo: allow user to pick image from a range
        FileUtil.insertData(coverImage, msg)

        ImageIO.write(coverImage, "jpg", fileBos)
        return fileBos
    }
}