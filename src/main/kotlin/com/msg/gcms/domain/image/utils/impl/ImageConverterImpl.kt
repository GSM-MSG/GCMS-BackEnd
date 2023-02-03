package com.msg.gcms.domain.image.utils.impl

import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.response.ImagesResponseDto
import com.msg.gcms.domain.image.utils.ImageConverter
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ImageConverterImpl : ImageConverter {
    override fun toDto(files: List<MultipartFile>): ImagesDto =
        ImagesDto(images = files)

    override fun toResponse(images: List<String>): ImagesResponseDto =
        ImagesResponseDto(images = images)
}