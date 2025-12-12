package com.segnities007.ui

actual fun platform(): String = "Java ${System.getProperty("java.version")}"
