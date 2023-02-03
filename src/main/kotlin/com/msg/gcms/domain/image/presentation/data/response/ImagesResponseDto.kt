package com.msg.gcms.domain.image.presentation.data.response

import org.springframework.web.multipart.MultipartFile

data class ImagesResponseDto(
    val images: List<MultipartFile>
)
