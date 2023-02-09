package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoLog {
    Logger logger = LoggerFactory.getLogger(DemoLog.class);
    public void log(String s) {
        logger.debug(s);
        logger.info(s);
        logger.warn(s);
        logger.error(s);
    }
}