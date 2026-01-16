package hadoop.mapreduce.tp1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SalesByStoreMapper extends Mapper<Object, Text, Text, DoubleWritable> {

    private final Text store = new Text();
    private final DoubleWritable amount = new DoubleWritable();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();
        if (line.isEmpty()) return;

        // split on whitespace (one or more spaces/tabs)
        String[] tokens = line.split("\\s+");
        // Need at least: date time store product cost payment => 6 tokens
        if (tokens.length < 6) return;

        String storeName = tokens[2];
        String costStr = tokens[tokens.length - 2];

        try {
            double cost = Double.parseDouble(costStr);
            store.set(storeName);
            amount.set(cost);
            context.write(store, amount);
        } catch (NumberFormatException e) {
            // ignore malformed lines
        }
    }
}
