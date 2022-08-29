package com.github.watabee.anilist.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.watabee.anilist.core.designsystem.theme.AnilistTheme

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    onRetryButtonClicked: () -> Unit
) {
    Box(modifier = modifier) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onRetryButtonClicked
        ) {
            Text(text = stringResource(R.string.retry_button_title))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewErrorItem() {
    AnilistTheme {
        ErrorItem(modifier = Modifier.fillMaxSize()) {}
    }
}
