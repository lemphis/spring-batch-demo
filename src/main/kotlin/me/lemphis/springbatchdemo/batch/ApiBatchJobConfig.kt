//package me.lemphis.springbatchdemo.batch
//
//import org.slf4j.LoggerFactory
//import org.springframework.batch.core.job.builder.JobBuilder
//import org.springframework.batch.core.repository.JobRepository
//import org.springframework.batch.core.step.builder.StepBuilder
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpMethod
//import org.springframework.http.MediaType
//import org.springframework.transaction.PlatformTransactionManager
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.web.client.RestTemplate
//import org.springframework.web.util.UriComponentsBuilder
//import java.net.URLDecoder
//import java.time.YearMonth
//import java.time.format.DateTimeFormatter
//
//@Configuration
//class ApiBatchJobConfig(
//	private val jobRepository: JobRepository,
//	private val platformTransactionManager: PlatformTransactionManager,
//) {
//
//	private val log = LoggerFactory.getLogger(this.javaClass)
//	private val formatter = DateTimeFormatter.ofPattern("yyyyMM")
//
//	@Bean("apiJob")
//	fun apiJob() = JobBuilder("API_JOB", jobRepository)
//		.start(saveExecutionContextStep())
//		.next(getDataFromApiAndInsertStep())
//		.next(printStep())
//		.build()
//
//	fun saveExecutionContextStep() = StepBuilder("STEP1", jobRepository)
//		.tasklet(
//			{ contribution, chunkContext ->
//				chunkContext.stepContext.stepExecution.jobExecution.executionContext.put("from", "202301")
//				chunkContext.stepContext.stepExecution.jobExecution.executionContext.put("to", "202303")
//				RepeatStatus.FINISHED
//			},
//			platformTransactionManager,
//		)
//		.build()
//
//	fun getDataFromApiAndInsertStep() = StepBuilder("STEP1", jobRepository)
//		.tasklet(
//			{ contribution, chunkContext ->
//				val from = chunkContext.stepContext.jobExecutionContext["from"] as String
//				val to = chunkContext.stepContext.jobExecutionContext["to"] as String
//				val uri = UriComponentsBuilder
//					.fromUriString("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcLandTrade")
//					.queryParam("LAWD_CD", "11110")
//					.queryParam("DEAL_YMD", from)
//					.queryParam(
//						"serviceKey",
//						URLDecoder.decode("JNAehmcQrMWQUltxPPbQ9GnvJEl2XfhfSxlGxe03iDfi3tHTvOTA7wNfSxeIEz5dBBr8ZRhAPNaWcipy9RUXgQ%3D%3D"),
//					)
//					.build()
//				log.info(uri.toString())
//				val restTemplate = RestTemplate()
//				val map = LinkedMultiValueMap<String, String>()
//				map[HttpHeaders.ACCEPT] = MediaType.TEXT_XML_VALUE
//				val entity = HttpEntity(mapOf("1" to "2"), map)
////				val dto = restTemplate.exchange(uri.toUri(), HttpMethod.GET, entity, Response::class.java).body
//				val dto = restTemplate.getForEntity(uri.toString(),Response::class.java).body
//				println(dto)
//				println(dto?.response?.body?.items?.size)
//				val fromYearMonth = YearMonth.parse(from, formatter)
//				val toYearMonth = YearMonth.parse(to, formatter)
//				if (!fromYearMonth.equals(toYearMonth)) {
//					chunkContext.stepContext.stepExecution.jobExecution.executionContext.put(
//						"from",
//						fromYearMonth.plusMonths(1).format(formatter),
//					)
//					log.info("from: ${chunkContext.stepContext.jobExecutionContext["from"] as String}")
//					log.info("to: ${chunkContext.stepContext.jobExecutionContext["to"] as String}")
//					return@tasklet RepeatStatus.CONTINUABLE
//				}
//				log.info("from: ${chunkContext.stepContext.jobExecutionContext["from"] as String}")
//				log.info("to: ${chunkContext.stepContext.jobExecutionContext["to"] as String}")
//				RepeatStatus.FINISHED
//			},
//			platformTransactionManager,
//		)
//		.build()
//
//	fun printStep() = StepBuilder("STEP2", jobRepository)
//		.tasklet(
//			{ _, chunkContext ->
//				log.info(chunkContext.stepContext.jobExecutionContext["from"].toString())
//				RepeatStatus.FINISHED
//			},
//			platformTransactionManager,
//		)
//		.build()
//
//}
//
//data class Response(
//	val response: Dto,
//)
//
//data class Dto(
//	val header: Header?,
//	val body: Body?,
//)
//
//data class Header(
//	val resultCode: Int?,
//	val resultMsg: String?,
//)
//
//data class Body(
//	val items: List<Item>?,
//	val numOfRows: Int?,
//	val pageNo: Int?,
//	val totalCount: Int?,
//)
//
//data class Item(
//	val 거래금액: String?,
//	val 거래면적: String?,
//	val 거래유형: String?,
//	val 구분: String?,
//	val 년: String?,
//	val 법정동: String?,
//	val 시군구: String?,
//	val 용도지역: String?,
//	val 월: String?,
//	val 일: String?,
//	val 중개사소재지: String?,
//	val 지목: String?,
//	val 지번: String?,
//	val 지역코드: String?,
//	val 해제사유발생일: String?,
//	val 해제여부: String?,
//)
