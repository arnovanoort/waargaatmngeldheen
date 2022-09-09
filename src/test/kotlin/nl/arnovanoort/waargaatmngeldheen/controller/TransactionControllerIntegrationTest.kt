package nl.arnovanoort.waargaatmngeldheen.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import nl.arnovanoort.waargaatmngeldheen.domain.Transactions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest (@Autowired val restTemplate: TestRestTemplate){

    val testTransactionJson = """{"transactions":[{"id":"000aeab3-2bf6-4f7d-a2b5-74fc4d877f05","date":"2022-07-29","description":"'GELDMAAT HOOFDSTRAAT 1>HELMOND  29.07.2022 14U05 KV014 812180                                 NLNEDERLAND'","amount":-10.0,"currency":"EUR"},{"id":"801f0b46-2531-4905-98ab-e5b66283668f","date":"2022-07-29","description":"'ASML To Go            >VELDHOVEN29.07.2022 14U20 KV001 Z388HT   MCC:5411                      NLNEDERLAND'","amount":-4.5,"currency":"EUR"}]}""".trimIndent()

    val testTransactionCsv = """
        |29-7-2022;NL84ASNB0950025895;;;;;;EUR;2452.59;EUR;-10.00;29-7-2022;29-7-2022;7910;GEA;52045228;;'GELDMAAT HOOFDSTRAAT 1>HELMOND  29.07.2022 14U05 KV014 812180                                 NLNEDERLAND';32
        |29-7-2022;NL84ASNB0950025895;;;;;;EUR;2442.59;EUR;-4.50;29-7-2022;29-7-2022;7913;BEA;52152448;;'ASML To Go            >VELDHOVEN29.07.2022 14U20 KV001 Z388HT   MCC:5411                      NLNEDERLAND';32
        |""".trimMargin()

    val mapper: ObjectMapper = ObjectMapper();
    init {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.registerModule(JavaTimeModule())
    }


    @Test
    fun `create transactions`(@Autowired webApplicationContext: WebApplicationContext ) {
        val uri = URI("/transactions")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(testTransactionJson, headers)
        val firstFile: MockMultipartFile = MockMultipartFile("data", "filename.txt", MediaType.APPLICATION_JSON_VALUE, testTransactionCsv.toByteArray());
        val mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        val result = mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
            .file(firstFile)
            .param("some-random", "4"))
            .andExpect(status().`is`(200))

        val retrievedEntity = restTemplate.getForEntity<String>(uri, String::class.java  )
        Assertions.assertEquals(retrievedEntity.statusCode,HttpStatus.OK)

        val deserialised = mapper.readValue(retrievedEntity.body, Transactions::class.java)
        Assertions.assertEquals(deserialised.transactions.size, 2)
    }
}
