package com.rubensgomes.helloworld.event

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.net.InetAddress

/**
 * Handles application initialization event to display IP and port.
 *
 * @author Rubens Gomes
 */
@Component
class AppInitEventListener : ApplicationListener<ServletWebServerInitializedEvent> {

    override fun onApplicationEvent(event: ServletWebServerInitializedEvent) {
        log.info("application started")
        val address: InetAddress = InetAddress.getLocalHost()
        val ip: String = address.hostAddress
        val port: Int = event.webServer.port
        log.info("IP address {}", ip)
        log.info("Listening port {}", port)
    }

    internal companion object {
        private val log: Logger = LoggerFactory.getLogger(AppInitEventListener::class.java)
    }
}