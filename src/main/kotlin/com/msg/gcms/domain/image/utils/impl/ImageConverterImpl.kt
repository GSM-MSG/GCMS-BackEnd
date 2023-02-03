package com.msg.gcms.domain.image.utils.impl

import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.utils.ImageConverter
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ImageConverterImpl : ImageConverter {
    override fun toDto(file: List<MultipartFile>): ImagesDto =
        ImagesDto(file)
}