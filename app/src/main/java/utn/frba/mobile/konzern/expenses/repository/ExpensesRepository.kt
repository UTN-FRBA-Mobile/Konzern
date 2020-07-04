package utn.frba.mobile.konzern.expenses.repository

import com.google.firebase.firestore.FirebaseFirestore
import utn.frba.mobile.konzern.contact.model.Contact
import utn.frba.mobile.konzern.expenses.model.Expenses

class ExpensesRepository {

    fun getExpensesData(expensesInterface: ExpensesRepositoryInterface) {
        val expensesList = mutableListOf<Expenses>()
        val database = FirebaseFirestore.getInstance()
        database.collectionGroup(COLLECTION_ID)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    expensesList.add(document.toObject(Expenses::class.java))
                }
                expensesInterface.onComplete(expensesList.toList())
            }
            .addOnFailureListener {
                expensesInterface.onFailure()
            }
    }

    companion object {
        const val COLLECTION_ID = "expenses"
    }

    interface ExpensesRepositoryInterface {
        fun onComplete(expensesList: List<Expenses>)

        fun onFailure()
    }
}