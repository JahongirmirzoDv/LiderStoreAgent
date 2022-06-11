package com.mobile.liderstoreagent.ui.screens.report

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.reportmodel.ClientReportData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.adapters.ImagesAdapter
import com.mobile.liderstoreagent.ui.viewmodels.report.ClientReportViewModel
import com.mobile.liderstoreagent.utils.hideKeyboard
import com.mobile.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.client_report_fragment.*
import java.io.File

class ClientReportFragment : Fragment(R.layout.client_report_fragment) {

    private val viewModel: ClientReportViewModel by viewModels()
    var inputComm = ""
    private val imageAdapter by lazy { ImagesAdapter() }
    var imageAddresses = ArrayList<File>()
    var clientId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ClientReportFragmentArgs.fromBundle(requireArguments())
        clientId = args.myClientIdForReport

        backToHomeReport.setOnClickListener {
            inputComment.hideKeyboard(requireActivity())
            findNavController().navigateUp()
        }
        setUpReport()

        pickImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .saveDir(
                    File(
                        requireContext()
                            .getExternalFilesDir(null)?.let { it.absolutePath }, "DotanationBox"
                    )
                )
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            imageAddresses.add(ImagePicker.getFile(data)!!)
                            requireContext().showToast("Расм қўшилди!")
                            initImagesList(imageAddresses)
                        }

                        else -> {

                        }
                    }
                }

        }

        sendReport.setOnClickListener {
            inputComm = inputComment.text.toString()
            if (imageAddresses.isNotEmpty()) {
                if (inputComm.isNotEmpty()) {
                    sendPackage()
                } else requireContext().showToast("Ҳисобот техтини киритинг!")
            } else requireContext().showToast("Расм қўшинг!")
        }
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            reportProgressBar.visibility = View.VISIBLE
        } else {
            reportProgressBar.visibility = View.GONE
        }
    }

    private val errorLoginObserver = Observer<String> {
        requireActivity().showToast("Хатолик!")
    }

    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    private val timeOutObserver = Observer<Unit> {
        requireActivity().showToast("Интернет секин ишламаяпди!")
    }

    private val successObserver = Observer<Any> {
        AlertDialog.Builder(requireContext())
            .setTitle("Диққат!")
            .setMessage("Диққат ҳисобот юборилди !")
            .setPositiveButton("Ок") { dialog, _ ->
                findNavController().navigateUp()
                dialog.cancel()
            }.show()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpReport() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorClientReportLiveData.observe(this, errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorTimeOutLiveData.observe(this, timeOutObserver)
    }

    private fun sendPackage() {
        viewModel.sendClientReportPackage(
            ClientReportData(
                inputComm,
                imageAddresses,
                TokenSaver.getAgentId(),
                clientId
            )
        )
    }

    private fun initImagesList(data: List<File>) {
        imageAdapter.submitList(data)
        recyclerImages.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerImages.adapter = imageAdapter
    }

}