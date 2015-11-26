package fr.enseirb.t3g7;
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SimpleApp {
  @SuppressWarnings({ "resource", "serial", "deprecation" })
public static void main(String[] args) {
    String logFile = "src/main/resources/persons.txt"; // Should be some file on your system
    SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> logData = sc.textFile(logFile).cache();

    JavaPairRDD<String, Integer> pairs = logData.mapToPair(new PairFunction<String, String, Integer>() {
        public Tuple2<String, Integer> call(String s) throws Exception {
          return new Tuple2<String, Integer>(s.split(",")[0], Integer.parseInt(s.split(",")[1]));
        }
      });
    Function2<Integer, Integer, Integer> a = new Function2<Integer, Integer, Integer>(){
    	  public Integer call(Integer a, Integer b) { return a + b; }
    };
	JavaPairRDD<String, Integer> counter = pairs.reduceByKey(a );
	for (int j = 0; j < counter.toArray().size(); j++) {
	    System.out.println(counter.toArray().get(j).toString());

	}
  }
}