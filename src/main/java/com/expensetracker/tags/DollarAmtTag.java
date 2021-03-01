package com.expensetracker.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.appslandia.plum.tags.Attribute;
import com.appslandia.plum.tags.Tag;
import com.appslandia.plum.tags.UITagBase;
import com.appslandia.plum.utils.HtmlUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Tag(name = "dollarAmt", dynamicAttributes = false)
public class DollarAmtTag extends UITagBase {

	private Number value;

	@Override
	protected String getTagName() {
		return "span";
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	@Override
	protected void initTag() throws JspException, IOException {
	}

	@Override
	protected void writeAttributes(JspWriter out) throws JspException, IOException {
		if (this.clazz != null)
			HtmlUtils.attribute(out, "class", this.clazz);
		if (this.style != null)
			HtmlUtils.attribute(out, "style", this.style);
	}

	@Override
	protected void writeBody(JspWriter out) throws JspException, IOException {
		if (this.value != null) {
			out.write(String.format("$%,.2f", this.value.doubleValue()));
		}
	}

	@Attribute(required = true, rtexprvalue = true)
	public void setValue(Number value) {
		this.value = value;
	}
}
