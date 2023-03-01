package com.msg.gcms.domain.image.utils.impl

import com.msg.gcms.domain.image.exception.FileSizeOverException
import com.msg.gcms.domain.image.utils.ImageValidator
import org.springframework.stereotype.Component

@Component
class ImageValidatorImpl : ImageValidator {
    override fun validatorFileSize(size: Int) {
       if(size > 5) {
           throw FileSizeOverException()
       }
    }
}