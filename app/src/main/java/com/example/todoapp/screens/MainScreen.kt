package com.example.todoapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todoapp.MainViewModal
import com.example.todoapp.navigation.Screens
import com.example.todoapp.room.NoteEntity
import com.example.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, noteViewModal: MainViewModal) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer( drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Localized description", modifier = Modifier.height(40.dp)
                        )
                    Text("Drawer title", modifier = Modifier.padding(16.dp))

                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "First Item ")}, selected = true, onClick = { /*TODO*/ })
                NavigationDrawerItem(label = { Text(text = "Second Item ") }, selected = false, onClick = { /*TODO*/ })

            }
        },
        gesturesEnabled = true) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "ToDo App",
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Yellow,
                        navigationIconContentColor = Color.Red
                    )
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Add") },
                    icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        noteViewModal.setNote(NoteEntity("",""))
                        navHostController.navigate(Screens.EditorScreen.route) },
                    containerColor = Color(0xFFFFDD00),
                    contentColor = Color.Red
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
//                val list = (0..75).map { it.toString() }

                    items(noteViewModal.notes.value) { item ->
                        ListItem(item,noteViewModal,navHostController)
                    }
                }
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItem(note: NoteEntity, noteViewModal: MainViewModal,navHostController: NavHostController) {
    Card(
        onClick = {
             noteViewModal.setNote(note)
            navHostController.navigate(Screens.EditorScreen.route)  },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFB8C8))
    ) {
        Column {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp, 5.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold

            )
            Row {
                Text(
                    text = note.content, modifier = Modifier
                        .padding(2.dp)
                        .weight(9f),
                    textAlign = TextAlign.Justify,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(onClick = { noteViewModal.deleteNote(note)}, modifier = Modifier.weight(2f)) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(drawerState:DrawerState) {
    ModalNavigationDrawer( drawerState = drawerState,
        drawerContent = {
        ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(label = { Text(text = "First Item ")}, selected = true, onClick = { /*TODO*/ })
            NavigationDrawerItem(label = { Text(text = "Second Item ") }, selected = false, onClick = { /*TODO*/ })

        }
    },
    gesturesEnabled = false) {
    }
}