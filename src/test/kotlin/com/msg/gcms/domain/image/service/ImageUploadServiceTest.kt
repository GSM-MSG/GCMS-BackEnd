package com.msg.gcms.domain.image.service

import com.amazonaws.services.s3.AmazonS3
import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto
import com.msg.gcms.domain.image.service.impl.UploadImageServiceImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import org.springframework.mock.web.MockMultipartFile
import java.io.InputStream

class ImageUploadServiceTest : BehaviorSpec({
   val amazonS3 = mockk<AmazonS3>()

    val uploadImagesServiceImpl = UploadImageServiceImpl(amazonS3)

    given("find user") {
        val size = (1..4).random()
        val images = (1..size)
            .map { MockMultipartFile("adfa.jpg", InputStream.nullInputStream()) }
        val dto = UploadImagesDto(images)
        val imagesDto = ImagesDto(listOf())
        `when`("is invoked") {
            val result = uploadImagesServiceImpl.execute(dto)

            then("result should not be null") {
                result shouldNotBe null
            }
            then("result should be same as userDto") {
                result shouldBe result
            }
        }
    }
})