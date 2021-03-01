package com.expensetracker.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.appslandia.common.utils.DateUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class TimeUtils {

	private static final ConcurrentMap<String, DateTimeFormatter> FORMATTERS = new ConcurrentHashMap<>();

	public static OffsetDateTime nowAtGMT7() {
		return DateUtils.nowAt("+7").truncatedTo(ChronoUnit.MINUTES);
	}

	public static DateTimeFormatter getFormatter(String pattern) {
		return FORMATTERS.computeIfAbsent(pattern, p -> DateTimeFormatter.ofPattern(p));
	}
}
