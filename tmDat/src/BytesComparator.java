import java.util.Comparator;

public class BytesComparator implements Comparator<Byte[]> {
    @Override
    public int compare(Byte[] buff1, Byte[] buff2) {
        if(buff1[0].equals(buff2[0])){
            if(buff1[1].equals(buff2[1])) return 0;
            else
                if((short)(buff1[1] & 0x0ff) > (short)(buff2[1] & 0x0ff)) return 1;
                else return -1;
        }
        else
            if((short)(buff1[0] & 0x0ff) > (short)(buff2[0] & 0x0ff)) return 1;
            else return -1;
    }
}
