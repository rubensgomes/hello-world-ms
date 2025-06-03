package com.rubensgomes.helloworld

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AppTest {
    @Test
    fun `ensure spring context loads`() {
        val context = SpringApplication.run(App::class.java)
        assertNotNull(context)
    }
}
