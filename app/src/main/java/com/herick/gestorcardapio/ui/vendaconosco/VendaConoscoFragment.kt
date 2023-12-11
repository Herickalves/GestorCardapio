package com.herick.gestorcardapio.ui.vendaconosco

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herick.gestorcardapio.databinding.FragmentVendaConoscoBinding

class VendaConoscoFragment : Fragment() {

    private lateinit var binding: FragmentVendaConoscoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVendaConoscoBinding.inflate(inflater, container, false)

        val handler = Handler()
        handler.postDelayed({
            val url = "https://cardapio.ai/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

            activity?.finish()
        }, 2000)


        return binding.root
    }
}
