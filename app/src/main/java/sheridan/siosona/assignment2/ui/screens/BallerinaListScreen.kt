package sheridan.siosona.assignment2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.shadow.Shadow
import sheridan.siosona.assignment2.R
import sheridan.siosona.assignment2.model.Profile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileListViewModel = viewModel(factory = ProfileListViewModel.Factory),
    onItemClick: (Int) -> Unit = {}
){
    val state: State<ProfileListUiState> = viewModel.uiState
    val uiState: ProfileListUiState = state.value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ProfileDataTopAppBar(
                title = stringResource(R.string.list_title),
                onReloadButtonClick = viewModel::reloadData,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        when(uiState){
            is ProfileListUiState.Loading -> LoadingScreenContent()
            is ProfileListUiState.Error -> ErrorScreenContent(
                onRetry = viewModel::reloadData
            )
            is ProfileListUiState.Success -> ListScreenContent(
                profileList = uiState.profiles,
                onItemClick = onItemClick,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun LoadingScreenContent(modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.loading_data),
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ErrorScreenContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 32.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.loading_error),
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(R.string.cannot_load_data),
            fontSize = 24.sp
        )
        Button(
            onClick = onRetry
        ) {
            Text(
                text = stringResource(R.string.retry_button_label),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun ListScreenContent(
    profileList: List<Profile>,
    modifier: Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp, horizontal = 16.dp
        ), modifier = modifier

    ) {
        items(profileList) { profile ->
            ProfileListItem(profile, { })
        }
    }

}

@Composable
fun ProfileListItem(profile: Profile, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .dropShadow(
                RectangleShape,
                shadow = Shadow(
                    radius = 8.dp,
                    spread = 4.dp,
                    alpha = 0.25f,
                )
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = profile.photo,
                contentDescription = "${profile.name}'s photo",
                modifier = Modifier.dropShadow(
                    RectangleShape,
                    shadow = Shadow(
                        radius = 8.dp,
                        spread = 4.dp,
                        alpha = 0.25f,
                    )
                )
            )

            Column(
                Modifier
                    .padding(5.dp, 0.dp)
            ) {
                // name of ballerina
                Text(
                    text = profile.name,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                // ballerina position
                Text(
                    text = profile.position,
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                if (expanded) {
                    // show additional information when expanded
                    Spacer(Modifier.size(5.dp))

                    Text(
                        text = "Born in ${profile.born}, joined in ${profile.yearJoined}",
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


