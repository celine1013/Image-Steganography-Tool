package com.celine.imagesteganography.fileutil

import com.celine.imagesteganography.util.FileUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileUtilTest {

    private val messageBinary = listOf<Char>('1', '1', '0', '0', '0', '0', '1')

    @ExperimentalStdlibApi
    @Test
    fun testToBinary(){
        val result = FileUtil.toBinary("a")
        assertEquals(messageBinary, result)
    }

    @ExperimentalStdlibApi
    @Test
    fun modifyColor(){
        var result = FileUtil.modifyColor(255, '1')
        assertEquals(255, result)
        result = FileUtil.modifyColor(254, '1')
        assertEquals(255, result)
        result = FileUtil.modifyColor(255, '0')
        assertEquals(254, result)
        result = FileUtil.modifyColor(254, '0')
        assertEquals(254, result)
    }
}