package com.mymestavendorapplication.ui.dashboad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mymestavendorapplication.R
import com.mymestavendorapplication.databinding.FragmentHomeBinding
import com.mymestavendorapplication.ui.base.UiState
import com.mymestavendorapplication.ui.dashboad.adpters.VendorListAdapter
import com.mymestavendorapplication.ui.dashboad.dashboardviewmodel.DashBoardViewModel
import com.mymestavendorapplication.ui.dashboad.dashboardviewmodel.DashBoardViewModelFactory
import com.mymestavendorapplication.ui.productdetails.ProductDetailsActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.android.material.search.SearchView



class HomeFragment : Fragment() {


    @Inject
    lateinit var dashBoardViewModel: DashBoardViewModel

    @Inject
    lateinit var dashboardViewModelFactory: DashBoardViewModelFactory

    @Inject
    lateinit var vendorListAdapter: VendorListAdapter

    private var _homebinding: FragmentHomeBinding? = null
    private val homebinding get() = _homebinding!!



    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onDetach() {
        super.onDetach()
   //     dataListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _homebinding = FragmentHomeBinding.inflate(inflater,container,false)
        dashBoardViewModel = ViewModelProvider(this, dashboardViewModelFactory).get(DashBoardViewModel::class.java)

        setUI()



        return homebinding.root

    }

    private fun setUI() {

      //  dashBoardViewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        vendorListAdapter = VendorListAdapter(emptyList()) { vendor ->

            val intent = Intent(requireContext(), ProductDetailsActivity::class.java).apply {
                putExtra("vendor", vendor)
            }
            startActivity(intent)
        }
        dashBoardViewModel.vendors.observe(viewLifecycleOwner, Observer { vendorList ->
            if (vendorList != null) {
                Log.d("HomeFragment", "Vendors list size: ${vendorList.size}")
                vendorListAdapter.updateList(vendorList)
            } else {
                Log.d("HomeFragment", "Vendor list is empty or null")
            }
        })

        dashBoardViewModel.loadVendors()

        val recyclerView = homebinding.vedorProductItemsRecycler

        val gridLayoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = vendorListAdapter




        homebinding.edtSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { dashBoardViewModel.filterVendors(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { dashBoardViewModel.filterVendors(it) }
                return true
            }
        })



    }


    override fun onDestroyView() {
        super.onDestroyView()

        _homebinding = null // Clear the binding reference to avoid memory leaks
    }
}


