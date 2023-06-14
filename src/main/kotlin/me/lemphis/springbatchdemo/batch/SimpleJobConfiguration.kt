//package me.lemphis.springbatchdemo.batch
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import jakarta.persistence.EntityManagerFactory
//import me.lemphis.springbatchdemo.entity.DummyItem
//import org.apache.ibatis.session.SqlSessionFactory
//import org.springframework.batch.core.ChunkListener
//import org.springframework.batch.core.ItemWriteListener
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.configuration.annotation.JobScope
//import org.springframework.batch.core.configuration.annotation.StepScope
//import org.springframework.batch.core.job.builder.JobBuilder
//import org.springframework.batch.core.repository.JobRepository
//import org.springframework.batch.core.scope.context.ChunkContext
//import org.springframework.batch.core.step.builder.StepBuilder
//import org.springframework.batch.item.Chunk
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.io.ClassPathResource
//import org.springframework.jdbc.datasource.DataSourceTransactionManager
//import org.springframework.transaction.PlatformTransactionManager
//import org.springframework.util.LinkedCaseInsensitiveMap
//import java.util.logging.Logger
//import javax.sql.DataSource
//
//
//@Configuration
//class SimpleJobConfiguration(
//	private val jobRepository: JobRepository,
//	private val entityManagerFactory: EntityManagerFactory,
//	private val dataSource: DataSource,
//	private val sqlSessionFactory: SqlSessionFactory,
//	private val mapper: Mapper,
//	private val platformTransactionManager: PlatformTransactionManager,
//) {
//
//	private companion object {
//		const val STEP_1 = "step1"
//	}
//
//	@Bean
//	fun simpleJob(@Qualifier(STEP_1) step1: Step) = JobBuilder("simpleJob", jobRepository)
//		.start(step1)
//		.build()
//
//	@Bean
//	fun taskletJob() = JobBuilder("taskletJob", jobRepository)
//		.start(tasklet())
//		.next(tasklet2())
//		.build()
//
//	@Bean(STEP_1)
//	@JobScope
//	fun simpleStep1(transactionManager: PlatformTransactionManager) = StepBuilder("simpleStep1", jobRepository)
//		.allowStartIfComplete(true)
//		.chunk<DummyCsvItem, DummyItem>(10000, transactionManager)
////		.listener(
////			// csv file 10000 line read time: 100ms
////			// 10000 line insert to db time: 5s, JpaItemWriter
////			// chunk간 4.5초 차이, chunk size: 10000
////
////			// 10000 line insert to db time: 150ms, JdbcBatchItemWriter
////			// 15499999 line insert to db time: 335s, JdbcBatchItemWriter
////
////			// 10000 line insert to db time: 150ms, MyBatisBatchItemWriter
////			// 15499999 line insert to db time: 371s, MyBatisBatchItemWriter
////			object : ChunkListener {
////				override fun beforeChunk(context: ChunkContext) {
////					println("readCount: ${context.stepContext.stepExecution.readCount}")
////					println("beforeChunk: ${System.currentTimeMillis()}")
////				}
////
////				override fun afterChunk(context: ChunkContext) {
////					super.afterChunk(context)
////					println("afterChunk: ${System.currentTimeMillis()}")
////				}
////			},
////		)
////		.listener(
////			object : ItemWriteListener<DummyItem> {
////				override fun beforeWrite(items: Chunk<out DummyItem>) {
////					println("beforeWrite: ${System.currentTimeMillis()}")
////				}
////
////				override fun afterWrite(items: Chunk<out DummyItem>) {
////					println("afterWrite: ${System.currentTimeMillis()}")
////				}
////			},
////		)
//		.faultTolerant().noRetry(Throwable::class.java)
//		.reader(reader())
//		.processor { DummyItem(firstName = it.firstName, email = it.email) }
//		.writer(writer())
//		.build()
//
//	fun reader() = FlatFileItemReaderBuilder<DummyCsvItem>()
//		.name("dummyCsvReader")
//		.resource(ClassPathResource("/data/MOCK_DATA.csv"))
//		.linesToSkip(1)
//		.delimited()
//		.delimiter(DelimitedLineTokenizer.DELIMITER_COMMA)
//		.names("first_name", "last_name", "email")
//		.fieldSetMapper {
//			DummyCsvItem(
//				firstName = it.readString("first_name"),
//				lastName = it.readString("last_name"),
//				email = it.readString("email"),
//			)
//		}
//		.build()
//
////	fun reader() = ItemReader<List<DummyCsvItem>> {
////		var count = 0
////		val items = mutableListOf<DummyCsvItem>()
////		BufferedReader(FileReader(ClassPathResource("/data/MOCK_DATA.csv").file)).useLines {
////			for ()
////				count++;
////			}
////		}
////		items
////	}
//
////	fun writer() = JpaItemWriterBuilder<DummyItem>()
////		.entityManagerFactory(entityManagerFactory)
////		.build()
//
//	@StepScope
//	@Bean
//	fun writer() = JdbcBatchItemWriterBuilder<DummyItem>()
//		.dataSource(dataSource)
//		.sql("insert into dummy_item(first_name,email,created_at) values (:firstName, :email, :createdAt)")
//		.beanMapped()
//		.build()
//
////	@StepScope
////	fun writer() = MyBatisBatchItemWriterBuilder<DummyItem>()
////		.sqlSessionFactory(sqlSessionFactory)
////		.statementId("me.lemphis.springbatchdemo.batch.mybatis.Mapper.test")
////		.build()!!
//
//	@JobScope
//	@Bean
//	fun tasklet() = StepBuilder("sss", jobRepository)
//		.allowStartIfComplete(true)
//		.tasklet(
//			{ contribution, chunkContext ->
//				val selectTwoColumns = mapper.selectTwoColumns()
//				println("===============================================================")
//				println(selectTwoColumns)
//				contribution.stepExecution.jobExecution.executionContext.put("columns", selectTwoColumns)
//				RepeatStatus.FINISHED
//			},
//			platformTransactionManager,
//		)
//		.build()
//
//	@JobScope
//	@Bean
//	fun tasklet2() = StepBuilder("aaaa", jobRepository)
//		.allowStartIfComplete(true)
//		.tasklet(
//			{ contribution, chunkContext ->
//				val any = contribution.stepExecution.jobExecution.executionContext["columns"] as List<LinkedCaseInsensitiveMap<String>>
//				println(any[0])
//				println(any)
//				RepeatStatus.FINISHED
//			},
//			platformTransactionManager,
//		)
//		.build()
//
//}
