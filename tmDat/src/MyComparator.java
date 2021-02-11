import java.util.Comparator;

public class MyComparator implements Comparator<TelemetrySign> {
    public int compare(TelemetrySign ts1, TelemetrySign ts2) {
        if(ts1.getParameterName().compareTo(ts2.getParameterName()) == 0){
            return 1;
        }
        return ts1.getParameterName().compareTo(ts2.getParameterName());
    }
}
