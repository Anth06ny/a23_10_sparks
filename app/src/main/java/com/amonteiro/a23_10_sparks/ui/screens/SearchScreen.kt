package com.amonteiro.a23_10_sparks.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amonteiro.a23_10_sparks.PictureData
import com.amonteiro.a23_10_sparks.R
import com.amonteiro.a23_10_sparks.ui.MainViewModel
import com.amonteiro.a23_10_sparks.ui.theme.A23_10_sparksTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    A23_10_sparksTheme(dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background) {
            SearchScreen()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val filterList = viewModel.myList.filter { it.text.contains(viewModel.searchText) }

    Column(modifier = modifier.padding(8.dp)) {
        SearchBar(text = viewModel.searchText, onValueChange = {
            viewModel.uploadSearchText(it)
        })

        if(viewModel.runInProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(CenterHorizontally),
                color = MaterialTheme.colorScheme.primary //progress color
            )
        }

        if(viewModel.errorMessage.isNotBlank()) {
            Text(
                text = viewModel.errorMessage,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(8.dp)
            )
        }


        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1F)) {
            items(items = filterList) {
                PictureRowItem(it)
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(
                onClick = { viewModel.uploadSearchText("") },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear")
            }

            Spacer(Modifier.size(8.dp))

            Button(
                onClick = { viewModel.loadData() },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load Data")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, text: String, onValueChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onValueChange, //Action
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        label = { Text("Enter text") },
        placeholder = { //Texte d'aide
            Text("Votre texte ici")
        },
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(data: PictureData, modifier: Modifier = Modifier) {

    var isExpended by remember { mutableStateOf(false) }

    val displayText = if (isExpended) data.longText else data.longText.take(20) + "..."

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .clickable { isExpended = !isExpended }
    ) {

        GlideImage(
            model = data.url,
            contentDescription = "Pas de description",
            loading = placeholder(R.mipmap.ic_launcher_round), // Image de chargement
            // Image d'échec. Permet également de voir l'emplacement de l'image dans la Preview
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            //même autres champs qu'une Image classique
            modifier = Modifier
                .width(100.dp)
                .align(CenterVertically)
                .fillMaxHeight()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = data.text,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = displayText,
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )
        }

    }
}