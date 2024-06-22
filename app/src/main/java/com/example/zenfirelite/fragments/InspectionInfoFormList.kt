package com.example.zenfirelite.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zenfirelite.R
import com.example.zenfirelite.adapters.AdapterForFormTemplatesList
import com.example.zenfirelite.adapters.AdapterForInspectionForm
import com.example.zenfirelite.adapters.AdapterForPreviousFormList
import com.example.zenfirelite.databinding.FragmentInspectionInfoFormListBinding
import com.example.zenfirelite.datamodels.InspectionListModel
import com.example.zenfirelite.viewmodels.FormDetailsViewModel
import com.example.zenfirelite.viewmodels.FormTemplatesListViewModel
import com.example.zenfirelite.viewmodels.PreviousFormsViewModel
import com.example.zenfirelite.viewmodels.TicketFormsViewModel
import com.example.zenfirelite.viewmodels.TicketInfoViewModel


@Suppress("DEPRECATION")
class InspectionInfoFormList : Fragment() {
    private lateinit var binding : FragmentInspectionInfoFormListBinding
    private lateinit var parentTopLinearLayout : LinearLayout
    private var parentFragment: Fragment? = null

    private var screenWidth: Int = 0

    private val viewModel: FormTemplatesListViewModel by viewModels()
    private lateinit var ticketFormsViewModel: TicketFormsViewModel
    private lateinit var previousFormsViewModel: PreviousFormsViewModel
    private val ticketInfoViewModel: TicketInfoViewModel by viewModels({ requireParentFragment()})
    //requireParentFragment - LifeCycleAwareness  , tiedBound , workOn Single Instance , destroy with parent

    private lateinit var formDetailsviewModel: FormDetailsViewModel


    private lateinit var ticketInfo : InspectionListModel
    private var isTicketInfoObserved = false

    companion object {
        private const val KEY_TICKET_INFO_OBSERVED = "key_ticket_info_observed"
        private const val KEY_TICKET_INFO = "key_ticket_info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
    }
    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInspectionInfoFormListBinding.inflate(inflater,container,false)

        binding.formsRecycleView.layoutManager = LinearLayoutManager(context)

        // Initialize TicketFormsViewModel
        ticketFormsViewModel = ViewModelProvider(this)[TicketFormsViewModel::class.java]

        if (savedInstanceState != null) {
            isTicketInfoObserved = savedInstanceState.getBoolean(KEY_TICKET_INFO_OBSERVED, false)
            ticketInfo = savedInstanceState.getParcelable(KEY_TICKET_INFO)!!
        }

        // Observe ticketInfo only once
        if (!isTicketInfoObserved) {
            ticketInfoViewModel.ticketInfo.observe(viewLifecycleOwner, Observer { ticketInfoData ->
                ticketInfo = ticketInfoData
                ticketFormsViewModel.setTicketId(ticketInfo.ticketId)
                // Set flag to true after observing ticketInfo
                isTicketInfoObserved = true
            })
        }

        ticketFormsViewModel.ticketFormsList.observe(viewLifecycleOwner, Observer { ticketFormsList ->
            if (ticketFormsList != null) {
                val adapter = AdapterForInspectionForm(ticketFormsList,screenWidth){ticketFormInfo->
                    Toast.makeText(requireContext(), ticketFormInfo.fpFormDisplayName, Toast.LENGTH_SHORT).show()
                }
                binding.formsRecycleView.adapter = adapter
            }else{
                Toast.makeText(requireContext(), "previousFormsList-Null", Toast.LENGTH_SHORT).show()
            }
        })


//        // Get the ViewModel
//        formDetailsviewModel = ViewModelProvider(this).get(FormDetailsViewModel::class.java)
//        // Observe the LiveData
//        formDetailsviewModel.formDetails.observe(viewLifecycleOwner, Observer { formDetails ->
//            // Update the UI with formDetails
//            Log.d("neel","!!!!!!$formDetails")
//        })


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addForm.setOnClickListener{
            openFormList()
        }

        parentFragment  = requireParentFragment()
        when (parentFragment) {
            is InspectionInfo -> {
                val parentView = (parentFragment as InspectionInfo).requireView()
                parentTopLinearLayout = parentView.findViewById<LinearLayout>(R.id.insInfo_TopLayout)
            }
            is CustomerDetails -> {
                binding.addForm.visibility =View.GONE
                val parentView = (parentFragment as CustomerDetails).requireView()
                parentTopLinearLayout = parentView.findViewById<LinearLayout>(R.id.cusDetail_TopLayout)
            }
        }

        binding.searchForm.setOnClickListener {
            parentTopLinearLayout.visibility = View.GONE
        }


        binding.searchForm.addTextChangedListener(object: TextWatcher {
            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                parentTopLinearLayout.visibility = View.GONE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_TICKET_INFO_OBSERVED, isTicketInfoObserved)
        outState.putParcelable(KEY_TICKET_INFO, ticketInfo)
    }


    private fun openFormList() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_addform_list)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialogeboxbackground)

        val dialogWidth = WindowManager.LayoutParams.MATCH_PARENT
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(900, 1300)

        dialog.window?.setGravity(Gravity.CENTER)


        val formTemplatesRecycleView = dialog.findViewById<RecyclerView>(R.id.formTemplatesRecycleView)
        val previousFormsRecycleView = dialog.findViewById<RecyclerView>(R.id.previousFormsRecycleView)
        val formTemplate = dialog.findViewById<TextView>(R.id.formtemplates)
        val previousForm = dialog.findViewById<TextView>(R.id.previousForm)
        var formTemplateflag = true

        formTemplate.setOnClickListener{
            if(!formTemplateflag) {
                formTemplatesRecycleView.visibility = View.VISIBLE
            }else{
                formTemplatesRecycleView.visibility = View.GONE
            }
            formTemplateflag = !formTemplateflag
        }

        var PreviousFormflag = true
        previousForm.setOnClickListener{
            if(!PreviousFormflag) {
                previousFormsRecycleView.visibility = View.VISIBLE
            }else{
                previousFormsRecycleView.visibility = View.GONE
            }
            PreviousFormflag = !PreviousFormflag
        }
        formTemplatesRecycleView .layoutManager = LinearLayoutManager(requireContext())
        previousFormsRecycleView .layoutManager = LinearLayoutManager(requireContext())


        viewModel.formTemplatesList.observe(viewLifecycleOwner, Observer { formTemplatesList ->
            if (formTemplatesList != null) {
                val formTemplatesAdapter = AdapterForFormTemplatesList(formTemplatesList , dialog){
                    formDetails,formName ->
                    val action = InspectionInfoDirections.actionInspectionInfoToFormDetails2(formDetails,formName,0)
                    val navController = Navigation.findNavController(requireParentFragment().requireView())
                    navController.navigate(action)
                }
                formTemplatesRecycleView.adapter = formTemplatesAdapter
            }else{
                Toast.makeText(requireContext(), "FormTemplatesList-Null", Toast.LENGTH_SHORT).show()
            }
        })

        previousFormsViewModel = ViewModelProvider(this)[PreviousFormsViewModel::class.java]
        previousFormsViewModel.setServiceAddressId(ticketInfo.serviceAddressId)

        previousFormsViewModel.previousFormsList.observe(viewLifecycleOwner, Observer {previousFormsList ->
            if (previousFormsList  != null) {
                val previousFormAdapter = AdapterForPreviousFormList(previousFormsList){formDetails->
                    Toast.makeText(requireContext(), formDetails.displayName, Toast.LENGTH_SHORT).show()
                }
                previousFormsRecycleView.adapter = previousFormAdapter
            }else{
                Toast.makeText(requireContext(), "PreviousFormsList-Null", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.show()
    }


}

