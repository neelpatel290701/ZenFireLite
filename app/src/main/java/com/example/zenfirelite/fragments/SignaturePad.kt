package com.example.zenfirelite.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.zenfirelite.R
import com.example.zenfirelite.databinding.FragmentSignaturePadBinding
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class SignaturePad : Fragment() {

    private lateinit var binding: FragmentSignaturePadBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignaturePadBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.signatureClear.setOnClickListener{
            binding.signaturePad.clear()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbaritems, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem_save = menu.findItem(R.id.save_signature)
        menuItem_save.isVisible = true //ion
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId) {
            R.id.save_signature -> {
                Toast.makeText(requireContext(), "Signature Saved", Toast.LENGTH_SHORT).show()

                val signatureBitmap = binding.signaturePad.signatureBitmap
                passDataBackToParentFragment(signatureBitmap)
                parentFragmentManager.popBackStack()

//                navController.popBackStack()
//                binding.ivSignature.setImageBitmap(binding.signaturePad.signatureBitmap)
                true
            }
            else -> false
        }
    }

    private fun passDataBackToParentFragment(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val result = Bundle().apply {
            putByteArray(FormDetails.SIGNATURE_DATA_KEY, byteArray)
        }
        parentFragmentManager.setFragmentResult(FormDetails.SIGNATURE_RESULT_KEY, result)
    }


}