package com.greatdreams.learn.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordCountExample {
    public static void main(String []args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        DataSet<String> set = env.fromElements(
                "Who's there?",
                "I think that I hear them. Stand, ho! Who's there?");
        DataSet<Tuple2<String, Integer>> wordCounts = set
                .flatMap(new LineSplitter())
                .groupBy(0)
                .sum(1);

        wordCounts.print();

    }
    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String line, Collector<Tuple2<String, Integer>> out) {
            for (String word : line.split(" ")) {
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
