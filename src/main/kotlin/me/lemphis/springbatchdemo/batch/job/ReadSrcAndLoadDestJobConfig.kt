package me.lemphis.springbatchdemo.batch.job

import me.lemphis.springbatchdemo.batch.jpa.Dest
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
class ReadSrcAndLoadDestJobConfig(
	private val jobRepository: JobRepository,
	private val transactionManager: PlatformTransactionManager,
	private val mybatisPagingItemReader: ItemReader<Src>,
	private val mybatisCursorItemReader: ItemReader<Src>,
	private val jpaPagingItemReader: ItemReader<Src>,
	private val jpaCursorItemReader: ItemReader<Src>,
	private val mybatisBatchItemWriter: ItemWriter<Dest>,
	private val jpaItemWriter: ItemWriter<Dest>,
) {

	private companion object {
		const val JOB_NAME = "readSrcAndLoadDestJob"
		const val STEP_NAME = "readSrcAndLoadDestStep"
		const val CHUNK_SIZE = 1_000
	}

	@Bean(JOB_NAME)
	fun readSrcAndLoadDestJob() = JobBuilder(JOB_NAME, jobRepository)
		.incrementer(RunIdIncrementer())
		.start(readSrcAndLoadDestStep())
		.build()

	private fun readSrcAndLoadDestStep() = StepBuilder(STEP_NAME, jobRepository)
		.chunk<Src, Dest>(CHUNK_SIZE, transactionManager)
		.reader(mybatisCursorItemReader)
		.processor { it.toDest() }
		.writer(mybatisBatchItemWriter)
		.build()

}
