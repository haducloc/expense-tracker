package com.expensetracker.formatters;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.appslandia.common.cdi.CDISupplier;
import com.appslandia.common.cdi.Supplier;
import com.appslandia.common.formatters.Formatter;
import com.appslandia.common.formatters.RoundedDoubleFormatter;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Supplier(Formatter.class)
public class FormatterSupplier implements CDISupplier {

	@Override
	public Map<String, Formatter> get() {
		Map<String, Formatter> map = new HashMap<>();

		map.put(Formatters.VENDORS, new VendorsFormatter());
		map.put(Formatters.MONEY_AMT, new RoundedDoubleFormatter(2, BigDecimal.ROUND_HALF_UP));

		return map;
	}
}
