@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.hiltwithcompose

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hiltwithcompose.room.TableItems
import com.example.hiltwithcompose.ui.theme.HiltWithComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HiltWithComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Black
                ) {
                    val viewModel: UsersViewModel by viewModels()
                    NetworkChecker(context).observe(this) {
                        when (it) {
                            NetworkStatus.Available -> {
                                viewModel.isConnected.value = true
                            }
                            NetworkStatus.Unavailable -> {
                                viewModel.isConnected.value = false
                            }
                        }
                    }
                    Screens(viewModel)

                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreenContent(viewModel: UsersViewModel, padding: PaddingValues) {
    Column(Modifier.padding(padding)) {
        val charactersStateFromAPi = viewModel.charactersFlow.collectAsState()
        val charactersStateFromDb = viewModel.charactersFlowFromDb.collectAsState()
        if (viewModel.isConnected.value) {
            viewModel.getUsers()
            ListFromApi(items = charactersStateFromAPi.value)
        } else {
            viewModel.getUsersFromDb()
            ListFromDb(items = charactersStateFromDb.value)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ListFromDb(items: List<TableItems>) {
    LazyColumn {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                backgroundColor = Color.White,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)

                ) {
                    GlideImage(
                        model = Image(imageVector = Icons.Default.Image, contentDescription = ""),
                        contentDescription = "user profile",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = item.id.toString(), color = Color.Black)
                        Text(
                            text = item.name.toString(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ListFromApi(items: List<Result>) {
    LazyColumn {
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                backgroundColor = Color.White,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)

                ) {
                    GlideImage(
                        model = item.image,
                        contentDescription = "user profile",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = item.id.toString(), color = Color.Black)
                        Text(
                            text = item.name,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

}








