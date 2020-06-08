package utn.frba.mobile.konzern.expenses

import org.json.JSONArray

data class Expenses (
    val month: String,
    val amount: String,
    val expirationDate: String,
    val apartment: String
)