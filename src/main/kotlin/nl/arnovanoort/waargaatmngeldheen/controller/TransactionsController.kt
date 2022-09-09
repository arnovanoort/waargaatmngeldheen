package nl.arnovanoort.waargaatmngeldheen

import com.mongodb.client.MongoClient
import nl.arnovanoort.waargaatmngeldheen.domain.Transaction
import nl.arnovanoort.waargaatmngeldheen.domain.Transactions
import nl.arnovanoort.waargaatmngeldheen.repository.TransactionRepository
import nl.arnovanoort.waargaatmngeldheen.services.CsvService
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDate
import java.util.*


//@RestController
//class TransactionsController(
//    private val transactionRepository: TransactionRepository
//) {
//
//    var logger: Logger = LoggerFactory.getLogger(TransactionsController::class.java)
//
//    @RequestMapping(value = ["/transactions2"], method = [RequestMethod.POST],
//        consumes = [MediaType.MULTIPART_MIXED_VALUE],
//        produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun importTransactions(@RequestBody  request: Transaction){
//        logger.debug("processing request " + request)
//
////        for(transaction in request.transactions) print(transaction)
//    }

@RestController
class CsvController(
    private val transactionRepository: TransactionRepository,
    private val csvService: CsvService
) {
    @RequestMapping(value = ["/transactions"], method = [RequestMethod.POST])
    fun uploadCsvFile(
        @RequestParam("data") data: MultipartFile
    ): ResponseEntity<List<Transaction>> {

        println("filename " + data.originalFilename)
        val importedEntries = csvService.uploadCsvFile(data)
        for(transaction in importedEntries){
            val createdTransaction = transactionRepository.save(transaction)
            println("created" + createdTransaction)
        }
        return ResponseEntity.ok(importedEntries)
    }

    @GetMapping("/transactions")
    fun getTransactions(): Transactions {
        val retrievedTransactions: List<Transaction> = transactionRepository.findAll();
        return Transactions(retrievedTransactions)
    }
}


