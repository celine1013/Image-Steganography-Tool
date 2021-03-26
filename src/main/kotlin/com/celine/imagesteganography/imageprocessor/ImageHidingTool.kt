package com.celine.imagesteganography.imageprocessor

import com.celine.imagesteganography.util.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import javax.imageio.ImageIO

@Service
class ImageHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(file: MultipartFile): ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        val coverImage = FileUtil.openImage(srcPath, "wave.jpg") //todo: allow user to pick image from a range
        val dataImage = FileUtil.openImage(file)//todo:compress the dataImage first so it's smaller than coverImage
        insertData(coverImage, dataImage)
        ImageIO.write(coverImage, "jpg", fileBos)//return jpg as compression wouldn't affect the "look" of a image
        return fileBos
    }

    //srcImage should always be smaller than destImage
    private fun insertData(destImage: BufferedImage, srcImage: BufferedImage) {
        loop@for (y in 0 until srcImage.height) {
            for (x in 0 until srcImage.width) {
                //retrieving rgb colors of each pixel
                val firstColor = Color(destImage.getRGB(x, y), true)
                val secondColor = Color(srcImage.getRGB(x, y), true)
                val red = FileUtil.mixColorBits(firstColor.red, secondColor.red)
                val green = FileUtil.mixColorBits(firstColor.green, secondColor.green)
                val blue = FileUtil.mixColorBits(firstColor.blue, secondColor.blue)
                //modify the color of the pixel
                destImage.setRGB(x, y, Color(red, green, blue).rgb)
            }
        }
    }

    fun extractImageFromImage(file: MultipartFile):ByteArrayOutputStream{
        val fileBos = ByteArrayOutputStream()
        val image = FileUtil.openImage(file)

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                //retrieving rgb colors of each pixel
                val color = Color(image.getRGB(x, y), true)
                //retrieve last 4 bit of each color value

            }
        }

        return fileBos
    }
}