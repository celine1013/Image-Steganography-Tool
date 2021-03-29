package com.celine.imagesteganography.imageprocessor

import com.celine.imagesteganography.util.FileUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
class ImageHidingTool {

    @Value("\${com.celine.imagesteganography.src}")
    lateinit var srcPath: String

    fun hideMessageInImage(coverFile: MultipartFile, stegoFile: MultipartFile): ByteArrayOutputStream{
        val coverImage = FileUtil.openImage(coverFile)
        val stegoImage = FileUtil.openImage(stegoFile)//todo:compress the dataImage first so it's smaller than coverImage
        if(stegoImage.width > coverImage.width || stegoImage.height > coverImage.height) throw Exception("stego file must be smaller than cover file")
        insertData(coverImage, stegoImage)
        val fileBos = ByteArrayOutputStream()
        ImageIO.write(coverImage, "png", fileBos)//has to be a png for decoding later
        return fileBos
    }

    //srcImage should always be smaller than destImage
    private fun insertData(destImage: BufferedImage, srcImage: BufferedImage) {
        loop@for (y in 0 until srcImage.height) {
            for (x in 0 until srcImage.width) {
                //retrieving rgb colors of each pixel
                val firstColor = Color(destImage.getRGB(x, y), false)
                val secondColor = Color(srcImage.getRGB(x, y), false)
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
                val color = Color(image.getRGB(x, y), false)
                //retrieve last 4 bit of each color value
                val newRed = FileUtil.readColorValue(color.red)
                val newGreen = FileUtil.readColorValue(color.green)
                val newBlue = FileUtil.readColorValue(color.blue)
                image.setRGB(x, y, Color(newRed, newGreen, newBlue).rgb)
            }
        }

        ImageIO.write(image, "png", fileBos)//has to be png so that the image shows properly.
        return fileBos
    }
}