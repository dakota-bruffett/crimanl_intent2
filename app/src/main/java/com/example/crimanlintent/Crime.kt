package com.example.crimanlintent


import java.util.Date
import java.util.UUID
@Entity

data class Crime(@PrimaryKey val id:UUID= UUID.randomUUID(),
                        var title: String= "",
                            var date: Date= Date(),
                            var isSolved:Boolean = false)//datee code


