package com.example.marvelcharacters.utilities

import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

//TODO igual que el del modulo DATA.  Ver un sitio comun para...
object Mappers {

    fun mapperToCharacter(characterResponse: CharactersResponse): CharacterModel {
        characterResponse.let {
            return CharacterModel(
                it.id,
                it.name,
                it.description,
                DateUtils.getDateFormatted(it.modified.time),
                it.resourceURI,
                it.thumbnail.path + "." + it.thumbnail.extension,
                extractComics(it)
            )
        }
    }

    fun mapperToListCharacters(list: List<CharactersResponse>): List<CharacterModel> {
        list.let {
            val charactersList : MutableList<CharacterModel> = emptyList<CharacterModel>().toMutableList()
            list.forEach { charactersResponse ->
                charactersList.add(mapperToCharacter(charactersResponse))
            }
            return charactersList
        }
    }
    fun mapperCharacterModelToCharacterEntity(characterModel: CharacterModel): CharactersEntity{
        characterModel.let {
            return CharactersEntity(
                it.idCharacter,
                it.name,
                it.description,
                it.modified,
                it.resourceURI,
                it.thumbnail_path,
                it.comics
            )
        }
    }

    fun mapperListCharacterModelToListCharactersEntity(list: List<CharacterModel>): List<CharactersEntity> {
        list.let {
            val charactersList : MutableList<CharactersEntity> = emptyList<CharactersEntity>().toMutableList()
            list.forEach { characterModel ->
                charactersList.add(mapperCharacterModelToCharacterEntity(characterModel))
            }
            return charactersList
        }
    }

    fun mapperCharacterEntityToCharacterModel(characterEntity: CharactersEntity): CharacterModel{
        characterEntity.let {
            return CharacterModel(
                it.idCharacter,
                it.name,
                it.description,
                it.modified,
                it.resourceURI,
                it.thumbnail_path,
                it.comics
            )
        }
    }

    fun mapperListEntityToListCharacterModel(list: List<CharactersEntity>): List<CharacterModel> {
        list.let {
            val charactersList : MutableList<CharacterModel> = emptyList<CharacterModel>().toMutableList()
            list.forEach { characterModel ->
                charactersList.add(mapperCharacterEntityToCharacterModel(characterModel))
            }
            return charactersList
        }
    }



    fun extractComics(characterResponse: CharactersResponse):List<String>{
        val comicsList : MutableList<String> = emptyList<String>().toMutableList()
        characterResponse.comics.items.forEach{
            comicsList.add(it.name)
        }
        return comicsList
    }
}
