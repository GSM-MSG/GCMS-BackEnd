package com.msg.gcms.domain.image.service

import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto

interface UploadImageService {
    fun execute(dto: UploadImagesDto): ImagesDto
}