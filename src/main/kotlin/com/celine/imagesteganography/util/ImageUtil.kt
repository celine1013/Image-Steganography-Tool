package com.celine.imagesteganography.util

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageUtil {

    fun openImage(srcPath: String, imageName: String):BufferedImage{
        return ImageIO.read(File(srcPath + File.separator + imageName))
    }
}