package me.lemphis.springbatchdemo.interfaces

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchController(
	private val jobLauncher: JobLauncher,
	@Qualifier("simpleJob") private val simpleJob: Job,
) {

	@GetMapping("/")
	fun runBatch() = jobLauncher.run(simpleJob, JobParameters())

}
