package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaBasedItemWriter(
	private val entityManagerFactory: EntityManagerFactory,
) {

	@Bean
	fun jpaItemWriter() = JpaItemWriterBuilder<DummyItem>()
		.entityManagerFactory(entityManagerFactory)
		.build()

}
