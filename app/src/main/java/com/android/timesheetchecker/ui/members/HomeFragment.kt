package com.android.timesheetchecker.ui.members

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.timesheetchecker.R
import com.android.timesheetchecker.TimesheetCheckerViewModelFactory
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.databinding.FragmentHomeBinding
import com.android.timesheetchecker.ui.timesheetservice.TimesheetCheckerService
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : DaggerFragment(), MemberListAdapter.MemberListInteractionListener {

    @Inject
    lateinit var viewModelFactory: TimesheetCheckerViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentHomeBinding
    private var memberListAdapter = MemberListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
            val intent = Intent(context, TimesheetCheckerService::class.java).apply {
                putExtra(TimesheetCheckerService.HOLIDAYS_KEY, spinner.selectedItemPosition)
            }
            activity?.startService(intent)
        }

        homeViewModel.users.observe(viewLifecycleOwner, Observer {
            memberListAdapter.swapData(it)
        })

        val spinnerAdapter = ArrayAdapter.createFromResource(
            binding.membersRv.context,
            R.array.days,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        binding.gotoDevTeamFragment.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDevTeamFragment())
        }
    }

    override fun onMemberClicked(user: UserLocal) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMemberDetailFragment(
                user.id
            )
        )
    }
}
