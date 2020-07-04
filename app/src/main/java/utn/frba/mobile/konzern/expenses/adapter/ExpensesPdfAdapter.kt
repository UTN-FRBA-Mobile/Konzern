package utn.frba.mobile.konzern.expenses.adapter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.contact.model.Contact
import utn.frba.mobile.konzern.expenses.model.Expenses
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToLong

class ExpensesPdfAdapter {

    fun permitPDFFile (activity: Activity) {

        try {
            //Valido permisos
            val permissionCheck = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No tiene permiso")
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    225
                )
            } else {
                Log.i("Mensaje", "Tiene permiso")
            }
        } catch (e:Exception) {
            Log.e("Error", e.message)
        }
    }

    fun createPDFFile (expense: Expenses, consortium: Contact, context: Context?) : String {

        try {

            //Creo la carpeta de la app
            val dir = File(
                Environment.getExternalStorageDirectory().toString()
                    + File.separator
                    + context!!.resources.getString(R.string.app_name)
                    + File.separator)
            if(!dir.exists())
                dir.mkdir()

            val path = dir.path+ File.separator+"expenses"+expense.monthLabel+".pdf"

            if(File(path).exists())
                File(path).delete()

            //Genero el documento
            val document = Document()

            PdfWriter.getInstance(document, FileOutputStream(path))

            document.open()

            //Configuracion del PDF
            document.pageSize = PageSize.A4
            document.addCreationDate()
            document.addAuthor(context.resources.getString(R.string.app_name))
            document.addCreator(context.resources.getString(R.string.app_name))

            //Estilos
            val titleStyle = Font(Font.FontFamily.HELVETICA, 28.0f, Font.NORMAL, BaseColor.BLACK)
            val subtitleStyle = Font(Font.FontFamily.HELVETICA, 20.0f, Font.NORMAL, BaseColor.LIGHT_GRAY)
            val detailStyle = Font(Font.FontFamily.HELVETICA, 22.0f, Font.NORMAL, BaseColor.GRAY)
            val subDetailStyle = Font(Font.FontFamily.HELVETICA, 16.0f, Font.NORMAL, BaseColor.LIGHT_GRAY)

            //Contenido del PDF
            addNewTitle(document, context.resources.getString(R.string.expenses_pdf_title), Element.ALIGN_CENTER, titleStyle)
            addNewTitle(document, context.resources.getString(R.string.expenses_pdf_subtitle_month) + " " + expense.monthLabel + " " + expense.year, Element.ALIGN_CENTER, titleStyle)

            addLineSeparator(document)
            addLineSeparator(document)

            addNewTitle(document,context.resources.getString(R.string.expenses_pdf_consortium_label),Element.ALIGN_CENTER, subtitleStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_consortium_name),consortium.name, detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_consortium_address),consortium.address, detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_consortium_mail),consortium.email, detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_consortium_phone),consortium.phone, detailStyle)

            addLineSeparator(document)
            addLineSeparator(document)

            addNewTitle(document,context.resources.getString(R.string.expenses_pdf_account_state),Element.ALIGN_CENTER, subtitleStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_apartment_label),expense.apartment, detailStyle)

            addLineSpace(document)

            addNewTitle(document,context.resources.getString(R.string.expenses_pdf_concepts_to_pay),Element.ALIGN_CENTER, subtitleStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_expenses_type_a),context.resources.getString(R.string.expenses_coin) + " " +(expense.amount.toBigDecimal().multiply(0.8.toBigDecimal()).setScale(2)), detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_expenses_type_b),context.resources.getString(R.string.expenses_coin) + " " +(expense.amount.toBigDecimal().multiply(0.2.toBigDecimal()).setScale(2)), detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_total_amount),context.resources.getString(R.string.expenses_coin) + " " +(expense.amount.toBigDecimal().setScale(2)), detailStyle)
            addNewDetail(document,context.resources.getString(R.string.expenses_pdf_expiration_date),expense.expirationDate, detailStyle)

            addLineSeparator(document)

            addNewTitle(document,context.resources.getString(R.string.expenses_pdf_extra_info),Element.ALIGN_LEFT, subtitleStyle)
            addNewTitle(document,context.resources.getString(R.string.expenses_pdf_extra_info_detail),Element.ALIGN_LEFT, subDetailStyle)

            addLineSeparator(document)

            //Cierro el documento
            document.close()

            return path

        } catch (e:Exception) {
            Log.e("Error", e.message)
        }

        return ""
    }


    private fun addLineSeparator(document: Document) {
        val lineSeparator = LineSeparator()
        lineSeparator.lineColor = BaseColor.LIGHT_GRAY
        addLineSpace(document)
        document.add(Chunk(lineSeparator))
        addLineSpace(document)
    }

    private fun addLineSpace(document: Document) {
        document.add(Paragraph(""))
    }

    @Throws(DocumentException::class)
    private fun addNewTitle(document: Document, text:String, align:Int, style: Font) {
        val chunk = Chunk(text, style)
        val p = Paragraph(chunk)
        p.alignment = align
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addNewDetail(document: Document, textLeft:String, textRight:String, style: Font) {
        val chunkTextLeft = Chunk(textLeft, style)
        val chunkTextRight = Chunk(textRight, style)
        val p = Paragraph(chunkTextLeft)
        p.add(Chunk(VerticalPositionMark()))
        p.add(chunkTextRight)
        document.add(p)
    }
}