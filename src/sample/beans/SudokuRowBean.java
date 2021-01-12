package sample.beans;

import javafx.beans.property.SimpleStringProperty;

/**
 * @see <a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm"></a>
 */
public class SudokuRowBean {

    private final SimpleStringProperty col0 = new SimpleStringProperty();
    private final SimpleStringProperty col1 = new SimpleStringProperty();
    private final SimpleStringProperty col2 = new SimpleStringProperty();
    private final SimpleStringProperty col3 = new SimpleStringProperty();
    private final SimpleStringProperty col4 = new SimpleStringProperty();
    private final SimpleStringProperty col5 = new SimpleStringProperty();
    private final SimpleStringProperty col6 = new SimpleStringProperty();
    private final SimpleStringProperty col7 = new SimpleStringProperty();
    private final SimpleStringProperty col8 = new SimpleStringProperty();

    public String getCol4() {
        return col4.get();
    }

    public SimpleStringProperty col4Property() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4.set(col4);
    }

    public String getCol5() {
        return col5.get();
    }

    public SimpleStringProperty col5Property() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5.set(col5);
    }

    public String getCol6() {
        return col6.get();
    }

    public SimpleStringProperty col6Property() {
        return col6;
    }

    public void setCol6(String col6) {
        this.col6.set(col6);
    }

    public String getCol7() {
        return col7.get();
    }

    public SimpleStringProperty col7Property() {
        return col7;
    }

    public void setCol7(String col7) {
        this.col7.set(col7);
    }

    public String getCol8() {
        return col8.get();
    }

    public SimpleStringProperty col8Property() {
        return col8;
    }

    public void setCol8(String col8) {
        this.col8.set(col8);
    }

    public String getCol0() {
        return col0.get();
    }

    public SimpleStringProperty col0Property() {
        return col0;
    }

    public void setCol0(String col0) {
        this.col0.set(col0);
    }

    public String getCol1() {
        return col1.get();
    }

    public SimpleStringProperty col1Property() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1.set(col1);
    }

    public String getCol2() {
        return col2.get();
    }

    public SimpleStringProperty col2Property() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2.set(col2);
    }

    public String getCol3() {
        return col3.get();
    }

    public SimpleStringProperty col3Property() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3.set(col3);
    }

}
