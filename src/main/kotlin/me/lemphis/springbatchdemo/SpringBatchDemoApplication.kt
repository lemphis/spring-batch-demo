package me.lemphis.springbatchdemo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchDemoApplication

fun main(args: Array<String>) {
	runApplication<SpringBatchDemoApplication>(*args)
}
