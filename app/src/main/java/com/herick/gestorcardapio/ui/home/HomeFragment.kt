package com.herick.gestorcardapio.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.herick.gestorcardapio.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var advertisingIdGlobal: String
    private lateinit var tokenRecuperado: String

    private val sharedPreferencesKey = "SWITCH_STATE_KEY"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        advertisingIdGlobal = ""
        tokenRecuperado = ""

        GlobalScope.launch(Dispatchers.Main) {
            if (!shouldReceiveNotifications()) {
                FirebaseMessaging.getInstance().isAutoInitEnabled = false;
            }

            var advertisingId = getAdvertisingId()
            if (advertisingId != null) {
                advertisingIdGlobal = advertisingId
            }

            println("Advertising ID: $advertisingId")

            val webView = binding.webViewPrincipal



            val settings = webView.settings
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            settings.domStorageEnabled = true

            webView.webViewClient = WebViewClient()
            webView.loadUrl("https://staging.cardapio.ai/gestor?udid=$advertisingIdGlobal&token=$tokenRecuperado")
            webView.settings.javaScriptEnabled = true


        }

        getFCMToken { token ->
            tokenRecuperado = token ?: ""
        }

        return root
    }

    private suspend fun getAdvertisingId(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(requireContext())
                adInfo?.id
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun getFCMToken(callback: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(object : OnCompleteListener<String?> {
                override fun onComplete(task: Task<String?>) {
                    if (task.isSuccessful) {
                        val token = task.result
                        callback.invoke(token)
                    } else {
                        Log.e("ErroFCMToken", "Falha ao recuperar o token: ${task.exception}")
                        callback.invoke(null)
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun shouldReceiveNotifications(): Boolean {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(sharedPreferencesKey, true)
    }
}
