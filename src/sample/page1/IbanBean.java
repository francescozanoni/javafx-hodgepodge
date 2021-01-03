package sample.page1;

import javafx.beans.property.SimpleStringProperty;

/**
 * Data model representing a an IBAN.
 *
 * It implements properties for data binding.
 *
 * @see <a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm"></a>
 */
public class IbanBean {

    // Define variables to store the property
    private final SimpleStringProperty code = new SimpleStringProperty();

    // Define getters for the properties' values.
    // Both are required, although IDE could complain they are unused.
    public final String getCode() {
        return code.get();
    }

    // Define setters for the properties' values.
    public final void setCode(String value) {
        code.set(value);
    }

    // Define getters for the properties themselves.
    // Both are required, although IDE could complain they are unused.
    public SimpleStringProperty codeProperty() {
        return code;
    }

}
