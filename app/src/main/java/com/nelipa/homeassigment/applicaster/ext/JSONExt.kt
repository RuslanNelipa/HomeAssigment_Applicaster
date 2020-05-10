package com.nelipa.homeassigment.applicaster.ext

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.forEach(fetchObject: (JSONObject) -> Unit) {
    for (i in 0 until length()) {
        fetchObject(getJSONObject(i))
    }
}

fun JSONObject.getStringOrNull(key: String): String? {
    if (this.has(key) && !this.isNull(key)) {
        return this.getString(key)
    }
    return null
}

fun JSONArray.firstOrNull(): JSONObject? {
    return if (length() > 0) this.getJSONObject(0) else null
}