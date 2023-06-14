package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.EntityManagerFactory
import me.lemphis.springbatchdemo.batch.jpa.JpaDataImportJob.Companion.CHUNK_SIZE
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaBasedItemReader(
	private val entityManagerFactory: EntityManagerFactory,
) {

	@Bean
	fun jpaPagingItemReader() = JpaPagingItemReaderBuilder<DummyItem>()
		.name("jpaPagingItemReader")
		.entityManagerFactory(entityManagerFactory)
		.queryString("SELECT i FROM DummyItem i")
		.pageSize(CHUNK_SIZE)
		.build()

	@Bean
	fun jpaCursorItemReader() = JpaCursorItemReaderBuilder<DummyItem>()
		.name("jpaCursorItemReader")
		.entityManagerFactory(entityManagerFactory)
		.queryString("SELECT i FROM DummyItem i")
		.build()

}
