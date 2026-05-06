package lv.eduardspozarickis.ubiquititestassignment.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import lv.eduardspozarickis.ubiquititestassignment.ui.theme.UbiquityTestAssignmentTheme
import lv.eduardspozarickis.ubiquititestassignment.utils.ImageHelper
import lv.eduardspozarickis.ubiquititestassignment.utils.NavigationMap

@Composable
fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF808893)
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF212427))
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(color = valueColor)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp),
            thickness = 1.dp,
            color = Color(0xFFF4F5F6)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productDetails: NavigationMap.ProductDetails,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(productDetails.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF4F5F6)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageHelper.getImageUrl(
                        id = productDetails.id,
                        imageIdentifier = productDetails.imageId,
                        size = 640
                    ),
                    contentDescription = productDetails.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp)
                )
            }

            DetailRow(
                label = "Product line",
                value = productDetails.line,
                valueColor = Color(0xFF39CC64)
            )
            DetailRow(label = "ID", value = productDetails.id, valueColor = Color(0xFF39CC64))
            DetailRow(label = "Name", value = productDetails.name)
            DetailRow(label = "Device Type", value = productDetails.deviceType)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7
)
@Composable
fun ProductDetailsPreview() {
    UbiquityTestAssignmentTheme {
        ProductDetailsScreen(
            productDetails = NavigationMap.ProductDetails(
                id = "cdd9b268-13a2-404f-b295-9e8d0c605de0",
                deviceType = "access-point",
                line = "airMAX",
                name = "airCube AC",
                imageId = "1435ef4d1b7caf065251ea2fcab512fb"
            ),
            onBackClick = { /* ignore */ }
        )
    }
}
