package com.herick.gestorcardapio.ui.suporte

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.herick.gestorcardapio.databinding.FragmentSuporteBinding

class SuporteFragment : Fragment() {
    private lateinit var binding: FragmentSuporteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuporteBinding.inflate(inflater, container, false)

        binding.contactWpp.setOnClickListener {
            val phoneNumber = "+5531994374195"
            val uri = Uri.parse("smsto:$phoneNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.setPackage("com.whatsapp")

            val activityContext = requireActivity()

            if (intent.resolveActivity(activityContext.packageManager) != null) {
                startActivity(intent)
            } else {
                val playStoreUri =
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
                startActivity(playStoreIntent)
            }
        }


        return binding.root
    }
}
