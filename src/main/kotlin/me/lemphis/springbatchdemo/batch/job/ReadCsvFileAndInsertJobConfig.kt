package me.lemphis.springbatchdemo.batch.job

import me.lemphis.springbatchdemo.batch.DummyItemDto
import me.lemphis.springbatchdemo.batch.jpa.Src
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ReadCsvFileAndInsertJobConfig(
	private val transactionManager: PlatformTransactionManager,
	private val jobRepository: JobRepository,
	private val csvFileItemReader: ItemReader<DummyItemDto>,
	private val jdbcBatchItemWriter: ItemWriter<Src>,
) {

	private companion object {
		const val JOB_NAME = "readCsvFileAndInsertJob"
		const val STEP_NAME = "readCsvFileAndInsertStep"
		const val CHUNK_SIZE = 1_000
	}

	@Bean
	fun readCsvFileAndInsertJob() = JobBuilder(JOB_NAME, jobRepository)
		.incrementer(RunIdIncrementer())
		.start(readCsvFileAndInsertStep())
		.build()

	private fun readCsvFileAndInsertStep() = StepBuilder(STEP_NAME, jobRepository)
		.chunk<DummyItemDto, Src>(CHUNK_SIZE, transactionManager)
		.reader(csvFileItemReader)
		.processor { it.toSrc() }
		.writer(jdbcBatchItemWriter)
		.build()

}
