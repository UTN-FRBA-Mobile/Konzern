package utn.frba.mobile.konzern.expenses

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import kotlinx.android.synthetic.main.activity_expensas_arreglo.*
import kotlinx.android.synthetic.main.activity_expenses.*
import kotlinx.android.synthetic.main.custom_expenses_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.expenses.adapter.ExpensesAdapter
import java.io.File
import java.io.FileOutputStream


class ExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expensas_arreglo)

        val expensesList = arrayListOf(
            Expenses(
                "Octubre",
                "10.123",
                "01/10/2019"
            ),
            Expenses(
                "Noviembre",
                "10.123",
                "01/11/2019"
            ),
            Expenses(
                "Diciembre",
                "10.123",
                "01/12/2019"
            ),
            Expenses(
                "Enero",
                "10.123",
                "01/01/2020"
            ),
            Expenses(
                "Febrero",
                "11.123",
                "01/02/2020"
            ),
            Expenses(
                "Marzo",
                "12.123",
                "01/03/2020"
            ),
            Expenses(
                "Abril",
                "13.123",
                "01/04/2020"
            )
        )

        val viewManager = LinearLayoutManager(this)
        val adapter = ExpensesAdapter(expensesList)

        vExpensesRecyclerView.apply {
            this.layoutManager = viewManager
            this.adapter = adapter
        }

        vExpensesLastExpenseItem.apply {
            this.vExpensesItemMonth.text = "Mayo"
            this.vExpensesItemAmountNumber.text = "15.123"
            this.vExpensesItemExpireDate.text = "01/05/2020"
        }
        //------------------------------------------------------------

/*
        //TODO: Este JSONArray debe ser la respuesta del servicio que traiga de la base las expensas
        var expenses = JSONArray("""[{"amount": "1582.20", "month":"Abril", "exp_date":"12/05/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Marzo", "exp_date":"16/04/2020", "apartment":"1 C"},
                                {"amount": "1350.20", "month":"Febrero", "exp_date":"15/03/2020", "apartment":"1 C"},
                                {"amount": "1032.20", "month":"Enero", "exp_date":"10/02/2020", "apartment":"1 C"}]""")
        var i = 0;
        var coin = resources.getString(R.string.expenses_coin)

        while (i < expenses.length()) {
            var expense = expenses.getJSONObject(i)

            vDownloadButton.setOnClickListener{ createPDFFile (this@ExpensesActivity, expense) }

            if (i == 0) {
                vTextLastExpenseMonth.append(expense.getString("month"))
                vTextLastExpenseAmount.append(" " + coin + " " + expense.getString("amount"))
                vTextLastExpenseExpirationDate.append(" " + expense.getString("exp_date"))
            } else {

                val horizontal_linear_layout = LinearLayout(this)

                horizontal_linear_layout.layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                horizontal_linear_layout.orientation = LinearLayout.HORIZONTAL

                linear_previous_expenses_layout.addView(horizontal_linear_layout)

                val dynamic_month_text = TextView(this)
                dynamic_month_text.textSize = 21f
                dynamic_month_text.setPadding(resources.getDimension(R.dimen.expenses_text_padding_left).toInt(),0,0,0)
                dynamic_month_text.text = expense.getString("month")

                horizontal_linear_layout.addView(dynamic_month_text,
                    LinearLayout.LayoutParams(resources.getDimension(R.dimen.expenses_month_text_width).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT))

                val dynamic_download_button = Button(this)
                dynamic_download_button.background = resources.getDrawable(R.drawable.download_icon)
                dynamic_download_button.setOnClickListener{createPDFFile (this@ExpensesActivity, expense)}

                horizontal_linear_layout.addView(dynamic_download_button,
                    LinearLayout.LayoutParams(resources.getDimension(R.dimen.expenses_download_icon_size).toInt(), resources.getDimension(R.dimen.expenses_download_icon_size).toInt()))

                val dynamic_amount_text = TextView(this)
                dynamic_amount_text.textSize = 21f
                dynamic_amount_text.setPadding(resources.getDimension(R.dimen.expenses_text_padding_left).toInt(),0,0,0)
                dynamic_amount_text.text = resources.getString(R.string.expense_amount_label) + " " + coin + " " + expense.getString("amount")

                linear_previous_expenses_layout.addView(dynamic_amount_text,
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))

                val dynamic_expiration_date_text = TextView(this)
                dynamic_expiration_date_text.textSize = 20f
                dynamic_expiration_date_text.setPadding(resources.getDimension(R.dimen.expenses_text_padding_left).toInt(),0,0,0)
                dynamic_expiration_date_text.text = resources.getString(R.string.expense_expiration_date_label) + " " + expense.getString("exp_date")

                linear_previous_expenses_layout.addView(dynamic_expiration_date_text,
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))

                val separator = TextView(this)
                linear_previous_expenses_layout.addView(separator,
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            }
            i++
        }

 */

    }

    private fun createPDFFile (context: Context, json: JSONObject) {

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

            val path = dir.path+File.separator+"expenses"+json.getString("month")+".pdf"

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

            addNewDetail(document,"Monto",resources.getString(R.string.expenses_coin) + " " +json.getString("amount"), detailStyle)
            addNewDetail(document,"Mes",json.getString("month"), detailStyle)
            addNewDetail(document,"Fecha Vencimiento",json.getString("exp_date"), detailStyle)

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