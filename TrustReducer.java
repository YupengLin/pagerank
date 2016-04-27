import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class TrustReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {
    public void reduce(IntWritable key, Iterable<NodeOrDouble> values, Context context)
        throws IOException, InterruptedException {
        //Implement
        Node emitNode = null;
        double pageRank = 0;
        for(NodeOrDouble val : values) {
        	if(val.isNode()) {
        		emitNode = val.getNode();
        	} else {
        		pageRank += val.getDouble();
        	}
        }
        
        emitNode.setPageRank(pageRank);
        context.write(key, emitNode);
    }
}

