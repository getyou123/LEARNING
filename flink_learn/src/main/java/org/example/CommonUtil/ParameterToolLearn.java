package org.example.CommonUtil;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import  org.apache.flink.api.java.utils.ParameterTool;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.example.CommonUtil.MyEnvironmentUtils.createConfiguredEnvironment;

public class ParameterToolLearn {

    public static void main(String[] args) throws IOException, URISyntaxException {
        org.apache.flink.api.java.utils.ParameterTool parameters = ParameterTool.fromArgs(args);
        StreamExecutionEnvironment env = createConfiguredEnvironment(parameters);
    }
}
