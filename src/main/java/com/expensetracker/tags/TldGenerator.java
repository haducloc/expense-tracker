package com.expensetracker.tags;

import com.appslandia.common.base.TextBuilder;
import com.appslandia.plum.tags.TagTldGenerator;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class TldGenerator {

	public static void main(final String[] args) throws Exception {
		final TextBuilder sb = new TextBuilder();

		TagTldGenerator.generateTag(NavItemTag.class, sb);

		TagTldGenerator.generateTag(DollarAmtTag.class, sb);
		TagTldGenerator.generateTag(TotalAmtTag.class, sb);

		System.out.println(sb);
	}
}
