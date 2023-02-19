package com.msg.gcms.domain.image.presentation

import com.msg.gcms.domain.image.presentation.data.response.ImagesResponseDto
import com.msg.gcms.domain.image.service.UploadImageService
import com.msg.gcms.domain.image.utils.ImageConverter
import com.msg.gcms.domain.image.utils.ImageValidator
import com.msg.gcms.global.annotation.RequestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RequestController("/image")
class ImageController(
    private val imageConverter: ImageConverter,
    private val imageValidator: ImageValidator,
    private val uploadImageService: UploadImageService
) {
    @PostMapping
    fun uploadFile(@Valid @RequestPart(value = "file") multipartFile: List<MultipartFile>): ResponseEntity<ImagesResponseDto> =
        imageValidator.validatorFileSize(multipartFile.size)
            .let { imageConverter.toDto(multipartFile) }
            .let { uploadImageService.execute(it) }
            .let { imageConverter.toResponse(it) }
            .let { ResponseEntity.ok().body(it) }

}