package com.spacewalk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpaceWorkApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpaceWorkApplication>(*args)
}
