# Clean Hitbox

A Minecraft Fabric mod for version 1.21 that enhances the built-in debug hitboxes (F3 + B) with customization options.

## Features

- **Target-Based Highlighting**: Hitboxes change color when you aim at an entity.
- **Custom Colors**: Individually configure colors for bounding boxes, eye height lines, and look vectors.
- **Line Thickness**: Adjust the thickness of hitbox lines.
- **Toggle Components**: Enable or disable specific hitbox elements.
- **Mod Menu Integration**: Easy-to-use config screen.

## Requirements

- Minecraft 1.21
- Fabric Loader
- Fabric API
- Cloth Config API (for configuration)
- Mod Menu (optional, for accessing the config screen)

## Building from Source

1. Clone the repository.
2. Open a terminal in the project directory.
3. Run the following command:
   ```bash
   ./gradlew build
   ```
4. The compiled mod JAR will be in `build/libs/`.

## Running the Mod

1. Install Fabric for Minecraft 1.21.
2. Place the compiled mod JAR (and its dependencies: Fabric API, Cloth Config) into your `.minecraft/mods/` folder.
3. Launch Minecraft.
4. Press `F3 + B` in-game to enable hitboxes.
5. Access the configuration via Mod Menu.

## License

This project is licensed under the MIT License.
