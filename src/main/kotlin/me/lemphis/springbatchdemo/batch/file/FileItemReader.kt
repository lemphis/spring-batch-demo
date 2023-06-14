package me.lemphis.springbatchdemo.batch.file

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FileItemReader {

	@Bean
	fun fileItemReader() = FlatFileItemReaderBuilder<DummyItemDto>()
		.linesToSkip(1)
		.build()

}
