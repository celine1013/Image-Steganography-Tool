package com.celine.imagesteganography.util

object Log {
    fun d(TAG: String, message: String) {
        println("$TAG: $message")
    }

    fun e(TAG: String, message: String) {
        System.err.println("$TAG: $message")
    }
}