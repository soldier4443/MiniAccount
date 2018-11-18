package com.turastory.miniaccount.mapper

interface Mapper<From, To> {
    fun convert(from: From): To
}