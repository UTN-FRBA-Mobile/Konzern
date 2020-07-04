package utn.frba.mobile.konzern.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import utn.frba.mobile.konzern.R
import java.lang.RuntimeException

class CustomToolbar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var menuInterface: ToolbarMenuInterface? = null

    init {
        View.inflate(context, R.layout.custom_toolbar, this)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            0,0
        ).recycle()

        if (context is ToolbarMenuInterface) {
            menuInterface = context
        } else {
            throw RuntimeException("$context must be ToolbarMenuInterface")
        }

        vCustomToolbarDotsMore.setOnClickListener { showPopup(this) }
        vCustomToolbarLogo.setOnClickListener { menuInterface?.onToolbarLogoClicked() }
    }

    fun setTitle(title: String) {
        vCustomToolbarTitle.text = title
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(context, v, Gravity.END, R.attr.popupMenuStyle, 0)
        val inflater: MenuInflater = popup.menuInflater

        addMenuItems(popup)
        popup.setOnMenuItemClickListener { goToActivity(it.itemId) }
        inflater.inflate(R.menu.custom_toolbar_menu, popup.menu)
        popup.show()
    }

    private fun goToActivity(menuItemId: Int): Boolean {
        return when(menuItemId) {
            0 -> {
                menuInterface?.onMenuMyProfileClicked()
                false
            }
            1 -> {
                menuInterface?.onMenuExpensesClicked()
                false
            }
            2 -> {
                menuInterface?.onMenuSeeReservationsClicked()
                false
            }
            3 -> {
                menuInterface?.onMenuContactInfoClicked()
                false
            }
            4 -> {
                //TODO: Cerrar Sesión
                Toast.makeText(context, context.getString(R.string.custom_toolbar_logout_item), Toast.LENGTH_SHORT).show()
                false
            }
            else -> false
        }
    }

    private fun addMenuItems(popupMenu: PopupMenu) {
        popupMenu.menu.apply {
            this.add(GROUP_ID, 0, 0, context.getString(R.string.custom_toolbar_my_profile_item))
            this.add(GROUP_ID, 1, 1, context.getString(R.string.custom_toolbar_expenses_item))
            this.add(GROUP_ID, 2, 2, context.getString(R.string.custom_toolbar_reservations_item))
            this.add(GROUP_ID, 3, 3, context.getString(R.string.custom_toolbar_contact_info_item))
            this.add(GROUP_ID, 3, 4, context.getString(R.string.custom_toolbar_logout_item))
        }
    }

    companion object {
        const val GROUP_ID = 0
    }
}

interface ToolbarMenuInterface {
    fun onMenuMyProfileClicked()

    fun onMenuExpensesClicked()

    fun onMenuSeeReservationsClicked()

    fun onToolbarLogoClicked()

    fun onMenuContactInfoClicked()
}