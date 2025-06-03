package com.rubensgomes.helloworld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * A very basic Spring Boot microservice application.
 *
 * @author Rubens Gomes
 */
@SpringBootApplication class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
