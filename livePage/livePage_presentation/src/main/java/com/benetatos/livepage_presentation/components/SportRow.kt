package com.benetatos.livepage_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.benetatos.livepage_presentation.UiEvent
import com.benetatos.core_ui.LocalSpacing
import com.benetatos.core_ui.blue
import com.benetatos.core_ui.grey
import kotlin.reflect.KFunction1

@Composable
fun SportRow(
    icon: Int?,
    sportLabel: String,
    isFavoriteFilterActive: Boolean,
    isViewExpanded: Boolean,
    onEvent: KFunction1<UiEvent, Unit>,
    sportId: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {

            icon?.let {
                Icon(
                    modifier = Modifier
                        .padding(LocalSpacing.current.spaceMedium)
                        .size(24.dp),
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

            Text(
                text = sportLabel,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Switch(
                modifier = Modifier.testTag("favoriteFilterSwitch"),
                checked = isFavoriteFilterActive,
                onCheckedChange = {
                    onEvent(
                        UiEvent.ShowOnlyFavoriteGames(
                            isFavoriteFilterActive,
                            sportId = sportId
                        )
                    )
                },
                thumbContent = {
                    Icon(
                        painter = painterResource(com.benetatos.core.R.drawable.ic_star_switch),
                        contentDescription = null,
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = blue,
                    uncheckedThumbColor = grey,
                    checkedTrackColor = Color.DarkGray,
                    uncheckedTrackColor = grey,
                    uncheckedBorderColor = Color.Transparent

                )
            )

            Icon(
                modifier = Modifier
                    .padding(LocalSpacing.current.spaceMedium)
                    .size(24.dp)
                    .clickable {
                        onEvent(UiEvent.ExpandCollapseSport(isViewExpanded, sportId = sportId))
                    },
                painter = painterResource(if (isViewExpanded) com.benetatos.core.R.drawable.ic_expanded else com.benetatos.core.R.drawable.ic_collaped),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}
