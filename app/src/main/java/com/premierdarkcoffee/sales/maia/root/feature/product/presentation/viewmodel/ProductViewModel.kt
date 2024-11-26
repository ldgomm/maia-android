package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Category
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Image
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Information
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Price
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Product
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Specifications
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.Warranty
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.DeleteProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PostProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.model.product.request.PutProductRequest
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.serviceable.Group
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.AddEditProductState
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.InformationResultState
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.state.ProductsState
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.AddProductUseCase
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.DeleteProductUseCase
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.GetGroupsUseCase
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.GetProductsUseCase
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.SearchProductUseCase
import com.premierdarkcoffee.sales.maia.root.feature.product.domain.usecase.UpdateProductUseCase
import com.premierdarkcoffee.sales.maia.root.util.function.getUrlFor
import com.premierdarkcoffee.sales.maia.root.util.key.getMaiaKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase,
    private val addEditedProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,

    private val searchProductUseCase: SearchProductUseCase,

    private val getGroupsUseCase: GetGroupsUseCase

) : AndroidViewModel(application) {

    // StateFlow to manage the list of products and their loading state
    private val _productsState = MutableStateFlow(ProductsState())
    val productsState: StateFlow<ProductsState> = _productsState

    private val _searchState = MutableStateFlow(ProductsState())
    val searchState: StateFlow<ProductsState> = _searchState

    // StateFlow to manage the Product and its loading state
    private var _addEditProductState = MutableStateFlow(AddEditProductState())
    var addEditProductState: StateFlow<AddEditProductState> = _addEditProductState

    // StateFlow to hold a list of information results related to a product
    private val _informationResultStateList = MutableStateFlow<List<InformationResultState>>(emptyList())
    val informationResultStateList: StateFlow<List<InformationResultState>> = _informationResultStateList.asStateFlow()

    // StateFlow to manage the search text input
    private val _searchProductText = MutableStateFlow("")
    val searchProductText = _searchProductText.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Firebase Authentication current user
//    private val user = FirebaseAuth.getInstance().currentUser

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    // Store key associated with the application instance
    private val storeKey = getMaiaKey(getApplication<Application>().applicationContext)

    init {
        // Initialize by fetching products and observing search text changes
//        getProducts(user?.uid ?: "")
//        observeSearchProductTextChanges(user?.uid ?: "")
//        observeSearchTextChanges()
    }

    fun initData(token: String) {
        getProducts(token = token)
        observeSearchProductTextChanges(token = token)
        observeSearchTextChanges(token)
        getGroups()
    }

    private fun getGroups() {
        val url = getUrlFor(endpoint = "data/groups")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getGroupsUseCase(url).collect { result ->
                    result.onSuccess { groups ->
                        _groups.value = groups
                    }.onFailure { exception ->
                        Log.e(TAG, "Error in getting groups: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getting groups: ${e.message}")
            }
        }
    }

    /**
     * Observes changes in the search text input and triggers a product search
     * if the text meets the required criteria.
     *
     * @param storeId The ID of the store associated with the search.
     */
    @OptIn(FlowPreview::class)
    private fun observeSearchProductTextChanges(
        token: String
    ) {
        viewModelScope.launch {
            _searchProductText.debounce(300).distinctUntilChanged().filter { it.length >= 3 }.collect { searchText ->
                searchStoreProduct(text = searchText, token = token)
            }
        }
    }

    /**
     * Triggers a search for products in the store based on the search text.
     *
     * @param storeId The ID of the store to search within.
     * @param text The search text input.
     */
    private fun searchStoreProduct(
        text: String,
        token: String
    ) {
        executeProductSearch(text = text, token = token)
    }

    /**
     * Fetches products from the store or all stores if no storeId is provided.
     *
     * @param storeId The ID of the store to fetch products from, or null to fetch all products.
     */
    private fun getProducts(
        token: String
    ) {
        executeProductSearch(token = token)
    }

    /**
     * Executes the product search or fetch operation.
     *
     * @param storeId The ID of the store to fetch products from, or null for all stores.
     * @param text The search text input, or null to fetch all products.
     */
    private fun executeProductSearch(
        text: String? = null,
        token: String
    ) {
        val url = getUrlFor(endpoint = "maia-product", keywords = text)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                getProductsUseCase(url, token).collect { result ->
                    result.onSuccess { products ->
                        _productsState.update { productState ->
                            productState.copy(products = products.map { it.toProduct() })
                        }
                    }.onFailure { exception ->
                        Log.e(TAG, "Error in product search: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in executeProductSearch: ${e.message}")
            }
        }
    }

    /**
     * Updates the search text value.
     *
     * @param text The new search text input.
     */
    fun onSearchProductTextChange(text: String) {
        _searchProductText.value = text
    }

    fun onRefresh(token: String) {
        _productsState.update { state ->
            state.copy(products = emptyList())
        }
        executeProductSearch(token = token)
    }

    /**
     * Clears the current search text.
     */
    fun clearSearchProductText() {
        _searchProductText.value = ""
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchTextChanges(
        token: String
    ) {
        viewModelScope.launch {
            _searchText.debounce(100).distinctUntilChanged().collect { searchText ->
                executeSearch(searchText, token)
            }
        }
    }

    private fun executeSearch(
        text: String,
        token: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                searchProductUseCase(getUrlFor(endpoint = "maia-product/products", keywords = text), token).collect { result ->
                    result.onSuccess { products ->
                        // Get the list of products that already exist in _productsState
                        val existingProducts = _productsState.value.products.orEmpty()

                        // Filter out products already present in _productsState
                        val newProducts = products.map { it.toProduct() }.filter { newProduct ->
                            !existingProducts.any { it.id == newProduct.id }
                        }

                        // Update the _searchState with the filtered products
                        _searchState.update { searchState ->
                            searchState.copy(products = newProducts)
                        }
                    }.onFailure { exception ->
                        Log.d(TAG, "SearchViewModel | searchProducts: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "SearchViewModel | Exception in searchProducts: ${e.message}")
            }
        }
    }

    fun onSearchTextChange(text: String) {
        Log.d(TAG, "SearchViewModel | onSearchTextChange: $text")
        _searchText.value = text
    }

    fun clearSearchText() {
        _searchText.value = ""
    }

    /**
     * Sets the current product to the provided product and updates the corresponding states.
     *
     * @param product The product to set.
     */
    fun setProduct(product: Product) {
        viewModelScope.launch {
            setId(product.id)
            setName(product.name)
            setLabel(product.label ?: "")
            setOwner(product.owner ?: "")
            setYear(product.year ?: "2024")
            setModel(product.model)
            setDescription(product.description)
            setCategory(product.category)
            setPrice(product.price)
            setStock(product.stock)
            setImage(product.image)
            setOrigin(product.origin)
            setDate(product.date)
            setOverview(product.overview)
            product.keywords?.let { setKeywords(it) }
            setSpecifications(product.specifications)
            setWarranty(product.warranty)
            setLegal(product.legal)
            setWarning(product.warning)

//            product.overview.forEach { result ->
//                result.image.path?.let { path ->
//                    val informationResult = InformationResultState(
//                        id = result.id,
//                        title = result.title,
//                        subtitle = result.subtitle,
//                        description = result.description,
//                        path = path,
//                        belongs = result.image.belongs,
//                        place = result.place
//                    )
//                    addInformationResult(informationResult)
//                }
//            }
        }
    }

    /**
     * Adds an information result to the list of information result states.
     *
     * @param informationResult The information result to add.
     */
    private fun addInformationResult(informationResult: InformationResultState) {
        _informationResultStateList.value += informationResult
    }

    /**
     * Adds a new product to the store.
     *
     * @param product The product to add.
     * @param onSuccess A callback invoked upon successful addition.
     * @param onFailure A callback invoked when the addition fails.
     */
    fun addProduct(
        product: Product,
        token: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addEditedProductUseCase(
                    getUrlFor(endpoint = "maia-product"), PostProductRequest(storeKey, product.toProductDto()), token = token
                ).collect { result ->
                    result.onSuccess {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }.onFailure {
                        withContext(Dispatchers.Main) {
                            onFailure()
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    /**
     * Updates an existing product in the store.
     *
     * @param product The product to update.
     * @param onSuccess A callback invoked upon successful update.
     * @param onFailure A callback invoked when the update fails.
     */
    fun updateProduct(
        product: Product,
        token: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateProductUseCase(
                    getUrlFor(endpoint = "maia-product"), PutProductRequest(storeKey, product.toProductDto()), token
                ).collect { result ->
                    result.onSuccess {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }.onFailure {
                        withContext(Dispatchers.Main) {
                            onFailure()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    fun deleteProduct(
        product: Product,
        token: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteProductUseCase(
                    getUrlFor(endpoint = "maia-product"), DeleteProductRequest(storeKey, product.id), token = token
                ).collect { result ->
                    result.onSuccess {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }.onFailure {
                        withContext(Dispatchers.Main) {
                            onFailure()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    /**
     * Sets the product ID in the add/edit product state.
     *
     * @param id The product ID.
     */
    private fun setId(id: String) {
        _addEditProductState.value = _addEditProductState.value.copy(id = id)
    }

    /**
     * Sets the product name in the add/edit product state.
     *
     * @param name The product name.
     */
    fun setName(name: String) {
        _addEditProductState.value = _addEditProductState.value.copy(name = name)
    }

    fun setLabel(label: String) {
        _addEditProductState.value = _addEditProductState.value.copy(label = label)
    }

    fun setOwner(owner: String) {
        _addEditProductState.value = _addEditProductState.value.copy(owner = owner)
    }

    fun setYear(year: String) {
        _addEditProductState.value = _addEditProductState.value.copy(year = year)
    }

    /**
     * Sets the product model in the add/edit product state.
     *
     * @param model The product model.
     */
    fun setModel(model: String) {
        _addEditProductState.value = _addEditProductState.value.copy(model = model)
    }

    /**
     * Sets the product description in the add/edit product state.
     *
     * @param description The product description.
     */
    fun setDescription(description: String) {
        _addEditProductState.value = _addEditProductState.value.copy(description = description)
    }

    /**
     * Sets the product category in the add/edit product state.
     *
     * @param category The product category.
     */
    private fun setCategory(category: Category) {
        _addEditProductState.value = _addEditProductState.value.copy(category = category)
    }

    /**
     * Sets the product price in the add/edit product state.
     *
     * @param price The product price.
     */
    fun setPrice(price: Price) {
        _addEditProductState.value = _addEditProductState.value.copy(price = price)
    }

    /**
     * Sets the product stock quantity in the add/edit product state.
     *
     * @param stock The product stock quantity.
     */
    fun setStock(stock: Int) {
        _addEditProductState.value = _addEditProductState.value.copy(stock = stock)
    }

    /**
     * Sets the product image in the add/edit product state.
     *
     * @param image The product image.
     */
    fun setImage(image: Image) {
        _addEditProductState.value = _addEditProductState.value.copy(image = image)
    }

    /**
     * Sets the product origin in the add/edit product state.
     *
     * @param origin The product origin.
     */
    private fun setOrigin(origin: String) {
        _addEditProductState.value = _addEditProductState.value.copy(origin = origin)
    }

    /**
     * Sets the product date in the add/edit product state.
     *
     * @param date The product date.
     */
    private fun setDate(date: Long) {
        _addEditProductState.value = _addEditProductState.value.copy(date = date)
    }

    /**
     * Sets the product overview information in the add/edit product state.
     *
     * @param overview The product overview information.
     */
    private fun setOverview(overview: List<Information>) {
        _addEditProductState.value = _addEditProductState.value.copy(overview = overview)
    }

    /**
     * Sets the product keywords in the add/edit product state.
     *
     * @param keywords The product keywords.
     */
    private fun setKeywords(keywords: List<String>) {
        _addEditProductState.value = _addEditProductState.value.copy(keywords = keywords.toMutableList())
    }

    fun addKeyword(keyword: String) {
        val updatedKeywords = _addEditProductState.value.keywords.toMutableList().apply {
            add(keyword)
        }
        _addEditProductState.value = _addEditProductState.value.copy(keywords = updatedKeywords)
    }

    fun deleteKeyword(index: Int) {
        val updatedKeywords = _addEditProductState.value.keywords.toMutableList().apply {
            removeAt(index)
        }
        _addEditProductState.value = _addEditProductState.value.copy(keywords = updatedKeywords)
    }

    /**
     * Sets the product specifications in the add/edit product state.
     *
     * @param specifications The product specifications, if any.
     */
    private fun setSpecifications(specifications: Specifications?) {
        _addEditProductState.value = _addEditProductState.value.copy(specifications = specifications)
    }

    /**
     * Sets the product warranty information in the add/edit product state.
     *
     * @param warranty The product warranty information, if any.
     */
    private fun setWarranty(warranty: Warranty?) {
        _addEditProductState.value = _addEditProductState.value.copy(warranty = warranty)
    }

    /**
     * Sets the product legal information in the add/edit product state.
     *
     * @param legal The product legal information, if any.
     */
    fun setLegal(legal: String?) {
        _addEditProductState.value = _addEditProductState.value.copy(legal = legal)
    }

    /**
     * Sets the product warning information in the add/edit product state.
     *
     * @param warning The product warning information, if any.
     */
    fun setWarning(warning: String?) {
        _addEditProductState.value = _addEditProductState.value.copy(warning = warning)
    }
}
