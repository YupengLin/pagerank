import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class LeftoverReducer extends Reducer<IntWritable, Node, IntWritable, Node> {
    public static double alpha = 0.85;
    public void reduce(IntWritable nid, Iterable<Node> Ns, Context context) throws IOException, InterruptedException {
        //Implement
        
    	String leftover = context.getConfiguration().get("leftover");
    	String size = context.getConfiguration().get("size");
    	double lossMass = Double.parseDouble(leftover); 
        lossMass = lossMass / 10000;
    	double numNodes = Double.parseDouble(size);
    	
        Node node = null;
        
    	for(Node val : Ns) {
    		node = val;
    	}
    	
    	double originRank = node.pageRank;
    	double redistRank = alpha / numNodes + (1 - alpha) * (lossMass / numNodes + originRank);
    	node.setPageRank(redistRank);
        
    	context.write(nid, node);

    }
}


/*
public class LeftoverReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {
    public void reduce(IntWritable key, Iterable<NodeOrDouble> values, Context context)
        throws IOException, InterruptedException {
        //Implement
        Node emitNode = null;
        for(NodeOrDouble val : values) {
            if(val.isNode()) {
                emitNode = val.getNode();
            } else {
                emitNode.setPageRank(emitNode.getPageRank() + val.getDouble());
                context.write(key, emitNode);
            }
        }
    }
}
*/