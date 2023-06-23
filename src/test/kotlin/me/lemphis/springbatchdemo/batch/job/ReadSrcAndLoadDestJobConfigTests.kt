package me.lemphis.springbatchdemo.batch.job

import me.lemphis.springbatchdemo.batch.jpa.DestRepository
import me.lemphis.springbatchdemo.batch.jpa.Src
import me.lemphis.springbatchdemo.batch.jpa.SrcRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBatchTest
@SpringBootTest
class ReadSrcAndLoadDestJobConfigTests @Autowired constructor(
	readSrcAndLoadDestJob: Job,
	private val jobLauncherTestUtils: JobLauncherTestUtils,
	private val srcRepository: SrcRepository,
	private val destRepository: DestRepository,
) {

	init {
		jobLauncherTestUtils.job = readSrcAndLoadDestJob
	}

	val fixture = createFixture()

	@BeforeEach
	fun setUp() {
		srcRepository.saveAll(fixture)
	}

	@AfterEach
	fun tearDown() {
		srcRepository.deleteAll()
	}

	private fun createFixture() = (1..100).map {
		Src(
			it.toString().repeat(1),
			it.toString().repeat(1),
			"$it@$it",
		)
	}

	@Test
	fun readSrcAndLoadDestJobTest() {
		val jobExecution = jobLauncherTestUtils.launchJob()
		val stepExecution = jobExecution.stepExecutions.first()

		val firstItem = destRepository.findById(1).get()
		val lastItem = destRepository.findById(100).get()

		assertEquals("${fixture[0].firstName} ${fixture[0].lastName}", firstItem.fullName)
		assertEquals("${fixture[99].firstName} ${fixture[99].lastName}", lastItem.fullName)
		assertEquals(fixture[0].email, firstItem.email)
		assertEquals(fixture[99].email, lastItem.email)
		assertEquals(100, stepExecution.readCount)
		assertEquals(100, stepExecution.writeCount)
		assertEquals(BatchStatus.COMPLETED, jobExecution.status)
	}

}
