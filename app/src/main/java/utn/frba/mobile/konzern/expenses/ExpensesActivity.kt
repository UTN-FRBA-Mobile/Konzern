package utn.frba.mobile.konzern.expenses

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import kotlinx.android.synthetic.main.activity_expenses.*
import org.json.JSONArray
import utn.frba.mobile.konzern.R
import java.io.File
import java.io.FileOutputStream

class ExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        vDownloadButton.setOnClickListener{ createPDFFile (this@ExpensesActivity) }

        var expenses = JSONArray("""[{"amount": "1582.20", "month":"Abril", "exp_date":"12/05/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Marzo", "exp_date":"16/04/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Febrero", "exp_date":"15/03/2020", "apartment":"1 C"}]""")
        var i = 0;
        var coin = resources.getString(R.string.expenses_coin)

        while (i < expenses.length()) {
            var expense = expenses.getJSONObject(i)

            if (i == 0) {
                vTextLastExpenseMonth.append(expense.getString("month"))
                vTextLastExpenseAmount.append(" " + coin + " " + expense.getString("amount"))
                vTextLastExpenseExpirationDate.append(" " + expense.getString("exp_date"))
            } else {
                vTextPreviousExpensesMonth.append(expense.getString("month"))
                vTextPreviousExpensesAmount.append(" " + coin + " " + expense.getString("amount"))
                vTextPreviousExpensesExpirationDate.append(" " + expense.getString("exp_date"))
            }
            i++
        }

    }

    private fun createPDFFile (context: Context) {

        try {
            //Valido permisos
            val permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No tiene permiso")
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),225)
            } else {
                Log.i("Mensaje", "Tiene permiso")
            }

            //Creo la carpeta de la app
            val dir = File(Environment.getExternalStorageDirectory().toString()
                    +File.separator
                    +context.resources.getString(R.string.app_name)
                    +File.separator)
            if(!dir.exists())
                dir.mkdir()

            val path = dir.path+File.separator+"expenses.pdf"

            if(File(path).exists())
                File(path).delete()

            //Genero el documento
            val document = Document()

            PdfWriter.getInstance(document,FileOutputStream(path))

            document.open()

            //Configuracion del PDF
            document.pageSize = PageSize.A4
            document.addCreationDate()
            document.addAuthor(context.resources.getString(R.string.app_name))
            document.addCreator(context.resources.getString(R.string.app_name))

            //Estilos
            val titleStyle = Font(Font.FontFamily.HELVETICA, 36.0f, Font.NORMAL, BaseColor.BLACK)
            val subtitleStyle = Font(Font.FontFamily.HELVETICA, 26.0f,Font.NORMAL, BaseColor.LIGHT_GRAY)
            val detailStyle = Font(Font.FontFamily.HELVETICA, 30.0f,Font.NORMAL, BaseColor.GRAY)

            //Contenido del PDF
            addNewTitle(document, "Expensas", Element.ALIGN_CENTER, titleStyle)

            addLineSeparator(document)

            addNewDetail(document,"Monto","1582.20", detailStyle)
            addNewDetail(document,"Mes","Abril", detailStyle)
            addNewDetail(document,"Fecha Vencimiento","12/05/2020", detailStyle)

            addLineSeparator(document)

            addNewTitle(document, "Detalle", Element.ALIGN_LEFT, titleStyle)
            addNewTitle(document, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Integer vitae augue quis odio efficitur efficitur. Maecenas at ipsum sapien. " +
                    "Praesent id libero cursus justo vulputate tempus. Suspendisse vitae arcu ut.", Element.ALIGN_LEFT, subtitleStyle)

            //Cierro el documento
            document.close()

            Toast.makeText(this@ExpensesActivity,"Guardado",Toast.LENGTH_LONG).show()

        } catch (e:Exception) {
            Log.e("Error", e.message)
        }
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
    private fun addNewTitle(document:Document, text:String, align:Int, style:Font) {
        val chunk = Chunk(text, style)
        val p = Paragraph(chunk)
        p.alignment = align
        document.add(p)
    }

    @Throws(DocumentException::class)
    private fun addNewDetail(document:Document, textLeft:String, textRight:String, style:Font) {
        val chunkTextLeft = Chunk(textLeft, style)
        val chunkTextRight = Chunk(textRight, style)
        val p = Paragraph(chunkTextLeft)
        p.add(Chunk(VerticalPositionMark()))
        p.add(chunkTextRight)
        document.add(p)
    }
}