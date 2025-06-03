package com.rubensgomes.helloworld.service

import com.rubensgomes.helloworld.model.response.MessageResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * A very simple service class that responds with a "Hello World!" message response.
 *
 * This class uses the following patterns:
 *
 * - it returns a model response type [MessageResponse]
 * - it separates front-end web layer and back-end business domain layer
 *
 * @author Rubens Gomes
 */
@Service
class HelloWorldService {
    fun helloWorld(): MessageResponse {
        log.trace("helloWorld()")
        // this is where business domain layer would be called from.
        return MessageResponse("Hello World!")
    }

    internal companion object {
        private val log: Logger = LoggerFactory.getLogger(HelloWorldService::class.java)
    }
}
