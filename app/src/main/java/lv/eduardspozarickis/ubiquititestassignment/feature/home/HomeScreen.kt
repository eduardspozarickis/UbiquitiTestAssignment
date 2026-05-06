package lv.eduardspozarickis.ubiquititestassignment.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import lv.eduardspozarickis.ubiquititestassignment.R
import lv.eduardspozarickis.ubiquititestassignment.data.remote.ApiResult
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.Product
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.ProductCategory
import lv.eduardspozarickis.ubiquititestassignment.ui.theme.UbiquityTestAssignmentTheme
import lv.eduardspozarickis.ubiquititestassignment.utils.ImageHelper

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onItemClick: (Product) -> Unit
) {
    val imageUrl = ImageHelper.getImageUrl(
        id = product.id,
        imageIdentifier = product.imageId,
        size = 128
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onItemClick(product) }
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // product image
            AsyncImage(
                model = imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(28.dp)
            )

            // title, subtitle
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF212427)
                    )
                )
                Text(
                    text = product.line,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF808893)
                    )
                )
            }

            // right arrow-icon
            Icon(
                painter = painterResource(R.drawable.ic_arrow_to_right),
                contentDescription = null
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp),
            thickness = 1.dp,
            color = Color(0xFFF4F5F6)
        )
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onItemClick: (Product) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(products) {
        if (products.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.05f to Color.Black,
                    ),
                    blendMode = BlendMode.DstIn
                )
            },
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(product = product, onItemClick = onItemClick)
        }
    }
}

@Composable
fun HomeScreen(
    selectedTab: ProductCategory = ProductCategory.ALL,
    onTabClicked: (ProductCategory) -> Unit,
    products: List<Product>,
    isLoading: Boolean = false,
    error: ApiResult.Error? = null,
    onRefresh: () -> Unit,
    onItemClick: (Product) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(error) {
        error?.let {
            val message = when (it) {
                is ApiResult.Error.ServerError -> "Server Error: ${it.code}, ${it.errorMessage}"
                is ApiResult.Error.NetworkError -> "No Internet Connection"
                is ApiResult.Error.TimeoutError -> "Connection Timeout"
                is ApiResult.Error.UnknownError -> "Unexpected error: ${it.errorMessage}"
            }
            snackBarHostState.showSnackbar(message = message)
        }
    }

    val refreshState = rememberPullToRefreshState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                PrimaryTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    ProductCategory.entries.forEach { category ->
                        Tab(
                            selected = selectedTab == category,
                            onClick = { onTabClicked(category) },
                            text = {
                                Text(
                                    text = category.categoryName,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = onRefresh,
                state = refreshState,
                modifier = Modifier.padding(innerPadding)
            ) {
                ProductList(
                    products = products,
                    onItemClick = onItemClick
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

val mockProducts = listOf(
    Product(
        id = "cdd9b268-13a2-404f-b295-9e8d0c605de0",
        deviceType = "access-point",
        line = "airMAX",
        name = "airCube AC",
        imageId = "1435ef4d1b7caf065251ea2fcab512fb"
    ),
    Product(
        id = "3e9ea70f-416b-4c66-8740-1b6d41465b87",
        deviceType = "access-point",
        line = "airMAX",
        name = "airCube ISP",
        imageId = "1435ef4d1b7caf065251ea2fcab512fb"
    ),
    Product(
        id = "eed7a4b4-577b-425d-a179-e6e0f571fb2e",
        deviceType = "radio",
        line = "AirFiber",
        name = "airFiber 11FX",
        imageId = "06b20db4e3635be2c3bbf82231f7e6c4"
    )
)

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7
)
@Composable
fun HomePreview() {
    UbiquityTestAssignmentTheme {
        HomeScreen(
            selectedTab = ProductCategory.ALL,
            onTabClicked = { /* ignore */ },
            products = mockProducts,
            isLoading = true,
            error = ApiResult.Error.TimeoutError,
            onRefresh = { /* ignore */ },
            onItemClick = { /* ignore */ }
        )
    }
}