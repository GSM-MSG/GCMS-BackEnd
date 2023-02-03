package com.msg.gcms.domain.image.utils

import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import org.springframework.web.multipart.MultipartFile

interface ImageConverter {
    fun toDto(file: List<MultipartFile>): ImagesDto
}