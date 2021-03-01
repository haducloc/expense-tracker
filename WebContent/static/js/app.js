function getCookie(cname) {
	var v = document.cookie.match('(^|;) ?' + cname + '=([^;]*)(;|$)');
	return v ? v[2] : null;
}

function setCookie(cname, cvalue, exdays, path) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=" + path;
}

function getClientZoneId() {
	var d = new Date();
	var tzos = d.getTimezoneOffset();
	if (tzos == 0) return "Z";
	var behindUtc = tzos > 0;
	if (tzos < 0) tzos = -tzos;
	var h = Math.floor(tzos / 60);
	var hh = h < 10 ? "0" + h : h.toString();
	var m = tzos % 60;
	var mm = m < 10 ? "0" + m : m.toString();
	var tz = (behindUtc ? "-" + hh : "+" + hh) + ":" + mm;
	return tz;
}

function setClientZoneCookie(cname, exdays, path) {
	var tz = getClientZoneId();
	var cur = getCookie(cname);
	if (tz != cur) {
		setCookie(cname, tz, exdays, path);
	}
}

function initCountDown(seconds, cdSpan, cdHandler) {
	var countDownTime = new Date(new Date().getTime() + seconds * 1000).getTime();

	var countDown = setInterval(function() {
		var secRemaining = Math.trunc((countDownTime - new Date().getTime() + 100) / 1000);

		if (secRemaining == 0) {
			clearInterval(countDown);
		}
		cdHandler(secRemaining, seconds, cdSpan);

	}, 1000);
}

function __on_enter(event, enterFn) {
	if (event.keyCode == 13) {
		enterFn();
		return false;
	}
	return true;
}

function __click_btn_on_enter(event, btn) {
	if (event.keyCode == 13) {
		document.getElementById(btn).click()
		return false;
	}
	return true;
}

function getLabelWait() {
	return (typeof LABEL_WAIT != "undefined") ? LABEL_WAIT : "Please wait...";
}

function __click_submit_btn(event, waitLabel, actionType) {
	var element = event.target;
	if (waitLabel) {
		element.innerHTML = getLabelWait();
		element.disabled = true;
		element.classList.add('btn-waiting');
	}
	if (actionType != null) element.form['actionType'].value = actionType;
	element.form.submit();
	return false;
}

function initButtons(btnSel) {
	$(window).bind("pageshow", function(event) {
		if (event.originalEvent.persisted) {

			$(btnSel).each(function() {
				$(this).removeAttr("disabled").removeClass("btn-waiting").text($(this).attr("data-label"));
			});
		}
	});
}

function initDropdowns(dropdownSel) {
	$(dropdownSel).each(function() {
		if ($(this).attr("unset") == "unset") {
			$(this).prop("selectedIndex", -1);
		}
	});
}

function ajax(method, url, queryParams, headerFn, jsonResult, onSuccess, onError, onDone) {
	var xhr = new XMLHttpRequest();
	xhr.open(method, url, true);

	if (headerFn == null) {
		if (method == 'POST') xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	} else headerFn(xhr);

	xhr.onreadystatechange = function() {
		if (this.readyState === XMLHttpRequest.DONE) {
			var res = jsonResult ? JSON.parse(xhr.responseText) : xhr.responseText;

			if (this.status === 200) {
				if (onSuccess != null)
					onSuccess(res);
			} else {
				if (onError != null)
					onError(res, this.status);
			}
			if (onDone != null)
				onDone(res, this.status);
		}
	}
	xhr.send(queryParams);
}

function initSignUp(btnSelector) {
	$(btnSelector).click(function() {
		var jqBtn = $(this);
		var jqEmail = $("input[name='email']");

		jqBtn.prop("disabled", true).addClass("btn-waiting").text(getLabelWait());

		ajax("POST", SIGNUP_URL, "email=" + encodeURIComponent(jqEmail.val()), null, true,
			function(res) {
				$("#signUpMsg").text(res.message).removeClass("d-none").removeClass("messages-error").addClass("messages-notice");
				jqEmail.val("");
			},
			function(err) {
				$("#signUpMsg").text(err.title).removeClass("d-none").removeClass("messages-notice").addClass("messages-error");
			},
			function() {
				jqBtn.removeAttr("disabled").removeClass("btn-waiting").text(jqBtn.attr("data-label"));
			}
		);
	});
}

function initClkSearch() {
	$(".clk-search").click(function() {
		var _this = $(this);
		var words = _this.text();
		var encoded = words.indexOf(" ") > 0 ? '"' + encodeURIComponent(words) + '"' : encodeURIComponent(words);
		var lastTime = _this.attr("data-clk");
		var curTime = new Date().getTime();
		if (lastTime != null) {
			if (curTime - lastTime <= 500) {
				_this.attr("data-clk", curTime + 800);
				window.open("https://www.google.com/search?q=" + encoded + "+definition", "_blank");
			} else {
				_this.attr("data-clk", curTime);
			}
		} else {
			_this.attr("data-clk", curTime);
		}
	});
}

function updateTags(str, tag) {
	if (str.length = 0) return tag;
	if (str == "#unsorted") {
		if (tag != "#tbd") return tag;
	}
	var items = str.split(",");

	var found = false;
	for (var i = 0; i < items.length; i++) {

		items[i] = items[i].trim();
		if (items[i] == tag) {
			found = true;
		}
	}

	if (!found) items.push(tag);
	else items.splice(items.indexOf(tag), 1);

	// #tbd
	var idx = items.indexOf("#tbd");
	var hasTbd = idx >= 0;
	if (hasTbd)
		items.splice(idx, 1);

	// #unsorted
	if (items.indexOf("#unsorted") >= 0)
		items = ["#unsorted"];

	if (hasTbd)
		items.push("#tbd");

	// Implement string.join
	str = "";
	for (var i = 0; i < items.length; i++) {
		if (str.length > 0) str += ", ";
		str += items[i];
	}
	return str;
}