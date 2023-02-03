package com.msg.gcms.domain.image.utils

import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto
import com.msg.gcms.domain.image.presentation.data.response.ImagesResponseDto
import org.springframework.web.multipart.MultipartFile

interface ImageConverter {
    fun toDto(files: List<MultipartFile>): UploadImagesDto
    fun toResponse(images: List<String>): ImagesResponseDto
}