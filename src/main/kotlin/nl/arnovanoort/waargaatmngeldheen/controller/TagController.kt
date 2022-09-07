package nl.arnovanoort.waargaatmngeldheen

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class TagController {

    var logger: Logger = LoggerFactory.getLogger(TagController::class.java)

    @RequestMapping(value = ["/tags"], method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun executeAlgorithm(@RequestBody  request: Tag){
        logger.debug("processing request " + request)

//        for(transaction in request.transactions) print(transaction)
    }

    @GetMapping("/tags")
    fun getTags(): Tag {
        return testTransaction
    }

    companion object TestObject{
        val testTransaction = Tag(
            "recurring"
        )
    }
}


data class Tag (
    val name: String
)