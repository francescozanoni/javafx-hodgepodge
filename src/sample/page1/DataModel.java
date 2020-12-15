package sample.page1;

import javafx.beans.property.SimpleStringProperty;

/**
 * Data model implementing properties for data binding.
 *
 * @see <a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm"></a>
 */
public class DataModel {

    public DataModel (String url, String data) {
        this.setUrl(url);
        this.setData(data);
    }

    // Define variables to store the property
    private final SimpleStringProperty url = new SimpleStringProperty();
    private final SimpleStringProperty data = new SimpleStringProperty();

    // Define getters for the properties' values.
    // Both are required, although IDE could complain they are unused.
    public final String getUrl() {
        return url.get();
    }
    public final String getData() {
        return data.get();
    }

    // Define setters for the properties' values.
    public final void setUrl(String value) {
        url.set(value);
    }
    public final void setData(String value) {
        data.set(value);
    }

    // Define getters for the properties themselves.
    // Both are required, although IDE could complain they are unused.
    public SimpleStringProperty urlProperty() {
        return url;
    }
    public SimpleStringProperty dataProperty() {
        return data;
    }

}
