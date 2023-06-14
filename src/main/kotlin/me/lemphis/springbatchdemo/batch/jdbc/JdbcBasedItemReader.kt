package me.lemphis.springbatchdemo.batch.jdbc

import me.lemphis.springbatchdemo.batch.DummyItemDto
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
	fun jdbcPagingItemReader() = JdbcPagingItemReaderBuilder<DummyItemDto>()
		.dataSource(dataSource)
		.queryProvider(
			MySqlPagingQueryProvider().apply {
				setSelectClause("SELECT *")
				setFromClause("FROM dummy_item")
			},
		)
		.pageSize(1000)
		.beanRowMapper(DummyItemDto::class.java)
		.build()

}
