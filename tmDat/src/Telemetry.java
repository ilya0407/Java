import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Telemetry {
    private static final int codeServiceMessege = 0xffff;
    private static final String[] attributes = {"norm", "low not norm", "low attention", "high attention", "high no norm","","","",
            "invalid value", "value out of range of calibration characteristic",
            "value outside the range of calibration levels (“Scaling”)"};
    private static TreeMap<Integer,String> dimensions = new TreeMap<>();
    private static short prevNumber = 0;
    public static TelemetrySign getTelemetryInfo(byte[] mas, FileInputStream fileInputStream){
        String parameterName;
        short parameterNumber;
        int createTime;
        byte dimension;
        byte attribute;
        byte valueType;
        byte[] parameterValue;
        int len = 0;

        parameterNumber = (short) (((mas[0] << 8) & 0x0ff00) + (mas[1] & 0x0ff));
        parameterName = XMLReader.getParamNames().get(parameterNumber & 0xffff);
        createTime = (((mas[2] << 24) & 0x0ff000000) + ((mas[3] << 16) & 0x0ff0000)
                + ((mas[4] << 8) & 0x0ff00) + (mas[5] & 0x0ff));
        createTime += 3*60*60*1000;
        dimension = (byte) (mas[6] & 0x0ff);
        attribute = (byte) (mas[7] & 0x0f0);
        valueType = (byte) (mas[7] & 0x00f);

        switch (valueType){
            case 0:
                parameterValue = new byte[4];
                for(int i = 12; i<16; i++){
                    parameterValue[i-12] = (byte) (mas[i] & 0x0ff);
                }
                break;
            case 1:
                parameterValue = new byte[8];
                for(int i = 8; i<16; i++){
                    parameterValue[i-8] = (byte) (mas[i] & 0x0ff);
                }
                break;
            case 2:
                len = ((mas[10] << 8) & 0xff00) | (mas[11] & 0xff);

                parameterValue = new byte[4];

                for(int i = 12; i<16; i++){
                    parameterValue[i-12] = (byte) (mas[i] & 0x0ff);
                }
                break;
            case 3:
                len = ((mas[10] << 8) & 0x0ff00) + (mas[11] & 0x0ff);
                parameterValue = new byte[len];
                for (int i = 12; i < 16; i++) {
                    parameterValue[i-12] = (byte) (mas[i] & 0x0ff);
                }
                if(len <= 4) len = 0;
                else {
                    byte[] m = new byte[len - 4];

                    try {
                        fileInputStream.read(m, 0, len - 4);
                        for(int i = 0; i<len-4; i++){
                            parameterValue[i+4] = m[i];
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
                break;
            default:
                parameterValue = new byte[4];
                break;
        }
        return new TelemetrySign(parameterName,parameterNumber,createTime,dimension,attribute,valueType,parameterValue,len);
    }

    public static void makeSigns(TelemetrySign telemetrySign, PrintWriter f_outputInfo){
        if(telemetrySign.getParameterNumber() != codeServiceMessege){
            if(telemetrySign.getParameterNumber() != prevNumber){
                f_outputInfo.printf("%s %04d ",
                        telemetrySign.getParameterName(),
                        telemetrySign.getParameterNumber() & 0xffff
                        );
            }else
                f_outputInfo.printf("\t\t");
            f_outputInfo.printf("%s ",makeTime(telemetrySign.getCreateTime()));
            prevNumber = telemetrySign.getParameterNumber();
            try {
                f_outputInfo.printf("%s ",attributes[(int) telemetrySign.getAttribute() & 0xff]);
            }
            catch(Exception e){
                f_outputInfo.printf("%02x ",telemetrySign.getAttribute() & 0xff);
            }

            f_outputInfo.printf("type=");
            switch (telemetrySign.getValueType()){
                case 0:
                    f_outputInfo.printf("Long ");
               //     long val =
                    f_outputInfo.printf("%d ", ByteBuffer.wrap(telemetrySign.getParameterValue()).getInt());
                    break;
                case 1:
                    f_outputInfo.printf("Double ");
                    f_outputInfo.printf("%f ", ByteBuffer.wrap(telemetrySign.getParameterValue()).getDouble());
                    break;
                case 2:
                    f_outputInfo.printf("Code len=%d ",telemetrySign.getLen());
                    int len = telemetrySign.getLen();

                    f_outputInfo.printf("%s ", makeBinary(telemetrySign));
                    break;
                case 3:
                    f_outputInfo.printf("Point len=%d",telemetrySign.getLen());
                    break;
                default:
                    f_outputInfo.printf(" " + telemetrySign.getValueType());
                    break;
            }
            try{
                f_outputInfo.printf(" %s ", dimensions.get((int) telemetrySign.getDimension()));
            }
            catch(Exception e){
                f_outputInfo.printf("%02x ",telemetrySign.getDimension() & 0xff);
            }
            f_outputInfo.printf("\n");
        }else{
            f_outputInfo.printf("\n");
        }
    }

    private static String makeBinary(TelemetrySign telemetrySign){
        String s = "";
        int numByte = (telemetrySign.getLen() - 1)/8;
        int numBit = (telemetrySign.getLen()-1)%8;
        while(true) {
            s = s + ((telemetrySign.getParameterValue()[3-numByte] >> numBit) & 0x1);
            numBit--;
            if(numBit <0) {
                numBit+=8;
                numByte--;
            }
            if(numByte < 0)
                break;
        }
        return s;
    }

    public static void fillDimensions() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("dimension.txt"));
        String s;
        int i = 1;
        try {
            while(scanner.hasNextLine()){
                s = scanner.nextLine();
                dimensions.put(i, s);
                i++;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public static void printDimensions() {
        for(int i = 1; i<dimensions.size(); i++){
            System.out.println(dimensions.get(i));
        }
    }

    public static TreeMap<Integer, String> getDimensions() {
        return dimensions;
    }

    public static int getCodeServiceMessege() {
        return codeServiceMessege;
    }

    private static String makeTime(int time){
        return String.format("%02d:%02d:%02d.%d", time/60/60/1000, time/60/1000%60, time/1000%60,time%1000);
    }
}
