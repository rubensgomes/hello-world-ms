package com.rubensgomes.helloworld.model.response

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MessageResponseTest {
    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `ensure constructor works`() {
        val message = "Hello World!"
        val response = MessageResponse(message)
        assertEquals(message, response.message)
    }

    @Test
    fun `fail to create response due to empty message`() {
        val response = MessageResponse("")
        val constraintViolations: Set<ConstraintViolation<MessageResponse>> =
            validator.validate(response)
        assertEquals(1, constraintViolations.size)
    }

    @Test
    fun `fail to create response due to blank message`() {
        val response = MessageResponse("    ")
        val constraintViolations: Set<ConstraintViolation<MessageResponse>> =
            validator.validate(response)
        assertEquals(1, constraintViolations.size)
    }
}
