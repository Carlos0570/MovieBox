package com.example.moviebox.core.presentation.composeComponents


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviebox.R
import com.example.moviebox.core.data.dataClasses.ProvidersByCountry


@Composable
fun WhereToWatch(providersByCountry: ProvidersByCountry?) {
    providersByCountry?.let { provider ->
        LazyColumn(
            Modifier
                .padding(9.dp)
                .fillMaxWidth()
        ) {
            item {
                if (provider.CA?.flatrate.isNullOrEmpty().not()) {
                    Text(
                        text = stringResource(R.string.stream),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(5.dp)
                    )
                    LazyRow {
                        items(provider.CA?.flatrate ?: emptyList()) { stream ->
                            ProviderCard(
                                logoPath = stream.logoPath,
                                providerName = stream.providerName
                            )
                        }
                    }
                }
            }

            item {
                if (provider.CA?.buy.isNullOrEmpty().not()) {
                    Text(
                        text = stringResource(R.string.buy),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(5.dp)
                    )
                    LazyRow {
                        items(provider.CA?.buy ?: emptyList()) { buy ->
                            ProviderCard(
                                logoPath = buy.logoPath,
                                providerName = buy.providerName
                            )
                        }
                    }
                }
            }

            item {
                if (provider.CA?.rent.isNullOrEmpty().not()) {
                    Text(
                        text = stringResource(R.string.rent),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(5.dp)
                    )
                    LazyRow {
                        items(provider.CA?.rent ?: emptyList()) { rent ->
                            ProviderCard(
                                logoPath = rent.logoPath,
                                providerName = rent.providerName
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProviderCard(logoPath: String?, providerName: String?) {
    Column(
        Modifier
            .width(80.dp)
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        logoPath?.let { logo ->
            ProviderLogo(imageURL = logo)
            Text(
                text = providerName ?: "",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }

    }
}

@Composable
private fun ProviderLogo(imageURL: String) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .padding(7.dp)
            .clip(CircleShape)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = "${stringResource(id = R.string.image_url_200)}$imageURL",
            placeholder = null,
            error = null,
            contentDescription = "",
            modifier = Modifier
                .height(170.dp)
                .width(170.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
    }
}
