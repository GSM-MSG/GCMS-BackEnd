package com.msg.gcms.domain.image.service.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.g3c1.aidboss.domain.image.exception.FailUploadImageException
import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto
import com.msg.gcms.domain.image.service.UploadImageService
import com.msg.gcms.global.annotation.ServiceWithTransaction
import org.springframework.beans.factory.annotation.Value
import java.io.IOException
import java.util.*

@ServiceWithTransaction
class UploadImageServiceImpl(
    private val amazonS3: AmazonS3,
) : UploadImageService {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    @Value("\${cloud.aws.s3.url}")
    lateinit var url: String

    override fun execute(dto: UploadImagesDto): ImagesDto {
        val result = mutableListOf<String>()
        dto.images.forEach {
            val fileName = createFileName(it.originalFilename.toString())
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentLength = it.size
            objectMetadata.contentType = it.contentType

            try {
                it.inputStream.use { inputStream ->
                    amazonS3.putObject(
                        PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
                    )
                }
            } catch (e: IOException) {
                throw FailUploadImageException()
            }
            result.add(url+fileName)
        }
        return ImagesDto(images = result)
    }

    private fun createFileName(fileName: String): String {
        return UUID.randomUUID().toString() + fileName
    }
}