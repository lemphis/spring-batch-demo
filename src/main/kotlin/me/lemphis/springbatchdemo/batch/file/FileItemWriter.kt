package me.lemphis.springbatchdemo.batch.file

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import java.nio.charset.StandardCharsets

@Configuration
class FileItemWriter {

	@Bean
	fun csvFileItemWriter() = FlatFileItemWriterBuilder<DummyItemDto>()
		.name("csvFileItemWriter")
		.encoding(StandardCharsets.UTF_8.name())
		.resource(FileSystemResource("output.csv"))
		.headerCallback { it.write("first_name,last_name,email") }
		.lineAggregator(delimitedLineAggregator())
		.build()

	private fun delimitedLineAggregator() = DelimitedLineAggregator<DummyItemDto>().apply {
		setDelimiter(",")
		setFieldExtractor(beanWrapperFieldExtractor())
	}

	private fun beanWrapperFieldExtractor() = BeanWrapperFieldExtractor<DummyItemDto>().apply {
		setNames(arrayOf("firstName", "lastName", "email"))
	}

}
