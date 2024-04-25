package com.powilliam.studyingcompose.stories.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import com.powilliam.studyingcompose.stories.data.TopicWithStories
import com.powilliam.studyingcompose.utils.LaunchOnLifecycle

private val modifier: Modifier = Modifier

@Composable
fun StoriesScreen(
    snapshot: ItemSnapshotList<TopicWithStories>,
    loadStates: CombinedLoadStates
) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isLoading = listOf(loadStates.refresh, loadStates.append).contains(LoadState.Loading)

    LaunchOnLifecycle { _, event ->
        Log.d("StoriesScreen:LaunchOnLifecycle", "$event")
    }

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = behavior,
                navigationIcon = {
                    AnimatedVisibility(isLoading) {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = modifier.padding(start = 8.dp)
                        )
                    }
                },
                title = {
                    Text(text = "Latest Stories")
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (index in 0 until snapshot.size)
                snapshot[index]?.let { topicWithStories ->
                    items(topicWithStories.stories, key = { it.id }) { story ->
                        Card(
                            modifier = modifier.fillMaxWidth(),
                            colors = CardDefaults
                                .outlinedCardColors()
                        ) {
                            Text(text = story.title)
                        }
                    }
                }
        }
    }
}