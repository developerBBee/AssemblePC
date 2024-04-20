package bbee.developer.jp.assemble_pc.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import java.io.FileInputStream

@InitApi
fun initFirebase(context: InitApiContext) {
    val refreshToken = FileInputStream("src/jvmMain/resources/service-account.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(refreshToken))
        .build()
    FirebaseApp.initializeApp(options)
}
