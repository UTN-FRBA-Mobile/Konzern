package utn.frba.mobile.konzern.contact.model

data class Contact(
    val name: String = "",
    val imageUrl: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val attentionTime: String = "",
    val extraInfo: String = ""
)