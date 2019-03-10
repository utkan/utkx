package io.utkan.marvel.data.character.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CharacterViewEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterViewEntity(): CharacterViewDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInMemoryDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                val db =
                    Room.databaseBuilder(context, AppDatabase::class.java, "MARVEL_UTX")
                INSTANCE = db.build()
            }
            return INSTANCE!!
        }
    }
}
