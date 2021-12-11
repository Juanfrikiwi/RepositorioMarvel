/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.marvelcharacters.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.marvelcharacters.MainCoroutineRule
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.networkDataRepository.CharactersRepositoryImpl
import com.example.marvelcharacters.ui.HomeAdapter
import com.example.marvelcharacters.utils.characterA
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.RuleChain
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CharactersRepositoryTest {

    @Inject
    lateinit var marvelService: MarvelService

    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    var adapter: HomeAdapter = HomeAdapter()


    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var charactersRepositoryImpl: CharactersRepositoryImpl

    @Before
    fun setUp() {
        hiltRule.inject()
        charactersRepositoryImpl = CharactersRepositoryImpl(marvelService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getListCharactersTest() = runBlocking {
        val job = launch {
            adapter.submitData(charactersRepositoryImpl.getListCharacter().first())
            Assert.assertEquals(adapter.snapshot().items[0].name, "3-D Man")
        }
        job.cancel()
    }

    @Test
    fun getCharacterTest() = runBlocking {
        val job = launch {
            Assert.assertEquals(charactersRepositoryImpl.getCharacter(characterA.idCharacter).first().name, "3-D Man")
        }
        job.cancel()
    }
}
