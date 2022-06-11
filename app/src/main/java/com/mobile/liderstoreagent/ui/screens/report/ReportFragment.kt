package com.mobile.liderstoreagent.ui.screens.report

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.reportmodel.ReportData
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.viewmodels.report.ReportViewModel
import com.mobile.liderstoreagent.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.report_fragment.*
import java.io.File

class ReportFragment : Fragment(R.layout.report_fragment) {
    private val viewModel: ReportViewModel by viewModels()
    var inputComm = ""
    var imageAddress: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHomeReport.setOnClickListener {
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
//            .maxResultSize(1080, 1920)
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            imageAddress = ImagePicker.getFile(data)!!
                            requireContext().showToast("Расм танланди!")
                            selectedImage.setImageURI(Uri.fromFile(imageAddress))
                            selectedImage.visibility = View.VISIBLE
                        }

                        else -> {

                        }
                    }
                }


        }

        sendReport.setOnClickListener {
            inputComm = inputComment.text.toString()
            if (inputComm.isNotEmpty()) {
                sendPackage()
            } else requireContext().showToast("Ҳисобот техтини киритинг!")
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

    private val successObserver = Observer<Any> {
        AlertDialog.Builder(requireContext())
            .setTitle("Диққат!")
            .setMessage("Диққат ҳисобот юборилди !")
            .setPositiveButton("Ok") { dialog, _ ->
                findNavController().navigateUp()
                dialog.cancel()
            }.show()
    }



    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpReport() {
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorReportLiveData.observe(this, errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successObserver)
    }

    private fun sendPackage() {
        viewModel.sendPackage(ReportData(inputComm, imageAddress, TokenSaver.getAgentId()))
    }
}