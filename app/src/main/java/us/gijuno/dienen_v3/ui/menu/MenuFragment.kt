package us.gijuno.dienen_v3.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import us.gijuno.dienen_v3.R

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        menuViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}