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
	@Qualifier("testJob") private val testJob: Job,
//	@Qualifier("taskletJob") private val taskletJob: Job,
//	@Qualifier("apiJob") private val apiJob: Job,
) {

	@GetMapping("/testjob")
	fun runSimpleJob(): String {
		jobLauncher.run(testJob, JobParameters())
		return "OK"
	}

//	@GetMapping("/taskletjob")
//	fun runTaskletJob(): List<Map<String, Any>> {
//		val run = jobLauncher.run(taskletJob, JobParameters())
//		return run.executionContext.get("columns") as List<LinkedCaseInsensitiveMap<String>>
//	}
//
//	@GetMapping("/apiJob")
//	fun runApiJob(): String {
//		val run = jobLauncher.run(apiJob, JobParameters())
//		return run.executionContext.get("from") as String
//	}

}
