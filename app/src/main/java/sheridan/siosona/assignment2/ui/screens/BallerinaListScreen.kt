package sheridan.siosona.assignment2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import sheridan.siosona.assignment2.R
import sheridan.siosona.assignment2.model.Profile
import sheridan.siosona.assignment2.ui.theme.Assignment2Theme


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

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
            color = colorResource(id = R.color.purple_500)
        )
        Text(
            text = stringResource(R.string.fake_delay),
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenContentPreview(){
    Assignment2Theme() {
        LoadingScreenContent()
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
            color = colorResource(id = R.color.pink_500)
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

@Preview(showBackground = true)
@Composable
fun ErrorScreenContentPreview(){
    Assignment2Theme() {
        ErrorScreenContent(
            onRetry = {}
        )
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
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ){
            AsyncImage(
                model = profile.photo,
                contentDescription = "${profile.name}'s photo"
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = profile.position,
                        fontSize = 28.sp
                    )
                    Text(
                        text = profile.name,
                        fontSize = 28.sp
                    )
                }
                Text(
                    text = pluralStringResource(id = R.plurals.years_old, profile.yearJoined),
                    fontSize = 24.sp
                )
            }
        }
    }
}


