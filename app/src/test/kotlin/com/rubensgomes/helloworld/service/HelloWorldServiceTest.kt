package com.rubensgomes.helloworld.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HelloWorldServiceTest {
    private lateinit var service: HelloWorldService

    @BeforeEach
    fun setup() {
        service = HelloWorldService()
    }

    @Test
    fun `ensure service returns non-blank response`() {
        val response = service.helloWorld()
        assertTrue(response.message.isNotBlank())
    }

    @Test
    fun `ensure sigterm predestroy handler works`() {
        service.cleanup()
        // simply ensure we got this far.
        assertTrue(true)
    }
}
