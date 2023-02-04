package com.msg.gcms.domain.image.service

import com.amazonaws.services.s3.AmazonS3
import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto
import com.msg.gcms.domain.image.service.impl.UploadImageServiceImpl
import com.msg.gcms.domain.image.utils.ImageValidator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.mock.web.MockMultipartFile
import java.io.InputStream

class ImageUploadServiceTest : BehaviorSpec({
    val amazonS3 = mockk<AmazonS3>()
    val imageValidator = mockk<ImageValidator>()

    val uploadImagesServiceImpl = UploadImageServiceImpl(amazonS3, imageValidator)

    given("find user") {
        val size = (1..4).random()
        val images = (1..size)
            .map { MockMultipartFile("adfa.jpg", InputStream.nullInputStream()) }
        val dto = UploadImagesDto(images)
        val response = ImagesDto(listOf())

        `when`("is invoked") {
            every { imageValidator.validatorFileSize(size) } returns Unit
            val result = uploadImagesServiceImpl.execute(dto)

            then("result should not be null") {
                result shouldNotBe null
            }
            then("validatorFileSize in imageValidator must be called") {
                verify(exactly = 1) { imageValidator.validatorFileSize(size) }
            }
            then("result should be same as userDto") {
                result shouldBe response
            }
        }
    }
})