-obfuscationdictionary ./proguard-keywords.txt
-classobfuscationdictionary ./proguard-keywords.txt
-packageobfuscationdictionary ./proguard-keywords.txt

-flattenpackagehierarchy a
-repackageclasses a

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-allowaccessmodification
-forceprocessing

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-optimizationpasses 1 # 7

-mergeinterfacesaggressively
-allowaccessmodification
-renamesourcefileattribute _

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {
    *;
}

# -keepdirectories
-keepattributes Exceptions, InnerClasses, *Annotation*, Signature, EnclosingMethod

-keep class javax.inject.* { *; }

-keep class javax.ws.** { *; }
-keepclassmembers @javax.ws.rs.Path class ** { *; }
-keepclassmembers class * implements @javax.ws.rs.Path ** { *; }

-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * { public <init>(...); }
-keepclassmembers class **.dao.** { public *; }

-keepclassmembers class **Entity { public *; private <fields>; }
-keepclassmembers class **Request { public *; private <fields>; }
-keepclassmembers class **Response { public *; private <fields>; }
-keepclassmembers class **Status { public *; private <fields>; }

-keepclassmembers interface **Schema { *; }

-keepclassmembers class com.alloc64.restfileprovider.server.shared.cnf.JsonConfig$Database { *; }
-keepclassmembers class * extends com.alloc64.restfileprovider.server.shared.cnf.JsonConfig { *; }
-keepclassmembers class * extends com.alloc64.restfileprovider.server.shared.cnf.JsonConfigSharedAware { *; }
-keepclassmembers class com.alloc64.restfileprovider.server.shared.response.PaginatedResponse { public *; }

# jackson

-keep class com.fasterxml.jackson.ValueEnum { *; }
-keepclassmembers class * implements com.google.gson.JsonSerializer { *; }

# retrofit rules

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Rules for Gson
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keepclassmembers class * implements com.google.gson.TypeAdapterFactory { *; }
-keepclassmembers class * implements com.google.gson.JsonSerializer { *; }
-keepclassmembers class * implements com.google.gson.JsonDeserializer { *; }

# lib keeps

-keep class com.googlecode.cqengine.** { *; }
-dontwarn com.googlecode.cqengine.**

-keep class org.antlr.** { *; }
-dontwarn org.antlr.**

-keep class kotlin.** { *; }
-dontwarn kotlin.**

-keep class com.mysql.** { *; }
-dontwarn com.mysql.**

-keep class org.sqlite.** { *; }
-dontwarn org.sqlite.**

-keep class org.jdbi.** { *; }
-dontwarn org.jdbi.**

-keep class com.zaxxer.** { *; }
-dontwarn com.zaxxer.**

-keep class io.perfmark.** { *; }
-dontwarn io.perfmark.**

-keep class io.leangen.** { *; }
-dontwarn io.leangen.**

-keep class io.grpc.** { *; }
-dontwarn io.grpc.**

-keep class nl.basjes.** { *; }
-dontwarn nl.basjes.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**

-keep class okio.** { *; }
-dontwarn okio.**

-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class org.glassfish.** { *; }
-dontwarn org.glassfish.**

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keep class com.sksamuel.scrimage.** { *; }
-dontwarn com.sksamuel.scrimage.**

-keep class com.apptastic.** { *; }
-dontwarn com.apptastic.**

-keep class org.jsoup.** { *; }
-dontwarn org.jsoup.**

-keep class net.sf.kxml.** { *; }
-dontwarn net.sf.kxml.**

-keep class org.json.** { *; }
-dontwarn org.json.**

-keep class org.jvnet.** { *; }
-dontwarn org.jvnet.**

-keep class com.beust.** { *; }
-dontwarn com.beust.**

-keep class org.bitbucket.** { *; }
-dontwarn org.bitbucket.**

-keep class com.squareup.** { *; }
-dontwarn com.squareup.**

-keep class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**

-keep class org.mapdb.** { *; }
-dontwarn org.mapdb.**

-keep class net.jpountz.** { *; }
-dontwarn net.jpountz.**

-keep class org.jetbrains.kotlin.** { *; }
-dontwarn org.jetbrains.kotlin.**

-keep class org.msgpack.** { *; }
-dontwarn org.msgpack.**

-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

-keep class org.java-websocket.** { *; }
-dontwarn org.java-websocket.**

-keep class org.igniterealtime.smack.** { *; }
-dontwarn org.igniterealtime.smack.**

-keep class com.google.** { *; }
-dontwarn com.google.**

-keep class com.dropbox.** { *; }
-dontwarn com.dropbox.**

-keep class com.jcraft.** { *; }
-dontwarn com.jcraft.**

-keep class com.github.mike10004.** { *; }
-dontwarn com.github.mike10004.**

-keep class sockslib.** { *; }
-dontwarn sockslib.**

-keep class de.javakaffee.kryoserializers.** { *; }
-dontwarn de.javakaffee.kryoserializers.**

-keep class org.objenesis.** { *; }
-dontwarn org.objenesis.**

-keep class org.slf4j.** { *; }
-dontwarn org.slf4j.**

-keep class org.theeten.** { *; }
-dontwarn org.theeten.**

-keep class com.twelvemonkeys.** { *; }
-dontwarn com.twelvemonkeys.**

-keep class ch.qos.logback.** { *; }
-dontwarn ch.qos.logback.**

-keep class com.github.benmanes.** { *; }
-dontwarn com.github.benmanes.**

-keep class io.sentry.** { *; }
-dontwarn io.sentry.**

-keep class javassist.** { *; }
-dontwarn javassist.**

-dontwarn a.a.sdk.**
-dontwarn com.sun.**
-dontwarn org.conscrypt.**
-dontwarn javax.servlet.http.**