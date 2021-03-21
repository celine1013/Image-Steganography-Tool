package com.celine.imagesteganography.util

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object FileUtil {

    fun openImage(srcPath: String, imageName: String): BufferedImage {
        return ImageIO.read(File(srcPath + File.separator + imageName))
    }

    fun toBinary() {

    }

    fun insertData(image: BufferedImage, data: Any) {
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                //retrieving rgb colors of each pixel
                val color = Color(image.getRGB(x, y), true)

                //todo: extrat variant from data
                val red = modifyColor(color.red, 0)
                val green = modifyColor(color.green, 0)
                val blue = modifyColor(color.blue, 0)
                val alpha = modifyColor(color.alpha, 0)

                //modify the color of the pixel
                image.setRGB(x, y, Color(red, green, blue, alpha).rgb)
            }
        }
    }

    private fun modifyColor(orig: Int, variant: Int): Int {
        val result = orig + variant
        if (result > MAX_RGB_VALUE) return MAX_RGB_VALUE
        if (result < 0) return 0
        return result
    }

    const val MAX_RGB_VALUE = 255
}