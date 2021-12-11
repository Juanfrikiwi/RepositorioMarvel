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

package com.example.marvelcharacters.utilities

import com.example.marvelcharacters.BuildConfig.PRIVATE_API_KEY
import com.example.marvelcharacters.BuildConfig.PUBLIC_API_KEY
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

/**
 * Constants used throughout the app.
 *
 */
class Constants{

    companion object {

        //Connection web service
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        const val CHARACTERS_URL = "characters"
        val ts = System.currentTimeMillis().toString()
        const val PUBLIC_KEY = "$PUBLIC_API_KEY"
        const val PRIVATE_KEY = "${PRIVATE_API_KEY}"
        const val limit = "100"
        val input = "$ts$PRIVATE_KEY$PUBLIC_KEY"
        fun md5(): ByteArray = MessageDigest.getInstance("MD5").digest(input.toByteArray(UTF_8))
        fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte)
        }
        const val DATABASE_NAME = "characters-db"
        const val DATA_FILENAME = "characters.json"

    }

}

