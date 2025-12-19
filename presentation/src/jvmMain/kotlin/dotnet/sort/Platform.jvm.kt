package dotnet.sort

actual fun platform(): String = "Java ${System.getProperty("java.version")}"
