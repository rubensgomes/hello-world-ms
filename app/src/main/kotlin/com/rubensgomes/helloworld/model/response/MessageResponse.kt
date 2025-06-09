package com.rubensgomes.helloworld.model.response

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

/**
 * A very basic message response type.
 *
 * @property message any text to be in the response.
 * @constructor Creates a
 */
class MessageResponse(
    @field:Valid @field:NotBlank(message = "message cannot be blank") val message: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageResponse

        return (message != other.message)
    }

    override fun hashCode(): Int = message.hashCode()

    override fun toString(): String = "MessageResponse(" + "message='$message'" + ")"
}
