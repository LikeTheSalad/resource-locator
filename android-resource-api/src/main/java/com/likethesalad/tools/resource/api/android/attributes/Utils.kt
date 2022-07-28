package com.likethesalad.tools.resource.api.android.attributes

fun plain(value: String) = AndroidAttributeKey.Plain(value)
fun namespaced(value: String, namespaceValue: String) = AndroidAttributeKey.Namespaced(value, namespaceValue)