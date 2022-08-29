package com.github.watabee.anilist.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.github.watabee.anilist.core.designsystem.theme.AnilistTheme

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    title: String?,
    coverImageUrl: String?
) {
    Column(modifier = modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .aspectRatio(0.7f, matchHeightConstraintsFirst = false)
                .clip(RoundedCornerShape(8.dp)),
            model = coverImageUrl,
            contentDescription = null
        ) {
            when (painter.state) {
                AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Error,
                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier.background(color = Color(red = 221, green = 230, blue = 238, alpha = 255)))
                }
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title.orEmpty(),
            color = Color.Gray,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewMediaItem() {
    AnilistTheme {
        MediaItem(
            modifier = Modifier.width(105.dp),
            title = "Hataraku Maou-sama!!",
            coverImageUrl = ""
        )
    }
}
