package com.expensetracker.utils;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.appslandia.common.base.MemoryStream;
import com.appslandia.common.base.Out;
import com.appslandia.common.base.StringWriter;
import com.appslandia.common.easyrecord.Record;
import com.appslandia.common.easyrecord.RecordUtils;
import com.appslandia.common.jdbc.JdbcUtils;
import com.appslandia.common.jdbc.ResultSetImpl;
import com.appslandia.common.json.JsonProcessor;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ResultUtils {

	public static String executeCSV(ResultSetImpl rs, Out<Integer> count) throws Exception {
		final String[] columnLabels = JdbcUtils.getColumnLabels(rs);
		final List<Record> records = RecordUtils.executeList(rs);
		count.value = records.size();

		// CSV
		CSVFormat format = CSVFormat.EXCEL.withHeader(columnLabels);
		MemoryStream ms = new MemoryStream();

		try (Writer out = new BufferedWriter(new OutputStreamWriter(ms, StandardCharsets.UTF_8))) {
			try (CSVPrinter csvOut = new CSVPrinter(out, format)) {

				for (Record record : records) {
					csvOut.printRecord(record.toValues(columnLabels));
				}
			}
		}
		return ms.toString(StandardCharsets.UTF_8);
	}

	public static String executeJson(ResultSetImpl rs, JsonProcessor jsonProcessor, Out<Integer> count) throws Exception {
		final List<Record> records = RecordUtils.executeList(rs);
		count.value = records.size();

		MemoryStream ms = new MemoryStream();

		// JSON
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ms, StandardCharsets.UTF_8)))) {
			out.println('[');
			StringWriter buf = new StringWriter(512);

			for (int idx = 0; idx < records.size(); idx++) {
				jsonProcessor.write(buf, records.get(idx));
				out.append("  ").write(buf.toString());

				if (idx < records.size() - 1) {
					out.println(',');
				}

				buf.reset();
			}
			out.println();
			out.write(']');
			out.flush();
		}
		return ms.toString(StandardCharsets.UTF_8);
	}
}
