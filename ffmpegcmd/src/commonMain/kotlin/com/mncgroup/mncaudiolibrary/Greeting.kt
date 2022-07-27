package com.mncgroup.mncaudiolibrary


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    fun jumlah2Angka(angka1: Int, angka2: Int): Int {
        return angka1 + angka2
    }
}