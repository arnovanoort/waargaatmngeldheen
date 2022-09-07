package nl.arnovanoort.waargaatmngeldheen.repository

import nl.arnovanoort.waargaatmngeldheen.domain.Transaction
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository: MongoRepository<Transaction, String> {
    fun findOneById(id: ObjectId): Transaction
}
