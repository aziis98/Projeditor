package com.aziis98.projeditor

import java.util.*


// Copyright 2016 Antonio De Lucreziis

@Suppress("UNCHECKED_CAST")
class PropertyMap {

    val map: HashMap<String, Any> = HashMap()

    fun <T> get(name: String, def: T): T = (map[name] as T) ?: def

    operator fun <T> get(name: String) : T? = map[name] as T?

    operator fun set(name: String, value: Any) {
        map[name] = value
    }

    override fun toString(): String {
        return map.toString()
    }

}