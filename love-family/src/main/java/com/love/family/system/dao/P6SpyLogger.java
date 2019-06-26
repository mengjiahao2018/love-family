package com.love.family.system.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p6spy.engine.spy.appender.StdoutLogger;

public class P6SpyLogger extends StdoutLogger {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());


}
