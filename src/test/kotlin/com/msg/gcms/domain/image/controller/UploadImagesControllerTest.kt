package com.msg.gcms.domain.image.controller

import com.msg.gcms.domain.image.presentation.ImageController
import com.msg.gcms.domain.image.presentation.data.dto.ImagesDto
import com.msg.gcms.domain.image.presentation.data.dto.UploadImagesDto
import com.msg.gcms.domain.image.presentation.data.response.ImagesResponseDto
import com.msg.gcms.domain.image.service.UploadImageService
import com.msg.gcms.domain.image.utils.ImageConverter
import com.msg.gcms.domain.image.utils.ImageValidator
import com.msg.gcms.domain.image.utils.impl.ImageConverterImpl
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import java.io.InputStream

class UploadImagesControllerTest : BehaviorSpec({
    @Bean
    fun imageConverter(): ImageConverter {
        return ImageConverterImpl()
    }
    val uploadImageService = mockk<UploadImageService>()
    val imageValidator = mockk<ImageValidator>()
    val imageController = ImageController(imageConverter(), imageValidator, uploadImageService)

    given("upload image request") {
        val size = (1..4).random()
        val images = (1..size)
            .map { MockMultipartFile("adfa.jpg", InputStream.nullInputStream()) }
        val dto = UploadImagesDto(images)
        val serviceResponseDto = ImagesDto(listOf())
        val responseDto = ImagesResponseDto(listOf())

        `when`("is received") {
            every { imageValidator.validatorFileSize(size) } returns Unit
            every { uploadImageService.execute(dto) } returns serviceResponseDto
            val response = imageController.uploadFile(images)
            val body = response.body

            then("response body should not be null") {
                response.body shouldNotBe null
            }

            then("validatorFileSize in imageValidator must be called") {
                verify(exactly = 1) {imageValidator.validatorFileSize(size)}
            }

            then("business logic in findUserService should be called") {
                verify(exactly = 1) { uploadImageService.execute(dto) }
            }
            then("response status should be ok") {
                response.statusCode shouldBe HttpStatus.OK
            }
            then("result should be same as responseDto") {
                body shouldBe responseDto
            }
        }
    }
})