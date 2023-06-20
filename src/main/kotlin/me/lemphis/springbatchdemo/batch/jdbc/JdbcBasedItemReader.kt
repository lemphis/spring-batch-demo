package me.lemphis.springbatchdemo.batch.jdbc

import me.lemphis.springbatchdemo.batch.jpa.Src
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcBasedItemReader(
	private val dataSource: DataSource,
) {

	@Bean
	fun jdbcPagingItemReader() = JdbcPagingItemReaderBuilder<Src>()
		.name("jdbcPagingItemReader")
		.dataSource(dataSource)
		.queryProvider(
			MySqlPagingQueryProvider().apply {
				setSelectClause("SELECT first_name, last_name, email")
				setFromClause("FROM src")
				sortKeys = mapOf("id" to Order.ASCENDING)
			},
		)
		.pageSize(1_000)
		.beanRowMapper(Src::class.java)
		.build()

	@Bean
	fun jdbcCursorItemReader() = JdbcCursorItemReaderBuilder<Src>()
		.name("jdbcCursorItemReader")
		.dataSource(dataSource)
		.sql("SELECT first_name, last_name, email FROM src")
		.beanRowMapper(Src::class.java)
		.build()

}
