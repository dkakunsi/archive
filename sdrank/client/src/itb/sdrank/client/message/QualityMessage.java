package itb.sdrank.client.message;

import java.io.Serializable;

public class QualityMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String unit;
    private Float value;

    public QualityMessage() {
	super();
    }

    public QualityMessage(String type, String unit, Float value) {
	super();
	this.type = type;
	this.unit = unit;
	this.value = value;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public Float getValue() {
	return value;
    }

    public void setValue(Float value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return "QualityMessage [type=" + type + ", unit=" + unit + ", value="
		+ value + "]";
    }
}
