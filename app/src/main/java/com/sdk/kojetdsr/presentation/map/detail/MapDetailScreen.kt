package com.sdk.kojetdsr.presentation.map.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.sdk.kojetdsr.R
import com.sdk.kojetdsr.ui.theme.Orange
import com.sdk.kojetdsr.util.Graph
import kotlinx.coroutines.launch

@Composable
fun MapDetailScreen(navHostController: NavHostController) {
    val viewModel: MapDetailViewModel = hiltViewModel()
    var text by remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var clicked by remember {
        mutableStateOf(false)
    }
    val snackText = stringResource(R.string.enter_l_name)
    if (clicked) {
        LaunchedEffect(key1 = Unit) {
            viewModel.saveLocationName(text)
            navHostController.navigate(Graph.HOME) {
                popUpTo(Graph.HOME) {
                    inclusive = true
                }
            }
            clicked = false
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.location_name))
                },
                backgroundColor = Orange,
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        clicked = true
                    } else {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(snackText)
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                label = {
                    Text(text = stringResource(R.string.l_name))
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Orange,
                    focusedIndicatorColor = Orange,
                    focusedLabelColor = Orange,
                    unfocusedIndicatorColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                )
            )
        }
    }
}