package com.example.submission_1_fundamental.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission_1_fundamental.adapter.ListUserFollowAdapter
import com.example.submission_1_fundamental.databinding.FragmentFollowBinding
import com.example.submission_1_fundamental.data.remote.response.UserFollow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycleUser: RecyclerView
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleUser = binding.rvUsersFollow
        recycleUser.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        recycleUser.layoutManager = layoutManager

        detailViewModel.pageNumber.observe(viewLifecycleOwner){
            showfollow(it)
        }

        detailViewModel.userFollow.observe(viewLifecycleOwner) {
            setListFollow(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showfollow(index : Int = 0){

        val detail = arguments?.getString(DATA_LOGIN)
        if( index == 0){
            detailViewModel.setUserFollower(detail!!)
        }else{
            detailViewModel.setUserFollowing(detail!!)
        }
    }

    private fun setListFollow(data : List<UserFollow>){
        val listUsers = data
        val adapter = ListUserFollowAdapter(listUsers)
        binding.rvUsersFollow.adapter = adapter
    }

    companion object {
        const val DATA_LOGIN = "data_login"
        const val ARG_SECTION_NUMBER = "section_number"
    }
}