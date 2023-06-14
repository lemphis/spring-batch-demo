package me.lemphis.springbatchdemo

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest

@SpringBatchTest
@SpringBootTest
class SpringBatchDemoApplicationTests(
	jobLauncherTestUtils: JobLauncherTestUtils,
	job: Job,
) : BehaviorSpec(
	{
		given("simpleJob") {
			jobLauncherTestUtils.job = job
			val launchJob = jobLauncherTestUtils.launchJob()
			`when`("test") {
				then("result") {
					println(jobLauncherTestUtils)
					println(launchJob.executionContext)
					jobLauncherTestUtils shouldNotBe null

				}
			}
		}
	},
)
