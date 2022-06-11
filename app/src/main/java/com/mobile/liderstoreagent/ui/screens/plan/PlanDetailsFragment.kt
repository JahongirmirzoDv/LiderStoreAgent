package com.mobile.liderstoreagent.ui.screens.plan

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.planmodel.PlanDetail
import com.mobile.liderstoreagent.ui.adapters.plan.PlansDetailsAdapter
import com.mobile.liderstoreagent.ui.viewmodels.plan.PlanDetailViewModel
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.plan_details_fragment.*

@Suppress("DEPRECATION")
class PlanDetailsFragment : Fragment(R.layout.plan_details_fragment) {

    private val viewModel: PlanDetailViewModel by viewModels()
    lateinit var recycler: RecyclerView
    var planDetailsData: List<PlanDetail> = ArrayList()
    private val plansDetailsAdapter by lazy { PlansDetailsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planDetailsSetUp()
        recycler = view.findViewById(R.id.recyclerPlansDetail)
        val args = PlanDetailsFragmentArgs.fromBundle(requireArguments())

       if(args.myArg!=0) viewModel.getPlansDetails(args.myArg.toString())

        refreshPlansDetail.setOnRefreshListener {
             viewModel.getPlansDetails(args.myArg.toString())
            Handler().postDelayed(Runnable {
                refreshPlansDetail.isRefreshing = false
            }, 2000)
        }


        backToHomePlanDetail.setOnClickListener {
           findNavController().navigateUp()
        }

    }

    private fun initRecycler(plans: List<PlanDetail>) {
        plansDetailsAdapter.submitList(plans)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = plansDetailsAdapter
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            plansProgressBarDetail.visibility = View.VISIBLE
        } else {
            plansProgressBarDetail.visibility = View.GONE
        }
    }

    private val errorHistoryObserver = Observer<Unit> {
        requireActivity().showToast("Уланишда хатолик!")
        plansProgressBarDetail.visibility = View.GONE
    }

    private val errorEmptyDataObserver = Observer<String> {
        requireActivity().showToast("Маълумот мавжуд эмас! Бўш.")
        plansProgressBarDetail.visibility = View.GONE
    }

    private val successHistoryObserver = Observer<List<PlanDetail>> { plans ->
        planDetailsData = plans
        initRecycler(planDetailsData)
    }



    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun planDetailsSetUp() {
        viewModel.errorNotEmptyResponseLiveData.observe(this, errorEmptyDataObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.errorPlanDetailLiveData.observe(this, errorHistoryObserver)
        viewModel.connectionErrorLiveData.observe(this, connectionErrorObserver)
        viewModel.successLiveData.observe(this, successHistoryObserver)
    }


}