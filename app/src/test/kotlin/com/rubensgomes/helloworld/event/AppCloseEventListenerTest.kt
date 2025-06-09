package com.rubensgomes.helloworld.event

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.event.ContextClosedEvent

class AppCloseEventListenerTest {
    private lateinit var listener: AppCloseEventListener

    @BeforeEach
    fun setup() {
        listener = AppCloseEventListener()
    }

    @Test
    fun `ensure sigterm event handler works`() {
        val mockEvent: ContextClosedEvent = mockk<ContextClosedEvent>()
        listener.onApplicationEvent(mockEvent)
        // simply ensure we get this far
        assertTrue(true)
    }
}
