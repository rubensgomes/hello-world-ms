package com.rubensgomes.helloworld.event

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.stereotype.Component

/**
 * Handles SIGTERM events.
 *
 * @author Rubens Gomes
 */
@Component
class AppCloseEventListener : ApplicationListener<ContextClosedEvent> {
    override fun onApplicationEvent(event: ContextClosedEvent) {
        log.info("Handling SIGTERM")
    }

    internal companion object {
        private val log: Logger = LoggerFactory.getLogger(AppCloseEventListener::class.java)
    }
}
