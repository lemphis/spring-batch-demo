package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JpaDataImportJob(
	private val jobRepository: JobRepository,
	private val transactionManager: PlatformTransactionManager,
	private val entityManagerFactory: EntityManagerFactory,
) {

	companion object {
		const val JOB_NAME = "jpaBasedFileImportJob"
		const val STEP_NAME = "fileImportStep"
		const val CHUNK_SIZE = 1_000
	}

	@Bean(JOB_NAME)
	fun jpaBasedJob() = JobBuilder(JOB_NAME, jobRepository)
		.start(dataImportStep())
		.build()

	@Bean(STEP_NAME)
	fun dataImportStep() = StepBuilder(STEP_NAME, jobRepository)
		.chunk<DummyItem, DummyItem>(CHUNK_SIZE, transactionManager)
		.reader(reader())
		.processor(processor())
		.writer(writer())
		.build()

	fun reader() = JpaPagingItemReaderBuilder<DummyItem>()
		.name("fileImportStepReader")
		.entityManagerFactory(entityManagerFactory)
		.queryString("SELECT i FROM DummyItem i")
		.pageSize(CHUNK_SIZE)
		.build()

	fun processor() = ItemProcessor<DummyItem, DummyItem> {
		it.apply { firstName?.uppercase() }
	}

	fun writer() = JpaItemWriterBuilder<DummyItem>()
		.entityManagerFactory(entityManagerFactory)
		.build()

}
