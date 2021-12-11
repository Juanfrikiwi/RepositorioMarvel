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

package com.example.marvelcharacters.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.marvelcharacters.data.network.models.ComicsList
import java.util.*
import kotlin.collections.ArrayList

@Entity(
    tableName = "characters",
    indices = [Index(value = ["id_character"])]
)

data class CharactersEntity(
    @ColumnInfo(name = "id_character") val idCharacter: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "modified") val modified: String,
    @ColumnInfo(name = "resourceURI") val resourceURI: String,
    @ColumnInfo(name = "thumbnail_path") val thumbnail_path: String,
    @ColumnInfo(name = "comics") val comics: List<String>,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}

