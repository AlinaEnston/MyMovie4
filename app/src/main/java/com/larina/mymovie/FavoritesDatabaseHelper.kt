package com.larina.mymovie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoritesDatabaseHelper : SQLiteOpenHelper {

    constructor(context: Context) : super(context, "favorites.db", null, 1)

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE favorites (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              title TEXT NOT NULL,
              description TEXT NOT NULL,
              poster INTEGER NOT NULL,
              rating REAL NOT NULL  -- Добавлено поле rating
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }

    fun addFavorite(film: Film): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", film.title)
            put("description", film.description)
            put("poster", film.poster)
            put("rating", film.rating)  // Добавлено поле rating
        }
        val result = db.insert("favorites", null, values)
        db.close()
        return result != -1L
    }

    fun removeFavorite(title: String): Boolean {
        val db = writableDatabase
        val result = db.delete("favorites", "title = ?", arrayOf(title))
        db.close()
        return result > 0
    }

    fun isFavorite(title: String): Boolean {
        val db = readableDatabase
        val cursor = db.query("favorites", arrayOf("id"), "title=?", arrayOf(title), null, null, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun getAllFavorites(): List<Film> {
        val db = readableDatabase
        val films = mutableListOf<Film>()
        val cursor = db.query("favorites", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val poster = cursor.getInt(cursor.getColumnIndexOrThrow("poster"))
                val rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
                films.add(Film(title, poster, description, rating))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return films
    }
}
