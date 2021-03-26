package com.celine.imagesteganography.imageprocessor

import com.celine.imagesteganography.errors.FieldMissingException
import com.celine.imagesteganography.textprocessor.TextHidingTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin
@RestController
class ImageHidingController {

    @Autowired
    lateinit var imageHidingTool: ImageHidingTool


    @GetMapping(value = ["/api/hideImage"])
    @ResponseBody
    fun hideMessageInImage(@RequestBody image: MultipartFile?): ResponseEntity<Any> {
        if (image == null) throw FieldMissingException("image to be hidden")
        val fileBos = imageHidingTool.hideMessageInImage(image)
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(fileBos.toByteArray())
    }

    @PostMapping(value = ["/api/extractImage"])
    @ResponseBody
    fun extractMessageFromImage(@RequestBody image: MultipartFile?): ResponseEntity<Any> {
        if(image == null)throw FieldMissingException("image with hidden message(image)")
        val hiddenImage = imageHidingTool.extractMessageFromImage(image)
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(hiddenImage.toByteArray())
    }
}