package com.expensetracker.formatters;

import java.util.Set;

import com.appslandia.common.base.FormatProvider;
import com.appslandia.common.formatters.Formatter;
import com.appslandia.common.formatters.FormatterException;
import com.appslandia.common.utils.StringUtils;
import com.expensetracker.utils.VendorUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class VendorsFormatter implements Formatter {

	public static final String ERROR_MSG_KEY = VendorsFormatter.class.getName() + ".message";

	@Override
	public String getErrorMsgKey() {
		return ERROR_MSG_KEY;
	}

	@Override
	public Class<?> getArgType() {
		return String.class;
	}

	@Override
	public String format(Object obj, FormatProvider formatProvider) {
		if (obj == null) {
			return null;
		}
		return VendorUtils.toDispVendors((String) obj);
	}

	@Override
	public String parse(String str, FormatProvider formatProvider) throws FormatterException {
		str = StringUtils.trimToNull(str);
		if (str == null) {
			return null;
		}
		Set<String> vendors = VendorUtils.toVendors(str);
		return !vendors.isEmpty() ? VendorUtils.toDbVendors(vendors) : null;
	}
}
