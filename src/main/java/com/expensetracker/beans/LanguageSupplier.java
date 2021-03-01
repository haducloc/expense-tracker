package com.expensetracker.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.appslandia.common.base.Language;
import com.appslandia.common.cdi.CDISupplier;
import com.appslandia.common.cdi.Supplier;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Supplier(Language.class)
public class LanguageSupplier implements CDISupplier {

	@Override
	public List<Language> get() {
		List<Language> langs = new ArrayList<>();

		langs.add(Language.EN);
		return langs;
	}
}
