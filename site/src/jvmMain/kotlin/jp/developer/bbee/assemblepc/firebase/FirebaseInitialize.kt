package jp.developer.bbee.assemblepc.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import java.io.ByteArrayInputStream

@InitApi
fun initFirebase(context: InitApiContext) {
    val credentials = GoogleCredentials
        .fromStream(ByteArrayInputStream(Credentials))
    val options = FirebaseOptions.builder()
        .setCredentials(credentials)
        .build()
    FirebaseApp.initializeApp(options)
}
