package nl.arnovanoort.waargaatmngeldheen

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest (@Autowired val restTemplate: TestRestTemplate){

    val testTransactionJson = "{\"date\":\"2022-08-31T20:53:53.3022841\",\"description\":\"Description\",\"amount\":10.5,\"type\":\"Contactloos\"}"

    @Test
    fun `get transaction`() {
        val uri = URI("/transactions")
        val entity = restTemplate.getForEntity<String>(uri, String::class.java  )
        Assertions.assertEquals(entity.statusCode,HttpStatus.OK)
        Assertions.assertEquals(entity.body, "{\"date\":\"2022-08-31T20:53:53.3022841\",\"description\":\"Description\",\"amount\":10.5,\"type\":\"Contactloos\"}")
    }

    @Test
    fun `create transactions`() {
        val uri = URI("/transactions")
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON

//==
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON


// if you need to pass form parameters in request with headers.
//        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
//
//        val request = HttpEntity(map, headers)
//        val responses: ResponseEntity<Transactions> = restTemplate.postForEntity<Transactions>(uri, request, String::class.java)
//==
        val entity = HttpEntity(testTransactionJson, headers)
        val result = restTemplate.postForEntity<String>(uri, entity, String::class.java)
//==
//        val entity2 = HttpEntity<String>(testTransactionJson, headers)
//        restTemplate.put(uri, entity2)
//        val entity = restTemplate.postForEntity(uri, testTransactionJson, String::class.java  )
//        val headers = HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        entity.headers(headers)
        Assertions.assertEquals(result.statusCode,HttpStatus.OK)
//        Assertions.assertEquals(result.body, "{\"date\":\"2022-08-31T20:53:53.3022841\",\"description\":\"Description\",\"amount\":10.5,\"type\":\"Contactloos\"}")
    }

}
