package com.example.crimanlintent

class CrimeDatabase {
    @Database(entites = [Crime::class],version=1)
    @TypeConverters(TypeConverters::class)
    abstract class CrimeDatabase : RoomDatabase(){
        abstract fun crimeDao(): CrimeDao

    }
}