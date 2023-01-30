package com.msg.gcms.testUtils

object TestUtils {
    fun data() = TestDataUtil

    object TestDataUtil {
        fun user() = UserDataUtil
        fun club() = ClubDataUtil
    }
}