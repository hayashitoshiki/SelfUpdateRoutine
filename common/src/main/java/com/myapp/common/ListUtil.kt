package com.myapp.common

fun listEquals(list1: MutableList<*>, list2: MutableList<*>) : Boolean {
    if (list1.size != list2.size) return false
    for (i in 0 until list1.size) {
        if (list1[i] == list2[i]) return false
    }
    return true
}