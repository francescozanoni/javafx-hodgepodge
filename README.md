# javafx-hodgepodge

Several [JavaFX](https://openjfx.io) 11 experiments, without [Maven](https://maven.apache.org) support.

Thorough documentation sources are listed within code comments.

---

### Tips for [IntelliJ IDEA](https://www.jetbrains.com/idea) IDE

* setup by following [this guide](https://www.jetbrains.com/help/idea/javafx.html)
* in order to correctly handle resources, mark `resources` folder as **Resources Root**,
  as per [this guide](https://www.jetbrains.com/help/idea/content-roots.html#folder-categories)
* package as JAR, not as JavaFX Application
  * to run the package:

        java --module-path /path/to/javafx-sdk-11.0.2/lib \
             --add-modules javafx.controls,javafx.fxml \
             -jar out/artifacts/javafx_hodgepodge_jar/javafx-hodgepodge.jar