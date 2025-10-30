package com.example.huertogourmet.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.huertogourmet.data.dao.PlatoDao
import com.example.huertogourmet.data.dao.UsuarioDao
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.data.model.Usuario

@Database(entities = [Usuario::class, Plato::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun platoDao(): PlatoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "huertogourmet_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = inst
                inst
            }
    }
}
