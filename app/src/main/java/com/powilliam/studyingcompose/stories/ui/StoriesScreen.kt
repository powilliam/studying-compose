package com.powilliam.studyingcompose.stories.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import com.powilliam.studyingcompose.stories.data.TopicWithStories
import com.powilliam.studyingcompose.utils.LaunchOnLifecycle
import com.powilliam.studyingcompose.utils.collectCanScrollToTopAsState
import kotlinx.coroutines.launch

private val modifier: Modifier = Modifier

@Composable
fun StoriesScreen(
    snapshot: ItemSnapshotList<TopicWithStories>,
    loadStates: CombinedLoadStates
) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isLoading = listOf(loadStates.refresh, loadStates.append).contains(LoadState.Loading)

    val listState = rememberLazyListState()
    val canScrollToTop by listState.collectCanScrollToTopAsState()

    val coroutineScope = rememberCoroutineScope()

    LaunchOnLifecycle { _, event ->
        Log.d("StoriesScreen:LaunchOnLifecycle", "$event")
    }

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = behavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
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
        },
        floatingActionButton = {
            AnimatedVisibility(canScrollToTop) {
                SmallFloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowUp, contentDescription = "Scroll to top")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (index in 0 until snapshot.size)
                snapshot[index]?.let { topicWithStories ->
                    itemsIndexed(topicWithStories.stories, key = { _, story -> story.id }) { index, story ->
                        Column {
                            if (index > 0)
                                HorizontalDivider(modifier = modifier.padding(bottom = 8.dp))

                            Surface(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 8.dp),
                            ) {
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = modifier.fillMaxWidth(fraction = 0.8f)) {
                                        Text(text = story.title, style = MaterialTheme.typography.bodyLarge)

                                        Text(
                                            modifier = modifier.padding(top = 4.dp),
                                            text = story.updatedAt,
                                            style = MaterialTheme.typography.labelSmall
                                        )

                                        Text(
                                            modifier = modifier.padding(top = 8.dp),
                                            text = "@${story.author}",
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    }

                                    Column {
                                        IconButton(onClick = { /*TODO*/ }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                                contentDescription = "Go to article"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}

