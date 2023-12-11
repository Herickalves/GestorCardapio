package com.herick.gestorcardapio.ui.sobre

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.herick.gestorcardapio.R
import com.herick.gestorcardapio.databinding.FragmentSobreBinding

class SobreFragment : Fragment() {

    private lateinit var binding: FragmentSobreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSobreBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAvalie.setOnClickListener {

            redirecionarParaAvaliacoes()
        }

        binding.txtSugestao.setOnClickListener {
            val email = "suporte@cardapio.ai"
            val subject = "Sugestão"
            val message = getString(R.string.sugestao_padrao)

            val uri = Uri.parse("mailto:$email?subject=${Uri.encode(subject)}&body=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_SENDTO, uri)

            val activityContext = requireActivity()

            if (intent.resolveActivity(activityContext.packageManager) != null) {
                startActivity(intent)
            }
        }


    }

    private fun redirecionarParaAvaliacoes() {
        val packageName = requireActivity().packageName

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}
