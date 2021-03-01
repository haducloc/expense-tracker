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
@Tag(name = "totalAmt", dynamicAttributes = false)
public class TotalAmtTag extends UITagBase {

	private Double amt;
	private Integer max;

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
		if (amt == null) {
			return;
		}

		if (max != null) {
			double per = (amt * 100) / max;

			if (per <= 75.00) {
				// default

			} else if (per <= 95.00) {
				this.style = "color:#856404;";

			} else {
				this.style = "color:#ff0000;";
			}
		}
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
		if (amt != null) {
			out.write(String.format("$%,.2f", amt));
		}
	}

	@Attribute(required = true, rtexprvalue = true)
	public void setAmt(Double amt) {
		this.amt = amt;
	}

	@Attribute(required = true, rtexprvalue = true)
	public void setMax(Integer max) {
		this.max = max;
	}
}
