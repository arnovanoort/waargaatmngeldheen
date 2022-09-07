package nl.arnovanoort.waargaatmngeldheen

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@RestController
class TransactionsController {

    var logger: Logger = LoggerFactory.getLogger(TransactionsController::class.java)
    @RequestMapping(value = ["/transactions2"], method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
//    @PostMapping(value = "/transactions", consumes = {"application/json"})
//    @PostMapping(value=["/transactions"])
    fun executeAlgorithm(@RequestBody  request: Transaction){
        logger.debug("processing request " + request)

//        for(transaction in request.transactions) print(transaction)
    }

    @GetMapping("/transactions2")
    fun getTransactions(): Transaction {
        return testTransaction
    }

    companion object TestObject{
        val testTransaction = Transaction(
            LocalDateTime.parse("2022-08-31T20:53:53.3022841"),
            "Description",
            10.50f,
            "Contactloos"
        )
    }
}


data class Transactions (
    val transactions: List<Transaction>
)

data class Transaction (
    val date: LocalDateTime,
    val description: String,
    val amount: Float,
    val type: String
)