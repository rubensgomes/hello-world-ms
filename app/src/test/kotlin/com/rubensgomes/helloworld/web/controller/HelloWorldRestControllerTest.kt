package com.rubensgomes.helloworld.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.ninjasquad.springmockk.MockkBean
import com.rubensgomes.helloworld.model.response.MessageResponse
import com.rubensgomes.helloworld.service.HelloWorldService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [HelloWorldRestController::class])
class HelloWorldRestControllerTest(
    @Autowired val mockMvc: MockMvc,
) {
    private val ow: ObjectWriter = ObjectMapper().writerWithDefaultPrettyPrinter()

    @LocalServerPort private var port: Int = 0

    @MockkBean private lateinit var service: HelloWorldService

    @Test
    fun `ensure hello world message is returned`() {
        val response = MessageResponse("Hello World!")
        val jsonResponse: String = ow.writeValueAsString(response)
        log.debug("expected response: $jsonResponse")

        every { service.helloWorld() } returns response

        mockMvc
            .perform(
                get(HelloWorldRestController.HELLO_WORLD_OPERATION_PATH)
                    .accept(MediaType.APPLICATION_JSON),
            ).andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(jsonResponse),
            )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(HelloWorldRestControllerTest::class.java)
    }
}
