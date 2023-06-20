package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaBasedItemReader(
	private val entityManagerFactory: EntityManagerFactory,
) {

	private companion object {
		const val JPA_PAGING_ITEM_READER = "jpaPagingItemReader"
		const val JPA_CURSOR_ITEM_READER = "jpaCursorItemReader"
		const val JPQL = "SELECT s FROM Src s"
	}

	@Bean
	fun jpaPagingItemReader() = JpaPagingItemReaderBuilder<Src>()
		.name(JPA_PAGING_ITEM_READER)
		.entityManagerFactory(entityManagerFactory)
		.queryString(JPQL)
		.pageSize(1_000)
		.build()

	@Bean
	fun jpaCursorItemReader() = JpaCursorItemReaderBuilder<Src>()
		.name(JPA_CURSOR_ITEM_READER)
		.entityManagerFactory(entityManagerFactory)
		.queryString(JPQL)
		.build()

}
