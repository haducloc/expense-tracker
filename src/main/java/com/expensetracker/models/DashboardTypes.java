package com.expensetracker.models;

import java.util.ArrayList;
import java.util.List;

import com.appslandia.common.models.SelectItem;
import com.appslandia.common.models.SelectItemImpl;
import com.appslandia.plum.base.Resources;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class DashboardTypes {

	public static final int TYPE_MONTHLY = 1;

	public static final int TYPE_YEARLY = 2;

	public static List<SelectItem> createList(Resources resources) {
		List<SelectItem> list = new ArrayList<>();

		list.add(new SelectItemImpl(TYPE_MONTHLY, resources.getOrDefault("dashboardTypes.type_monthly", "Monthly")));
		list.add(new SelectItemImpl(TYPE_YEARLY, resources.getOrDefault("dashboardTypes.type_yearly", "Yearly")));

		return list;
	}
}
