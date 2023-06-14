package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisBasedItemReader(
	private val sqlSessionFactory: SqlSessionFactory,
) {

	@Bean
	fun mybatisPagingItemReader() = MyBatisPagingItemReaderBuilder<DummyItemDto>()
		.sqlSessionFactory(sqlSessionFactory)
		.queryId("Mapper.id")
		.pageSize(1000)
		.build()!!

}
