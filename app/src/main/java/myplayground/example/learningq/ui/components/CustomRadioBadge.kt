package myplayground.example.learningq.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CustomRadioBadge(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    text: String = "",
    contentAlignment: Alignment = Alignment.CenterStart,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                1.dp,
                if (selected) Color.Transparent else MaterialTheme.colorScheme.onBackground,
                CircleShape,
            )
            .clickable { onClick() }
            .background(
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                shape = CircleShape
            )
            .padding(16.dp, 8.dp),
        contentAlignment = contentAlignment,
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(4.dp)
        )
    }
}

@Preview
@Composable
fun CustomRadioBadgePreview() {
    var selectedValue by remember { mutableStateOf("") }

    Row {
        CustomRadioBadge(
            selected = selectedValue == "1",
            onClick = { selectedValue = "1" },
            text = "Lorem"
        )

        Spacer(modifier = Modifier.width(12.dp))

        CustomRadioBadge(
            selected = selectedValue == "2",
            onClick = { selectedValue = "2" },
            text = "Ipsum"
        )
    }
}

//
//@Composable
//fun RadioOutlinedTextField(
//    options: List<String>,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    singleLine: Boolean = true,
//) {
//    var selectedOption by remember { mutableStateOf(options.first()) }
//    var isTextFieldActive by remember { mutableStateOf(false) }
//
//    Column(modifier = modifier) {
//        options.forEach { option ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//                    .background(if (selectedOption == option) Color.Gray else Color.Transparent)
//                    .clickable {
//                        onValueChange(option)
//                        isTextFieldActive = true
//                    }
//            ) {
//                Box(
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clip(CircleShape)
//                        .background(if (selectedOption == option) Color.Blue else Color.Transparent)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = option,
//                    onValueChange = {
//                        selectedOption = it
//                        onValueChange(it)
//                    },
//                    singleLine = singleLine,
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        imeAction = if (options.last() == option) ImeAction.Done else ImeAction.Next
//                    ),
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(Color.Transparent)
//                        .clip(CircleShape)
//                        .padding(4.dp),
//                    textStyle = MaterialTheme.typography.bodySmall,
////                    activeColor = Color.Transparent,
////                    inactiveColor = Color.Transparent,
////                    cursorColor = Color.Transparent
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewRadioOutlinedTextField() {
//    RadioOutlinedTextField(
//        options = listOf("Option 1", "Option 2"),
//        onValueChange = { /* Handle selected value */ }
//    )
//}
