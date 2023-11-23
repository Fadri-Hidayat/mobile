package myplayground.example.learningq.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import myplayground.example.learningq.ui.theme.LearningQTheme

/*
    This is a simple switch component that doesn't support RTL
*/

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
    text: String? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
        if (text != null) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CustomSwitchPreview() {
    LearningQTheme {
        Column {
            CustomSwitch(
                checked = true,
                onCheckedChange = {},
                text = "Enabled",
            )
            CustomSwitch(
                checked = false,
                onCheckedChange = {},
                text = "Enabled",
            )
            CustomSwitch(
                checked = true,
                onCheckedChange = {},
                enabled = false,
                text = "Disabled",
            )
            CustomSwitch(
                checked = false,
                onCheckedChange = {},
                enabled = false,
                text = "Disabled",
            )
            CustomSwitch(
                checked = true,
                onCheckedChange = {},
            )
            CustomSwitch(
                checked = false,
                onCheckedChange = {},
            )
        }
    }
}
