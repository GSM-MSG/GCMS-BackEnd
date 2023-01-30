package com.msg.gcms.testUtils

object TestUtils {
    fun data() = TestDaTaUtil

    object TestDaTaUtil {
        fun user() = UserDataUtil
        fun club() = ClubDataUtil
    }
}