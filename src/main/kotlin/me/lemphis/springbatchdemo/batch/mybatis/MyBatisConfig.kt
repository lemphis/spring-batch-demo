package me.lemphis.springbatchdemo.batch.mybatis

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class MyBatisConfig(
	private val applicationContext: ApplicationContext,
) {

	@Bean
	fun sqlSessionFactory(dataSource: DataSource) = SqlSessionFactoryBean().apply {
		setDataSource(dataSource)
		setMapperLocations(*applicationContext.getResources("classpath:/mapper/**/*Mapper.xml"))
	}

	@Bean
	fun sqlSession(sqlSessionFactory: SqlSessionFactory) = SqlSessionTemplate(sqlSessionFactory)

}
