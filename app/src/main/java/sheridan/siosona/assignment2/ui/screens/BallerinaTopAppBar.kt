package sheridan.siosona.assignment2.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import sheridan.siosona.assignment2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDataTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onReloadButtonClick: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null
) = CenterAlignedTopAppBar(
    title = {
        Text(
            text = title,
            fontSize = 24.sp
        )
    },
    navigationIcon = {
        if (onNavigateBack != null) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    },
    actions = {
        if (onReloadButtonClick != null){
            IconButton(
                onClick = onReloadButtonClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.reload)
                )
            }
        }
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.primary,
        actionIconContentColor = MaterialTheme.colorScheme.primary
    ),
    scrollBehavior = scrollBehavior,
)