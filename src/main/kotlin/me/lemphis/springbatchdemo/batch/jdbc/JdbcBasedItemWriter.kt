package me.lemphis.springbatchdemo.batch.jdbc

import me.lemphis.springbatchdemo.batch.jpa.Src
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcBasedItemWriter(
	private val dataSource: DataSource,
) {

	private companion object {
		const val JDBC_INSERT_QUERY =
			"INSERT INTO src(first_name, last_name, email) VALUES(:firstName, :lastName, :email)"
	}

	@Bean
	fun jdbcBatchItemWriter() = JdbcBatchItemWriterBuilder<Src>()
		.dataSource(dataSource)
		.sql(JDBC_INSERT_QUERY)
		.beanMapped()
		.build()

}
