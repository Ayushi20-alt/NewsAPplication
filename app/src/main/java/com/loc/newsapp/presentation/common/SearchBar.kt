package com.loc.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.newsapp.R
import com.loc.newsapp.presentation.Dimens.IconSize
import com.loc.newsapp.ui.theme.NewsAppTheme

//IME (Input Method Editor) actions in the context of Android refer to
//actions that a software keyboard can perform when the user interacts with it.
//IME actions are associated with UI components that receive text input, such as EditText fields.
//They provide a way to enhance the user experience by allowing users to trigger specific actions,
//such as "Done" or "Search," directly from the keyboard.
//Here are some common IME actions:
//
//ActionDone: Typically used to indicate that the user has finished entering text, and the software keyboard should hide.
//
//ActionGo: Used to indicate that the user wants to proceed with an action, often used for a "Go" or "Submit" button.
//
//ActionNext: Indicates that the user wants to move to the next input field.
//
//ActionPrevious: Indicates that the user wants to move to the previous input field.
//
//ActionSearch: Indicates that the user wants to perform a search operation.
//
//ActionSend: Used to send the input data, often associated with a "Send" button.
//
//ActionNone: Indicates that there is no specific action associated with the input field.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    text : String,
    readOnly : Boolean,
    onClick : (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
){
    // interation source will give u the alll the interactions that will happen on the text field
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked){
        if(isClicked){
            onClick?.invoke()
        }
    }
    
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .searchBarBorder(),
            value = text,
            onValueChange = onValueChange,
            readOnly = readOnly,
            leadingIcon = {
                Icon(modifier = Modifier.size(IconSize), painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null, tint = colorResource(id = R.color.body))
            },
            placeholder = {
                Text(text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.placeholder)
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.input_background),
                textColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                cursorColor = if(isSystemInDarkTheme()) Color.White else Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
    }
}

// creating a extension function
// where we want to display border in the light theme but not in dark theme
fun Modifier.searchBarBorder() = composed {
    if(!isSystemInDarkTheme()){
        border(
            width = 1.dp,
            color = Color.Black,
            shape = MaterialTheme.shapes.medium
        )
    }else {
        this
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchBarPreview(){
    NewsAppTheme() {
        SearchBar(text = "", readOnly = false, onValueChange = {}) {
        }
    }
}