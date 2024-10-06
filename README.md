## Features

- This app is a web browser-based custom PC building tool
- Visitors to this app will automatically be given a Firebase anonymous account
- Anonymous accounts can be handed over to logged-in accounts via GoogleAuthProvider
- The information of the PC you have built is managed by account
- When you publish the configuration you created, it will be fed to other users' top screens

## Planned features

- Android app version release
- Start building PC by referencing other users' configurations
- Users post requests, such as PC specs, and other users suggest builds.

## Getting Started for developer

For development, you need to install the [kobweb framework](https://github.com/varabyte/kobweb):
```bash
$ brew install varabyte/tap/kobweb
```

Add firebase secrets to jsMain in project:

```kotlin
// ($ProjectRoot)/site/src/jsMain/kotlin/bbee/developer/jp/assemble_pc/firebase/FirebaseSecret.kt

val MyFirebaseOptions = FirebaseOptions(
    apikey = "..."
    applicationId = "..."
    projectId = "..."
    .
    .
)
```

And add credentials to jvmMain:

```kotlin
// ($ProjectRoot)/site/src/jvmMain/kotlin/bbee/developer/jp/assemble_pc/firebase/FirebaseSecrets.kt

val Credentials = """
    {
        "type": "...",
        "project_id": "...",
        "private_key_id": "...",
        "private_key": "...",
        .
        .
    }
"""
    .trimIndent()
    .toByteArray()
```

First, run the development server by typing the following command in a terminal under the `site` folder:

```bash
$ cd site
$ kobweb run
```

Open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

Press `Q` in the terminal to gracefully stop the server.

## Exporting the Project as Fullstack application

When you are ready to ship, you should shutdown the development server and then export the project using:

```bash
$ kobweb export --layout fullstack
```

When finished, you can run a Kobweb server in production mode to test it.

```bash
$ kobweb run --env prod --layout fullstack
```

The only file required for deployment is `${projectRoot}/site/.kobweb`

If you are unable to start due to OutOfMemory at the deployment destination,
add `args="-Xmx512m ...` to the `.kobweb/server/start.sh` file and run the script:

```bash
$ .kobweb/server/start.sh
```
