
public class TelemetrySign {
    private String parameterName;
    private short parameterNumber;
    private int createTime;
    private byte dimension;
    private byte attribute;
    private byte valueType;
    private byte[] parameterValue;
    private int len;

    TelemetrySign(){}

    TelemetrySign(String parameterName,short parameterNumber, int createTime, byte dimention,
                  byte attribute, byte valueType, byte[] parameterValue,
                  int len){
        this.parameterName = parameterName;
        this.parameterNumber = parameterNumber;
        this.createTime = createTime;
        this.dimension = (byte) Byte.toUnsignedInt(dimention);
        this.attribute = (byte) Byte.toUnsignedInt(attribute);
        this.valueType = (byte) Byte.toUnsignedInt(valueType);
        this.parameterValue = parameterValue;
        this.len = len;
    }

    public void setAttribute(byte attribute) {
        this.attribute = attribute;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public void setDimention(byte dimention) {
        this.dimension = dimention;
    }

    public void setParameterNumber(short parameterNumber) {
        this.parameterNumber = parameterNumber;
    }

    public void setParameterValue(byte[] parameterValue) {
        this.parameterValue = parameterValue;
    }

    public void setValueType(byte valueType) {
        this.valueType = valueType;
    }

    public void setLen(int len) {
        this.len = len;
    }





    public String getParameterName() {
        return parameterName;
    }

    public byte getAttribute() {
        return attribute;
    }

    public byte getDimension() {
        return dimension;
    }

    public byte getValueType() {
        return valueType;
    }

    public byte[] getParameterValue() {
        return parameterValue;
    }

    public int getCreateTime() {
        return createTime;
    }

    public short getParameterNumber() {
        return parameterNumber;
    }

    public int getLen() {
        return len;
    }

}

