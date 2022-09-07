package nl.arnovanoort.waargaatmngeldheen.controller

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.net.URI
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest (@Autowired val restTemplate: TestRestTemplate){

    val testTransactionJson = "{\"date\":\"2022-08-31T20:53:53.3022841\",\"description\":\"Description\",\"amount\":10.5,\"type\":\"Contactloos\"}"


    @Test
    fun `get transaction`() {
        val uri = URI("/transactions")
        val entity = restTemplate.getForEntity<String>(uri, String::class.java  )
        Assertions.assertEquals(entity.statusCode,HttpStatus.OK)
        Assertions.assertEquals(entity.body, "{\"transactions\":[{\"id\":\"adf7b1af-354e-47e6-82fb-cb9839ed6e0e\",\"date\":\"2022-08-31\",\"description\":\"Description\",\"amount\":10.5,\"currency\":\"Contactloos\"}]}")
    }

    @Test
    fun `create transactions`(@Autowired webApplicationContext: WebApplicationContext ) {
        val uri = URI("/transactions")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(testTransactionJson, headers)
        val firstFile: MockMultipartFile = MockMultipartFile("data", "filename.txt", MediaType.APPLICATION_JSON_VALUE, testTransactionJson.toByteArray());
        val mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        val result = mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
            .file(firstFile)
            .param("some-random", "4"))
            .andExpect(status().`is`(200))
//            .andExpect(content().string("success"));
//    }


//==
//        val entity2 = HttpEntity<String>(testTransactionJson, headers)
//        restTemplate.put(uri, entity2)
//        val entity = restTemplate.postForEntity(uri, testTransactionJson, String::class.java  )
//        val headers = HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        entity.headers(headers)
//        Assertions.assertEquals(result.statusCode,HttpStatus.OK)
//        Assertions.assertEquals(result.body, "{\"date\":\"2022-08-31T20:53:53.3022841\",\"description\":\"Description\",\"amount\":10.5,\"type\":\"Contactloos\"}")
    }

}
//@Test
//public void test() throws Exception {
//
//    MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
//    MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
//    MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
//
//    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
//        .file(firstFile)
//        .file(secondFile)
//        .file(jsonFile)
//        .param("some-random", "4"))
//        .andExpect(status().is(200))
//        .andExpect(content().string("success"));
//}
//}