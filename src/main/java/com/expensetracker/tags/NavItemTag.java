package com.expensetracker.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.appslandia.common.base.Params;
import com.appslandia.plum.base.ActionParser;
import com.appslandia.plum.tags.Attribute;
import com.appslandia.plum.tags.Tag;
import com.appslandia.plum.tags.TagUtils;
import com.appslandia.plum.tags.UITagBase;
import com.appslandia.plum.utils.HtmlUtils;
import com.appslandia.plum.utils.ServletUtils;
import com.appslandia.plum.utils.XmlEscaper;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Tag(name = "navItem", dynamicAttributes = false)
public class NavItemTag extends UITagBase {

	protected String linkKey;
	protected String controller;
	protected String action;

	protected String _href;
	protected Map<String, Object> _parameters;

	@Override
	protected String getTagName() {
		return "li";
	}

	protected Map<String, Object> getParams() {
		if (this._parameters == null) {
			return this._parameters = new Params();
		}
		return this._parameters;
	}

	@Override
	public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
		if (TagUtils.isForParameter(name)) {
			getParams().put(TagUtils.getParameterName(name), value);
		} else {
			super.setDynamicAttribute(uri, name, value);
		}
	}

	@Override
	protected void initTag() throws JspException, IOException {
		// Class
		if (getRequestContext().isRoute(this.controller, this.action)) {
			this.clazz = this.clazz + " active";
		}

		ActionParser actionParser = ServletUtils.getAppScoped(this.pageContext.getServletContext(), ActionParser.class);
		this._href = actionParser.toActionUrl(this.getRequest(), this.controller, this.action, this._parameters, false);
		this._href = this.getResponse().encodeURL(this._href);
	}

	@Override
	protected void writeAttributes(JspWriter out) throws JspException, IOException {
		HtmlUtils.attribute(out, "class", this.clazz);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	@Override
	protected void writeBody(JspWriter out) throws JspException, IOException {
		out.print("<a class=\"nav-link\"");
		HtmlUtils.escAttribute(out, "href", this._href);
		out.write('>');

		XmlEscaper.escapeXmlContent(out, this.getRequestContext().res(this.linkKey));
		out.print("</a>");
	}

	@Attribute(required = true, rtexprvalue = false)
	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	@Attribute(required = true, rtexprvalue = false)
	public void setController(String controller) {
		this.controller = controller;
	}

	@Attribute(required = true, rtexprvalue = false)
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void setId(String id) {
	}

	@Override
	public void setDatatag(Object datatag) {
	}

	@Override
	public void setHidden(boolean hidden) {
	}

	@Override
	public void setStyle(String style) {
	}

	@Override
	public void setTitle(String title) {
	}
}
