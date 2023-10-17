package com.example.todoapp.screens

import android.provider.SyncStateContract.Helpers.insert
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todoapp.MainViewModal
import com.example.todoapp.room.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(navHostController: NavHostController, noteViewModal: MainViewModal) {
    val note = noteViewModal.note.collectAsState()
    var titleText by remember { mutableStateOf(note.value.title) }
    var descriptionText by remember { mutableStateOf(note.value.content) }
 /*   if (note.value.title != "" && note.value.content != "") {

    }else {
        var titleText by remember { mutableStateOf("") }
        var descriptionText by remember { mutableStateOf("") }
    }*/

    Column {
        TextField(value = titleText,
            onValueChange = { newText ->
                titleText = newText
            }, label = {
                Text(text = "Enter Title")
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        TextField(value = descriptionText,
            onValueChange = { newText ->
                descriptionText = newText
            }, label = {
                Text(text = "Enter detail")
            }, modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(10.dp, 0.dp, 10.dp, 10.dp)
        )
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    if (note.value.title != ""  && note.value.content != "") {
                        updateNote(titleText, descriptionText, noteViewModal, note.value)
                    } else {
                        insert(titleText, descriptionText, noteViewModal)
                    }

                    navHostController.navigateUp()
                },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier.width(200.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Save")
            }
        }
    }

}

fun updateNote(
    titleText: String,
    descriptionText: String,
    noteViewModal: MainViewModal,
    note: NoteEntity,
) {
    if (!TextUtils.isEmpty(titleText) && !TextUtils.isEmpty(descriptionText)) {
        note.title = titleText
        note.content = descriptionText
        noteViewModal.updateNote(
            note
        )
        Log.d("main", "Inserted")
    }
    Log.d("main", "Inserted")
}

fun insert(
    title: String,
    content: String,
    noteViewModal: MainViewModal
) {
    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
        noteViewModal.insertNote(
            NoteEntity(title, content)
        )
        Log.d("main", "Inserted")
    }
    Log.d("main", "Inserted")
}