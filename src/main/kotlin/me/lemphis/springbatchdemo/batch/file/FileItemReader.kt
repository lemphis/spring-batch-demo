package me.lemphis.springbatchdemo.batch.file

import me.lemphis.springbatchdemo.batch.DummyItemDto
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FileItemReader {

	@Bean
	fun csvFileItemReader() = FlatFileItemReaderBuilder<DummyItemDto>()
		.name("csvFileItemReader")
		.linesToSkip(1)
		.targetType(DummyItemDto::class.java)
		.resource(ClassPathResource("data/MOCK_DATA.csv"))
		.delimited().delimiter(",").names(
			"first_name",
			"last_name",
			"email",
		)
		.build()

}
