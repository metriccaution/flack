# Flack

## What

Remote control a computer with a phone. Run a Java program on your computer, and connect in to control it.

### Mouse control

![Mouse pad screenshot](screenshots/mouse-pad.png)

Runs a basic trackpad - Move the mouse and click from the comfort of the sofa.

## Security

The application runs over a unencrypted HTTP / websocket based site - Only run it on a trusted network (even then, its probably a terrible idea).

## Development

To work on this project, the minimum requirements are:

- Java (min Java 8)
- Maven
- Node

The build is managed with a combination of `Maven` and `npm`, orchestrated using `Maven`.

### Build

From the top-level of the project:

```bash
mvn install
```

This will produce a standalone jar file in `flack-main/target`.

### Running

After building (see above), Flack can be started using `java -jar <jar-name>`.

### Test

From the top-level of the project:

```bash
mvn verify
```

From `flack-ui/src/main/resources`:

```bash
npm test
```
