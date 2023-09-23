package com.ilhomsoliev.shared.shared.utils

fun String.getPhoneNumberStructured(
    prefix: String? = null,
): String {
    val str = this.trim()
    var total = 0;
    var newStr = "";
    var i = 0;
    while (i < str.length) {
        newStr += str[i]
        if (i % 3 == 2 && total != 2) {
            newStr += " "
            total++
        }
        ++i;
    }
    return if (prefix == null) newStr else "$prefix $newStr"
}