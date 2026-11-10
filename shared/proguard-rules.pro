# kotlinx-datetime
-keep class kotlinx.datetime.** { *; }
-keep interface kotlinx.datetime.** { *; }
-keepclassmembers class kotlinx.datetime.** { *; }
-dontwarn kotlinx.datetime.**

# Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.mysketch.**$$serializer { *; }
-keepclassmembers class com.mysketch.** {
    *** Companion;
}
-keepclasseswithmembers class com.mysketch.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Supabase
-keep class io.github.jan.supabase.** { *; }
-dontwarn io.github.jan.supabase.**

# Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# libsignal-android (org.signal:libsignal-android)
# The library ships native .so files loaded via JNI. Keep the entire public API
# and JNI bridge so nothing is stripped when minification is enabled.
-keep class org.signal.libsignal.** { *; }
-keepclassmembers class org.signal.libsignal.** { *; }
-dontwarn org.signal.libsignal.**
