package com.jacksafblaze.storeowner.domain.model

data class Product(val id: String = "",
                    val name: String = "",
                    val cost: Double = 0.0,
                    val price: Double = 0.0,
                    val unit: String = "",
                    val imageUri: String = ""
                    )
