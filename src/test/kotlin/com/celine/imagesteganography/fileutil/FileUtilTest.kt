package com.celine.imagesteganography.fileutil

import com.celine.imagesteganography.util.FileUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileUtilTest {

    private val messageBinary = listOf<Char>('0','1', '1', '0', '0', '0', '0', '1')

    @ExperimentalStdlibApi
    @Test
    fun testToBinary(){
        val result = FileUtil.toBinary("a")
        assertEquals(messageBinary, result)
    }

    @ExperimentalStdlibApi
    @Test
    fun testModifyColor(){
        var result = FileUtil.modifyColor(255, '1')
        assertEquals(255, result)
        result = FileUtil.modifyColor(254, '1')
        assertEquals(255, result)
        result = FileUtil.modifyColor(255, '0')
        assertEquals(254, result)
        result = FileUtil.modifyColor(254, '0')
        assertEquals(254, result)
    }

    @Test
    fun testGetColorBits(){
        var result = FileUtil.getColorBit(255, 2)
        assertEquals("11", result)
        result = FileUtil.getColorBit(254, 1)
        assertEquals("0", result)
        result = FileUtil.getColorBit(254, 3)
        assertEquals("110", result)
    }

    @Test
    fun testMixColorBits(){
        var result = FileUtil.mixColorBits(240, 240)
        assertEquals(255, result)
    }

    @Test
    fun testReadColorValue(){
        val result = FileUtil.readColorValue(15)
        assertEquals(240, result)
    }
}