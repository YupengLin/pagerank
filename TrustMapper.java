import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class TrustMapper extends Mapper<IntWritable, Node, IntWritable, NodeOrDouble> {
    public void map(IntWritable key, Node value, Context context) throws IOException, InterruptedException {

        //Implement 
        double currRank = value.pageRank / value.outgoing.length;
        NodeOrDouble emitNode = new NodeOrDouble(value);
        context.getCounter(Counter.counters.GLOBALNODE).increment(1);
        context.write(key, emitNode);
        //
        if(value.outgoing.length == 0) {
        	long currPageRank =new Double(value.getPageRank()*10000).longValue();
        	//currPageRank *= 10000;
        	context.getCounter(Counter.counters.DANGLINGNODE).increment(currPageRank);
        	//context.getCounter(Counter.counters.DANGLINGNODE).increment(45);
        }
        for(int i = 0; i < value.outgoing.length; i++) {
        	NodeOrDouble emitRank = new NodeOrDouble(new Double(currRank));
        	IntWritable adjNode = new IntWritable(value.outgoing[i]);
        	context.write(adjNode, emitRank);
        }


    }
}
