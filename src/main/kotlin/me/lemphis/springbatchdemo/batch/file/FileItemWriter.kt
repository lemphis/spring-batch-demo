package me.lemphis.springbatchdemo.batch.file

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
class FileItemWriter {

	@Bean
	@StepScope
	fun fileItemWriter() = FlatFileItemWriterBuilder<DummyItemDto>()
		.name("flatFileItemWriter")
		.encoding("UTF-8")
		.resource(FileSystemResource("."))
		.lineAggregator(delimitedLineAggregator())
		.build()

	private fun delimitedLineAggregator() = DelimitedLineAggregator<DummyItemDto>().apply {
		setDelimiter(",")
		setFieldExtractor(beanWrapperFieldExtractor())
	}

	private fun beanWrapperFieldExtractor() = BeanWrapperFieldExtractor<DummyItemDto>().apply {
		setNames(arrayOf("first_name", "last_name", "email"))
	}

}
