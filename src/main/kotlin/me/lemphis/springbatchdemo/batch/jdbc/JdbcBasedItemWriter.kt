package me.lemphis.springbatchdemo.batch.jdbc

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcBasedItemWriter(
	private val dataSource: DataSource,
) {

	@Bean
	fun jdbcBatchItemWriter() = JdbcBatchItemWriterBuilder<DummyItemDto>()
		.dataSource(dataSource)
		.sql("INSERT INTO dummy_item(first_item, last_name, email) VALUES(:firstName, :lastName, :email)")
		.beanMapped()
		.build()

}
