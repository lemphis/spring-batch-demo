package me.lemphis.springbatchdemo.batch

import jakarta.persistence.EntityManagerFactory
import me.lemphis.springbatchdemo.entity.DummyItem
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class SimpleJobConfiguration(
	private val jobRepository: JobRepository,
	private val entityManagerFactory: EntityManagerFactory,
) {

	private companion object {
		const val STEP_1 = "step1"
	}

	@Bean
	fun simpleJob(@Qualifier(STEP_1) step1: Step) = JobBuilder("simpleJob", jobRepository)
		.start(step1)
		.build()

	@Bean(STEP_1)
	@JobScope
	fun simpleStep1(transactionManager: PlatformTransactionManager) = StepBuilder("simpleStep1", jobRepository)
		.chunk<DummyCsvItem, DummyItem>(10000, transactionManager)
		.reader(reader())
		.processor { DummyItem(firstName = it.firstName, email = it.email) }
		.writer(writer())
		.build()

	fun reader() = FlatFileItemReaderBuilder<DummyCsvItem>()
		.name("dummyCsvReader")
		.resource(ClassPathResource("/data/MOCK_DATA.csv"))
		.linesToSkip(1)
		.delimited()
		.delimiter(DelimitedLineTokenizer.DELIMITER_COMMA)
		.names("first_name", "last_name", "email")
		.fieldSetMapper {
			DummyCsvItem(
				firstName = it.readString("first_name"),
				lastName = it.readString("last_name"),
				email = it.readString("last_name"),
			)
		}
		.build()

	fun writer() = JpaItemWriterBuilder<DummyItem>()
		.entityManagerFactory(entityManagerFactory)
		.build()

}
