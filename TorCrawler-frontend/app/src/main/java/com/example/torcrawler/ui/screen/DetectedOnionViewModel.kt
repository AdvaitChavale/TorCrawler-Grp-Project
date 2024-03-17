package com.example.torcrawler.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torcrawler.data.api.RetrofitInstance
import com.example.torcrawler.data.api.SearchRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetectedOnionViewModel: ViewModel() {

    private val apiService = RetrofitInstance.api

    private val _searchResults = MutableLiveData<List<String>>(emptyList())
    val searchResults: LiveData<List<String>> = _searchResults

    fun searchOnion(query: String) {
        viewModelScope.launch {
            delay(3000)
            _searchResults.value = detectedOnions
        }
    }
}































val detectedOnions = listOf(
    "777777ipar4tzwxylsznx6o7trdgo5dgirkjx3orufjjnenjbe5xheyd.onion",
    "amazonalixuydfexvh4w5xifzk74pupijdaqtgfiv24rvmnlhkdfixqd.onion",
    "hgunsxfupqwrf3nrr64tzmbhopaxqjfzofplkzs2nn24xlxptlxmadqd.onion",
    "27ezymw7uhjvgybj6em3bp4hx57gx2hv74rx2h7dldyk26pj3ablg5ad.onion",
    "dreamshk3pkeizw2rfdxn73odxuido3rwevvt4sfl3ewc4frvcf5h4yd.onion",
    "777777ilrk7nqto5ff5f3kqj7wmk4ndkhr2aalzhbcwhrjje5xwdruqd.onion",
    "kxyxzejb35dt6yaqt4vkxzgztav5bcb7q6bbpjlf26epa22ft3huokqd.onion",
    "stuffdl3egmih7cqjy4xvibyhhnbamtm2q4wmyo44uafmkld7f4njpid.onion",
    "outlwlkeogfx6st5vurttiteaii4fw4uzvldpwtv4zw5g2kzedmx2rad.onion",
    "wvaelbpvuh4x6lldesvkkycelyrt3vrzlig7ktujqkyoga4h6ypvzjqd.onion",
    "stuffdleakt4xraufncfbovubw6pmdv3i2wekbdrblqph43fwmkhsvqd.onion",
    "aby6efzmqxqpzizujvokx2inrv4k6kkoyxa6r5qweq2qapygpiyhpiid.onion",
    "4dy3gt7zgirids6qtrcvxjdxcewclpdvnxuve5pxizfhr6bzfsd7keqd.onion",
    "4bwxgwwise2ws74wsrhjph63u7l3xkk44c7r4dfmnwrnmjniwhjpobqd.onion",
    "shopsjfee4t6uf3ds5idbag3fflhzxv3zmrbha5bjsce26iedzk5yiyd.onion",
    "5obv6utvb4xgs6kodbvkjol2fp2i6uj3oejkbq4r2n2g4k2hgmvurgyd.onion",
    "agoraqxaz3pyvxsedpamwk4pg4y7ime52gmno6azba7im5i7e2bqxaad.onion",
    "4bujn7xhwvkmya7alh5fg7uiuw2keygaa5nx7f4so6r5o5whqsavq5yd.onion",
    "5xttrskiaioxjexorkttwdry2xah2l7mirucfkrbr6ncipkobnxi6yyd.onion",
)