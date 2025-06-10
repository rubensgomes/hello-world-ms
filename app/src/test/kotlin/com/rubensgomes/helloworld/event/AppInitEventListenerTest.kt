package com.rubensgomes.helloworld.event

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent

class AppInitEventListenerTest {
    private lateinit var listener: AppInitEventListener

    @BeforeEach
    fun setup() {
        listener = AppInitEventListener()
    }

    @Test
    fun `ensure port and ip address displayed`() {
        val mockEvent: ServletWebServerInitializedEvent = mockk<ServletWebServerInitializedEvent>()
        every { mockEvent.webServer.port } returns 80
        listener.onApplicationEvent(mockEvent)
        // simply ensure we get this far
        assertTrue(true)
    }
}
