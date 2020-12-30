package com.android.timesheetchecker.ui.members.developers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.timesheetchecker.R
import com.android.timesheetchecker.TimesheetCheckerViewModelFactory
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.databinding.FragmentDevTeamBinding
import com.android.timesheetchecker.ui.members.MemberListAdapter
import com.android.timesheetchecker.ui.timesheetservice.DailyTimesheetCheckerService
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DevTeamFragment : DaggerFragment(), MemberListAdapter.MemberListInteractionListener {

    @Inject
    lateinit var viewModelFactory: TimesheetCheckerViewModelFactory
    private val devTeamViewModel: DevTeamViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDevTeamBinding
    private var memberListAdapter = MemberListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev_team, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.membersRv.adapter = memberListAdapter
        binding.membersRv.addItemDecoration(
            DividerItemDecoration(
                binding.membersRv.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.startScanningBtn.setOnClickListener {
            val intent = Intent(context, DailyTimesheetCheckerService::class.java)
            activity?.startService(intent)
        }

        devTeamViewModel.users.observe(viewLifecycleOwner, Observer {
            memberListAdapter.swapData(it)
        })
    }

    override fun onMemberClicked(user: UserLocal) {
        findNavController().navigate(
            DevTeamFragmentDirections.actionDevTeamFragmentToMemberDetailFragment(
                user.id
            )
        )
    }

}
