package com.herick.gestorcardapio.ui.preferencias

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.herick.gestorcardapio.R
import com.herick.gestorcardapio.databinding.FragmentPreferenciasBinding

class PreferenciasFragment : Fragment() {




    private lateinit var binding: FragmentPreferenciasBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val MEU_VALOR_PADRAO_TRUE = true
        const val MEU_VALOR_PADRAO_FALSE = false
        const val SWITCH_STATE_KEY = "switch_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreferenciasBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val switchState = sharedPreferences.getBoolean(SWITCH_STATE_KEY, MEU_VALOR_PADRAO_TRUE)
        binding.switch1.isChecked = switchState


        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(requireContext(), "Notificações ativadas..", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Notificações desativadas..", Toast.LENGTH_SHORT)
                    .show()
            }

            // Salvar o estado do switch no SharedPreferences
            with(sharedPreferences.edit()) {
                putBoolean(SWITCH_STATE_KEY, isChecked)
                apply()
            }
        }


        return binding.root
    }
}

