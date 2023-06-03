package com.example.mausam.data

import java.lang.Exception

class DataOrException<T,Boolean, E:Exception>(
    var data:T? = null,
    var loading: Boolean?=null,
    var e:E? = null
)

