package com.celine.imagesteganography.textprocessor

import com.celine.imagesteganography.errors.FieldMissingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.awt.image.BufferedImage
import java.io.File

@CrossOrigin
@RestController
class TextHidingController {

    @Autowired
    lateinit var textHidingTool: TextHidingTool


    @GetMapping(value = ["/api/hideText/{msg}"])
    @ResponseBody
    fun hideMessageInImage(@PathVariable msg: String?): ResponseEntity<Any> {
        if (msg.isNullOrBlank()) throw FieldMissingException("message to be hidden")
        val fileBos = textHidingTool.hideMessageInImage(msg)
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBos.toByteArray())
    }

    @GetMapping(value = ["/api/extractText"])
    @ResponseBody
    fun extractMessageFromImage(): ResponseEntity<Any> {
        val message = ""
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(mapOf("hiddenMessage", message))
    }
}