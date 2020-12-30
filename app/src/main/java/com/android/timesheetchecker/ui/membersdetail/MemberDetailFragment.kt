package com.android.timesheetchecker.ui.membersdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.timesheetchecker.R
import com.android.timesheetchecker.TimesheetCheckerViewModelFactory
import com.android.timesheetchecker.databinding.FragmentMemberDetailBinding
import com.android.timesheetchecker.utils.getTotal
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_member_detail.view.*
import javax.inject.Inject

class MemberDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: TimesheetCheckerViewModelFactory
    private val memberDetailViewModel: MemberDetailViewModel by viewModels { viewModelFactory }
    private val args: MemberDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMemberDetailBinding
    private val entriesAdapter = EntriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_member_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weeklyReportRv.apply {
            adapter = entriesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.includeUserSwitch.setOnCheckedChangeListener { _, checked ->
            memberDetailViewModel.updateUserExcludedStatus(!checked)
        }

        binding.includeUserInDev.setOnCheckedChangeListener { _, checked ->
            memberDetailViewModel.updateUserDevTeamStatus(checked)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.markCompleted.setOnClickListener {
            memberDetailViewModel.markTimesheetCompleted()
        }

        memberDetailViewModel.setSelectedUserId(args.selectedUserId)

        memberDetailViewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            binding.includeUserSwitch.isChecked = !it.excluded
            binding.includeUserInDev.isChecked = it.isDev
            binding.appBarLayout2.toolbar.title = it.name
            if (!it.excluded) {
                binding.weeklyReportGroup.visibility = View.VISIBLE
                memberDetailViewModel.fetchEntries()
                if (!it.completed) {
                    binding.markCompleted.visibility = View.VISIBLE
                } else {
                    binding.markCompleted.visibility = View.GONE
                }
            } else {
                binding.weeklyReportGroup.visibility = View.GONE
                binding.markCompleted.visibility = View.GONE
            }
        })

        memberDetailViewModel.entriesReceivedEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { data ->
                entriesAdapter.swapData(data)
                binding.totalValue.text = String.format("%.2f hrs", data.getTotal())
            }
        })

        memberDetailViewModel.errorEvent.observe(viewLifecycleOwner, Observer {
            Log.d("Error", it.getContentIfNotHandled()?.message + "")
        })
    }
}
