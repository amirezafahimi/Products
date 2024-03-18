package com.example.products.ui.products

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.products.R
import com.example.products.domain.entity.Product
import com.example.products.util.NetworkUtils


@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val isInitialized = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(isInitialized) {
        if (!isInitialized.value) {
            if (NetworkUtils.isOnline(context = context)) {
                viewModel.getProductsOnline()
            } else {
                //viewModel.initOfflineMode()
            }
            isInitialized.value = true
        }
    }
    ExploreScreenContent(
        modifier = Modifier.fillMaxSize(),
        fetchData = viewModel::getProductsOnline,
        uiState = viewModel.uiState.collectAsState().value,
    )
}

@Composable
fun LoadingComposable() {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(10.dp)
        ) {
            items(20) {
                LoadingShimmer()
            }
        }
    }
}

@Composable
internal fun ExploreScreenContent(
    modifier: Modifier = Modifier,
    uiState: ProductsState,
    fetchData: () -> Unit,
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = modifier) {

            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp, top = 16.dp, bottom = 4.dp
                    ),
                onSearchClicked = { query ->
                    //fetchData(query)
                },
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.isLoading) {
                LoadingComposable()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(uiState.success.size) {
                        ProductItem(
                            modifier = Modifier.fillMaxWidth(),
                            product = uiState.success[it],
                        )
                    }
                }
            }

            uiState.error?.let { error ->
                Toast.makeText(LocalContext.current, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    MaterialTheme {
        Surface {
            ProductItem(
                Product(
                    id = 1,
                    title = "TTTT",
                    price = 125.4,
                    description = "DDDD",
                    category = "CCCC",
                    image = "",
                    rate = 1.1,
                    ratingCount = 14
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {

            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .respectCacheHeaders(false).build()
            Image(
                painter = rememberAsyncImagePainter(
                    product.image,
                    imageLoader = imageLoader
                ),
                contentDescription = product.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.category,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraLight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Outlined.StarBorder,
                    contentDescription = null,
                    tint = Color(0xFFE9B006)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${product.rate} (${product.ratingCount})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${product.price}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    MaterialTheme {
        Surface {
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp, top = 16.dp, bottom = 4.dp
                    ),
                onSearchClicked = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearchClicked: (query: String) -> Unit,
) {
    val query = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier
            .testTag("SearchBox")
            .background(MaterialTheme.colorScheme.background),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ), keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(query.value)
            keyboardController?.hide()
        }),
        value = query.value,
        onValueChange = {
            query.value = it
        },
        shape = CircleShape,
        placeholder = { Text("Search Product") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .testTag("ClearBtn")
                    .clickable(
                        onClickLabel = stringResource(id = R.string.cd_clear_search)
                    ) {
                        query.value = ""
                    },
                imageVector = Icons.Default.Cancel, contentDescription = null
            )
        }, singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LoadingShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerGridItem(brush = brush)
}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 1f)
                    .background(brush)
            )
        }
    }
}
