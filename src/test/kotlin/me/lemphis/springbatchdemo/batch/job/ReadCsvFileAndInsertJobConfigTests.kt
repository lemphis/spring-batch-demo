package me.lemphis.springbatchdemo.batch.job

import me.lemphis.springbatchdemo.batch.jpa.SrcRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBatchTest
@SpringBootTest
class ReadCsvFileAndInsertJobConfigTests @Autowired constructor(
	readCsvFileAndInsertJob: Job,
	private val jobLauncherTestUtils: JobLauncherTestUtils,
	private val srcRepository: SrcRepository,
) {

	init {
		jobLauncherTestUtils.job = readCsvFileAndInsertJob
	}

	@AfterEach
	fun tearDown() {
		srcRepository.deleteAll()
	}

	@Test
	fun readCsvFileAndInsertJobTest() {
		val fileLastLine = "Blancha,Brame,bbrame2r@thetimes.co.uk".split(",")
		val jobExecution = jobLauncherTestUtils.launchJob()

		val stepExecution = jobExecution.stepExecutions.first()
		val lastItem = srcRepository.findById(100L).get()

		assertEquals(fileLastLine[0], lastItem.firstName)
		assertEquals(fileLastLine[1], lastItem.lastName)
		assertEquals(fileLastLine[2], lastItem.email)
		assertEquals(1, stepExecution.commitCount)
		assertEquals(100, stepExecution.readCount)
		assertEquals(100, stepExecution.writeCount)
		assertEquals(BatchStatus.COMPLETED, jobExecution.status)
	}

}
