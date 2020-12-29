# javafx-hodgepodge

Several [JavaFX](https://openjfx.io) 11 experiments, without [Maven](https://maven.apache.org) support.

Thorough documentation sources are listed within code comments.

---

## Tips for [IntelliJ IDEA](https://www.jetbrains.com/idea) IDE

* setup by following [this guide](https://www.jetbrains.com/help/idea/javafx.html)
* in order to correctly handle resources, mark `resources` folder as **Resources Root**,
  as per [this guide](https://www.jetbrains.com/help/idea/content-roots.html#folder-categories)
* package as JAR, not as JavaFX Application
  * to run the package:

        java --module-path /path/to/javafx-sdk-11.0.2/lib \
             --add-modules javafx.controls,javafx.fxml \
             -jar out/artifacts/javafx_hodgepodge_jar/javafx-hodgepodge.jar

---

## Tips for command line

### Compile/run separate .class files

    FX=/path/to/javafx-sdk-N.N.N/lib

    cd /path/to/project

    javac -classpath $FX/javafx.fxml.jar:$FX/javafx.base.jar:$FX/javafx.controls.jar:$FX/javafx.graphics.jar \
          Main.java \
          Controller.java

    java --module-path $FX \
         --add-modules=javafx.controls,javafx.fxml \
         Main

### Build/run executable .jar file

    jar cvfe App.jar Main *.class

Sources:
 - https://stackoverflow.com/questions/52467561/intellij-cant-recognize-javafx-11-with-openjdk-11
 - http://www.skylit.com/javamethods/faqs/createjar.html