package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.errors.FieldMissingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File

@CrossOrigin
@RestController
class TextHidingController {

    @Autowired
    lateinit var textHidingTool: TextHidingTool


    @GetMapping(value = ["/api/hideText"])
    @ResponseBody
    fun hideMessageInImage(@RequestHeader msg: String?, @RequestBody coverFile: MultipartFile?): ResponseEntity<Any> {
        if (msg.isNullOrBlank()) throw FieldMissingException("message to be hidden")
        if (coverFile == null) throw FieldMissingException("cover file")
        val fileBos = textHidingTool.hideMessageInImage(msg, coverFile)
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(fileBos.toByteArray())
    }

    @PostMapping(value = ["/api/extractText"])
    @ResponseBody
    fun extractMessageFromImage(@RequestBody image: MultipartFile?): ResponseEntity<Any> {
        if(image == null)throw FieldMissingException("image with hidden message")
        val message = textHidingTool.extractMessageFromImage(image)
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapOf("hiddenMessage" to message))
    }
}