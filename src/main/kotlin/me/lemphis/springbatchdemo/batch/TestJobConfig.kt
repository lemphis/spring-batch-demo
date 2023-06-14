package me.lemphis.springbatchdemo.batch

import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class TestJobConfig(
	private val jobRepository: JobRepository,
//	private val entityManagerFactory: EntityManagerFactory,
	private val dataSource: DataSource,
//	private val sqlSessionFactory: SqlSessionFactory,
	private val platformTransactionManager: PlatformTransactionManager,
) {

	@Bean
	fun testJob() = JobBuilder("testJob", jobRepository)
		.start(teststep())
		.build()

	private fun teststep() = StepBuilder("testStep", jobRepository)
		.chunk<List<Int>, Int>(100, platformTransactionManager)
		.reader(CustomReader())
		.processor(CustomProcessor())
		.writer(CustomWriter())
		.build()

}


class CustomReader : ItemReader<List<Int>> {
	private var count = 0
	override fun read(): List<Int>? {
		if (count == 200) {
			println("READER END")
			return null
		}
		println(++count)
		return listOf(count, count+1)
	}
}

class CustomProcessor : ItemProcessor<List<Int>, Int> {
	override fun process(item: List<Int>): Int {
		println("Processor $item to ${item + 10}")
		return item[0] + 10
	}
}

class CustomWriter : ItemWriter<Int> {
	override fun write(chunk: Chunk<out Int>) {
		println("Writer $chunk")
	}
}
