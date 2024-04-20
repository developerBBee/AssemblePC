package bbee.developer.jp.assemble_pc.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.initialize

private val app = Firebase.initialize(options = MyFirebaseOptions)
val auth = Firebase.auth(app)
