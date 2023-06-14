package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisBasedItemWriter(
	private val sqlSessionFactory: SqlSessionFactory,
) {

	@Bean
	fun mybatisBatchItemWriter() = MyBatisBatchItemWriterBuilder<DummyItemDto>()
		.sqlSessionFactory(sqlSessionFactory)
		.statementId("Mapper.id")
		.build()!!

}
