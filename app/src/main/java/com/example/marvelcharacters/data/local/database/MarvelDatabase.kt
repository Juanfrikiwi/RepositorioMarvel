package com.example.marvelcharacters.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.marvelcharacters.data.local.database.CharactersDatabaseWorker.Companion.KEY_FILENAME
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.utilities.Constants
import com.example.marvelcharacters.utilities.Constants.Companion.DATA_FILENAME

@Database(entities = [CharactersEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: MarvelDatabase? = null

        fun getInstance(context: Context): MarvelDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): MarvelDatabase {
            return Room.databaseBuilder(context, MarvelDatabase::class.java, Constants.DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<CharactersDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}
