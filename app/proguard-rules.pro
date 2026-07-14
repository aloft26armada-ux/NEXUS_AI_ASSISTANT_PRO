# Hardened serialization preservation rules for architectural metadata frameworks
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep native structural compilation elements and bridge pointers mapping completely safe
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.nexus.ai.core.ai.native.** { *; }

# Guard Room architectural metadata interfaces from structural optimizations queries disruptions
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# Protect Hilt Android dependency structures injections endpoints tracking maps
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep enum com.nexus.ai.core.performance.PerformanceTier { *; }
