import java.io.*;
import java.util.*;

public class Program {

    public static void main(String[] args) throws IOException {
        byte[] buff = new byte[16];
        ArrayList<Byte[]> bytes;
        bytes = new ArrayList<>();
        int size;
        int count = 0;
        MyComparator mycmrtr = new MyComparator();
        TreeSet<TelemetrySign> signTreeSet = new TreeSet<TelemetrySign>(mycmrtr);
        FileInputStream f_input = new FileInputStream("190829_v29854.knp");
        PrintWriter f_outputInfo = new PrintWriter("190829_v29854.info");
        File xmlFile = new File("KNP-173.14.33.58.dat.xml");
        try {
            XMLReader.XMLread(xmlFile);
            XMLReader.printParamNames();
            Telemetry.fillDimensions();

            f_input.read(buff,0,16);
            f_input.read(buff,0,16);

            while ((size = f_input.read(buff, 0, 16)) > 0) {
                Byte[] Buff = new Byte[16];

                if (buff[0] != (byte) 0xff && buff[1] != (byte)0xff) {

                    signTreeSet.add(Telemetry.getTelemetryInfo(buff,f_input));

                    for (int i = 0; i < size; i++){
                        Buff[i] = buff[i];
                    }

                    bytes.add(Buff);
                }
            }
            f_input.close();

            for(TelemetrySign i : signTreeSet){
                Telemetry.makeSigns(i,f_outputInfo);
            }
            f_outputInfo.close();

        }
        catch(Exception e) {
            System.out.println("Exception " + e.toString());
            System.out.println(count);
        }
    }
}
//            PrintWriter f_output = new PrintWriter("190829_v29854.rez");
//                    f_output.printf("%06x\t", count);
/*                    for (int i = 0; i < size; i++) {
                        f_output.printf("%02x ", buff[i]);
                        if (i % 8 == 7)
                            f_output.write("\t");
                    }
                    count += size;
                    f_output.write('\n');*/
