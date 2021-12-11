package com.example.marvelcharacters.domain.models

data class CharacterModel(
    val idCharacter: Int = 0,
    val name: String = "",
    val description: String = "",
    val modified: String = "",
    val resourceURI: String = "",
    val thumbnail_path: String = "",
    val comics: List<String> = emptyList(),
)

