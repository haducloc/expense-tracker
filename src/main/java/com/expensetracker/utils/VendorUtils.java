package com.expensetracker.utils;

import java.util.Set;
import java.util.TreeSet;

import com.appslandia.common.base.TextBuilder;
import com.appslandia.common.utils.CollectionUtils;
import com.appslandia.common.utils.SplitUtils;
import com.appslandia.common.utils.StringUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class VendorUtils {

	public static Set<String> toVendors(String str) {
		String[] vendors = SplitUtils.splitByLine(str);
		return CollectionUtils.toSet(new TreeSet<>(), vendors);
	}

	public static String toDispVendors(String dbVendors) {
		if (dbVendors == null)
			return null;

		String[] vendors = SplitUtils.split(dbVendors, ',');
		TextBuilder sb = new TextBuilder(vendors.length * 16);

		for (int i = 0; i < vendors.length; i++) {
			sb.append(vendors[i]).append(",");
			sb.appendln();
		}
		return sb.toString();
	}

	public static String[] toVendorArray(String dbVendors) {
		if (dbVendors == null)
			return StringUtils.EMPTY_ARRAY;

		return SplitUtils.split(dbVendors, ',');
	}

	public static String toDbVendors(Set<String> vendors) {
		return String.join(",", vendors);
	}
}
