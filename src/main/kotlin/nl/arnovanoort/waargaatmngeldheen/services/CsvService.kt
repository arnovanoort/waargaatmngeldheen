package nl.arnovanoort.waargaatmngeldheen.services

import com.opencsv.bean.CsvBindByPosition
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import nl.arnovanoort.waargaatmngeldheen.domain.Transaction
import nl.arnovanoort.waargaatmngeldheen.domain.Transactions
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.IOException
import java.io.InputStreamReader


@Service
class CsvService {
    private fun throwIfFileEmpty(file: MultipartFile) {
        if (file.isEmpty)
            println("### File is empty")
            //throw BadRequestException("Empty file")
        else
            println("file: " + file)
    }

    private fun createCSVToBean(fileReader: BufferedReader?): CsvToBean<Transaction> =
        CsvToBeanBuilder<Transaction>(fileReader)
            .withType(Transaction::class.java)
            .withSeparator(';')
//            .withSkipLines(1)
            .withIgnoreLeadingWhiteSpace(true)
            .build()


    data class UserWithCsvBindByPosition(
        @CsvBindByPosition(position = 0)
        var id: Long? = null,
        @CsvBindByPosition(position = 1)
        var firstName: String? = null,
        @CsvBindByPosition(position = 2)
        var lastName: String? = null,
        @CsvBindByPosition(position = 3)
        var email: String? = null,
        @CsvBindByPosition(position = 4)
        var phoneNumber: String? = null,
        @CsvBindByPosition(position = 5)
        var age: Int? = null
    )

    fun uploadCsvFile(file: MultipartFile): List<Transaction> {
        throwIfFileEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val csvToBean = createCSVToBean(fileReader)
            return csvToBean.parse()
        } catch (ex: Exception) {
            throw CsvImportException("Error during csv import")
        } finally {
            closeFileReader(fileReader)
        }
    }

    private fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw CsvImportException("Error during csv import")
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequestException(msg: String) : RuntimeException(msg)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class CsvImportException(msg: String) : RuntimeException(msg)
}