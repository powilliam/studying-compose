package com.powilliam.studyingcompose.stories.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.powilliam.studyingcompose.stories.data.TopicWithStories
import com.powilliam.studyingcompose.utils.LaunchOnLifecycle
import com.powilliam.studyingcompose.utils.isLoading

private val modifier: Modifier = Modifier

@Composable
fun StoriesScreen(paging: LazyPagingItems<TopicWithStories>) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val isRefreshing by paging.isLoading { it.refresh }
    val isAppending by paging.isLoading { it.append }

    LaunchOnLifecycle { _, event ->
        Log.d("StoriesScreen:LaunchOnLifecycle", "$event")
    }

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = behavior,
                navigationIcon = {
                    AnimatedVisibility(isRefreshing) {
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
            for (index in 0 until paging.itemCount) {
                paging[index]?.let { topicWithStories ->
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

            item {
                AnimatedVisibility(
                    isAppending,
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Loading...", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}