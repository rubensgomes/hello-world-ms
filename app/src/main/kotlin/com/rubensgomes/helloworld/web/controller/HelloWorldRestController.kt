package com.rubensgomes.helloworld.web.controller

import com.rubensgomes.helloworld.model.response.MessageResponse
import com.rubensgomes.helloworld.service.HelloWorldService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A very simple [RestController] that responds with a "Hello World!" message.
 *
 * This class uses the pattern of delegating business responsibility calls to the service
 * [HelloWorldService] layer.
 *
 * @author Rubens Gomes
 */
@RestController
class HelloWorldRestController(
    @Autowired val service: HelloWorldService,
) {
    @Operation(
        summary = "Prints hello world message",
        operationId = "helloWorld",
        description = "A very basic Hello World! operation.",
    )
    @GetMapping(
        path = [HELLO_WORLD_OPERATION_PATH],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun helloWorld(): ResponseEntity<MessageResponse> {
        log.trace("helloWorld()")
        val response: MessageResponse = service.helloWorld()
        return ResponseEntity(response, HttpStatus.OK)
    }

    companion object {
        // constant becomes handy in unit testing.
        const val HELLO_WORLD_OPERATION_PATH = "/api/v1/helloworld"
        private val log: Logger = LoggerFactory.getLogger(HelloWorldRestController::class.java)
    }
}
