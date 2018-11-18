package com.turastory.miniaccount.mapper

interface InvertableMapper<From, To> : Mapper<From, To> {
    fun invert(to: To): From
}