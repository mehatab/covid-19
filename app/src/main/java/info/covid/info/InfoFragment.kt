package info.covid.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import info.covid.databinding.FragmentInfoBinding
import android.content.Intent
import android.net.Uri
import info.covid.R
import java.lang.Exception


class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)
        requireActivity().setTitle(R.string.info)

        binding.whatsapp.setOnClickListener {
            openLink("https://api.whatsapp.com/send?phone=41225017655&text=hi")
        }

        binding.twitter.setOnClickListener {
            openLink("https://twitter.com/ShaikhMehatab")
        }

        return binding.root
    }

    private fun openLink(link: String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    companion object {
        fun newInstance(bundle: Bundle? = Bundle()) = InfoFragment().apply {
            arguments = bundle
        }
    }
}