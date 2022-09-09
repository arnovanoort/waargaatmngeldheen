package nl.arnovanoort.waargaatmngeldheen.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.opencsv.bean.CsvBindByPosition
import com.opencsv.bean.CsvDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


data class Transactions (
    @JsonProperty("transactions")
    val transactions: List<Transaction>
)
@Document
data class Transaction (

    @Id
    val id: UUID,
    @CsvBindByPosition(position = 0)
    @CsvDate(value = "dd-M-yyyy")
    val date: LocalDate,
    @CsvBindByPosition(position = 17)
    val description: String,
    @CsvBindByPosition(position = 10)
    val amount: Float,
    @CsvBindByPosition(position = 7)
    val currency: String
){
    constructor() : this(UUID.randomUUID(), LocalDate.now(), "Desccription", 1.0f, "eur")
}