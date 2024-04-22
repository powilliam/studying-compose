package com.powilliam.studyingcompose.stories.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.powilliam.studyingcompose.stories.data.Story
import com.powilliam.studyingcompose.utils.isLoading

private val modifier: Modifier = Modifier

@Composable
fun StoriesScreen(paging: LazyPagingItems<Story>) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val isRefreshing by paging.isLoading { it.refresh }
    val isAppending by paging.isLoading { it.append }

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
            items(count = paging.itemCount) { index ->
                val story = paging[index]

                key(story?.id) {
                    Card(
                        modifier = modifier.fillMaxWidth(),
                        colors = CardDefaults
                            .outlinedCardColors()
                    ) {
                        Text(text = "${story?.title}")
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