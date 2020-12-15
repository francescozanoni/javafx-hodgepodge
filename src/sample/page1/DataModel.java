package sample.page1;

public class DataModel {

    public String url;
    public String data;

    public DataModel(String url, String data) {
        this.url = url;
        this.data = data;
    }

    /**
     * Property getters are required by table data binding logic
     *
     * @return String
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Property getters are required by table data binding logic
     *
     * @return String
     */
    public String getData() {
        return data;
    }

}
