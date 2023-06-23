package me.lemphis.springbatchdemo.batch.job

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
import java.nio.file.Files
import java.nio.file.Paths

@SpringBatchTest
@SpringBootTest
class ReadSrcAndWriteCsvFileJobConfigTests @Autowired constructor(
	readSrcAndWriteCsvFileJob: Job,
	private val jobLauncherTestUtils: JobLauncherTestUtils,
	private val srcRepository: SrcRepository,
) {

	init {
		jobLauncherTestUtils.job = readSrcAndWriteCsvFileJob
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
	fun readSrcAndWriteCsvFileJobTest() {
		val jobExecution = jobLauncherTestUtils.launchJob()
		val stepExecution = jobExecution.stepExecutions.first()

		val filePath = "output.csv"
		val file = Paths.get(filePath)
		val lastFixture = fixture.last()

		assertEquals(101, Files.lines(file).count())
		assertEquals(Files.lines(file).findFirst().get(), "first_name,last_name,email")
		assertEquals(
			Files.lines(file).reduce { _, b -> b }.get(),
			"${lastFixture.firstName},${lastFixture.lastName},${lastFixture.email}",
		)
		assertEquals(1, stepExecution.commitCount)
		assertEquals(100, stepExecution.readCount)
		assertEquals(100, stepExecution.writeCount)
		assertEquals(BatchStatus.COMPLETED, jobExecution.status)

		Files.delete(file)
	}

}
