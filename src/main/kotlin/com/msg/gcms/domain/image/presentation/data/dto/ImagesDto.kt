package com.msg.gcms.domain.image.presentation.data.dto

import org.springframework.web.multipart.MultipartFile

data class ImagesDto(
    val images: List<MultipartFile>
)
