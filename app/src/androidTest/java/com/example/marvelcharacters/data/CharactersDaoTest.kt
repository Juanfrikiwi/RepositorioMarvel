package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.example.marvelcharacters.data.local.database.CharactersDao
import com.example.marvelcharacters.data.local.database.MarvelDatabase
import com.example.marvelcharacters.utilities.Mappers
import com.example.marvelcharacters.utils.characterA
import com.example.marvelcharacters.utils.characterB
import com.example.marvelcharacters.utils.characterC
import com.example.marvelcharacters.utils.characterD
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CharactersDaoTest {
    @Inject
    @Named("test_db")
    lateinit var database: MarvelDatabase
    private lateinit var characterDao: CharactersDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()
        characterDao = database.charactersDao()
        characterDao.insertAll(Mappers.mapperListCharacterModelToListCharactersEntity(listOf(characterA, characterB, characterC)))
    }

    @Test
    fun testIsExistId() = runBlocking {
        assertEquals(characterDao.isExitsId(characterC.idCharacter).first(), true)
    }


    @Test
    fun testGetListCharacters() = runBlocking {
        assertEquals(characterDao.getListCharacters().first().size, 3)
        assertEquals(characterDao.getListCharacters().first()[0], Mappers.mapperCharacterModelToCharacterEntity(characterA))
        assertEquals(characterDao.getListCharacters().first()[1], Mappers.mapperCharacterModelToCharacterEntity(characterB))
        assertEquals(characterDao.getListCharacters().first()[2], Mappers.mapperCharacterModelToCharacterEntity(characterC))
    }

    @Test
    fun testGetCharacter() = runBlocking {
        assertEquals(characterDao.getCharacter(characterA.idCharacter).first(), Mappers.mapperCharacterModelToCharacterEntity(characterA))
    }

    @Test
    fun testInsertCharacter() = runBlocking {
        characterDao.insertCharacter(Mappers.mapperCharacterModelToCharacterEntity(characterD))
        assertEquals(characterDao.getListCharacters().first().size, 4)
    }

    @Test
    fun testDeleteCharacter() = runBlocking {
        characterDao.deleteCharacter(characterD.idCharacter)
        assertEquals(characterDao.getListCharacters().first().size, 3)
    }

    @Test
    fun testDeleteAllCharacter() = runBlocking {
        characterDao.deleteListCharacters()
        assertEquals(characterDao.getListCharacters().first().size, 0)
    }


    @After
    fun tearDown() {
        database.close()
    }


}