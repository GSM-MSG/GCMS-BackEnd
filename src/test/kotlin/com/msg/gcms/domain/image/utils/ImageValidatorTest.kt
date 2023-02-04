package com.msg.gcms.domain.image.utils

import com.msg.gcms.domain.image.exception.FileSizeOverException
import com.msg.gcms.domain.image.utils.impl.ImageValidatorImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.assertThrows

class ImageValidatorTest : BehaviorSpec({
    val imageValidatorImpl = ImageValidatorImpl()

    given("validate request") {
        val size = (1..4).random()

        `when`("is invoked") {
            val result = imageValidatorImpl.validatorFileSize(size)
            then("result should be Unit") {
                result shouldBe Unit
            }
        }
    }

    given("validate request with size over 4") {
        val size = 5

        `when`("사이즈가 4보다 큰 경우") {
            then("FileSizeOverException 예외를 던진다") {
                shouldThrow<FileSizeOverException> {
                    imageValidatorImpl.validatorFileSize(size)
                }
            }
        }


    }
})