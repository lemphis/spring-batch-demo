package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.jpa.Src
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisBasedItemReader(
	private val sqlSessionFactory: SqlSessionFactory,
) {

	@Bean
	fun mybatisPagingItemReader() = MyBatisPagingItemReaderBuilder<Src>()
		.sqlSessionFactory(sqlSessionFactory)
		.queryId("me.lemphis.springbatchdemo.batch.mybatis.Mapper.selectFromSrc")
		.pageSize(1000)
		.build()!!

	@Bean
	fun mybatisCursorItemReader() = MyBatisCursorItemReaderBuilder<Src>()
		.sqlSessionFactory(sqlSessionFactory)
		.queryId("me.lemphis.springbatchdemo.batch.mybatis.Mapper.selectFromSrc")
		.build()!!

}
