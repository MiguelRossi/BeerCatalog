package miguel.rossi.beercatalog.randombeer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import miguel.rossi.beercatalog.R
import miguel.rossi.beercatalog.databinding.FragmentRandomBeerBinding
import miguel.rossi.beercatalog.main.MyApplication
import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel
import miguel.rossi.beercatalog.randombeer.viewmodel.RandomBeerState
import miguel.rossi.beercatalog.randombeer.viewmodel.RandomBeerViewModel
import javax.inject.Inject

class RandomBeerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentRandomBeerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomBeerViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindView(inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentRandomBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is RandomBeerState.Beer -> handleBeer(it.beer)
                is RandomBeerState.Error -> handleError(it.message)
                RandomBeerState.Loading -> showLoadingViews(showLoading = true)
            }
        }
    }

    private fun handleError(message: String) {
        showLoadingViews(showLoading = false, showBeer = false)
        showSnackbar(message)
    }

    private fun handleBeer(beer: RandomBeerModel) {
        showLoadingViews(showLoading = false)
        populateBeer(beer)
    }

    private fun showLoadingViews(showLoading: Boolean, showBeer: Boolean = !showLoading) {
        with(binding) {
            progressbar.isVisible = showLoading
            beerDescriptionGroup.isVisible = showBeer
        }
    }

    private fun populateBeer(beer: RandomBeerModel) {
        with(binding) {
            beerName.text = beer.name
            beerAbv.text = beer.abv.toString()
            beerDescription.text = beer.description
            beerBrewerTips.text = beer.brewersTips
        }
    }

    private fun setOnClickListeners() {
        binding.freshBeerButton.setOnClickListener {
            viewModel.requestedFreshBeer()
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar
            .make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) { viewModel.requestedFreshBeer() }
            .show()
    }
}
