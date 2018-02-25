/*
jQWidgets v3.3.0 (2014-May-26)
Copyright (c) 2011-2014 jQWidgets.
License: http://jqwidgets.com/license/
 */

(function(a) {
	a.jqx.jqxWidget("jqxChart", "", {});
	a
			.extend(
					a.jqx._jqxChart.prototype,
					{
						defineInstance : function() {
							var c = {
								title : "Title",
								description : "Description",
								source : [],
								seriesGroups : [],
								categoryAxis : {},
								renderEngine : "",
								enableAnimations : true,
								enableAxisTextAnimation : false,
								backgroundImage : "",
								background : "#FFFFFF",
								padding : {
									left : 5,
									top : 5,
									right : 5,
									bottom : 5
								},
								backgroundColor : "#FFFFFF",
								showBorderLine : true,
								borderLineWidth : 1,
								borderLineColor : null,
								borderColor : null,
								titlePadding : {
									left : 5,
									top : 5,
									right : 5,
									bottom : 10
								},
								showLegend : true,
								legendLayout : null,
								enabled : true,
								colorScheme : "scheme01",
								animationDuration : 500,
								showToolTips : true,
								toolTipShowDelay : 500,
								toolTipDelay : 500,
								toolTipHideDelay : 4000,
								toolTipFormatFunction : null,
								columnSeriesOverlap : false,
								rtl : false,
								legendPosition : null,
								greyScale : false,
								axisPadding : 5,
								enableCrosshairs : false,
								crosshairsColor : "#888888",
								crosshairsDashStyle : "2,2",
								crosshairsLineWidth : 1,
								enableEvents : true,
								_itemsToggleState : [],
								_isToggleRefresh : false,
								drawBegin : null,
								drawEnd : null
							};
							a.extend(true, this, c)
						},
						createInstance : function(f) {
							if (!a.jqx.dataAdapter) {
								throw "jqxdata.js is not loaded"
							}
							var d = this;
							d._refreshOnDownloadComlete();
							d.host.on("mousemove", function(j) {
								if (d.enabled == false) {
									return
								}
								j.preventDefault();
								var i = j.pageX || j.clientX || j.screenX;
								var l = j.pageY || j.clientY || j.screenY;
								var k = d.host.offset();
								i -= k.left;
								l -= k.top;
								d.onmousemove(i, l)
							});
							d.addHandler(d.host, "mouseleave", function(k) {
								if (d.enabled == false) {
									return
								}
								var i = d._mouseX;
								var l = d._mouseY;
								var j = d._plotRect;
								if (j && i >= j.x && i <= j.x + j.width
										&& l >= j.y && l <= j.y + j.height) {
									return
								}
								d._cancelTooltipTimer();
								d._hideToolTip(0);
								d._unselect()
							});
							var e = a.jqx.mobile.isTouchDevice();
							d
									.addHandler(
											d.host,
											"click",
											function(i) {
												if (d.enabled == false) {
													return
												}
												if (!isNaN(d._lastClickTs)) {
													if ((new Date()).valueOf()
															- d._lastClickTs < 100) {
														return
													}
												}
												this._hostClickTimer = setTimeout(
														function() {
															if (!e) {
																d
																		._cancelTooltipTimer();
																d
																		._hideToolTip();
																d._unselect()
															}
															if (d._pointMarker
																	&& d._pointMarker.element) {
																var k = d.seriesGroups[d._pointMarker.gidx];
																var j = k.series[d._pointMarker.sidx];
																d
																		._raiseItemEvent(
																				"click",
																				k,
																				j,
																				d._pointMarker.iidx)
															}
														}, 100)
											});
							var h = d.element.style;
							if (h) {
								var c = false;
								if (h.width != null) {
									c |= h.width.toString().indexOf("%") != -1
								}
								if (h.height != null) {
									c |= h.height.toString().indexOf("%") != -1
								}
								if (c) {
									a(window).resize(function() {
										if (d.timer) {
											clearTimeout(d.timer)
										}
										var i = a.jqx.browser.msie ? 200 : 1;
										d.timer = setTimeout(function() {
											var j = d.enableAnimations;
											d.enableAnimations = false;
											d.refresh();
											d.enableAnimations = j
										}, i)
									})
								}
							}
						},
						_refreshOnDownloadComlete : function() {
							var e = this;
							var f = this.source;
							if (f instanceof a.jqx.dataAdapter) {
								var h = f._options;
								if (h == undefined
										|| (h != undefined && !h.autoBind)) {
									f.autoSync = false;
									f.dataBind()
								}
								var d = this.element.id;
								if (f.records.length == 0) {
									var c = function() {
										if (e.ready) {
											e.ready()
										}
										e.refresh()
									};
									f.unbindDownloadComplete(d);
									f.bindDownloadComplete(d, c)
								} else {
									if (e.ready) {
										e.ready()
									}
								}
								f.unbindBindingUpdate(d);
								f.bindBindingUpdate(d, function() {
									e.refresh()
								})
							}
						},
						propertyChangedHandler : function(c, d, f, e) {
							if (this.isInitialized == undefined
									|| this.isInitialized == false) {
								return
							}
							if (d == "source") {
								this._refreshOnDownloadComlete()
							}
							this.refresh()
						},
						_initRenderer : function(d) {
							var c = this;
							var e = null;
							if (document.createElementNS
									&& (c.renderEngine != "HTML5" && c.renderEngine != "VML")) {
								e = new a.jqx.svgRenderer();
								if (!e.init(d)) {
									if (c.renderEngine == "SVG") {
										throw "Your browser does not support SVG"
									}
									return
								}
							}
							if (e == null && c.renderEngine != "HTML5") {
								e = new a.jqx.vmlRenderer();
								if (!e.init(d)) {
									if (c.renderEngine == "VML") {
										throw "Your browser does not support VML"
									}
									return
								}
								c._isVML = true
							}
							if (e == null
									&& (c.renderEngine == "HTML5" || c.renderEngine == undefined)) {
								e = new a.jqx.HTML5Renderer();
								if (!e.init(d)) {
									throw "Your browser does not support HTML5 Canvas"
								}
							}
							return e
						},
						_internalRefresh : function() {
							var c = this;
							if (a.jqx.isHidden(c.host)) {
								return
							}
							c._stopAnimations();
							var e = c.renderer;
							if (!c._isToggleRefresh && !c._isUpdate) {
								c.host.empty();
								c._ttEl = undefined;
								e = c.renderer = c._initRenderer(c.host)
							}
							var d = e.getRect();
							c._render({
								x : 1,
								y : 1,
								width : d.width,
								height : d.height
							});
							if (e instanceof a.jqx.HTML5Renderer) {
								e.refresh()
							}
							c._isUpdate = false
						},
						saveAsPNG : function(e, c, d) {
							return this._saveAsImage("png", e, c, d)
						},
						saveAsJPEG : function(e, c, d) {
							return this._saveAsImage("jpeg", e, c, d)
						},
						_saveAsImage : function(m, i, p, k) {
							var n = this;
							if (i == undefined || i == "") {
								i = "chart." + m
							}
							if (p == undefined || p == "") {
								p = "http://www.jqwidgets.com/export_server/export.php"
							}
							var o = this.renderEngine;
							var h = this.enableAnimations;
							n.enableAnimations = false;
							n.renderEngine = "HTML5";
							if (n.renderEngine != o) {
								try {
									n.refresh()
								} catch (l) {
									n.renderEngine = o;
									n.refresh();
									n.enableAnimations = h
								}
							}
							var s = true;
							try {
								var f = n.renderer.getContainer()[0];
								if (f) {
									var j = f.toDataURL("image/" + m);
									j = j.replace("data:image/" + m
											+ ";base64,", "");
									if (k) {
										a.ajax({
											dataType : "string",
											url : p,
											type : "POST",
											data : {
												content : j,
												fname : i
											},
											async : false,
											success : function(t, e, u) {
												s = true
											},
											error : function(t, e, u) {
												s = false
											}
										})
									} else {
										var d = document.createElement("form");
										d.method = "POST";
										d.action = p;
										d.style.display = "none";
										document.body.appendChild(d);
										var q = document.createElement("input");
										q.name = "fname";
										q.value = i;
										q.style.display = "none";
										var c = document.createElement("input");
										c.name = "content";
										c.value = j;
										c.style.display = "none";
										d.appendChild(q);
										d.appendChild(c);
										d.submit();
										document.body.removeChild(d);
										s = true
									}
								}
							} catch (l) {
								s = false
							}
							if (n.renderEngine != o) {
								n.renderEngine = o;
								n.refresh();
								n.enableAnimations = h
							}
							return s
						},
						refresh : function() {
							this._internalRefresh()
						},
						update : function() {
							this._isUpdate = true;
							this._internalRefresh()
						},
						_seriesTypes : [ "line", "stackedline",
								"stackedline100", "spline", "stackedspline",
								"stackedspline100", "stepline",
								"stackedstepline", "stackedstepline100",
								"area", "stackedarea", "stackedarea100",
								"splinearea", "stackedsplinearea",
								"stackedsplinearea100", "steparea",
								"stackedsteparea", "stackedsteparea100",
								"rangearea", "splinerangearea",
								"steprangearea", "column", "stackedcolumn",
								"stackedcolumn100", "rangecolumn", "scatter",
								"stackedscatter", "stackedscatter100",
								"bubble", "stackedbubble", "stackedbubble100",
								"pie", "donut" ],
						_render : function(F) {
							var n = this;
							var J = n.renderer;
							n._colorsCache.clear();
							if (!n._isToggleRefresh && n._isUpdate
									&& n._renderData) {
								n._renderDataClone()
							}
							n._renderData = [];
							J.clear();
							n._unselect();
							n._hideToolTip(0);
							var o = n.backgroundImage;
							if (o == undefined || o == "") {
								n.host.css({
									"background-image" : ""
								})
							} else {
								n.host
										.css({
											"background-image" : (o
													.indexOf("(") != -1 ? o
													: "url('" + o + "')")
										})
							}
							var X = n.padding || {
								left : 5,
								top : 5,
								right : 5,
								bottom : 5
							};
							var s = J.createClipRect(F);
							var L = J.beginGroup();
							J.setClip(L, s);
							var af = J
									.rect(F.x, F.y, F.width - 2, F.height - 2);
							if (o == undefined || o == "") {
								J.attr(af, {
									fill : n.backgroundColor || n.background
											|| "white"
								})
							} else {
								J.attr(af, {
									fill : "transparent"
								})
							}
							if (n.showBorderLine != false) {
								var H = n.borderLineColor == undefined ? n.borderColor
										: n.borderLineColor;
								if (H == undefined) {
									H = "#888888"
								}
								var p = this.borderLineWidth;
								if (isNaN(p) || p < 0 || p > 10) {
									p = 1
								}
								J.attr(af, {
									"stroke-width" : p,
									stroke : H
								})
							}
							if (a.isFunction(n.drawBegin)) {
								n.drawBegin(J, F)
							}
							var U = {
								x : X.left,
								y : X.top,
								width : F.width - X.left - X.right,
								height : F.height - X.top - X.bottom
							};
							n._paddedRect = U;
							var k = n.titlePadding || {
								left : 2,
								top : 2,
								right : 2,
								bottom : 2
							};
							if (n.title && n.title.length > 0) {
								var R = n.toThemeProperty(
										"jqx-chart-title-text", null);
								var m = J.measureText(n.title, 0, {
									"class" : R
								});
								J.text(n.title, U.x + k.left, U.y + k.top,
										U.width - (k.left + k.right), m.height,
										0, {
											"class" : R
										}, true, "center", "center");
								U.y += m.height;
								U.height -= m.height
							}
							if (n.description && n.description.length > 0) {
								var S = n.toThemeProperty(
										"jqx-chart-title-description", null);
								var m = J.measureText(n.description, 0, {
									"class" : S
								});
								J.text(n.description, U.x + k.left,
										U.y + k.top, U.width
												- (k.left + k.right), m.height,
										0, {
											"class" : S
										}, true, "center", "center");
								U.y += m.height;
								U.height -= m.height
							}
							if (n.title || n.description) {
								U.y += (k.bottom + k.top);
								U.height -= (k.bottom + k.top)
							}
							var c = {
								x : U.x,
								y : U.y,
								width : U.width,
								height : U.height
							};
							n._buildStats(c);
							var I = n._isPieOnlySeries();
							var v = n.seriesGroups;
							var C = {};
							for ( var Y = 0; Y < v.length && !I; Y++) {
								if (v[Y].type == "pie" || v[Y].type == "donut") {
									continue
								}
								var G = v[Y].orientation == "horizontal";
								var E = v[Y].valueAxis;
								if (!E) {
									E = v[Y].valueAxis = {}
								}
								var e = n._getCategoryAxis(Y);
								if (!e) {
									throw "seriesGroup["
											+ Y
											+ "] is missing "
											+ (!G ? "categoryAxis"
													: "valueAxis")
											+ " definition"
								}
								var A = e == n.categoryAxis ? -1 : Y;
								C[A] = 0
							}
							var T = n.axisPadding;
							if (isNaN(T)) {
								T = 5
							}
							var u = {
								left : 0,
								right : 0,
								leftCount : 0,
								rightCount : 0
							};
							var q = [];
							for ( var Y = 0; Y < v.length; Y++) {
								var ac = v[Y];
								if (ac.type == "pie" || ac.type == "donut"
										|| ac.spider == true
										|| ac.polar == true) {
									q.push({
										width : 0,
										position : 0,
										xRel : 0
									});
									continue
								}
								var G = ac.orientation == "horizontal";
								var A = n._getCategoryAxis(Y) == n.categoryAxis ? -1
										: Y;
								var Q = E.axisSize;
								var l = {
									x : 0,
									y : c.y,
									width : c.width,
									height : c.height
								};
								var P = undefined;
								if (!Q || Q == "auto") {
									if (G) {
										Q = this._renderCategoryAxis(Y, l,
												true, c).width;
										if ((C[A] & 1) == 1) {
											Q = 0
										} else {
											if (Q > 0) {
												C[A] |= 1
											}
										}
										P = n._getCategoryAxis(Y).position
									} else {
										Q = n._renderValueAxis(Y, l, true, c).width;
										if (ac.valueAxis) {
											P = ac.valueAxis.position
										}
									}
								}
								if (P != "left" && n.rtl == true) {
									P = "right"
								}
								if (P != "right") {
									P = "left"
								}
								if (u[P + "Count"] > 0 && u[P] > 0 && Q > 0) {
									u[P] += T
								}
								q.push({
									width : Q,
									position : P,
									xRel : u[P]
								});
								u[P] += Q;
								u[P + "Count"]++
							}
							var ab = {
								top : 0,
								bottom : 0,
								topCount : 0,
								bottomCount : 0
							};
							var V = [];
							for ( var Y = 0; Y < v.length; Y++) {
								var ac = v[Y];
								if (ac.type == "pie" || ac.type == "donut"
										|| ac.spider == true
										|| ac.polar == true) {
									V.push({
										height : 0,
										position : 0,
										yRel : 0
									});
									continue
								}
								var G = ac.orientation == "horizontal";
								var e = n._getCategoryAxis(Y);
								var A = e == n.categoryAxis ? -1 : Y;
								P = undefined;
								var aa = e.axisSize;
								if (!aa || aa == "auto") {
									if (G) {
										aa = n._renderValueAxis(Y, {
											x : 0,
											y : 0,
											width : 10000000,
											height : 0
										}, true, c).height;
										if (n.seriesGroups[Y].valueAxis) {
											P = ac.valueAxis.position
										}
									} else {
										aa = n._renderCategoryAxis(Y, {
											x : 0,
											y : 0,
											width : 10000000,
											height : 0
										}, true).height;
										if ((C[A] & 2) == 2) {
											aa = 0
										} else {
											if (aa > 0) {
												C[A] |= 2
											}
										}
										P = n._getCategoryAxis(Y).position
									}
								}
								if (P != "top") {
									P = "bottom"
								}
								if (ab[P + "Count"] > 0 && ab[P] > 0 && aa > 0) {
									ab[P] += T
								}
								V.push({
									height : aa,
									position : P,
									yRel : ab[P]
								});
								ab[P] += aa;
								ab[P + "Count"]++
							}
							n._createAnimationGroup("series");
							n._plotRect = c;
							var z = (n.showLegend != false);
							var D = !z ? {
								width : 0,
								height : 0
							} : n._renderLegend(U, true);
							if (this.legendLayout
									&& (!isNaN(this.legendLayout.left) || !isNaN(this.legendLayout.top))) {
								D = {
									width : 0,
									height : 0
								}
							}
							if (U.height < ab.top + ab.bottom + D.height
									|| U.width < u.left + u.right) {
								J.endGroup();
								return
							}
							c.height -= ab.top + ab.bottom + D.height;
							c.x += u.left;
							c.width -= u.left + u.right;
							c.y += ab.top;
							var t = [];
							if (!I) {
								var ad = n.categoryAxis.tickMarksColor
										|| "#888888";
								for ( var Y = 0; Y < v.length; Y++) {
									var ac = v[Y];
									if (ac.polar == true || ac.spider == true) {
										continue
									}
									var G = ac.orientation == "horizontal";
									var A = n._getCategoryAxis(Y) == n.categoryAxis ? -1
											: Y;
									var l = {
										x : c.x,
										y : 0,
										width : c.width,
										height : V[Y].height
									};
									if (V[Y].position != "top") {
										l.y = c.y + c.height + V[Y].yRel
									} else {
										l.y = c.y - V[Y].yRel - V[Y].height
									}
									if (G) {
										n._renderValueAxis(Y, l, false, c)
									} else {
										if ((C[A] & 4) == 4) {
											continue
										}
										if (!n._isGroupVisible(Y)) {
											continue
										}
										t.push(l);
										n._renderCategoryAxis(Y, l, false, c);
										C[A] |= 4
									}
								}
							}
							if (z) {
								var O = U.x
										+ a.jqx._ptrnd((U.width - D.width) / 2);
								var N = c.y + c.height + ab.bottom;
								var Q = U.width;
								var aa = D.height;
								if (n.legendLayout) {
									if (!isNaN(n.legendLayout.left)) {
										O = n.legendLayout.left
									}
									if (!isNaN(n.legendLayout.top)) {
										N = n.legendLayout.top
									}
									if (!isNaN(n.legendLayout.width)) {
										Q = n.legendLayout.width
									}
									if (!isNaN(n.legendLayout.height)) {
										aa = n.legendLayout.height
									}
								}
								if (O + Q > F.x + F.width) {
									Q = F.x + F.width - O
								}
								if (N + aa > F.y + F.height) {
									aa = F.y + F.height - N
								}
								n._renderLegend({
									x : O,
									y : N,
									width : Q,
									height : aa
								})
							}
							n._hasHorizontalLines = false;
							if (!I) {
								for ( var Y = 0; Y < v.length; Y++) {
									var ac = v[Y];
									if (ac.polar == true || ac.spider == true) {
										continue
									}
									var G = v[Y].orientation == "horizontal";
									var l = {
										x : c.x - q[Y].xRel - q[Y].width,
										y : c.y,
										width : q[Y].width,
										height : c.height
									};
									if (q[Y].position != "left") {
										l.x = c.x + c.width + q[Y].xRel
									}
									if (G) {
										if ((C[n._getCategoryAxis(Y)] & 8) == 8) {
											continue
										}
										if (!n._isGroupVisible(Y)) {
											continue
										}
										t.push(l);
										n._renderCategoryAxis(Y, l, false, c);
										C[n._getCategoryAxis(Y)] |= 8
									} else {
										n._renderValueAxis(Y, l, false, c)
									}
								}
							}
							if (c.width <= 0 || c.height <= 0) {
								return
							}
							n._plotRect = {
								x : c.x,
								y : c.y,
								width : c.width,
								height : c.height
							};
							var K = J.createClipRect({
								x : c.x,
								y : c.y,
								width : c.width,
								height : c.height
							});
							var M = J.beginGroup();
							J.setClip(M, K);
							for ( var Y = 0; Y < v.length; Y++) {
								var ac = v[Y];
								var d = false;
								for ( var ae in n._seriesTypes) {
									if (n._seriesTypes[ae] == ac.type) {
										d = true;
										break
									}
								}
								if (!d) {
									throw 'jqxChart: invalid series type "'
											+ ac.type + '"'
								}
								if (ac.polar == true || ac.spider == true) {
									if (ac.type.indexOf("pie") == -1
											&& ac.type.indexOf("donut") == -1) {
										n._renderSpiderAxis(Y, c)
									}
								}
								if (ac.bands) {
									for ( var W = 0; W < ac.bands.length; W++) {
										n._renderBand(Y, W, c)
									}
								}
								if (ac.type.indexOf("column") != -1) {
									n._renderColumnSeries(Y, c)
								} else {
									if (ac.type.indexOf("pie") != -1
											|| ac.type.indexOf("donut") != -1) {
										n._renderPieSeries(Y, c)
									} else {
										if (ac.type.indexOf("line") != -1
												|| ac.type.indexOf("area") != -1) {
											n._renderLineSeries(Y, c)
										} else {
											if (ac.type.indexOf("scatter") != -1
													|| ac.type
															.indexOf("bubble") != -1) {
												n._renderScatterSeries(Y, c)
											}
										}
									}
								}
							}
							J.endGroup();
							if (n.enabled == false) {
								var Z = J.rect(F.x, F.y, F.width, F.height);
								J.attr(Z, {
									fill : "#777777",
									opacity : 0.5,
									stroke : "#00FFFFFF"
								})
							}
							if (a.isFunction(n.drawEnd)) {
								n.drawEnd(J, F)
							}
							J.endGroup();
							n._startAnimation("series");
							if (this._renderCategoryAxisRangeSelector) {
								var f = [];
								for ( var Y = 0; Y < n.seriesGroups.length; Y++) {
									var B = this._getCategoryAxis(Y);
									if (f.indexOf(B) == -1) {
										this._renderCategoryAxisRangeSelector(
												Y, t[Y]);
										f.push(B)
									}
								}
							}
						},
						_isPieOnlySeries : function() {
							var d = this.seriesGroups;
							if (d.length == 0) {
								return false
							}
							for ( var c = 0; c < d.length; c++) {
								if (d[c].type != "pie" && d[c].type != "donut") {
									return false
								}
							}
							return true
						},
						_renderChartLegend : function(T, D, R, v) {
							var l = this;
							var E = l.renderer;
							var J = {
								x : D.x + 3,
								y : D.y + 3,
								width : D.width - 6,
								height : D.height - 6
							};
							var F = {
								width : J.width,
								height : 0
							};
							var H = 0, G = 0;
							var q = 20;
							var m = 0;
							var h = 10;
							var P = 10;
							var w = 0;
							for ( var O = 0; O < T.length; O++) {
								var K = T[O].css;
								if (!K) {
									K = l.toThemeProperty(
											"jqx-chart-legend-text", null)
								}
								q = 20;
								var B = T[O].text;
								var k = E.measureText(B, 0, {
									"class" : K
								});
								if (k.height > q) {
									q = k.height
								}
								if (k.width > w) {
									w = k.width
								}
								if (v) {
									if (O != 0) {
										G += q
									}
									if (G > J.height) {
										G = 0;
										H += w + P;
										w = k.width;
										F.width = H + w
									}
								} else {
									if (H != 0) {
										H += P
									}
									if (H + 2 * h + k.width > J.width
											&& k.width < J.width) {
										H = 0;
										G += q;
										q = 20;
										m = J.width;
										F.height = G + q
									}
								}
								var L = false;
								if (k.width > D.width) {
									L = true;
									var s = D.width;
									var S = B;
									var V = S.split(/\s+/).reverse();
									var n = [];
									var u = "";
									var p = new Array();
									while (word = V.pop()) {
										n.push(word);
										u = n.join(" ");
										var C = l.renderer.measureText(u, 0, {
											"class" : K
										});
										if (C.width > s && p.length > 0) {
											n.pop();
											n = [ word ];
											u = n.join(" ")
										}
										p.push({
											text : u
										})
									}
									k.width = 0;
									var c = 0;
									for ( var I = 0; I < p.length; I++) {
										var U = p[I].text;
										var C = l.renderer.measureText(U, 0, {
											"class" : K
										});
										k.width = Math.max(k.width, C.width);
										c += k.height
									}
									k.height = c
								}
								var z = J.x + H + k.width < D.x + D.width
										&& J.y + G + k.height < D.y + D.height;
								if (l.legendLayout) {
									var z = J.x + H + k.width < l._paddedRect.x
											+ l._paddedRect.width
											&& J.y + G + k.height < l._paddedRect.y
													+ l._paddedRect.height
													+ l.padding.bottom
													+ l.padding.top
								}
								if (!R && z) {
									var j = T[O].seriesIndex;
									var o = T[O].groupIndex;
									var d = T[O].itemIndex;
									var A = T[O].color;
									var f = l._isSerieVisible(o, j, d);
									var Q = E.beginGroup();
									var N = f ? T[O].opacity : 0.1;
									if (L) {
										var S = B;
										var s = D.width;
										var V = S.split(/\s+/).reverse();
										var n = [];
										var u = "";
										var e = 0;
										var p = new Array();
										while (word = V.pop()) {
											n.push(word);
											u = n.join(" ");
											var C = l.renderer.measureText(u,
													0, {
														"class" : K
													});
											if (C.width > s && p.length > 0) {
												n.pop();
												e += C.height;
												n = [ word ];
												u = n.join(" ")
											}
											p.push({
												text : u,
												dy : e
											})
										}
										for ( var I = 0; I < p.length; I++) {
											var U = p[I].text;
											e = p[I].dy;
											var C = l.renderer.measureText(U,
													0, {
														"class" : K
													});
											if (v) {
												l.renderer.text(U, J.x + H
														+ 1.5 * h, J.y + G + e,
														k.width, q, 0, {
															"class" : K
														}, false, "left",
														"center")
											} else {
												l.renderer.text(U, J.x + H
														+ 1.5 * h, J.y + G + e,
														k.width, q, 0, {
															"class" : K
														}, false, "center",
														"center")
											}
										}
										var M = E.rect(J.x + H, J.y + G + h / 2
												+ e / 2, h, h);
										if (v) {
											G += e
										}
										l.renderer.attr(M, {
											fill : A,
											"fill-opacity" : N,
											stroke : A,
											"stroke-width" : 1,
											"stroke-opacity" : T[O].opacity
										})
									} else {
										var M = E.rect(J.x + H,
												J.y + G + h / 2, h, h);
										l.renderer.attr(M, {
											fill : A,
											"fill-opacity" : N,
											stroke : A,
											"stroke-width" : 1,
											"stroke-opacity" : T[O].opacity
										});
										if (v) {
											l.renderer.text(B, J.x + H + 1.5
													* h, J.y + G, k.width,
													k.height + h / 2, 0, {
														"class" : K
													}, false, "left", "center")
										} else {
											l.renderer.text(B, J.x + H + 1.5
													* h, J.y + G, k.width, q,
													0, {
														"class" : K
													}, false, "center",
													"center")
										}
									}
									l.renderer.endGroup();
									l._setLegendToggleHandler(o, j, d, Q)
								}
								if (v) {
								} else {
									H += k.width + 2 * h;
									if (m < H) {
										m = H
									}
								}
							}
							if (R) {
								F.height = a.jqx._ptrnd(G + q + 5);
								F.width = a.jqx._ptrnd(m);
								return F
							}
						},
						_isSerieVisible : function(h, c, e) {
							while (this._itemsToggleState.length < h + 1) {
								this._itemsToggleState.push([])
							}
							var f = this._itemsToggleState[h];
							while (f.length < c + 1) {
								f.push(isNaN(e) ? true : [])
							}
							var d = f[c];
							if (isNaN(e)) {
								return d
							}
							if (!a.isArray(d)) {
								f[c] = d = []
							}
							while (d.length < e + 1) {
								d.push(true)
							}
							return d[e]
						},
						_isGroupVisible : function(f) {
							var e = false;
							var d = this.seriesGroups[f].series;
							if (!d) {
								return e
							}
							for ( var c = 0; c < d.length; c++) {
								if (this._isSerieVisible(f, c)) {
									e = true;
									break
								}
							}
							return e
						},
						_toggleSerie : function(j, c, f, d) {
							var i = !this._isSerieVisible(j, c, f);
							if (d != undefined) {
								i = d
							}
							var k = this.seriesGroups[j];
							var h = k.series[c];
							this._raiseEvent("toggle", {
								state : i,
								seriesGroup : k,
								serie : h,
								elementIndex : f
							});
							if (isNaN(f)) {
								this._itemsToggleState[j][c] = i
							} else {
								var e = this._itemsToggleState[j][c];
								if (!a.isArray(e)) {
									e = []
								}
								while (e.length < f) {
									e.push(true)
								}
								e[f] = i
							}
							this._isToggleRefresh = true;
							this.update();
							this._isToggleRefresh = false
						},
						showSerie : function(e, c, d) {
							this._toggleSerie(e, c, d, true)
						},
						hideSerie : function(e, c, d) {
							this._toggleSerie(e, c, d, false)
						},
						_setLegendToggleHandler : function(k, d, i, f) {
							var j = this.seriesGroups[k];
							var h = j.series[d];
							var c = h.enableSeriesToggle;
							if (c == undefined) {
								c = j.enableSeriesToggle != false
							}
							if (c) {
								var e = this;
								this.renderer.addHandler(f, "click",
										function(l) {
											l.preventDefault();
											e._toggleSerie(k, d, i)
										})
							}
						},
						_renderLegend : function(p, o) {
							var v = this;
							var e = [];
							for ( var u = 0; u < v.seriesGroups.length; u++) {
								var m = v.seriesGroups[u];
								if (m.showLegend == false) {
									continue
								}
								for ( var q = 0; q < m.series.length; q++) {
									var w = m.series[q];
									if (w.showLegend == false) {
										continue
									}
									var h = v._getSerieSettings(u, q);
									if (m.type == "pie" || m.type == "donut") {
										var k = v._getCategoryAxis(u);
										var l = k.formatSettings
												|| m.formatSettings
												|| w.formatSettings;
										var d = k.formatFunction
												|| m.formatFunction
												|| w.formatFunction;
										var f = v._getDataLen(u);
										for ( var j = 0; j < f; j++) {
											var n = v._getDataValue(j,
													w.displayText, u);
											n = v
													._formatValue(n, l, d, u,
															q, j);
											var c = v._getColors(u, q, j);
											e.push({
												groupIndex : u,
												seriesIndex : q,
												itemIndex : j,
												text : n,
												css : w.displayTextClass,
												color : c.fillColor,
												opacity : h.opacity
											})
										}
										continue
									}
									var t = w.displayText || w.dataField || "";
									var c = v._getSeriesColors(u, q);
									e.push({
										groupIndex : u,
										seriesIndex : q,
										text : t,
										css : w.displayTextClass,
										color : c.fillColor,
										opacity : h.opacity
									})
								}
							}
							return v
									._renderChartLegend(
											e,
											p,
											o,
											(v.legendLayout && v.legendLayout.flow == "vertical"))
						},
						_renderCategoryAxis : function(f, E, V, e) {
							var k = this;
							var v = k._getCategoryAxis(f);
							var U = k.seriesGroups[f];
							var ac = U.orientation == "horizontal";
							var M = {
								width : 0,
								height : 0
							};
							if (!v || v.visible == false || U.type == "spider") {
								return M
							}
							if (!k._isGroupVisible(f)) {
								return M
							}
							var ab = k._alignValuesWithTicks(f);
							if (k.rtl) {
								v.flip = true
							}
							var H = ac ? E.height : E.width;
							var C = v.text;
							var z = k._calculateXOffsets(f, H);
							var Y = z.axisStats;
							var m = v.rangeSelector;
							var K = 0;
							if (m) {
								if (!this._selectorGetSize) {
									throw new Error(
											"jqxChart: Missing reference to jqxchart.rangeselector.js")
								}
								K = this._selectorGetSize(v)
							}
							var L = Y.interval;
							if (isNaN(L)) {
								return
							}
							var h = {
								visible : (v.showGridLines != false),
								color : (v.gridLinesColor || "#888888"),
								unitInterval : (v.gridLinesInterval || L),
								dashStyle : v.gridLinesDashStyle,
								offsets : []
							};
							var F = {
								visible : (v.showTickMarks != false),
								color : (v.tickMarksColor || "#888888"),
								unitInterval : (v.tickMarksInterval || L),
								dashStyle : v.tickMarksDashStyle,
								offsets : []
							};
							var t = v.textRotationAngle || 0;
							var P;
							var aa = Y.min;
							var w = Y.max;
							var S = z.padding;
							var X = v.flip == true || k.rtl;
							if (v.type == "date") {
								h.offsets = this._generateDTOffsets(aa, w, H,
										S, h.unitInterval, L, v.baseUnit, ab,
										NaN, false, X);
								F.offsets = this._generateDTOffsets(aa, w, H,
										S, F.unitInterval, L, v.baseUnit, ab,
										NaN, false, X);
								P = this._generateDTOffsets(aa, w, H, S, L, L,
										v.baseUnit, ab, NaN, true, X)
							} else {
								h.offsets = this._generateOffsets(aa, w, H, S,
										h.unitInterval, L, ab, NaN, false, X);
								F.offsets = this._generateOffsets(aa, w, H, S,
										F.unitInterval, L, ab, NaN, false, X);
								P = this._generateOffsets(aa, w, H, S, L, L,
										ab, NaN, true, X)
							}
							var d = v.horizontalTextAlignment;
							var p = k.renderer.getRect();
							var o = p.width - E.x - E.width;
							var s = k._getDataLen(f);
							var q;
							if (k._elementRenderInfo
									&& k._elementRenderInfo.length > f) {
								q = k._elementRenderInfo[f].categoryAxis
							}
							var u = [];
							var O = v.formatFunction;
							var B = v.formatSettings;
							if (v.type == "date" && !B && !O) {
								O = this._getDefaultDTFormatFn(v.baseUnit
										|| "day")
							}
							for ( var T = 0; T < P.length; T++) {
								var R = P[T].value;
								var N = P[T].offset;
								if (v.type != "date" && Y.useIndeces
										&& v.dataField) {
									var Z = Math.round(R);
									R = k._getDataValue(Z, v.dataField);
									if (R == undefined) {
										R = ""
									}
								}
								var C = k
										._formatValue(R, B, O, f, undefined, T);
								if (C == undefined || C == "") {
									C = Y.useIndeces ? (Y.min + T).toString()
											: (R == undefined ? "" : R
													.toString())
								}
								var c = {
									key : R,
									text : C,
									targetX : N,
									x : N
								};
								if (q && q.itemOffsets[R]) {
									c.x = q.itemOffsets[R].x;
									c.y = q.itemOffsets[R].y
								}
								u.push(c)
							}
							var Q = v.descriptionClass;
							if (!Q) {
								Q = k.toThemeProperty(
										"jqx-chart-axis-description", null)
							}
							var D = v["class"];
							if (!D) {
								D = k.toThemeProperty("jqx-chart-axis-text",
										null)
							}
							if (ac) {
								t -= 90
							}
							var W = {
								text : v.description,
								style : Q,
								halign : v.horizontalDescriptionAlignment
										|| "center",
								valign : v.verticalDescriptionAlignment
										|| "center",
								textRotationAngle : ac ? -90 : 0
							};
							var l = {
								textRotationAngle : t,
								style : D,
								halign : d,
								valign : v.verticalTextAlignment || "center",
								textRotationPoint : v.textRotationPoint
										|| "auto",
								textOffset : v.textOffset
							};
							var J = (ac && v.position == "right")
									|| (!ac && v.position == "top");
							var n = {
								rangeLength : z.rangeLength,
								itemWidth : z.itemWidth,
								intervalWidth : z.intervalWidth,
								data : z,
								rect : E
							};
							var G = {
								items : u,
								renderData : n
							};
							while (k._renderData.length < f + 1) {
								k._renderData.push({})
							}
							k._renderData[f].categoryAxis = n;
							var I = k._getAnimProps(f);
							var A = I.enabled && u.length < 500 ? I.duration
									: 0;
							if (k.enableAxisTextAnimation == false) {
								A = 0
							}
							if (!V && m) {
								if (ac) {
									E.width -= K;
									if (v.position != "right") {
										E.x += K
									}
								} else {
									E.height -= K;
									if (v.position == "top") {
										E.y += K
									}
								}
							}
							var j = k._renderAxis(ac, J, W, l, {
								x : E.x,
								y : E.y,
								width : E.width,
								height : E.height
							}, e, L, false, true, G, h, F, V, A);
							if (ac) {
								j.width += K
							} else {
								j.height += K
							}
							return j
						},
						_animateAxisText : function(h, k) {
							var d = h.items;
							var e = h.textSettings;
							for ( var f = 0; f < d.length; f++) {
								var j = d[f];
								if (!j.visible) {
									continue
								}
								var c = j.targetX;
								var l = j.targetY;
								if (!isNaN(j.x) && !isNaN(j.y)) {
									c = j.x + (c - j.x) * k;
									l = j.y + (l - j.y) * k
								}
								if (j.element) {
									this.renderer.removeElement(j.element);
									j.element = undefined
								}
								j.element = this.renderer.text(j.text, c, l,
										j.width, j.height, e.textRotationAngle,
										{
											"class" : e.style
										}, false, e.halign, e.valign,
										e.textRotationPoint)
							}
						},
						_getPolarAxisCoords : function(l, j) {
							var k = this.seriesGroups[l];
							var d = this._calcGroupOffsets(l, j).xoffsets;
							if (!d) {
								return
							}
							var f = j.x
									+ a.jqx.getNum([ k.offsetX, j.width / 2 ]);
							var e = j.y
									+ a.jqx.getNum([ k.offsetY, j.height / 2 ]);
							var h = k.radius;
							if (isNaN(h)) {
								h = Math.min(j.width, j.height) / 2 * 0.6
							}
							var c = this._alignValuesWithTicks(l);
							var i = k.startAngle;
							if (isNaN(i)) {
								i = 0
							} else {
								i = (i < 0 ? -1 : 1) * (Math.abs(i) % 360);
								i = 2 * Math.PI * i / 360
							}
							return {
								x : f,
								y : e,
								r : h,
								itemWidth : d.itemWidth,
								rangeLength : d.rangeLength,
								valuesOnTicks : c,
								startAngle : i
							}
						},
						_toPolarCoord : function(e, i, d, k) {
							var j = ((d - i.x) * 2 * Math.PI)
									/ Math.max(1, i.width) + e.startAngle;
							var c = ((i.height + i.y) - k) * e.r
									/ Math.max(1, i.height);
							var h = e.x + c * Math.cos(j);
							var f = e.y + c * Math.sin(j);
							return {
								x : a.jqx._ptrnd(h),
								y : a.jqx._ptrnd(f)
							}
						},
						_renderSpiderAxis : function(f, L) {
							var n = this;
							var G = n._getCategoryAxis(f);
							if (!G || G.visible == false) {
								return
							}
							var B = n.seriesGroups[f];
							var M = n._getPolarAxisCoords(f, L);
							if (!M) {
								return
							}
							var W = a.jqx._ptrnd(M.x);
							var T = a.jqx._ptrnd(M.y);
							var aa = M.r;
							var m = M.startAngle;
							if (aa < 1) {
								return
							}
							aa = a.jqx._ptrnd(aa);
							var P = Math.PI * 2 * aa;
							var I = n._calculateXOffsets(f, P);
							if (!I.rangeLength) {
								return
							}
							var S = G.unitInterval;
							if (isNaN(S) || S < 1) {
								S = 1
							}
							var h = {
								visible : (G.showGridLines != false),
								color : (G.gridLinesColor || "#888888"),
								unitInterval : (G.gridLinesInterval
										|| G.unitInterval || S),
								dashStyle : G.gridLinesDashStyle,
								offsets : []
							};
							var N = {
								visible : (G.showTickMarks != false),
								color : (G.tickMarksColor || "#888888"),
								unitInterval : (G.tickMarksInterval
										|| G.unitInterval || S),
								dashStyle : G.tickMarksDashStyle,
								offsets : []
							};
							var e = G.horizontalTextAlignment;
							var al = n._alignValuesWithTicks(f);
							var R = n.renderer;
							var X;
							var ai = I.axisStats;
							var ak = ai.min;
							var H = ai.max;
							var ac = this._getPaddingSize(I.axisStats, G, al,
									P, true, false);
							var ag = G.flip == true || n.rtl;
							if (G.type == "date") {
								h.offsets = this._generateDTOffsets(ak, H, P,
										ac, h.unitInterval, S, G.baseUnit,
										false, 0, false, ag);
								N.offsets = this._generateDTOffsets(ak, H, P,
										ac, N.unitInterval, S, G.baseUnit,
										false, 0, false, ag);
								X = this._generateDTOffsets(ak, H, P, ac, S, S,
										G.baseUnit, false, 0, true, ag)
							} else {
								h.offsets = this._generateOffsets(ak, H, P, ac,
										h.unitInterval, S, true, 0, false, ag);
								N.offsets = this._generateOffsets(ak, H, P, ac,
										N.unitInterval, S, true, 0, false, ag);
								X = this._generateOffsets(ak, H, P, ac, S, S,
										true, 0, false, ag)
							}
							var e = G.horizontalTextAlignment;
							var u = n.renderer.getRect();
							var t = u.width - L.x - L.width;
							var A = n._getDataLen(f);
							var w;
							if (n._elementRenderInfo
									&& n._elementRenderInfo.length > f) {
								w = n._elementRenderInfo[f].categoryAxis
							}
							var D = [];
							for ( var ad = 0; ad < X.length; ad++) {
								var U = X[ad].offset;
								var Z = X[ad].value;
								if (G.type != "date" && ai.useIndeces
										&& G.dataField) {
									var aj = Math.round(Z);
									Z = n._getDataValue(aj, G.dataField);
									if (Z == undefined) {
										Z = ""
									}
								}
								var J = n._formatValue(Z, G.formatSettings,
										G.formatFunction, f, undefined, ad);
								if (J == undefined || J == "") {
									J = ai.useIndeces ? (ai.min + ad)
											.toString() : (Z == undefined ? ""
											: Z.toString())
								}
								var d = {
									key : Z,
									text : J,
									targetX : U,
									x : U
								};
								if (w && w.itemOffsets[Z]) {
									d.x = w.itemOffsets[Z].x;
									d.y = w.itemOffsets[Z].y
								}
								D.push(d)
							}
							var Y = G.descriptionClass;
							if (!Y) {
								Y = n.toThemeProperty(
										"jqx-chart-axis-description", null)
							}
							var K = G["class"];
							if (!K) {
								K = n.toThemeProperty("jqx-chart-axis-text",
										null)
							}
							var J = G.text;
							var C = G.textRotationAngle || 0;
							var am = n.seriesGroups[f].orientation == "horizontal";
							if (am) {
								C -= 90
							}
							var af = {
								text : G.description,
								style : Y,
								halign : G.horizontalDescriptionAlignment
										|| "center",
								valign : G.verticalDescriptionAlignment
										|| "center",
								textRotationAngle : am ? -90 : 0
							};
							var p = {
								textRotationAngle : C,
								style : K,
								halign : e,
								valign : G.verticalTextAlignment || "center",
								textRotationPoint : G.textRotationPoint
										|| "auto",
								textOffset : G.textOffset
							};
							var Q = (am && G.position == "right")
									|| (!am && G.position == "top");
							var s = {
								rangeLength : I.rangeLength,
								itemWidth : I.itemWidth
							};
							var O = {
								items : D,
								renderData : s
							};
							while (n._renderData.length < f + 1) {
								n._renderData.push({})
							}
							n._renderData[f].categoryAxis = s;
							var q = {
								stroke : h.color,
								fill : "none",
								"stroke-width" : 1,
								"stroke-dasharray" : h.dashStyle || ""
							};
							var ab = R.circle(W, T, aa, q);
							var E = D.length;
							var o = 2 * Math.PI / (E);
							var c = m;
							for ( var ad = 0; ad < D.length; ad++) {
								var V = D[ad].x;
								var z = c + (V * 2 * Math.PI) / Math.max(1, P);
								z = (360 - z / (2 * Math.PI) * 360) % 360;
								if (z < 0) {
									z = 360 + z
								}
								var l = R.measureText(D[ad].text, 0, {
									"class" : K
								});
								var F = this._adjustTextBoxPosition(W, T, l, aa
										+ (N.visible ? 7 : 2), z, false, false);
								R.text(D[ad].text, F.x, F.y, l.width, l.height,
										0, {
											"class" : K
										}, false, "center", "center")
							}
							if (h.visible) {
								for ( var ad = 0; ad < h.offsets.length; ad++) {
									var V = h.offsets[ad].offset;
									if (!al) {
										V -= ac.right / 2
									}
									var z = c + (V * 2 * Math.PI)
											/ Math.max(1, P);
									var k = W + aa * Math.cos(z);
									var j = T + aa * Math.sin(z);
									R.line(W, T, a.jqx._ptrnd(k), a.jqx
											._ptrnd(j), q)
								}
							}
							if (N.visible) {
								var v = 5;
								var q = {
									stroke : N.color,
									fill : "none",
									"stroke-width" : 1,
									"stroke-dasharray" : N.dashStyle || ""
								};
								for ( var ad = 0; ad < N.offsets.length; ad++) {
									var V = N.offsets[ad].offset;
									if (!al) {
										V -= ac.right / 2
									}
									var z = c + (V * 2 * Math.PI)
											/ Math.max(1, P);
									var ah = {
										x : W + aa * Math.cos(z),
										y : T + aa * Math.sin(z)
									};
									var ae = {
										x : W + (aa + v) * Math.cos(z),
										y : T + (aa + v) * Math.sin(z)
									};
									R.line(a.jqx._ptrnd(ah.x), a.jqx
											._ptrnd(ah.y), a.jqx._ptrnd(ae.x),
											a.jqx._ptrnd(ae.y), q)
								}
							}
							n._renderSpiderValueAxis(f, L)
						},
						_renderSpiderValueAxis : function(f, d) {
							var l = this.seriesGroups[f];
							var w = this._getPolarAxisCoords(f, d);
							if (!w) {
								return
							}
							var J = a.jqx._ptrnd(w.x);
							var I = a.jqx._ptrnd(w.y);
							var j = w.r;
							var C = w.startAngle;
							if (j < 1) {
								return
							}
							j = a.jqx._ptrnd(j);
							var G = this.seriesGroups[f].valueAxis;
							if (!G || false == G.displayValueAxis
									|| false == G.visible) {
								return
							}
							var p = G["class"];
							if (!p) {
								p = this.toThemeProperty("jqx-chart-axis-text",
										null)
							}
							var o = G.formatSettings;
							var e = l.type.indexOf("stacked") != -1
									&& l.type.indexOf("100") != -1;
							if (e && !o) {
								o = {
									sufix : "%"
								}
							}
							this._calcValueAxisItems(f, j);
							var k = this._stats.seriesGroups[f].mu;
							var h = {
								visible : (G.showGridLines != false),
								color : (G.gridLinesColor || "#888888"),
								unitInterval : (G.gridLinesInterval || k || 1),
								dashStyle : G.gridLinesDashStyle
							};
							var c = {
								stroke : h.color,
								fill : "none",
								"stroke-width" : 1,
								"stroke-dasharray" : h.dashStyle || ""
							};
							var s = this._renderData[f].valueAxis;
							var v = s.items;
							if (v.length) {
								this.renderer.line(J, I, J,
										a.jqx._ptrnd(I - j), c)
							}
							v = v.reverse();
							var A = this.renderer;
							for ( var D = 0; D < v.length - 1; D++) {
								var z = v[D];
								var q = (G.formatFunction) ? G
										.formatFunction(z) : this
										._formatNumber(z, o);
								var t = A.measureText(q, 0, {
									"class" : p
								});
								var n = J + (G.showTickMarks != false ? 3 : 2);
								var m = I - s.itemWidth * D - t.height;
								A.text(q, n, m, t.width, t.height, 0, {
									"class" : p
								}, false, "center", "center")
							}
							var u = G.logarithmicScale == true;
							var F = u ? v.length : s.rangeLength;
							aIncrement = 2 * Math.PI / F;
							if (h.visible) {
								var c = {
									stroke : h.color,
									fill : "none",
									"stroke-width" : 1,
									"stroke-dasharray" : h.dashStyle || ""
								};
								for ( var D = 0; D < F; D += h.unitInterval) {
									var m = a.jqx._ptrnd(j * D / F);
									A.circle(J, I, m, c)
								}
							}
							var B = {
								visible : (G.showTickMarks != false),
								color : (G.tickMarksColor || "#888888"),
								unitInterval : (G.tickMarksInterval || k),
								dashStyle : G.tickMarksDashStyle
							};
							if (B.visible) {
								tickMarkSize = 5;
								var c = {
									stroke : B.color,
									fill : "none",
									"stroke-width" : 1,
									"stroke-dasharray" : B.dashStyle || ""
								};
								var H = J - Math.round(tickMarkSize / 2);
								var E = H + tickMarkSize;
								for ( var D = 0; D < F; D += B.unitInterval) {
									if (h.visible && (D % h.unitInterval) == 0) {
										continue
									}
									var m = a.jqx._ptrnd(I - j * D / F);
									A.line(a.jqx._ptrnd(H), m, a.jqx._ptrnd(E),
											m, c)
								}
							}
						},
						_renderAxis : function(O, K, Z, t, G, d, M, s, aa, J,
								f, H, Y, e) {
							var u = H.visible ? 4 : 0;
							var V = 2;
							var N = {
								width : 0,
								height : 0
							};
							var z = {
								width : 0,
								height : 0
							};
							if (O) {
								N.height = z.height = G.height
							} else {
								N.width = z.width = G.width
							}
							if (!Y && K) {
								if (O) {
									G.x -= G.width
								}
							}
							var q = J.renderData;
							var c = q.itemWidth;
							if (Z.text != undefined && Z != "") {
								var A = Z.textRotationAngle;
								var k = this.renderer.measureText(Z.text, A, {
									"class" : Z.style
								});
								z.width = k.width;
								z.height = k.height;
								if (!Y) {
									this.renderer.text(Z.text, G.x
											+ (O ? (!K ? V : -V + 2 * G.width
													- z.width) : 0), G.y
											+ (!O ? (!K ? G.height - V
													- z.height : V) : 0),
											O ? z.width : G.width,
											!O ? z.height : G.height, A, {
												"class" : Z.style
											}, true, Z.halign, Z.valign)
								}
							}
							var S = 0;
							var E = aa ? -c / 2 : 0;
							if (aa && !O) {
								t.halign = "center"
							}
							var U = G.x;
							var T = G.y;
							var L = t.textOffset;
							if (L) {
								if (!isNaN(L.x)) {
									U += L.x
								}
								if (!isNaN(L.y)) {
									T += L.y
								}
							}
							if (!O) {
								U += E;
								if (K) {
									T += z.height > 0 ? z.height + 3 * V
											: 2 * V;
									T += u - (aa ? u : u / 4)
								} else {
									T += aa ? u : u / 4
								}
							} else {
								U += V + (z.width > 0 ? z.width + V : 0)
										+ (K ? G.width - z.width : 0);
								T += E
							}
							var X = 0;
							var R = 0;
							var B = J.items;
							q.itemOffsets = {};
							if (this._isToggleRefresh || !this._isUpdate) {
								e = 0
							}
							var p = false;
							var m = 0;
							for ( var W = 0; W < B.length; W++, S += c) {
								var F = B[W].text;
								if (!isNaN(B[W].targetX)) {
									S = B[W].targetX
								}
								var k = this.renderer.measureText(F,
										t.textRotationAngle, {
											"class" : t.style
										});
								if (k.width > R) {
									R = k.width
								}
								if (k.height > X) {
									X = k.height
								}
								m += O ? X : R;
								if (!Y) {
									if ((O && S > G.height + 2)
											|| (!O && S > G.width + 2)) {
										break
									}
									var Q = O ? U
											+ (K ? (z.width == 0 ? u : u - V)
													: 0) : U + S;
									var P = O ? T + S : T;
									q.itemOffsets[B[W].key] = {
										x : Q,
										y : P
									};
									if (!p) {
										if (!isNaN(B[W].x) || !isNaN(B[W].y)
												&& e) {
											p = true
										}
									}
									B[W].targetX = Q;
									B[W].targetY = P;
									B[W].width = !O ? c : G.width - 2 * V - u
											- ((z.width > 0) ? z.width + V : 0);
									B[W].height = O ? c : G.height
											- 2
											* V
											- u
											- ((z.height > 0) ? z.height + V
													: 0);
									B[W].visible = !s || (s && (W % M) == 0)
								}
							}
							q.avgWidth = B.length == 0 ? 0 : m / B.length;
							if (!Y) {
								var C = {
									items : B,
									textSettings : t
								};
								if (isNaN(e) || !p) {
									e = 0
								}
								this._animateAxisText(C, e == 0 ? 1 : 0);
								if (e != 0) {
									var l = this;
									this._enqueueAnimation("series", undefined,
											undefined, e, function(i, h, w) {
												l._animateAxisText(h, w)
											}, C)
								}
							}
							N.width += 2 * V + u + z.width + R
									+ (O && z.width > 0 ? V : 0);
							N.height += 2 * V + u + z.height + X
									+ (!O && z.height > 0 ? V : 0);
							var I = {};
							var o = {
								stroke : f.color,
								"stroke-width" : 1,
								"stroke-dasharray" : f.dashStyle || ""
							};
							if (!Y) {
								var P = a.jqx._ptrnd(G.y + (K ? G.height : 0));
								if (O) {
									this.renderer.line(a.jqx._ptrnd(G.x
											+ G.width), G.y, a.jqx._ptrnd(G.x
											+ G.width), G.y + G.height, o)
								} else {
									this.renderer.line(a.jqx._ptrnd(G.x), P,
											a.jqx._ptrnd(G.x + G.width + 1), P,
											o)
								}
							}
							var v = 0.5;
							if (!Y && f.visible != false) {
								var D = f.offsets;
								for ( var W = 0; W < D.length; W++) {
									if (O) {
										n = a.jqx._ptrnd(G.y + D[W].offset);
										if (n < G.y - v) {
											break
										}
									} else {
										n = a.jqx._ptrnd(G.x + D[W].offset);
										if (n > G.x + G.width + v) {
											break
										}
									}
									if (O) {
										this.renderer.line(a.jqx._ptrnd(d.x),
												n, a.jqx._ptrnd(d.x + d.width),
												n, o)
									} else {
										this.renderer
												.line(n, a.jqx._ptrnd(d.y), n,
														a.jqx._ptrnd(d.y
																+ d.height), o)
									}
									I[n] = true
								}
							}
							var o = {
								stroke : H.color,
								"stroke-width" : 1,
								"stroke-dasharray" : H.dashStyle || ""
							};
							if (!Y && H.visible) {
								var D = H.offsets;
								for ( var W = 0; W < D.length; W++) {
									var n = a.jqx._ptrnd((O ? G.y + D[W].offset
											: G.x + D[W].offset));
									if (I[n - 1]) {
										n--
									} else {
										if (I[n + 1]) {
											n++
										}
									}
									if (O) {
										if (n > G.y + G.height + v) {
											break
										}
									} else {
										if (n > G.x + G.width + v) {
											break
										}
									}
									var j = !K ? -u : u;
									if (O) {
										this.renderer.line(G.x + G.width, n,
												G.x + G.width + j, n, o)
									} else {
										var P = a.jqx._ptrnd(G.y
												+ (K ? G.height : 0));
										this.renderer.line(n, P, n, P - j, o)
									}
								}
							}
							N.width = a.jqx._rup(N.width);
							N.height = a.jqx._rup(N.height);
							return N
						},
						_calcValueAxisItems : function(k, e) {
							var n = this._stats.seriesGroups[k];
							if (!n || !n.isValid) {
								return false
							}
							var z = this.seriesGroups[k];
							var c = z.orientation == "horizontal";
							var h = z.valueAxis;
							var m = h.valuesOnTicks != false;
							var f = h.dataField;
							var o = n.intervals;
							var t = e / o;
							var v = n.min;
							var s = n.mu;
							var d = h.logarithmicScale == true;
							var l = h.logarithmicScaleBase || 10;
							var j = z.type.indexOf("stacked") != -1
									&& z.type.indexOf("100") != -1;
							if (d) {
								s = !isNaN(h.unitInterval) ? h.unitInterval : 1
							}
							if (!m) {
								o = Math.max(o - 1, 1)
							}
							while (this._renderData.length < k + 1) {
								this._renderData.push({})
							}
							this._renderData[k].valueAxis = {};
							var p = this._renderData[k].valueAxis;
							p.itemWidth = p.intervalWidth = t;
							p.items = [];
							var q = p.items;
							for ( var w = 0; w <= o; w++) {
								var u = 0;
								if (d) {
									if (j) {
										u = n.max / Math.pow(l, o - w)
									} else {
										u = v * Math.pow(l, w)
									}
								} else {
									u = m ? v + w * s : v + (w + 0.5) * s
								}
								q.push(u)
							}
							p.rangeLength = d && !j ? n.intervals
									: (n.intervals) * s;
							if (z.valueAxis.flip != true) {
								q = q.reverse()
							}
							return true
						},
						_renderValueAxis : function(f, w, N, e) {
							var M = this.seriesGroups[f];
							var S = M.orientation == "horizontal";
							var p = M.valueAxis;
							if (!p) {
								throw "SeriesGroup " + f
										+ " is missing valueAxis definition"
							}
							var G = {
								width : 0,
								height : 0
							};
							if (!this._isGroupVisible(f)
									|| this._isPieOnlySeries()
									|| M.type == "spider") {
								return G
							}
							if (!this._calcValueAxisItems(f, (S ? w.width
									: w.height))
									|| false == p.displayValueAxis
									|| false == p.visible) {
								return G
							}
							var K = p.descriptionClass;
							if (!K) {
								K = this.toThemeProperty(
										"jqx-chart-axis-description", null)
							}
							var O = {
								text : p.description,
								style : K,
								halign : p.horizontalDescriptionAlignment
										|| "center",
								valign : p.verticalDescriptionAlignment
										|| "center",
								textRotationAngle : S ? 0 : (!this.rtl ? -90
										: 90)
							};
							var u = p.itemsClass;
							if (!u) {
								u = this.toThemeProperty("jqx-chart-axis-text",
										null)
							}
							var k = {
								style : u,
								halign : p.horizontalTextAlignment || "center",
								valign : p.verticalTextAlignment || "center",
								textRotationAngle : p.textRotationAngle || 0,
								textRotationPoint : p.textRotationPoint
										|| "auto",
								textOffset : p.textOffset
							};
							var R = p.valuesOnTicks != false;
							var H = this._stats.seriesGroups[f];
							var j = H.mu;
							var v = p.formatSettings;
							var d = M.type.indexOf("stacked") != -1
									&& M.type.indexOf("100") != -1;
							if (d && !v) {
								v = {
									sufix : "%"
								}
							}
							var F = p.logarithmicScale == true;
							var C = p.logarithmicScaleBase || 10;
							if (F) {
								j = !isNaN(p.unitInterval) ? p.unitInterval : 1
							}
							var o = [];
							var l = this._renderData[f].valueAxis;
							var n;
							if (this._elementRenderInfo
									&& this._elementRenderInfo.length > f) {
								n = this._elementRenderInfo[f].valueAxis
							}
							for ( var L = 0; L < l.items.length; L++) {
								var J = l.items[L];
								var t = (p.formatFunction) ? p
										.formatFunction(J) : this
										._formatNumber(J, v);
								var c = {
									key : J,
									text : t
								};
								if (n && n.itemOffsets[J]) {
									c.x = n.itemOffsets[J].x;
									c.y = n.itemOffsets[J].y
								}
								o.push(c)
							}
							var m = p.gridLinesInterval || p.unitInterval;
							if (isNaN(m) || (F && m < j)) {
								m = j
							}
							var B = S ? w.width : w.height;
							var h = {
								visible : (p.showGridLines != false),
								color : (p.gridLinesColor || "#888888"),
								unitInterval : m,
								dashStyle : p.gridLinesDashStyle
							};
							var Q = H.logarithmic ? H.minPow : H.min;
							var q = H.logarithmic ? H.maxPow : H.max;
							var P = false;
							if (h.visible) {
								h.offsets = this._generateOffsets(Q, q, B, {
									left : 0,
									right : 0
								}, h.unitInterval, j, true, 0, false, P)
							}
							var I = p.tickMarksInterval || p.unitInterval;
							if (isNaN(I) || (F && I < j)) {
								I = j
							}
							var z = {
								visible : (p.showTickMarks != false),
								color : (p.tickMarksColor || "#888888"),
								unitInterval : I,
								dashStyle : p.tickMarksDashStyle
							};
							if (z.visible) {
								z.offsets = this._generateOffsets(Q, q, B, {
									left : 0,
									right : 0
								}, z.unitInterval, j, true, 0, false, P)
							}
							var E = (S && p.position == "top")
									|| (!S && p.position == "right")
									|| (!S && this.rtl && p.position != "left");
							var A = {
								items : o,
								renderData : l
							};
							var D = this._getAnimProps(f);
							var s = D.enabled && o.length < 500 ? D.duration
									: 0;
							if (this.enableAxisTextAnimation == false) {
								s = 0
							}
							return this._renderAxis(!S, E, O, k, w, e, j, F, R,
									A, h, z, N, s)
						},
						_generateOffsets : function(o, q, v, n, w, e, c, t, u,
								j) {
							var h = [];
							var k = q - o;
							var m = v - n.left - n.right;
							if (k == 0) {
								if (u || c) {
									h.push({
										offset : n.left + m / 2,
										value : o
									})
								} else {
									h.push({
										offset : 0,
										value : o
									})
								}
								return h
							}
							var z = m / k;
							var d = z * e;
							var f = n.left;
							if (!c) {
								if (!u) {
									q += e
								}
							}
							for ( var s = o; s <= q; s += e, f += d) {
								h.push({
									offset : f,
									value : s
								})
							}
							if (!c && h.length > 1) {
								if (isNaN(t)) {
									t = u ? 0 : d / 2
								}
								for ( var s = 0; s < h.length; s++) {
									h[s].offset -= t;
									if (h[s].offset <= 2) {
										h[s].offset = 0
									}
									if (h[s].offset >= v - 2) {
										h[s].offset = v
									}
								}
							}
							if (w > e) {
								var p = [];
								var l = Math.round(w / e);
								for ( var s = 0; s < h.length; s++) {
									if ((s % l) == 0) {
										p.push({
											offset : h[s].offset,
											value : h[s].value
										})
									}
								}
								h = p
							}
							if (j) {
								for ( var s = 0; s < h.length; s++) {
									h[s].offset = v - h[s].offset
								}
							}
							return h
						},
						_generateDTOffsets : function(q, u, B, o, C, d, p, c,
								w, z, j) {
							if (!p) {
								p = "day"
							}
							var h = [];
							if (q > u) {
								return h
							}
							if (q == u) {
								if (z) {
									h.push({
										offset : c ? B / 2 : o.left,
										value : q
									})
								} else {
									if (c) {
										h.push({
											offset : B / 2,
											value : q
										})
									}
								}
								return h
							}
							var l = B - o.left - o.right;
							var A = q;
							var m = o.left;
							var f = m;
							d = Math.max(d, 1);
							var n = d;
							var e = Math.min(1, d);
							if (d > 1) {
								d = 1
							}
							while (a.jqx._ptrnd(f) <= a.jqx._ptrnd(o.left + l
									+ (c ? 0 : o.right))) {
								h.push({
									offset : f,
									value : A
								});
								var D = new Date(A.valueOf());
								if (p == "millisecond") {
									D.setMilliseconds(A.getMilliseconds() + d)
								} else {
									if (p == "second") {
										D.setSeconds(A.getSeconds() + d)
									} else {
										if (p == "minute") {
											D.setMinutes(A.getMinutes() + d)
										} else {
											if (p == "hour") {
												D.setHours(A.getHours() + d)
											} else {
												if (p == "day") {
													D.setDate(A.getDate() + d)
												} else {
													if (p == "month") {
														D.setMonth(A.getMonth()
																+ d)
													} else {
														if (p == "year") {
															D
																	.setFullYear(A
																			.getFullYear()
																			+ d)
														}
													}
												}
											}
										}
									}
								}
								A = D;
								f = m + (A.valueOf() - q.valueOf()) * e
										/ (u.valueOf() - q.valueOf()) * l
							}
							if (j) {
								for ( var t = 0; t < h.length; t++) {
									h[t].offset = B - h[t].offset
								}
							}
							if (n > 1) {
								var s = [];
								for ( var t = 0; t < h.length; t += n) {
									s.push({
										offset : h[t].offset,
										value : h[t].value
									})
								}
								h = s
							}
							if (!c && !z && h.length > 1) {
								var s = [];
								s.push({
									offset : 0,
									value : undefined
								});
								for ( var t = 1; t < h.length; t++) {
									s
											.push({
												offset : h[t - 1].offset
														+ (h[t].offset - h[t - 1].offset)
														/ 2,
												value : undefined
											})
								}
								var v = s.length;
								if (v > 1) {
									s
											.push({
												offset : s[v - 1].offset
														+ (s[v - 1].offset - s[v - 2].offset)
											})
								} else {
									s.push({
										offset : B,
										value : undefined
									})
								}
								h = s
							}
							if (C > d) {
								var s = [];
								var k = Math.round(C / n);
								for ( var t = 0; t < h.length; t++) {
									if ((t % k) == 0) {
										s.push({
											offset : h[t].offset,
											value : h[t].value
										})
									}
								}
								h = s
							}
							return h
						},
						_buildStats : function(H) {
							var Q = {
								seriesGroups : new Array()
							};
							this._stats = Q;
							for ( var o = 0; o < this.seriesGroups.length; o++) {
								var B = this.seriesGroups[o];
								Q.seriesGroups[o] = {};
								var w = Q.seriesGroups[o];
								w.isValid = true;
								var N = B.valueAxis != undefined;
								var I = (B.orientation == "horizontal") ? H.width
										: H.height;
								var K = false;
								var J = 10;
								if (N) {
									K = B.valueAxis.logarithmicScale == true;
									J = B.valueAxis.logarithmicScaleBase;
									if (isNaN(J)) {
										J = 10
									}
								}
								var E = -1 != B.type.indexOf("stacked");
								var e = E && -1 != B.type.indexOf("100");
								var G = -1 != B.type.indexOf("range");
								if (e) {
									w.psums = new Array();
									w.nsums = new Array()
								}
								var q = NaN, L = NaN;
								var d = NaN, f = NaN;
								var m = B.baselineValue;
								if (isNaN(m)) {
									m = K && !e ? 1 : 0
								}
								var A = this._getDataLen(o);
								var c = 0;
								var S = NaN;
								for ( var P = 0; P < A && w.isValid; P++) {
									var T = N ? B.valueAxis.minValue : Infinity;
									var D = N ? B.valueAxis.maxValue
											: -Infinity;
									var s = 0, u = 0;
									for ( var h = 0; h < B.series.length; h++) {
										if (!this._isSerieVisible(o, h)) {
											continue
										}
										var F = undefined, O = undefined, z = undefined;
										if (G) {
											var U = this._getDataValueAsNumber(
													P,
													B.series[h].dataFieldFrom,
													o);
											var C = this._getDataValueAsNumber(
													P, B.series[h].dataFieldTo,
													o);
											O = Math.max(U, C);
											z = Math.min(U, C)
										} else {
											F = this._getDataValueAsNumber(P,
													B.series[h].dataField, o);
											if (isNaN(F) || (K && F <= 0)) {
												continue
											}
											z = O = F
										}
										if ((isNaN(D) || O > D)
												&& ((!N || isNaN(B.valueAxis.maxValue)) ? true
														: O <= B.valueAxis.maxValue)) {
											D = Math.max(O, m)
										}
										if ((isNaN(T) || z < T)
												&& ((!N || isNaN(B.valueAxis.minValue)) ? true
														: z >= B.valueAxis.minValue)) {
											T = Math.min(z, m)
										}
										if (!isNaN(F)) {
											if (F > m) {
												s += F
											} else {
												if (F < m) {
													u += F
												}
											}
										}
									}
									if (K && e) {
										for ( var h = 0; h < B.series.length; h++) {
											if (!this._isSerieVisible(o, h)) {
												S = 0.01;
												continue
											}
											var F = this
													._getDataValueAsNumber(
															P,
															B.series[h].dataField,
															o);
											if (isNaN(F) || F <= 0) {
												S = 0.01;
												continue
											}
											var M = s == 0 ? 0 : F / s;
											if (isNaN(S) || M < S) {
												S = M
											}
										}
									}
									var l = s - u;
									if (c < l) {
										c = l
									}
									if (e) {
										w.psums[P] = s;
										w.nsums[P] = u
									}
									if (D > L || isNaN(L)) {
										L = D
									}
									if (T < q || isNaN(q)) {
										q = T
									}
									if (s > d || isNaN(d)) {
										d = s
									}
									if (u < f || isNaN(f)) {
										f = u
									}
								}
								if (e) {
									d = d == 0 ? 0 : Math.max(d, -f);
									f = f == 0 ? 0 : Math.min(f, -d)
								}
								var k = N ? B.valueAxis.unitInterval : 0;
								if (!k) {
									if (N) {
										k = this._calcInterval(E ? f : q, E ? d
												: L, Math.max(I / 80, 2))
									} else {
										k = E ? (d - f) / 10 : (L - q) / 10
									}
								}
								var v = NaN;
								var R = 0;
								var n = 0;
								if (K) {
									if (e) {
										v = 0;
										var M = 1;
										R = n = a.jqx.log(100, J);
										while (M > S) {
											M /= J;
											R--;
											v++
										}
										q = Math.pow(J, R)
									} else {
										if (E) {
											L = Math.max(L, d)
										}
										n = a.jqx
												._rnd(a.jqx.log(L, J), 1, true);
										L = Math.pow(J, n);
										R = a.jqx._rnd(a.jqx.log(q, J), 1,
												false);
										q = Math.pow(J, R)
									}
									k = J
								}
								if (q < f) {
									f = q
								}
								if (L > d) {
									d = L
								}
								var t = K ? q : a.jqx._rnd(E ? f : q, k, false);
								var j = K ? L : a.jqx._rnd(E ? d : L, k, true);
								if (e && j > 100) {
									j = 100
								}
								if (e && !K) {
									j = (j > 0) ? 100 : 0;
									t = (t < 0) ? -100 : 0;
									k = N ? B.valueAxis.unitInterval : 10;
									if (isNaN(k) || k <= 0 || k >= 100) {
										k = 10
									}
								}
								if (isNaN(j) || isNaN(t) || isNaN(k)) {
									continue
								}
								if (isNaN(v)) {
									v = parseInt(((j - t) / (k == 0 ? 1 : k))
											.toFixed())
								}
								if (K && !e) {
									v = n - R;
									c = Math.pow(J, v)
								}
								if (v < 1) {
									continue
								}
								w.min = t;
								w.max = j;
								w.logarithmic = K;
								w.logBase = J;
								w.base = m;
								w.minPow = R;
								w.maxPow = n;
								w.mu = k;
								w.maxRange = c;
								w.intervals = v
							}
						},
						_getDataLen : function(d) {
							var c = this.source;
							if (d != undefined && d != -1
									&& this.seriesGroups[d].source) {
								c = this.seriesGroups[d].source
							}
							if (c instanceof a.jqx.dataAdapter) {
								c = c.records
							}
							if (c) {
								return c.length
							}
							return 0
						},
						_getDataValue : function(c, f, e) {
							var d = this.source;
							if (e != undefined && e != -1) {
								d = this.seriesGroups[e].source || d
							}
							if (d instanceof a.jqx.dataAdapter) {
								d = d.records
							}
							if (!d || c < 0 || c > d.length - 1) {
								return undefined
							}
							if (a.isFunction(f)) {
								return f(c, d)
							}
							return (f && f != "") ? d[c][f] : d[c]
						},
						_getDataValueAsNumber : function(c, f, d) {
							var e = this._getDataValue(c, f, d);
							if (this._isDate(e)) {
								return e.valueOf()
							}
							if (typeof (e) != "number") {
								e = parseFloat(e)
							}
							if (typeof (e) != "number") {
								e = undefined
							}
							return e
						},
						_renderPieSeries : function(f, d) {
							var h = this._getDataLen(f);
							var j = this.seriesGroups[f];
							var o = this._calcGroupOffsets(f, d).offsets;
							for ( var t = 0; t < j.series.length; t++) {
								var m = j.series[t];
								var z = this._getSerieSettings(f, t);
								var k = m.colorScheme || j.colorScheme
										|| this.colorScheme;
								var v = this._getAnimProps(f, t);
								var c = v.enabled && h < 5000
										&& !this._isToggleRefresh
										&& this._isVML != true ? v.duration : 0;
								if (a.jqx.mobile.isMobileBrowser()
										&& (this.renderer instanceof a.jqx.HTML5Renderer)) {
									c = 0
								}
								var q = {
									rect : d,
									groupIndex : f,
									serieIndex : t,
									settings : z,
									items : []
								};
								for ( var w = 0; w < h; w++) {
									var p = o[t][w];
									if (!p.visible) {
										continue
									}
									var u = p.fromAngle;
									var e = p.toAngle;
									var A = this.renderer.pieslice(p.x, p.y,
											p.innerRadius, p.outerRadius, u,
											c == 0 ? e : u, p.centerOffset);
									var l = {
										element : A,
										displayValue : p.displayValue,
										itemIndex : w,
										visible : p.visible,
										x : p.x,
										y : p.y,
										innerRadius : p.innerRadius,
										outerRadius : p.outerRadius,
										fromAngle : u,
										toAngle : e,
										centerOffset : p.centerOffset
									};
									q.items.push(l)
								}
								this._animatePieSlices(q, 0);
								var n = this;
								this._enqueueAnimation("series", A, undefined,
										c, function(s, i, B) {
											n._animatePieSlices(i, B)
										}, q)
							}
						},
						_sliceSortFunction : function(d, c) {
							return d.fromAngle - c.fromAngle
						},
						_animatePieSlices : function(A, d) {
							var p;
							if (this._elementRenderInfo
									&& this._elementRenderInfo.length > A.groupIndex
									&& this._elementRenderInfo[A.groupIndex].series
									&& this._elementRenderInfo[A.groupIndex].series.length > A.serieIndex) {
								p = this._elementRenderInfo[A.groupIndex].series[A.serieIndex]
							}
							var l = 360 * d;
							var c = [];
							for ( var E = 0; E < A.items.length; E++) {
								var J = A.items[E];
								if (!J.visible) {
									continue
								}
								var B = J.fromAngle;
								var k = J.fromAngle + d
										* (J.toAngle - J.fromAngle);
								if (p && p[J.displayValue]) {
									var v = p[J.displayValue].fromAngle;
									var e = p[J.displayValue].toAngle;
									B = v + (B - v) * d;
									k = e + (k - e) * d
								}
								c.push({
									index : E,
									from : B,
									to : k
								})
							}
							if (p) {
								c.sort(this._sliceSortFunction)
							}
							var K = NaN;
							for ( var E = 0; E < c.length; E++) {
								var J = A.items[c[E].index];
								if (J.labelElement) {
									this.renderer.removeElement(J.labelElement)
								}
								var B = c[E].from;
								var k = c[E].to;
								if (p) {
									if (!isNaN(K) && B > K) {
										B = K
									}
									K = k;
									if (E == c.length - 1 && k != c[0].from) {
										k = 360 + c[0].from
									}
								}
								var C = this.renderer.pieSlicePath(J.x, J.y,
										J.innerRadius, J.outerRadius, B, k,
										J.centerOffset);
								this.renderer.attr(J.element, {
									d : C
								});
								var n = this._getColors(A.groupIndex,
										A.serieIndex, J.itemIndex,
										"radialGradient", J.outerRadius);
								var I = A.settings;
								this.renderer.attr(J.element, {
									fill : n.fillColor,
									stroke : n.lineColor,
									"stroke-width" : I.stroke,
									"fill-opacity" : I.opacity,
									"stroke-opacity" : I.opacity,
									"stroke-dasharray" : "none" || I.dashStyle
								});
								var F = this.seriesGroups[A.groupIndex];
								var u = F.series[A.serieIndex];
								if (u.showLabels == true
										|| (!u.showLabels && F.showLabels == true)) {
									var M = B, N = k;
									var q = Math.abs(M - N);
									var z = q > 180 ? 1 : 0;
									if (q > 360) {
										M = 0;
										N = 360
									}
									var w = M * Math.PI * 2 / 360;
									var m = N * Math.PI * 2 / 360;
									var o = q / 2 + M;
									o = o % 360;
									var L = o * Math.PI * 2 / 360;
									var t = this._showLabel(A.groupIndex,
											A.serieIndex, J.itemIndex, {
												x : 0,
												y : 0,
												width : 0,
												height : 0
											}, "center", "center", true);
									var j = u.labelRadius || J.outerRadius
											+ Math.max(t.width, t.height);
									j += J.centerOffset;
									var H = a.jqx.getNum([ u.offsetX,
											F.offsetX, A.rect.width / 2 ]);
									var G = a.jqx.getNum([ u.offsetY,
											F.offsetY, A.rect.height / 2 ]);
									var h = A.rect.x + H;
									var f = A.rect.y + G;
									var D = this._adjustTextBoxPosition(h, f,
											t, j, o, J.outerRadius > j,
											u.labelLinesAngles != false);
									J.labelElement = this._showLabel(
											A.groupIndex, A.serieIndex,
											J.itemIndex, {
												x : D.x,
												y : D.y,
												width : t.width,
												height : t.height
											}, "left", "top");
									if (j > J.outerRadius + 5
											&& u.labelLinesEnabled != false) {
										J.labelArrowPath = this
												._updateLebelArrowPath(
														J.labelArrowPath,
														h,
														f,
														j,
														J.outerRadius,
														L,
														u.labelLinesAngles != false,
														n, I)
									}
								}
								if (d == 1) {
									this._installHandlers(J.element,
											"pieslice", A.groupIndex,
											A.serieIndex, J.itemIndex)
								}
							}
						},
						_updateLebelArrowPath : function(f, l, i, k, m, j, p,
								c, h) {
							var e = a.jqx._ptrnd(l + (k - 3) * Math.cos(j));
							var o = a.jqx._ptrnd(i - (k - 3) * Math.sin(j));
							var d = a.jqx._ptrnd(l + (m + 2) * Math.cos(j));
							var n = a.jqx._ptrnd(i - (m + 2) * Math.sin(j));
							var q = "M " + e + "," + o + " L" + d + "," + n;
							if (p) {
								q = "M " + e + "," + o + " L" + d + "," + o
										+ " L" + d + "," + n
							}
							if (f) {
								this.renderer.attr(f, {
									d : q
								})
							} else {
								f = this.renderer.path(q, {})
							}
							this.renderer.attr(f, {
								fill : "none",
								stroke : c.lineColor,
								"stroke-width" : h.stroke,
								"stroke-opacity" : h.opacity,
								"stroke-dasharray" : "none" || h.dashStyle
							});
							return f
						},
						_adjustTextBoxPosition : function(i, d, j, h, e, c, m) {
							var f = e * Math.PI * 2 / 360;
							var l = a.jqx._ptrnd(i + h * Math.cos(f));
							var k = a.jqx._ptrnd(d - h * Math.sin(f));
							if (!c) {
								if (!m) {
									if (e >= 0 && e < 45 || e >= 315 && e < 360) {
										k -= j.height / 2
									} else {
										if (e >= 45 && e < 135) {
											k -= j.height;
											l -= j.width / 2
										} else {
											if (e >= 135 && e < 225) {
												k -= j.height / 2;
												l -= j.width
											} else {
												if (e >= 225 && e <= 360) {
													l -= j.width / 2
												}
											}
										}
									}
								} else {
									if (e >= 90 && e < 270) {
										k -= j.height / 2;
										l -= j.width
									} else {
										k -= j.height / 2
									}
								}
							} else {
								l -= j.width / 2;
								k -= j.height / 2
							}
							return {
								x : l,
								y : k
							}
						},
						_getColumnGroupsCount : function(d) {
							var f = 0;
							d = d || "vertical";
							var h = this.seriesGroups;
							for ( var e = 0; e < h.length; e++) {
								var c = h[e].orientation || "vertical";
								if (h[e].type.indexOf("column") != -1 && c == d) {
									f++
								}
							}
							return f
						},
						_getColumnGroupIndex : function(j) {
							var c = 0;
							var d = this.seriesGroups[j].orientation
									|| "vertical";
							for ( var f = 0; f < j; f++) {
								var h = this.seriesGroups[f];
								var e = h.orientation || "vertical";
								if (h.type.indexOf("column") != -1 && e == d) {
									c++
								}
							}
							return c
						},
						_renderBand : function(s, n, l) {
							var q = this.seriesGroups[s];
							if (!q.bands || q.bands.length <= n) {
								return
							}
							var d = l;
							if (q.orientation == "horizontal") {
								d = {
									x : l.y,
									y : l.x,
									width : l.height,
									height : l.width
								}
							}
							var t = this._calcGroupOffsets(s, d);
							if (!t || t.length <= s) {
								return
							}
							var u = q.bands[n];
							var j = t.bands[n];
							var p = j.from;
							var o = j.to;
							var f = Math.abs(p - o);
							var k = {
								x : d.x,
								y : Math.min(p, o),
								width : d.width,
								height : f
							};
							if (q.orientation == "horizontal") {
								var e = k.x;
								k.x = k.y;
								k.y = e;
								e = k.width;
								k.width = k.height;
								k.height = e
							}
							var m = this.renderer.rect(k.x, k.y, k.width,
									k.height);
							var c = u.color || "#AAAAAA";
							var i = u.opacity;
							if (isNaN(i) || i < 0 || i > 1) {
								i = 0.5
							}
							this.renderer.attr(m, {
								fill : c,
								"fill-opacity" : i,
								stroke : c,
								"stroke-opacity" : i,
								"stroke-width" : 0
							})
						},
						_renderColumnSeries : function(m, K) {
							var A = this.seriesGroups[m];
							if (!A.series || A.series.length == 0) {
								return
							}
							var E = A.type.indexOf("stacked") != -1;
							var f = E && A.type.indexOf("100") != -1;
							var I = A.type.indexOf("range") != -1;
							var u = this._getDataLen(m);
							var R = A.columnsGapPercent;
							if (isNaN(R) || R < 0 || R > 100) {
								R = 25
							}
							var S = A.seriesGapPercent;
							if (isNaN(S) || S < 0 || S > 100) {
								S = 10
							}
							var B = A.orientation == "horizontal";
							var q = K;
							if (B) {
								q = {
									x : K.y,
									y : K.x,
									width : K.height,
									height : K.width
								}
							}
							var v = this._calcGroupOffsets(m, q);
							if (!v || v.xoffsets.length == 0) {
								return
							}
							var k = this._getColumnGroupsCount(A.orientation);
							var c = this._getColumnGroupIndex(m);
							if (this.columnSeriesOverlap == true) {
								k = 1;
								c = 0
							}
							var V = true;
							var d;
							if (A.polar == true || A.spider == true) {
								d = this._getPolarAxisCoords(m, q);
								R = 0;
								S = 0
							}
							var C = {
								groupIndex : m,
								rect : K,
								vertical : !B,
								seriesCtx : [],
								renderData : v,
								polarAxisCoords : d
							};
							var z = this._getGroupGradientType(m);
							for ( var n = 0; n < A.series.length; n++) {
								var Q = A.series[n];
								var M = Q.columnsMaxWidth || A.columnsMaxWidth;
								var D = Q.dataField;
								var O = this._getAnimProps(m, n);
								var G = O.enabled && !this._isToggleRefresh
										&& v.xoffsets.length < 100 ? O.duration
										: 0;
								var P = 0;
								var j = v.xoffsets.itemWidth;
								if (V) {
									P -= j / 2
								}
								P += j * (c / k);
								j /= k;
								var w = P + j;
								var J = (w - P);
								var L = (w - P) / (1 + R / 100);
								var t = (!E && A.series.length > 1) ? (L * S / 100)
										/ (A.series.length - 1)
										: 0;
								var H = (L - t * (A.series.length - 1));
								if (L < 1) {
									L = 1
								}
								var o = 0;
								if (!E && A.series.length > 1) {
									H /= A.series.length;
									o = n
								}
								var W = P + (J - L) / 2 + o * (t + H);
								if (o == A.series.length) {
									H = J - P + L - x
								}
								if (!isNaN(M)) {
									var N = Math.min(H, M);
									W = W + (H - N) / 2;
									H = N
								}
								var l = this._isSerieVisible(m, n);
								var h = this._getSerieSettings(m, n);
								var F = this._getColors(m, n, NaN, this
										._getGroupGradientType(m), 4);
								var e = [];
								if (a.isFunction(Q.colorFunction) && !d) {
									for ( var T = v.xoffsets.first; T <= v.xoffsets.last; T++) {
										e.push(this._getColors(m, n, T, z, 4))
									}
								}
								var U = {
									seriesIndex : n,
									serieColors : F,
									itemsColors : e,
									settings : h,
									columnWidth : H,
									xAdjust : W,
									isVisible : l
								};
								C.seriesCtx.push(U)
							}
							this._animateColumns(C, G == 0 ? 1 : 0);
							var p = this;
							this._enqueueAnimation("series", undefined,
									undefined, G, function(s, i, X) {
										p._animateColumns(i, X)
									}, C)
						},
						_getColumnOffsets : function(t, f, w, E, m, c) {
							var l = this.seriesGroups[f];
							var j = [];
							for ( var D = 0; D < w.length; D++) {
								var B = w[D];
								var z = B.seriesIndex;
								var p = l.series[z];
								var A = t.offsets[z][E].from;
								var d = t.offsets[z][E].to;
								var F = t.xoffsets.data[E];
								var e = undefined;
								var v = B.isVisible;
								if (!v) {
									d = A
								}
								if (v && this._elementRenderInfo
										&& this._elementRenderInfo.length > f) {
									var n = t.xoffsets.xvalues[E];
									e = this._elementRenderInfo[f].series[z][n];
									if (e && !isNaN(e.from) && !isNaN(e.to)) {
										A = e.from + (A - e.from) * c;
										d = e.to + (d - e.to) * c;
										F = e.xoffset + (F - e.xoffset) * c
									}
								}
								if (!e) {
									d = A + (d - A) * (m ? 1 : c)
								}
								if (isNaN(A)) {
									A = 0
								}
								if (isNaN(d)) {
									d = isNaN(A) ? 0 : A
								}
								j.push({
									from : A,
									to : d,
									xOffset : F
								})
							}
							if (m
									&& j.length > 1
									&& !(this._elementRenderInfo && this._elementRenderInfo.length > f)) {
								var h = 0, k = 0;
								for ( var C = 0; C < j.length; C++) {
									if (j[C].to >= j[C].from) {
										k += j[C].to - j[C].from
									} else {
										h += j[C].from - j[C].to
									}
								}
								h *= c;
								k *= c;
								var q = 0, u = 0;
								for ( var C = 0; C < j.length; C++) {
									if (j[C].to >= j[C].from) {
										var o = j[C].to - j[C].from;
										if (o + u > k) {
											o = Math.max(0, k - u);
											j[C].to = j[C].from + o
										}
										u += o
									} else {
										var o = j[C].from - j[C].to;
										if (o + q > h) {
											o = Math.max(0, h - q);
											j[C].to = j[C].from - o
										}
										q += o
									}
								}
							}
							return j
						},
						_columnAsPieSlice : function(c, h, n, p, q) {
							var f = this._toPolarCoord(p, n, q.x, q.y);
							var i = this._toPolarCoord(p, n, q.x, q.y
									+ q.height);
							var s = this
									._toPolarCoord(p, n, q.x + q.width, q.y);
							var o = a.jqx._ptdist(p.x, p.y, i.x, i.y);
							var l = a.jqx._ptdist(p.x, p.y, f.x, f.y);
							var e = n.width;
							var d = -((q.x - n.x) * 360) / e;
							var k = -((q.x + q.width - n.x) * 360) / e;
							var m = p.startAngle;
							m = 360 * m / (Math.PI * 2);
							d -= m;
							k -= m;
							if (c[h] != undefined) {
								var j = this.renderer.pieSlicePath(p.x, p.y, o,
										l, k, d, 0);
								this.renderer.attr(c[h], {
									d : j
								})
							} else {
								c[h] = this.renderer.pieslice(p.x, p.y, o, l,
										k, d, 0)
							}
							return {
								fromAngle : k,
								toAngle : d,
								innerRadius : o,
								outerRadius : l
							}
						},
						_animateColumns : function(f, c) {
							var H = f.groupIndex;
							var l = this.seriesGroups[H];
							var v = f.renderData;
							var m = l.type.indexOf("stacked") != -1;
							var q = f.polarAxisCoords;
							var h = this._getGroupGradientType(H);
							for ( var F = v.xoffsets.first; F <= v.xoffsets.last; F++) {
								var k = this._getColumnOffsets(v, H,
										f.seriesCtx, F, m, c);
								for ( var E = 0; E < f.seriesCtx.length; E++) {
									var C = f.seriesCtx[E];
									var A = C.seriesIndex;
									var w = l.series[A];
									var B = k[E].from;
									var e = k[E].to;
									var I = k[E].xOffset;
									if (!C.elements) {
										C.elements = {}
									}
									if (!C.labelElements) {
										C.labelElements = {}
									}
									var s = C.elements;
									var u = C.labelElements;
									var D = (f.vertical ? f.rect.x : f.rect.y)
											+ C.xAdjust;
									var G = C.settings;
									var n = C.itemsColors.length != 0 ? C.itemsColors[F
											- v.xoffsets.first]
											: C.serieColors;
									var z = this._isSerieVisible(H, A);
									if (!z && !m) {
										continue
									}
									var o = a.jqx._ptrnd(D + I);
									var d = {
										x : o,
										width : C.columnWidth
									};
									var p = true;
									if (f.vertical) {
										d.y = B;
										d.height = e - B;
										if (d.height < 0) {
											d.y += d.height;
											d.height = -d.height;
											p = false
										}
									} else {
										d.x = B < e ? B : e;
										d.width = Math.abs(B - e);
										d.y = o;
										d.height = C.columnWidth
									}
									var t = B - e;
									if (isNaN(t)) {
										continue
									}
									t = Math.abs(t);
									if (s[F] == undefined) {
										if (!q) {
											s[F] = this.renderer.rect(d.x, d.y,
													f.vertical ? d.width : 0,
													f.vertical ? 0 : d.height)
										} else {
											this._columnAsPieSlice(s, F,
													f.rect, q, d)
										}
										this.renderer.attr(s[F], {
											fill : n.fillColor,
											"fill-opacity" : G.opacity,
											"stroke-opacity" : G.opacity,
											stroke : n.lineColor,
											"stroke-width" : G.stroke,
											"stroke-dasharray" : G.dashStyle
										})
									}
									if (t < 1 && c != 1) {
										this.renderer.attr(s[F], {
											display : "none"
										})
									} else {
										this.renderer.attr(s[F], {
											display : "block"
										})
									}
									if (q) {
										var j = this._columnAsPieSlice(s, F,
												f.rect, q, d);
										var n = this._getColors(H, A,
												undefined, "radialGradient",
												j.outerRadius);
										this.renderer.attr(s[F], {
											fill : n.fillColor,
											"fill-opacity" : G.opacity,
											"stroke-opacity" : G.opacity,
											stroke : n.lineColor,
											"stroke-width" : G.stroke,
											"stroke-dasharray" : G.dashStyle
										})
									} else {
										if (f.vertical == true) {
											this.renderer.attr(s[F], {
												x : d.x,
												y : d.y,
												height : t
											})
										} else {
											this.renderer.attr(s[F], {
												x : d.x,
												y : d.y,
												width : t
											})
										}
									}
									this.renderer.removeElement(u[F]);
									if (t == 0 && c < 1) {
										continue
									}
									u[F] = this._showLabel(H, A, F, d,
											undefined, undefined, false, false,
											p);
									if (c == 1) {
										this._installHandlers(s[F], "column",
												H, A, F)
									}
								}
							}
						},
						_renderScatterSeries : function(e, D) {
							var u = this.seriesGroups[e];
							if (!u.series || u.series.length == 0) {
								return
							}
							var f = u.type.indexOf("bubble") != -1;
							var v = u.orientation == "horizontal";
							var n = D;
							if (v) {
								n = {
									x : D.y,
									y : D.x,
									width : D.height,
									height : D.width
								}
							}
							var o = this._calcGroupOffsets(e, n);
							if (!o || o.xoffsets.length == 0) {
								return
							}
							var M = n.width;
							var c;
							if (u.polar || u.spider) {
								c = this._getPolarAxisCoords(e, n);
								M = 2 * c.r
							}
							var U = this._alignValuesWithTicks(e);
							var t = this._getGroupGradientType(e);
							for ( var h = 0; h < u.series.length; h++) {
								var S = this._getSerieSettings(e, h);
								var J = u.series[h];
								var A = J.dataField;
								var m = a.isFunction(J.colorFunction);
								var K = this._getColors(e, h, NaN, t);
								var T = NaN, z = NaN;
								if (f) {
									for ( var R = o.xoffsets.first; R <= o.xoffsets.last; R++) {
										var C = this._getDataValueAsNumber(R,
												J.radiusDataField, e);
										if (typeof (C) != "number") {
											throw "Invalid radiusDataField value at ["
													+ R + "]"
										}
										if (!isNaN(C)) {
											if (isNaN(T) || C < T) {
												T = C
											}
											if (isNaN(z) || C > z) {
												z = C
											}
										}
									}
								}
								var k = J.minRadius;
								if (isNaN(k)) {
									k = M / 50
								}
								var E = J.maxRadius;
								if (isNaN(E)) {
									E = M / 25
								}
								if (k > E) {
									E = k
								}
								var L = J.radius || 5;
								var F = this._getAnimProps(e, h);
								var B = F.enabled && !this._isToggleRefresh
										&& o.xoffsets.length < 5000 ? F.duration
										: 0;
								var w = {
									groupIndex : e,
									seriesIndex : h,
									"fill-opacity" : S.opacity,
									"stroke-opacity" : S.opacity,
									"stroke-width" : S.stroke,
									"stroke-dasharray" : S.dashStyle,
									items : [],
									polarAxisCoords : c
								};
								for ( var R = o.xoffsets.first; R <= o.xoffsets.last; R++) {
									var C = this._getDataValueAsNumber(R, A, e);
									if (typeof (C) != "number") {
										continue
									}
									var I = o.xoffsets.data[R];
									var H = o.offsets[h][R].to;
									var G = o.xoffsets.xvalues[R];
									if (isNaN(I) || isNaN(H)) {
										continue
									}
									if (v) {
										var O = I;
										I = H;
										H = O + D.y
									} else {
										I += D.x
									}
									var N = L;
									if (f) {
										var p = this._getDataValueAsNumber(R,
												J.radiusDataField, e);
										if (typeof (p) != "number") {
											continue
										}
										N = k + (E - k) * (p - T)
												/ Math.max(1, z - T);
										if (isNaN(N)) {
											N = k
										}
									}
									var l = NaN, P = NaN;
									var q = 0;
									if (G != undefined
											&& this._elementRenderInfo
											&& this._elementRenderInfo.length > e) {
										var d = this._elementRenderInfo[e].series[h][G];
										if (d && !isNaN(d.to)) {
											l = d.to;
											P = d.xoffset;
											q = L;
											if (v) {
												var O = P;
												P = l;
												l = O + D.y
											} else {
												P += D.x
											}
											if (f) {
												q = k + (E - k)
														* (d.valueRadius - T)
														/ Math.max(1, z - T);
												if (isNaN(q)) {
													q = k
												}
											}
										}
									}
									if (m) {
										K = this._getColors(e, h, R, t)
									}
									w.items.push({
										from : q,
										to : N,
										itemIndex : R,
										fill : K.fillColor,
										stroke : K.lineColor,
										x : I,
										y : H,
										xFrom : P,
										yFrom : l
									})
								}
								this._animR(w, 0);
								var j = this;
								var Q = undefined;
								this._enqueueAnimation("series", undefined,
										undefined, B, function(V, i, s) {
											j._animR(i, s)
										}, w)
							}
						},
						_animR : function(n, f) {
							var h = n.items;
							for ( var e = 0; e < h.length; e++) {
								var m = h[e];
								var k = m.x;
								var j = m.y;
								var c = Math
										.round((m.to - m.from) * f + m.from);
								if (!isNaN(m.yFrom)) {
									j = m.yFrom + (j - m.yFrom) * f
								}
								if (!isNaN(m.xFrom)) {
									k = m.xFrom + (k - m.xFrom) * f
								}
								if (n.polarAxisCoords) {
									var l = this._toPolarCoord(
											n.polarAxisCoords, this._plotRect,
											k, j);
									k = l.x;
									j = l.y
								}
								k = a.jqx._ptrnd(k);
								j = a.jqx._ptrnd(j);
								c = a.jqx._ptrnd(c);
								var d = m.element;
								if (!d) {
									d = this.renderer.circle(k, j, c);
									this.renderer
											.attr(
													d,
													{
														fill : m.fill,
														"fill-opacity" : n["fill-opacity"],
														"stroke-opacity" : n["fill-opacity"],
														stroke : m.stroke,
														"stroke-width" : n["stroke-width"],
														"stroke-dasharray" : n["stroke-dasharray"]
													});
									m.element = d
								}
								if (this._isVML) {
									this.renderer.updateCircle(d, undefined,
											undefined, c)
								} else {
									this.renderer.attr(d, {
										r : c,
										cy : j,
										cx : k
									})
								}
								if (m.labelElement) {
									this.renderer.removeElement(m.labelElement)
								}
								m.labelElement = this._showLabel(n.groupIndex,
										n.seriesIndex, m.itemIndex, {
											x : k - c,
											y : j - c,
											width : 2 * c,
											height : 2 * c
										});
								if (f >= 1) {
									this._installHandlers(d, "circle",
											n.groupIndex, n.seriesIndex,
											m.itemIndex)
								}
							}
						},
						_showToolTip : function(o, m, H, C, d) {
							var A = this;
							var z = A._getCategoryAxis(H);
							if (A._ttEl && H == A._ttEl.gidx
									&& C == A._ttEl.sidx && d == A._ttEl.iidx) {
								return
							}
							var l = A.seriesGroups[H];
							var p = l.series[C];
							var i = A.enableCrosshairs
									&& !(l.polar || l.spider);
							if (A._pointMarker) {
								o = parseInt(A._pointMarker.x + 5);
								m = parseInt(A._pointMarker.y - 5)
							} else {
								i = false
							}
							var k = i && A.showToolTips == false;
							o = a.jqx._ptrnd(o);
							m = a.jqx._ptrnd(m);
							var I = A._ttEl == undefined;
							if (l.showToolTips == false
									|| p.showToolTips == false) {
								return
							}
							var h = p.toolTipFormatSettings
									|| l.toolTipFormatSettings;
							var w = p.toolTipFormatFunction
									|| l.toolTipFormatFunction
									|| A.toolTipFormatFunction;
							var n = A._getColors(H, C, d);
							var c = A._getDataValue(d, z.dataField, H);
							if (z.dataField == undefined || z.dataField == "") {
								c = d
							}
							if (z.type == "date") {
								c = A._castAsDate(c)
							}
							var t = "";
							if (a.isFunction(w)) {
								var B = {};
								if (l.type.indexOf("range") == -1) {
									B = A._getDataValue(d, p.dataField, H)
								} else {
									B.from = A._getDataValue(d,
											p.dataFieldFrom, H);
									B.to = A._getDataValue(d, p.dataFieldTo, H)
								}
								t = w(B, d, p, l, c, z)
							} else {
								t = A._getFormattedValue(H, C, d, h, w);
								var M = z.toolTipFormatSettings
										|| z.formatSettings;
								var e = z.toolTipFormatFunction
										|| z.formatFunction;
								if (!e && !M && z.type == "date") {
									e = this._getDefaultDTFormatFn(z.baseUnit
											|| "day")
								}
								var L = A._formatValue(c, M, e);
								if (l.type != "pie" && l.type != "donut") {
									t = (p.displayText || p.dataField || "")
											+ ", " + L + ": " + t
								} else {
									c = A._getDataValue(d, p.displayText
											|| p.dataField, H);
									L = A._formatValue(c, M, e);
									t = L + ": " + t
								}
							}
							var G = p.toolTipClass
									|| l.toolTipClass
									|| this.toThemeProperty(
											"jqx-chart-tooltip-text", null);
							var J = p.toolTipBackground || l.toolTipBackground
									|| "#FFFFFF";
							var K = p.toolTipLineColor || l.toolTipLineColor
									|| n.lineColor;
							if (!A._ttEl) {
								A._ttEl = {}
							}
							A._ttEl.sidx = C;
							A._ttEl.gidx = H;
							A._ttEl.iidx = d;
							rect = A.renderer.getRect();
							if (i) {
								var F = a.jqx._ptrnd(A._pointMarker.x);
								var E = a.jqx._ptrnd(A._pointMarker.y);
								if (A._ttEl.vLine && A._ttEl.hLine) {
									A.renderer.attr(A._ttEl.vLine, {
										x1 : F,
										x2 : F
									});
									A.renderer.attr(A._ttEl.hLine, {
										y1 : E,
										y2 : E
									})
								} else {
									var D = A.crosshairsColor || "#888888";
									A._ttEl.vLine = A.renderer
											.line(
													F,
													A._plotRect.y,
													F,
													A._plotRect.y
															+ A._plotRect.height,
													{
														stroke : D,
														"stroke-width" : A.crosshairsLineWidth || 1,
														"stroke-dasharray" : A.crosshairsDashStyle
																|| ""
													});
									A._ttEl.hLine = A.renderer
											.line(
													A._plotRect.x,
													E,
													A._plotRect.x
															+ A._plotRect.width,
													E,
													{
														stroke : D,
														"stroke-width" : A.crosshairsLineWidth || 1,
														"stroke-dasharray" : A.crosshairsDashStyle
																|| ""
													})
								}
							}
							if (!k && A.showToolTips != false) {
								var v = !I ? A._ttEl.box : document
										.createElement("div");
								var f = {
									left : 0,
									top : 0
								};
								if (I) {
									v.style.position = "absolute";
									v.style.cursor = "default";
									v.style.overflow = "hidden";
									a(v).addClass("jqx-rc-all jqx-button");
									a(v).css("z-index", 9999999);
									a(document.body).append(v)
								}
								v.style.backgroundColor = J;
								v.style.borderColor = K;
								A._ttEl.box = v;
								A._ttEl.txt = t;
								var q = "<span class='" + G + "'>" + t
										+ "</span>";
								var j = A._ttEl.tmp;
								if (I) {
									A._ttEl.tmp = j = document
											.createElement("div");
									j.style.position = "absolute";
									j.style.cursor = "default";
									j.style.overflow = "hidden";
									j.style.display = "none";
									j.style.zIndex = 999999;
									j.style.backgroundColor = J;
									j.style.borderColor = K;
									a(j).addClass("jqx-rc-all jqx-button");
									A.host.append(j)
								}
								a(j).html(q);
								if (!t || t.length == 0) {
									a(v).fadeTo(0, 0);
									return
								}
								var u = {
									width : a(j).width(),
									height : a(j).height()
								};
								u.width = u.width + 5;
								u.height = u.height + 6;
								o = Math.max(o, rect.x);
								m = Math.max(m - u.height, rect.y);
								if (u.width > rect.width
										|| u.height > rect.height) {
									return
								}
								if (o + f.left + u.width > rect.x + rect.width
										- 5) {
									o = rect.x + rect.width - u.width - f.left
											- 5
								}
								if (m + f.top + u.height > rect.y + rect.height
										- 5) {
									m = rect.y + rect.height - u.height - 5
								}
								var s = A.host.coord();
								if (I) {
									a(v).fadeOut(0, 0);
									v.style.left = f.left + o + s.left + "px";
									v.style.top = f.top + m + s.top + "px"
								}
								a(v).html(q);
								a(v).clearQueue();
								a(v).animate({
									left : f.left + o + s.left,
									top : f.top + m + s.top,
									opacity : 1
								}, 300, "easeInOutCirc");
								a(v).fadeTo(400, 1)
							}
						},
						_hideToolTip : function(c) {
							if (!this._ttEl) {
								return
							}
							if (this._ttEl.box) {
								if (c == 0) {
									a(this._ttEl.box).hide()
								} else {
									a(this._ttEl.box).fadeOut()
								}
							}
							this._hideCrosshairs();
							this._ttEl.gidx = undefined
						},
						_hideCrosshairs : function() {
							if (!this._ttEl) {
								return
							}
							if (this._ttEl.vLine) {
								this.renderer.removeElement(this._ttEl.vLine);
								this._ttEl.vLine = undefined
							}
							if (this._ttEl.hLine) {
								this.renderer.removeElement(this._ttEl.hLine);
								this._ttEl.hLine = undefined
							}
						},
						_showLabel : function(F, C, f, c, u, j, e, l, d) {
							var n = this.seriesGroups[F];
							var s = n.series[C];
							var A = {
								width : 0,
								height : 0
							};
							if (s.showLabels == false
									|| (!s.showLabels && !n.showLabels)) {
								return e ? A : undefined
							}
							if (c.width < 0 || c.height < 0) {
								return e ? A : undefined
							}
							var B = s.labelClass
									|| n.labelClass
									|| this.toThemeProperty(
											"jqx-chart-label-text", null);
							var i = s.labelAngle || s.labelsAngle
									|| n.labelAngle || n.labelsAngle || 0;
							var k = s.labelOffset || s.labelsOffset
									|| n.labelOffset || n.labelsOffset || {};
							var D = {
								x : k.x,
								y : k.y
							};
							if (isNaN(D.x)) {
								D.x = 0
							}
							if (isNaN(D.y)) {
								D.y = 0
							}
							u = u || s.labelsHorizontalAlignment
									|| n.labelsHorizontalAlignment || "center";
							j = j || s.labelsVerticalAlignment
									|| n.labelsVerticalAlignment || "center";
							var z = this._getFormattedValue(F, C, f);
							var t = c.width;
							var E = c.height;
							if (l == true && u != "center") {
								u = u == "right" ? "left" : "right"
							}
							if (d == true && j != "center" && j != "middle") {
								j = j == "top" ? "bottom" : "top";
								D.y *= -1
							}
							A = this.renderer.measureText(z, i, {
								"class" : B
							});
							if (e) {
								return A
							}
							var q = 0;
							if (t > 0) {
								if (u == "" || u == "center") {
									q += (t - A.width) / 2
								} else {
									if (u == "right") {
										q += (t - A.width)
									}
								}
							}
							var o = 0;
							if (E > 0) {
								if (j == "" || j == "center") {
									o += (E - A.height) / 2
								} else {
									if (j == "bottom") {
										o += (E - A.height)
									}
								}
							}
							q += c.x + D.x;
							o += c.y + D.y;
							var p = this._plotRect;
							if (q <= p.x) {
								q = p.x + 2
							}
							if (o <= p.y) {
								o = p.y + 2
							}
							var m = {
								width : Math.max(A.width, 1),
								height : Math.max(A.height, 1)
							};
							if (o + m.height >= p.y + p.height) {
								o = p.y + p.height - m.height - 2
							}
							if (q + m.width >= p.x + p.width) {
								q = p.x + p.width - m.width - 2
							}
							var v = this.renderer.text(z, q, o, A.width,
									A.height, i, {
										"class" : B
									}, false, "center", "center");
							this.renderer.attr(v, {
								"class" : B
							});
							if (this._isVML) {
								this.renderer.removeElement(v);
								this.renderer.getContainer()[0].appendChild(v)
							}
							return v
						},
						_getAnimProps : function(k, h) {
							var f = this.seriesGroups[k];
							var d = !isNaN(h) ? f.series[h] : undefined;
							var c = this.enableAnimations == true;
							if (f.enableAnimations) {
								c = f.enableAnimations == true
							}
							if (d && d.enableAnimations) {
								c = d.enableAnimations == true
							}
							var j = this.animationDuration;
							if (isNaN(j)) {
								j = 1000
							}
							var e = f.animationDuration;
							if (!isNaN(e)) {
								j = e
							}
							if (d) {
								var i = d.animationDuration;
								if (!isNaN(i)) {
									j = i
								}
							}
							if (j > 5000) {
								j = 1000
							}
							return {
								enabled : c,
								duration : j
							}
						},
						_isColorTransition : function(h, e, f, i) {
							if (i - 1 < f.xoffsets.first) {
								return false
							}
							var c = this._getColors(h, e, i, this
									._getGroupGradientType(h));
							var d = this._getColors(h, e, i - 1, this
									._getGroupGradientType(h));
							return (c.fillColor != d.fillColor)
						},
						_renderLineSeries : function(l, S) {
							var J = this.seriesGroups[l];
							if (!J.series || J.series.length == 0) {
								return
							}
							var u = J.type.indexOf("area") != -1;
							var M = J.type.indexOf("stacked") != -1;
							var e = M && J.type.indexOf("100") != -1;
							var ag = J.type.indexOf("spline") != -1;
							var v = J.type.indexOf("step") != -1;
							var Q = J.type.indexOf("range") != -1;
							var ah = J.polar == true || J.spider == true;
							if (ah) {
								v = false
							}
							if (v && ag) {
								return
							}
							var C = this._getDataLen(l);
							var ae = S.width / C;
							var al = J.orientation == "horizontal";
							var E = this._getCategoryAxis(l).flip == true;
							var B = S;
							if (al) {
								B = {
									x : S.y,
									y : S.x,
									width : S.height,
									height : S.width
								}
							}
							var F = this._calcGroupOffsets(l, B);
							if (!F || F.xoffsets.length == 0) {
								return
							}
							if (!this._linesRenderInfo) {
								this._linesRenderInfo = {}
							}
							this._linesRenderInfo[l] = {};
							for ( var o = J.series.length - 1; o >= 0; o--) {
								var h = this._getSerieSettings(l, o);
								var aj = {
									groupIndex : l,
									serieIndex : o,
									swapXY : al,
									isArea : u,
									isSpline : ag,
									isRange : Q,
									isPolar : ah,
									settings : h,
									segments : [],
									pointsLength : 0
								};
								var k = this._isSerieVisible(l, o);
								if (!k) {
									this._linesRenderInfo[l][o] = aj;
									continue
								}
								var L = J.series[o];
								var A = a.isFunction(L.colorFunction);
								var W = F.xoffsets.first;
								var I = W;
								var P = this._getColors(l, o, NaN, this
										._getGroupGradientType(l));
								var ad = false;
								do {
									var Y = [];
									var V = [];
									var t = [];
									var R = -1;
									var q = 0;
									var T = NaN;
									var G = NaN;
									var ak = NaN;
									if (F.xoffsets.length < 1) {
										continue
									}
									var U = this._getAnimProps(l, o);
									var N = U.enabled && !this._isToggleRefresh
											&& F.xoffsets.length < 10000
											&& this._isVML != true ? U.duration
											: 0;
									var z = W;
									var w = false;
									var d = this._getColors(l, o, W, this
											._getGroupGradientType(l));
									for ( var af = W; af <= F.xoffsets.last; af++) {
										W = af;
										var Z = F.xoffsets.data[af];
										var X = F.xoffsets.xvalues[af];
										if (isNaN(Z)) {
											continue
										}
										Z = Math.max(Z, 1);
										q = Z;
										var p = F.offsets[o][af].to;
										var ac = F.offsets[o][af].from;
										if (isNaN(p) || isNaN(ac)) {
											if (L.emptyPointsDisplay == "connect") {
												continue
											} else {
												if (L.emptyPointsDisplay == "zero") {
													if (isNaN(p)) {
														p = F.baseOffset
													}
													if (isNaN(ac)) {
														ac = F.baseOffset
													}
												} else {
													w = true;
													break
												}
											}
										}
										if (A
												&& this._isColorTransition(l,
														o, F, W)) {
											if (Y.length > 1) {
												W--;
												break
											}
										}
										if (this._elementRenderInfo
												&& this._elementRenderInfo.length > l
												&& this._elementRenderInfo[l].series.length > o) {
											var f = this._elementRenderInfo[l].series[o][X];
											var ak = a.jqx._ptrnd(f ? f.to
													: undefined);
											var K = a.jqx._ptrnd(B.x
													+ (f ? f.xoffset
															: undefined));
											t.push(al ? {
												y : K,
												x : ak,
												index : af
											} : {
												x : K,
												y : ak,
												index : af
											})
										}
										I = af;
										if (h.stroke < 2) {
											if (p - B.y <= 1) {
												p = B.y + 1
											}
											if (ac - B.y <= 1) {
												ac = B.y + 1
											}
											if (B.y + B.height - p <= 1) {
												p = B.y + B.height - 1
											}
											if (B.y + B.height - p <= 1) {
												ac = B.y + B.height - 1
											}
										}
										if (!u && e) {
											if (p <= B.y) {
												p = B.y + 1
											}
											if (p >= B.y + B.height) {
												p = B.y + B.height - 1
											}
											if (ac <= B.y) {
												ac = B.y + 1
											}
											if (ac >= B.y + B.height) {
												ac = B.y + B.height - 1
											}
										}
										Z = Math.max(Z, 1);
										q = Z + B.x;
										if (v && !isNaN(T) && !isNaN(G)) {
											if (G != p) {
												Y.push(al ? {
													y : q,
													x : a.jqx._ptrnd(G)
												} : {
													x : q,
													y : a.jqx._ptrnd(G)
												})
											}
										}
										Y.push(al ? {
											y : q,
											x : a.jqx._ptrnd(p),
											index : af
										} : {
											x : q,
											y : a.jqx._ptrnd(p),
											index : af
										});
										V.push(al ? {
											y : q,
											x : a.jqx._ptrnd(ac),
											index : af
										} : {
											x : q,
											y : a.jqx._ptrnd(ac),
											index : af
										});
										T = q;
										G = p;
										if (isNaN(ak)) {
											ak = p
										}
									}
									if (Y.length == 0) {
										W++;
										continue
									}
									var H = Y[Y.length - 1].index;
									if (A) {
										P = this._getColors(l, o, H, this
												._getGroupGradientType(l))
									}
									var m = B.x + F.xoffsets.data[z];
									var ab = B.x + F.xoffsets.data[I];
									if (u
											&& J.alignEndPointsWithIntervals == true) {
										var D = E ? -1 : 1;
										if (m > B.x) {
											m = B.x
										}
										if (ab < B.x + B.width) {
											ab = B.x + B.width
										}
										if (E) {
											var aa = m;
											m = ab;
											ab = aa
										}
									}
									ab = a.jqx._ptrnd(ab);
									m = a.jqx._ptrnd(m);
									var n = F.baseOffset;
									ak = a.jqx._ptrnd(ak);
									var j = a.jqx._ptrnd(p) || n;
									if (Q) {
										Y = Y.concat(V.reverse())
									}
									aj.pointsLength += Y.length;
									var c = {
										lastItemIndex : H,
										colorSettings : P,
										pointsArray : Y,
										pointsStart : t,
										left : m,
										right : ab,
										pyStart : ak,
										pyEnd : j,
										yBase : n,
										labelElements : [],
										symbolElements : []
									};
									aj.segments.push(c)
								} while (W < F.xoffsets.length - 1 || w);
								this._linesRenderInfo[l][o] = aj
							}
							var O = this._linesRenderInfo[l];
							var ai = [];
							for ( var af in O) {
								ai.push(O[af])
							}
							if (u && M) {
								ai.reverse()
							}
							for ( var af = 0; af < ai.length; af++) {
								var aj = ai[af];
								this._animateLine(aj, N == 0 ? 1 : 0);
								var s = this;
								this._enqueueAnimation("series", undefined,
										undefined, N, function(am, i, an) {
											s._animateLine(i, an)
										}, aj)
							}
						},
						_animateLine : function(A, c) {
							var E = A.settings;
							var h = A.groupIndex;
							var j = A.serieIndex;
							var l = this.seriesGroups[h];
							var u = l.series[j];
							var z = this._getSymbol(h, j);
							var q = u.showLabels == true
									|| (l.showLabels && u.showLabels != false);
							var s = 0;
							for ( var e = 0; e < A.segments.length; e++) {
								var w = A.segments[e];
								var B = this._calculateLine(h, A.pointsLength,
										s, w.pointsArray, w.pointsStart,
										w.yBase, c, A.isArea, A.swapXY);
								s += w.pointsArray.length;
								if (B == "") {
									continue
								}
								var t = B.split(" ");
								var C = t.length;
								var k = B;
								if (k != "") {
									k = this._buildLineCmd(B, A.isRange,
											w.left, w.right, w.pyStart,
											w.pyEnd, w.yBase, A.isArea,
											A.isPolar, A.isSpline, A.swapXY)
								} else {
									k = "M 0 0"
								}
								var n = w.colorSettings;
								if (!w.pathElement) {
									w.pathElement = this.renderer.path(k, {
										"stroke-width" : E.stroke,
										stroke : n.lineColor,
										"stroke-opacity" : E.opacity,
										"fill-opacity" : E.opacity,
										"stroke-dasharray" : E.dashStyle,
										fill : A.isArea ? n.fillColor : "none"
									});
									this._installHandlers(w.pathElement,
											"path", h, j, w.lastItemIndex)
								} else {
									this.renderer.attr(w.pathElement, {
										d : k
									})
								}
								if (w.labelElements) {
									for ( var D = 0; D < w.labelElements.length; D++) {
										this.renderer
												.removeElement(w.labelElements[D])
									}
									w.labelElements = []
								}
								if (w.symbolElements) {
									for ( var D = 0; D < w.symbolElements.length; D++) {
										this.renderer
												.removeElement(w.symbolElements[D])
									}
									w.symbolElements = []
								}
								if (w.pointsArray.length == t.length) {
									if (z != "none" || q) {
										var F = u.symbolSize;
										for ( var D = 0; D < t.length; D++) {
											var v = t[D].split(",");
											v = {
												x : parseFloat(v[0]),
												y : parseFloat(v[1])
											};
											if (z != "none") {
												var p = this
														._getColors(
																h,
																j,
																w.pointsArray[D].index,
																this
																		._getGroupGradientType(h));
												var f = this._drawSymbol(z,
														v.x, v.y,
														p.fillColorSymbol,
														p.lineColorSymbol, 1,
														E.opacity, F);
												w.symbolElements.push(f)
											}
											if (q) {
												var m = (D > 0 ? t[D - 1]
														: t[D]).split(",");
												m = {
													x : parseFloat(m[0]),
													y : parseFloat(m[1])
												};
												var o = (D < t.length - 1 ? t[D + 1]
														: t[D]).split(",");
												o = {
													x : parseFloat(o[0]),
													y : parseFloat(o[1])
												};
												v = this
														._adjustLineLabelPosition(
																h,
																j,
																w.pointsArray[D].index,
																v, m, o);
												var d = this._showLabel(h, j,
														w.pointsArray[D].index,
														{
															x : v.x,
															y : v.y,
															width : 0,
															height : 0
														});
												w.labelElements.push(d)
											}
										}
									}
								}
								if (c == 1 && z != "none") {
									for ( var D = 0; D < w.symbolElements.length; D++) {
										this._installHandlers(
												w.symbolElements[D], "symbol",
												h, j, w.pointsArray[D].index)
									}
								}
							}
						},
						_adjustLineLabelPosition : function(k, i, e, j, h, f) {
							var c = this._showLabel(k, i, e, {
								width : 0,
								height : 0
							}, "", "", true);
							var d = {
								x : 0,
								y : 0
							};
							if (j.y == h.y && j.x == h.x) {
								if (f.y < j.y) {
									d = {
										x : j.x,
										y : j.y + c.height
									}
								} else {
									d = {
										x : j.x,
										y : j.y - c.height
									}
								}
							} else {
								if (j.y == f.y && j.x == f.x) {
									if (h.y < j.y) {
										d = {
											x : j.x,
											y : j.y + c.height
										}
									} else {
										d = {
											x : j.x,
											y : j.y - c.height
										}
									}
								}
							}
							if (j.y > h.y && j.y > f.y) {
								d = {
									x : j.x,
									y : j.y + c.height
								}
							} else {
								d = {
									x : j.x,
									y : j.y - c.height
								}
							}
							return d
						},
						_calculateLine : function(j, z, q, p, o, h, f, B, d) {
							var A = this.seriesGroups[j];
							var n = undefined;
							if (A.polar == true || A.spider == true) {
								n = this._getPolarAxisCoords(j, this._plotRect)
							}
							var u = "";
							var v = p.length;
							if (!B && o.length == 0) {
								var t = z * f;
								v = t - q
							}
							var k = NaN;
							for ( var w = 0; w < v + 1 && w < p.length; w++) {
								if (w > 0) {
									u += " "
								}
								var l = p[w].y;
								var m = p[w].x;
								var c = !B ? l : h;
								var e = m;
								if (o && o.length > w) {
									c = o[w].y;
									e = o[w].x;
									if (isNaN(c) || isNaN(e)) {
										c = l;
										e = m
									}
								}
								k = e;
								if (v <= p.length && w > 0 && w == v) {
									e = p[w - 1].x;
									c = p[w - 1].y
								}
								if (d) {
									m = a.jqx._ptrnd((m - c) * (B ? f : 1) + c);
									l = a.jqx._ptrnd(l)
								} else {
									m = a.jqx._ptrnd((m - e) * f + e);
									l = a.jqx._ptrnd((l - c) * f + c)
								}
								if (n) {
									var s = this._toPolarCoord(n,
											this._plotRect, m, l);
									m = s.x;
									l = s.y
								}
								u += m + "," + l;
								if (p.length == 1 && !B) {
									u += " " + (m + 2) + "," + (l + 2)
								}
							}
							return u
						},
						_buildLineCmd : function(m, k, h, q, p, c, s, o, t, e,
								l) {
							var f = m;
							if (o && !t && !k) {
								var d = l ? s + "," + h : h + "," + s;
								var j = l ? s + "," + q : q + "," + s;
								f = d + " " + m + " " + j
							}
							if (e) {
								f = this._getBezierPoints(f)
							}
							var n = f.split(" ");
							var i = n[0].replace("C", "");
							if (o && !t) {
								if (!k) {
									f = "M " + d + " L " + i + " " + f
								} else {
									f = "M " + i + " L " + i
											+ (e ? "" : (" L " + i + " ")) + f
								}
							} else {
								if (e) {
									f = "M " + i + " " + f
								} else {
									f = "M " + i + " L " + i + " " + f
								}
							}
							if (t) {
								f += " Z"
							}
							return f
						},
						_getSerieSettings : function(j, c) {
							var i = this.seriesGroups[j];
							var h = i.type.indexOf("area") != -1;
							var f = i.type.indexOf("line") != -1;
							var d = i.series[c];
							var l = d.dashStyle || i.dashStyle || "";
							var e = d.opacity || i.opacity;
							if (isNaN(e) || e < 0 || e > 1) {
								e = 1
							}
							var k = d.lineWidth;
							if (isNaN(k) && k != "auto") {
								k = i.lineWidth
							}
							if (k == "auto" || isNaN(k) || k < 0 || k > 15) {
								if (h) {
									k = 2
								} else {
									if (f) {
										k = 3
									} else {
										k = 1
									}
								}
							}
							return {
								stroke : k,
								opacity : e,
								dashStyle : l
							}
						},
						_getColors : function(p, m, i, d, k) {
							var n = this.seriesGroups[p];
							var j = n.series[m];
							var l = j.useGradient;
							if (l == undefined) {
								l = j.useGradientColors
							}
							if (l == undefined) {
								l = n.useGradient
							}
							if (l == undefined) {
								l = n.useGradientColors
							}
							if (l == undefined) {
								l = true
							}
							var c = this._getSeriesColors(p, m, i);
							if (!c.fillColor) {
								c.fillColor = color;
								c.fillColorSelected = a.jqx.adjustColor(color,
										1.1);
								c.lineColor = c.symbolColor = a.jqx
										.adjustColor(color, 0.9);
								c.lineColorSelected = c.symbolColorSelected = a.jqx
										.adjustColor(color, 0.9)
							}
							var h = [ [ 0, 1.4 ], [ 100, 1 ] ];
							var e = [ [ 0, 1 ], [ 25, 1.1 ], [ 50, 1.4 ],
									[ 100, 1 ] ];
							var q = [ [ 0, 1.3 ], [ 90, 1.2 ], [ 100, 1 ] ];
							var o = NaN;
							if (!isNaN(k)) {
								o = k == 2 ? h : e
							}
							if (l) {
								if (d == "verticalLinearGradient") {
									c.fillColor = this.renderer
											._toLinearGradient(c.fillColor,
													true, o || h);
									c.fillColorSelected = this.renderer
											._toLinearGradient(
													c.fillColorSelected, true,
													o || h)
								} else {
									if (d == "horizontalLinearGradient") {
										c.fillColor = this.renderer
												._toLinearGradient(c.fillColor,
														false, o || e);
										c.fillColorSelected = this.renderer
												._toLinearGradient(
														c.fillColorSelected,
														false, o || e)
									} else {
										if (d == "radialGradient") {
											var f = undefined;
											var o = h;
											if ((n.type == "pie"
													|| n.type == "donut" || n.polar)
													&& i != undefined
													&& this._renderData[p]
													&& this._renderData[p].offsets[m]) {
												f = this._renderData[p].offsets[m][i];
												o = q
											}
											c.fillColor = this.renderer
													._toRadialGradient(
															c.fillColor, o, f);
											c.fillColorSelected = this.renderer
													._toRadialGradient(
															c.fillColorSelected,
															o, f)
										}
									}
								}
							}
							return c
						},
						_installHandlers : function(d, h, j, i, e) {
							if (!this.enableEvents) {
								return false
							}
							var k = this;
							var f = this.seriesGroups[j];
							var l = this.seriesGroups[j].series[i];
							var c = f.type.indexOf("line") != -1
									|| f.type.indexOf("area") != -1;
							if (!c) {
								this.renderer.addHandler(d, "mousemove",
										function(n) {
											n.preventDefault();
											var m = n.pageX || n.clientX
													|| n.screenX;
											var p = n.pageY || n.clientY
													|| n.screenY;
											var o = k.host.offset();
											m -= o.left;
											p -= o.top;
											if (k._mouseX == m
													&& k._mouseY == p) {
												return
											}
											if (k._ttEl) {
												if (k._ttEl.gidx == j
														&& k._ttEl.sidx == i
														&& k._ttEl.iidx == e) {
													return
												}
											}
											k._startTooltipTimer(j, i, e)
										});
								this.renderer
										.addHandler(
												d,
												"mouseout",
												function(m) {
													if (!isNaN(k._lastClickTs)
															&& (new Date())
																	.valueOf()
																	- k._lastClickTs < 100) {
														return
													}
													m.preventDefault();
													if (e != undefined) {
														k._cancelTooltipTimer()
													}
													if (c) {
														return
													}
													k._unselect()
												})
							}
							this.renderer.addHandler(d, "mouseover",
									function(m) {
										m.preventDefault();
										k._select(d, h, j, i, e, e)
									});
							this.renderer
									.addHandler(
											d,
											"click",
											function(m) {
												clearTimeout(k._hostClickTimer);
												k._lastClickTs = (new Date())
														.valueOf();
												if (c
														&& (h != "symbol" && h != "pointMarker")) {
													return
												}
												if (f.type.indexOf("column") != -1) {
													k._unselect()
												}
												if (isNaN(e)) {
													return
												}
												k._raiseItemEvent("click", f,
														l, e)
											})
						},
						_getHorizontalOffset : function(C, u, l, k) {
							var d = this._plotRect;
							var j = this._getDataLen(C);
							if (j == 0) {
								return {
									index : undefined,
									value : l
								}
							}
							var q = this._calcGroupOffsets(C, this._plotRect);
							if (q.xoffsets.length == 0) {
								return {
									index : undefined,
									value : undefined
								}
							}
							var o = l;
							var n = k;
							var A = this.seriesGroups[C];
							var m;
							if (A.polar || A.spider) {
								m = this._getPolarAxisCoords(C, d)
							}
							if (A.orientation == "horizontal" && !m) {
								var B = o;
								o = n;
								n = B
							}
							var f = this._getCategoryAxis(C).flip == true;
							var c, p, z, h;
							for ( var v = q.xoffsets.first; v <= q.xoffsets.last; v++) {
								var w = q.xoffsets.data[v];
								var e = q.offsets[u][v].to;
								var s = 0;
								if (m) {
									var t = this
											._toPolarCoord(m, d, w + d.x, e);
									w = t.x;
									e = t.y;
									s = a.jqx._ptdist(o, n, w, e)
								} else {
									w += d.x;
									e += d.y;
									s = Math.abs(o - w)
								}
								if (isNaN(c) || c > s) {
									c = s;
									p = v;
									z = w;
									h = e
								}
							}
							return {
								index : p,
								value : q.xoffsets.data[p],
								polarAxisCoords : m,
								x : z,
								y : h
							}
						},
						onmousemove : function(m, l) {
							if (this._mouseX == m && this._mouseY == l) {
								return
							}
							this._mouseX = m;
							this._mouseY = l;
							if (!this._selected) {
								return
							}
							var D = this._selected.group;
							var u = this._selected.series;
							var A = this.seriesGroups[D];
							var p = A.series[u];
							var c = this._plotRect;
							if (this.renderer) {
								c = this.renderer.getRect();
								c.x += 5;
								c.y += 5;
								c.width -= 10;
								c.height -= 10
							}
							if (m < c.x || m > c.x + c.width || l < c.y
									|| l > c.y + c.height) {
								this._hideToolTip();
								this._unselect();
								return
							}
							var f = A.orientation == "horizontal";
							var c = this._plotRect;
							if (A.type.indexOf("line") != -1
									|| A.type.indexOf("area") != -1) {
								var j = this._getHorizontalOffset(D,
										this._selected.series, m, l);
								var z = j.index;
								if (z == undefined) {
									return
								}
								if (this._selected.item != z) {
									var t = this._linesRenderInfo[D][u].segments;
									var v = 0;
									while (z > t[v].lastItemIndex) {
										v++;
										if (v >= t.length) {
											return
										}
									}
									var d = t[v].pathElement;
									var E = t[v].lastItemIndex;
									this._unselect(false);
									this._select(d, "path", D, u, z, E)
								} else {
									return
								}
								var o = this._getSymbol(this._selected.group,
										this._selected.series);
								if (o == "none") {
									o = "circle"
								}
								var q = this._calcGroupOffsets(D, c);
								var e = q.offsets[this._selected.series][z].to;
								var w = e;
								if (A.type.indexOf("range") != -1) {
									w = q.offsets[this._selected.series][z].from
								}
								var n = f ? m : l;
								if (!isNaN(w)
										&& Math.abs(n - w) < Math.abs(n - e)) {
									l = w
								} else {
									l = e
								}
								if (isNaN(l)) {
									return
								}
								m = j.value;
								if (f) {
									var B = m;
									m = l;
									l = B + c.y
								} else {
									m += c.x
								}
								if (j.polarAxisCoords) {
									m = j.x;
									l = j.y
								}
								l = a.jqx._ptrnd(l);
								m = a.jqx._ptrnd(m);
								if (this._pointMarker
										&& this._pointMarker.element) {
									this.renderer
											.removeElement(this._pointMarker.element);
									this._pointMarker.element = undefined
								}
								if (isNaN(m) || isNaN(l)) {
									return
								}
								var k = this._getSeriesColors(D, u, z);
								var h = p.opacity;
								if (isNaN(h) || h < 0 || h > 1) {
									h = A.opacity
								}
								if (isNaN(h) || h < 0 || h > 1) {
									h = 1
								}
								var C = p.symbolSizeSelected;
								if (isNaN(C)) {
									C = p.symbolSize
								}
								if (isNaN(C) || C > 50 || C < 0) {
									C = A.symbolSize
								}
								if (isNaN(C) || C > 50 || C < 0) {
									C = 6
								}
								this._pointMarker = {
									type : o,
									x : m,
									y : l,
									gidx : D,
									sidx : u,
									iidx : z
								};
								this._pointMarker.element = this._drawSymbol(o,
										m, l, k.fillColorSymbolSelected,
										k.lineColorSymbolSelected, 1, h, C);
								this._installHandlers(
										this._pointMarker.element,
										"pointMarker", D, u, z);
								this._startTooltipTimer(D,
										this._selected.series, z)
							}
						},
						_drawSymbol : function(i, k, j, l, m, e, f, o) {
							var d;
							var h = o || 6;
							var c = h / 2;
							switch (i) {
							case "none":
								return undefined;
							case "circle":
								d = this.renderer.circle(k, j, h / 2);
								break;
							case "square":
								h = h - 1;
								c = h / 2;
								d = this.renderer.rect(k - c, j - c, h, h);
								break;
							case "diamond":
								var n = "M " + (k - c) + "," + (j) + " L" + (k)
										+ "," + (j - c) + " L" + (k + c) + ","
										+ (j) + " L" + (k) + "," + (j + c)
										+ " Z";
								d = this.renderer.path(n);
								break;
							case "triangle_up":
								var n = "M " + (k - c) + "," + (j + c) + " L "
										+ (k + c) + "," + (j + c) + " L " + (k)
										+ "," + (j - c) + " Z";
								d = this.renderer.path(n);
								break;
							case "triangle_down":
								var n = "M " + (k - c) + "," + (j - c) + " L "
										+ (k) + "," + (j + c) + " L " + (k + c)
										+ "," + (j - c) + " Z";
								d = this.renderer.path(n);
								break;
							case "triangle_left":
								var n = "M " + (k - c) + "," + (j) + " L "
										+ (k + c) + "," + (j + c) + " L "
										+ (k + c) + "," + (j - c) + " Z";
								d = this.renderer.path(n);
								break;
							case "triangle_right":
								var n = "M " + (k - c) + "," + (j - c) + " L "
										+ (k - c) + "," + (j + c) + " L "
										+ (k + c) + "," + (j) + " Z";
								d = this.renderer.path(n);
								break;
							default:
								d = this.renderer.circle(k, j, h)
							}
							this.renderer.attr(d, {
								fill : l,
								stroke : m,
								"stroke-width" : e,
								"stroke-opacity" : f,
								"fill-opacity" : f
							});
							return d
						},
						_getSymbol : function(h, c) {
							var d = [ "circle", "square", "diamond",
									"triangle_up", "triangle_down",
									"triangle_left", "triangle_right" ];
							var f = this.seriesGroups[h];
							var e = f.series[c];
							var i = undefined;
							if (e.symbolType != undefined) {
								i = e.symbolType
							}
							if (i == undefined) {
								i = f.symbolType
							}
							if (i == "default") {
								return d[c % d.length]
							} else {
								if (i != undefined) {
									return i
								}
							}
							return "none"
						},
						_startTooltipTimer : function(i, h, e) {
							this._cancelTooltipTimer();
							var c = this;
							var f = c.seriesGroups[i];
							var d = this.toolTipShowDelay || this.toolTipDelay;
							if (isNaN(d) || d > 10000 || d < 0) {
								d = 500
							}
							if (this._ttEl
									|| (true == this.enableCrosshairs && false == this.showToolTips)) {
								d = 0
							}
							clearTimeout(this._tttimerHide);
							if (d == 0) {
								c._showToolTip(c._mouseX, c._mouseY - 3, i, h,
										e)
							}
							this._tttimer = setTimeout(function() {
								if (d != 0) {
									c._showToolTip(c._mouseX, c._mouseY - 3, i,
											h, e)
								}
								var j = c.toolTipHideDelay;
								if (isNaN(j)) {
									j = 4000
								}
								c._tttimerHide = setTimeout(function() {
									c._hideToolTip();
									c._unselect()
								}, j)
							}, d)
						},
						_cancelTooltipTimer : function() {
							clearTimeout(this._tttimer)
						},
						_getGroupGradientType : function(d) {
							var c = this.seriesGroups[d];
							if (c.type.indexOf("area") != -1) {
								return c.orientation == "horizontal" ? "horizontalLinearGradient"
										: "verticalLinearGradient"
							} else {
								if (c.type.indexOf("column") != -1) {
									if (c.polar) {
										return "radialGradient"
									}
									return c.orientation == "horizontal" ? "verticalLinearGradient"
											: "horizontalLinearGradient"
								} else {
									if (c.type.indexOf("scatter") != -1
											|| c.type.indexOf("bubble") != -1
											|| c.type.indexOf("pie") != -1
											|| c.type.indexOf("donut") != -1) {
										return "radialGradient"
									}
								}
							}
							return undefined
						},
						_select : function(h, l, p, o, i, m) {
							if (this._selected) {
								if ((this._selected.item != i
										|| this._selected.series != o || this._selected.group != p)) {
									this._unselect()
								} else {
									return
								}
							}
							this._selected = {
								element : h,
								type : l,
								group : p,
								series : o,
								item : i,
								iidxBase : m
							};
							var k = this.seriesGroups[p];
							var q = k.series[o];
							var d = k.type.indexOf("line") != -1
									&& k.type.indexOf("area") == -1;
							var c = this._getColors(p, o, m || i, this
									._getGroupGradientType(p));
							var n = c.fillColorSelected;
							if (d) {
								n = "none"
							}
							var f = this._getSerieSettings(p, o);
							var e = l == "symbol" ? c.lineColorSymbolSelected
									: c.lineColorSelected;
							n = l == "symbol" ? c.fillColorSymbolSelected : n;
							var j = f.stroke;
							this.renderer.attr(h, {
								stroke : e,
								fill : n,
								"stroke-width" : j
							});
							this._raiseItemEvent("mouseover", k, q, i)
						},
						_unselect : function() {
							var p = this;
							if (p._selected) {
								var o = p._selected.group;
								var n = p._selected.series;
								var h = p._selected.item;
								var l = p._selected.iidxBase;
								var k = p._selected.type;
								var j = p.seriesGroups[o];
								var q = j.series[n];
								var d = j.type.indexOf("line") != -1
										&& j.type.indexOf("area") == -1;
								var c = p._getColors(o, n, l || h, p
										._getGroupGradientType(o));
								var e = c.fillColor;
								if (d) {
									e = "none"
								}
								var f = p._getSerieSettings(o, n);
								var m = k == "symbol" ? c.lineColorSymbol
										: c.lineColor;
								e = k == "symbol" ? c.fillColorSymbol : e;
								var i = f.stroke;
								this.renderer.attr(p._selected.element, {
									stroke : m,
									fill : e,
									"stroke-width" : i
								});
								p._selected = undefined;
								if (!isNaN(h)) {
									p._raiseItemEvent("mouseout", j, q, h)
								}
							}
							if (p._pointMarker) {
								if (p._pointMarker.element) {
									p.renderer
											.removeElement(p._pointMarker.element);
									p._pointMarker.element = undefined
								}
								p._pointMarker = undefined;
								p._hideCrosshairs()
							}
						},
						_raiseItemEvent : function(h, i, f, d) {
							var e = f[h] || i[h];
							var j = 0;
							for (; j < this.seriesGroups.length; j++) {
								if (this.seriesGroups[j] == i) {
									break
								}
							}
							if (j == this.seriesGroups.length) {
								return
							}
							var c = {
								event : h,
								seriesGroup : i,
								serie : f,
								elementIndex : d,
								elementValue : this._getDataValue(d,
										f.dataField, j)
							};
							if (e && a.isFunction(e)) {
								e(c)
							}
							this._raiseEvent(h, c)
						},
						_raiseEvent : function(e, d) {
							var f = new a.Event(e);
							f.owner = this;
							f.args = d;
							var c = this.host.trigger(f);
							return c
						},
						_calcInterval : function(e, l, k) {
							var o = Math.abs(l - e);
							var m = o / k;
							var h = [ 1, 2, 3, 4, 5, 10, 15, 20, 25, 50, 100 ];
							var c = [ 0.5, 0.25, 0.125, 0.1 ];
							var d = 0.1;
							var j = h;
							if (m < 1) {
								j = c;
								d = 10
							}
							var n = 0;
							do {
								n = 0;
								if (m >= 1) {
									d *= 10
								} else {
									d /= 10
								}
								for ( var f = 1; f < j.length; f++) {
									if (Math.abs(j[n] * d - m) > Math.abs(j[f]
											* d - m)) {
										n = f
									} else {
										break
									}
								}
							} while (n == j.length - 1);
							return j[n] * d
						},
						_renderDataClone : function() {
							if (!this._renderData || this._isToggleRefresh) {
								return
							}
							var e = this._elementRenderInfo = [];
							for ( var k = 0; k < this._renderData.length; k++) {
								var d = this._getCategoryAxis(k).dataField;
								while (e.length <= k) {
									e.push({})
								}
								var c = e[k];
								var h = this._renderData[k];
								if (!h.offsets) {
									continue
								}
								if (h.valueAxis) {
									c.valueAxis = {
										itemOffsets : {}
									};
									for ( var m in h.valueAxis.itemOffsets) {
										c.valueAxis.itemOffsets[m] = h.valueAxis.itemOffsets[m]
									}
								}
								if (h.categoryAxis) {
									c.categoryAxis = {
										itemOffsets : {}
									};
									for ( var m in h.categoryAxis.itemOffsets) {
										c.categoryAxis.itemOffsets[m] = h.categoryAxis.itemOffsets[m]
									}
								}
								c.series = [];
								var j = c.series;
								var l = this.seriesGroups[k].type;
								var o = l.indexOf("pie") != -1
										|| l.indexOf("donut") != -1;
								for ( var p = 0; p < h.offsets.length; p++) {
									j.push({});
									for ( var f = 0; f < h.offsets[p].length; f++) {
										if (!o) {
											j[p][h.xoffsets.xvalues[f]] = {
												value : h.offsets[p][f].value,
												valueFrom : h.offsets[p][f].valueFrom,
												valueRadius : h.offsets[p][f].valueRadius,
												xoffset : h.xoffsets.data[f],
												from : h.offsets[p][f].from,
												to : h.offsets[p][f].to
											}
										} else {
											var n = h.offsets[p][f];
											j[p][n.displayValue] = {
												value : n.value,
												x : n.x,
												y : n.y,
												fromAngle : n.fromAngle,
												toAngle : n.toAngle
											}
										}
									}
								}
							}
						},
						_calcGroupOffsets : function(l, L) {
							var z = this.seriesGroups[l];
							while (this._renderData.length < l + 1) {
								this._renderData.push({})
							}
							if (this._renderData[l] != null
									&& this._renderData[l].offsets != undefined) {
								return this._renderData[l]
							}
							if (z.type.indexOf("pie") != -1
									|| z.type.indexOf("donut") != -1) {
								return this._calcPieSeriesGroupOffsets(l, L)
							}
							if (!z.valueAxis || !z.series
									|| z.series.length == 0) {
								return this._renderData[l]
							}
							var A = z.valueAxis.flip == true;
							var O = z.valueAxis.logarithmicScale == true;
							var N = z.valueAxis.logarithmicScaleBase || 10;
							var T = new Array();
							var F = z.type.indexOf("stacked") != -1;
							var d = F && z.type.indexOf("100") != -1;
							var K = z.type.indexOf("range") != -1;
							var U = z.type.indexOf("column") != -1;
							var q = this._getDataLen(l);
							var p = z.baselineValue
									|| z.valueAxis.baselineValue || 0;
							if (K || (U && F) || d) {
								p = 0
							}
							var af = this._stats.seriesGroups[l];
							if (!af || !af.isValid) {
								return
							}
							if (p > af.max) {
								p = af.max
							}
							if (p < af.min) {
								p = af.min
							}
							var o = (d || O) ? af.maxRange : af.max - af.min;
							var aj = af.min;
							var D = af.max;
							var M = L.height / (O ? af.intervals : o);
							var ah = 0;
							if (d) {
								if (aj * D < 0) {
									o /= 2;
									ah = -(o + p) * M
								} else {
									ah = -p * M
								}
							} else {
								ah = -(p - aj) * M
							}
							if (A) {
								ah = L.y - ah
							} else {
								ah += L.y + L.height
							}
							var ag = new Array();
							var ac = new Array();
							var S = new Array();
							var ai, H;
							if (O) {
								ai = a.jqx.log(D, N) - a.jqx.log(p, N);
								if (F) {
									ai = af.intervals;
									p = d ? 0 : aj
								}
								H = af.intervals - ai;
								if (!A) {
									ah = L.y + ai / af.intervals * L.height
								}
							}
							ah = a.jqx._ptrnd(ah);
							var c = (aj * D < 0) ? L.height / 2 : L.height;
							var m = [];
							var C = [];
							if (z.bands) {
								for ( var aa = 0; aa < z.bands.length; aa++) {
									var u = z.bands[aa].minValue;
									var an = z.bands[aa].maxValue;
									if (isNaN(u)) {
										u = p
									}
									if (isNaN(an)) {
										an = p
									}
									var ap = 0;
									var ao = 0;
									if (O) {
										ap = (a.jqx.log(u, N) - a.jqx.log(p, N))
												* M;
										ao = (a.jqx.log(an, N) - a.jqx
												.log(p, N))
												* M
									} else {
										ap = (u - p) * M;
										ao = (an - p) * M
									}
									if (this._isVML) {
										ap = Math.round(ap);
										ao = Math.round(ao)
									} else {
										ap = a.jqx._ptrnd(ap);
										ao = a.jqx._ptrnd(ao)
									}
									if (A) {
										C.push({
											from : ah + ao,
											to : ah + ap
										})
									} else {
										C.push({
											from : ah - ao,
											to : ah - ap
										})
									}
								}
							}
							var W = [];
							var ak = U || (!U && !F) || d || O;
							for ( var aa = 0; aa < z.series.length; aa++) {
								if (!F && O) {
									m = []
								}
								var E = z.series[aa].dataField;
								var am = z.series[aa].dataFieldFrom;
								var P = z.series[aa].dataFieldTo;
								var Y = z.series[aa].radiusDataField;
								T.push(new Array());
								var f = this._isSerieVisible(l, aa);
								for ( var ab = 0; ab < q; ab++) {
									while (W.length <= ab) {
										W.push(0)
									}
									var al = NaN;
									if (K) {
										al = this._getDataValueAsNumber(ab, am,
												l);
										if (isNaN(al)) {
											al = p
										}
									}
									var J = NaN;
									if (K) {
										J = this
												._getDataValueAsNumber(ab, P, l)
									} else {
										J = this
												._getDataValueAsNumber(ab, E, l)
									}
									var e = this
											._getDataValueAsNumber(ab, Y, l);
									if (!f) {
										J = NaN
									}
									if (isNaN(J) || (O && J <= 0)) {
										T[aa].push({
											from : undefined,
											to : undefined
										});
										continue
									}
									var I;
									if (ak) {
										I = (J >= p) ? ag : ac
									} else {
										W[ab] = J = W[ab] + J
									}
									var ae = M * (J - p);
									if (K) {
										ae = M * (J - al)
									}
									if (O) {
										while (m.length <= ab) {
											m.push({
												p : {
													value : 0,
													height : 0
												},
												n : {
													value : 0,
													height : 0
												}
											})
										}
										var B = K ? al : p;
										var Z = J > B ? m[ab].p : m[ab].n;
										Z.value += J;
										if (d) {
											J = Z.value
													/ (af.psums[ab] + af.nsums[ab])
													* 100;
											ae = (a.jqx.log(J, N) - af.minPow)
													* M
										} else {
											ae = a.jqx.log(Z.value, N)
													- a.jqx.log(B, N);
											ae *= M
										}
										ae -= Z.height;
										Z.height += ae
									}
									var R = ah;
									if (K) {
										var s = 0;
										if (O) {
											s = (a.jqx.log(al, N) - a.jqx.log(
													p, N))
													* M
										} else {
											s = (al - p) * M
										}
										R += A ? s : -s
									}
									if (F) {
										if (d && !O) {
											var w = (af.psums[ab] - af.nsums[ab]);
											if (J > p) {
												ae = (af.psums[ab] / w) * c;
												if (af.psums[ab] != 0) {
													ae *= J / af.psums[ab]
												}
											} else {
												ae = (af.nsums[ab] / w) * c;
												if (af.nsums[ab] != 0) {
													ae *= J / af.nsums[ab]
												}
											}
										}
										if (ak) {
											if (isNaN(I[ab])) {
												I[ab] = R
											}
											R = I[ab]
										}
									}
									if (isNaN(S[ab])) {
										S[ab] = 0
									}
									var ad = S[ab];
									ae = Math.abs(ae);
									var V = ae;
									h_new = this._isVML ? Math.round(ae)
											: a.jqx._ptrnd(ae) - 1;
									if (Math.abs(ae - h_new) > 0.5) {
										ae = Math.round(ae)
									} else {
										ae = h_new
									}
									ad += ae - V;
									if (!F) {
										ad = 0
									}
									if (Math.abs(ad) > 0.5) {
										if (ad > 0) {
											ae -= 1;
											ad -= 1
										} else {
											ae += 1;
											ad += 1
										}
									}
									S[ab] = ad;
									if (aa == z.series.length - 1 && d) {
										var v = 0;
										for ( var X = 0; X < aa; X++) {
											v += Math.abs(T[X][ab].to
													- T[X][ab].from)
										}
										v += ae;
										if (v < c) {
											if (ae > 0.5) {
												ae = a.jqx._ptrnd(ae + c - v)
											} else {
												var X = aa - 1;
												while (X >= 0) {
													var G = Math
															.abs(T[X][ab].to
																	- T[X][ab].from);
													if (G > 1) {
														if (T[X][ab].from > T[X][ab].to) {
															T[X][ab].from += c
																	- v
														}
														break
													}
													X--
												}
											}
										}
									}
									if (A) {
										ae *= -1
									}
									var Q = J < p;
									if (K) {
										Q = al > J
									}
									var n = isNaN(al) ? J : {
										from : al,
										to : J
									};
									if (Q) {
										if (ak) {
											I[ab] += ae
										}
										T[aa].push({
											from : R,
											to : R + ae,
											value : n,
											valueFrom : al,
											valueRadius : e
										})
									} else {
										if (ak) {
											I[ab] -= ae
										}
										T[aa].push({
											from : R,
											to : R - ae,
											value : n,
											valueFrom : al,
											valueRadius : e
										})
									}
								}
							}
							var t = this._renderData[l];
							t.baseOffset = ah;
							t.offsets = T;
							t.bands = C;
							t.xoffsets = this._calculateXOffsets(l, L.width);
							return this._renderData[l]
						},
						_calcPieSeriesGroupOffsets : function(e, c) {
							var n = this._getDataLen(e);
							var o = this.seriesGroups[e];
							var A = this._renderData[e] = {};
							var G = A.offsets = [];
							for ( var C = 0; C < o.series.length; C++) {
								var v = o.series[C];
								var E = v.minAngle;
								if (isNaN(E) || E < 0 || E > 360) {
									E = 0
								}
								var M = v.maxAngle;
								if (isNaN(M) || M < 0 || M > 360) {
									M = 360
								}
								var f = M - E;
								var p = v.initialAngle || 0;
								if (p < E) {
									p = E
								}
								if (p > M) {
									p = M
								}
								var z = p;
								var h = v.radius || Math.min(c.width, c.height)
										* 0.4;
								if (isNaN(h)) {
									h = 1
								}
								var m = v.innerRadius || 0;
								if (isNaN(m) || m >= h) {
									m = 0
								}
								var d = v.centerOffset || 0;
								var K = a.jqx.getNum([ v.offsetX, o.offsetX,
										c.width / 2 ]);
								var J = a.jqx.getNum([ v.offsetY, o.offsetY,
										c.height / 2 ]);
								G.push([]);
								var j = 0;
								var k = 0;
								for ( var F = 0; F < n; F++) {
									var L = this._getDataValueAsNumber(F,
											v.dataField, e);
									if (isNaN(L)) {
										continue
									}
									if (!this._isSerieVisible(e, C, F)
											&& v.hiddenPointsDisplay != true) {
										continue
									}
									if (L > 0) {
										j += L
									} else {
										k += L
									}
								}
								var u = j - k;
								if (u == 0) {
									u = 1
								}
								for ( var F = 0; F < n; F++) {
									var L = this._getDataValueAsNumber(F,
											v.dataField, e);
									if (isNaN(L)) {
										G[C].push({});
										continue
									}
									var D = v.displayText || v.displayField;
									var l = this._getDataValue(F, D, e);
									if (l == undefined) {
										l = F
									}
									var I = 0;
									var B = this._isSerieVisible(e, C, F);
									if (B || v.hiddenPointsDisplay == true) {
										I = Math.abs(L) / u * f
									}
									var t = c.x + K;
									var q = c.y + J;
									var H = d;
									if (a.isFunction(d)) {
										H = d({
											seriesIndex : C,
											seriesGroupIndex : e,
											itemIndex : F
										})
									}
									if (isNaN(H)) {
										H = 0
									}
									var w = {
										key : e + "_" + C + "_" + F,
										value : L,
										displayValue : l,
										x : t,
										y : q,
										fromAngle : z,
										toAngle : z + I,
										centerOffset : H,
										innerRadius : m,
										outerRadius : h,
										visible : B
									};
									G[C].push(w);
									z += I
								}
							}
							return A
						},
						_isPointSeriesOnly : function() {
							for ( var c = 0; c < this.seriesGroups.length; c++) {
								var d = this.seriesGroups[c];
								if (d.type.indexOf("line") == -1
										&& d.type.indexOf("area") == -1
										&& d.type.indexOf("scatter") == -1
										&& d.type.indexOf("bubble") == -1) {
									return false
								}
							}
							return true
						},
						_hasColumnSeries : function() {
							for ( var c = 0; c < this.seriesGroups.length; c++) {
								var d = this.seriesGroups[c];
								if (d.type.indexOf("column") != -1) {
									return true
								}
							}
							return false
						},
						_alignValuesWithTicks : function(h) {
							var c = this._isPointSeriesOnly();
							var d = this.seriesGroups[h];
							var f = this._getCategoryAxis(h);
							var e = f.valuesOnTicks == undefined ? c
									: f.valuesOnTicks != false;
							if (h == undefined) {
								return e
							}
							if (d.valuesOnTicks == undefined) {
								return e
							}
							return d.valuesOnTicks
						},
						_getYearsDiff : function(d, c) {
							return c.getFullYear() - d.getFullYear()
						},
						_getMonthsDiff : function(d, c) {
							return 12 * (c.getFullYear() - d.getFullYear())
									+ c.getMonth() - d.getMonth()
						},
						_getDateDiff : function(h, f, e, c) {
							var d = 0;
							if (e != "year" && e != "month") {
								d = f.valueOf() - h.valueOf()
							}
							switch (e) {
							case "year":
								d = this._getYearsDiff(h, f);
								break;
							case "month":
								d = this._getMonthsDiff(h, f);
								break;
							case "day":
								d /= (24 * 3600 * 1000);
								break;
							case "hour":
								d /= (3600 * 1000);
								break;
							case "minute":
								d /= (60 * 1000);
								break;
							case "second":
								d /= (1000);
								break;
							case "millisecond":
								break
							}
							if (e != "year" && e != "month" && c != false) {
								d = a.jqx._rnd(d, 1, true)
							}
							return d
						},
						_getAsDate : function(c, d) {
							c = this._castAsDate(c);
							if (d == "month") {
								return new Date(c.getFullYear(), c.getMonth(),
										1)
							}
							if (d == "year") {
								return new Date(c.getFullYear(), 0, 1)
							}
							if (d == "day") {
								return new Date(c.getFullYear(), c.getMonth(),
										c.getDate())
							}
							return c
						},
						_getCategoryAxisStats : function(h, z, v) {
							var k = this._getDataLen(h);
							var c = z.type == "date" || z.type == "time";
							var l = c ? this._castAsDate(z.minValue) : this
									._castAsNumber(z.minValue);
							var n = c ? this._castAsDate(z.maxValue) : this
									._castAsNumber(z.maxValue);
							var q = l, t = n;
							var f, m;
							var d = z.type == undefined || z.type == "auto";
							var j = (d || z.type == "basic");
							var s = 0, e = 0;
							for ( var u = 0; u < k && z.dataField; u++) {
								var p = this._getDataValue(u, z.dataField, h);
								p = c ? this._castAsDate(p) : this
										._castAsNumber(p);
								if (isNaN(p)) {
									continue
								}
								if (c) {
									s++
								} else {
									e++
								}
								if (isNaN(f) || p < f) {
									f = p
								}
								if (isNaN(m) || p >= m) {
									m = p
								}
							}
							if (d && ((!c && e == k) || (c && s == k))) {
								j = false
							}
							if (j) {
								f = 0;
								m = k - 1
							}
							if (isNaN(q)) {
								q = f
							}
							if (isNaN(t)) {
								t = m
							}
							if (c) {
								q = new Date(q);
								t = new Date(t);
								if (!this._isDate(q)) {
									q = this._isDate(t) ? t : new Date()
								}
								if (!this._isDate(t)) {
									t = this._isDate(q) ? q : new Date()
								}
							} else {
								if (isNaN(q)) {
									q = 0
								}
								if (isNaN(t)) {
									t = j ? k - 1 : q
								}
							}
							if (f == undefined) {
								f = q
							}
							if (m == undefined) {
								m = t
							}
							var o, A;
							if (c) {
								o = z.baseUnit;
								if (!o) {
									o = "day"
								}
								A = o == "hour" || o == "minute"
										|| o == "second" || o == "millisecond"
							}
							var w = z.unitInterval;
							if (isNaN(w) || w <= 0) {
								w = this._estAxisInterval(q, t, h, v, o)
							}
							if (0) {
								if (o == "second") {
									w *= 1000
								} else {
									if (o == "minute") {
										w *= 60 * 1000
									} else {
										if (o == "hour") {
											w *= 3600 * 1000
										}
									}
								}
							}
							return {
								min : q,
								max : t,
								dsRange : {
									min : f,
									max : m
								},
								useIndeces : j,
								isDateTime : c,
								isTimeUnit : A,
								dateTimeUnit : o,
								interval : w
							}
						},
						_getDefaultDTFormatFn : function(e) {
							var c = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun",
									"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
							var d;
							if (e == "year" || e == "month" || e == "day") {
								d = function(f) {
									return f.getDate() + "-" + c[f.getMonth()]
											+ "-" + f.getFullYear()
								}
							} else {
								d = function(f) {
									return f.getHours() + ":" + f.getMinutes()
											+ ":" + f.getSeconds()
								}
							}
							return d
						},
						_estimateDTIntCnt : function(f, c, d, i) {
							var e = 0;
							var h = new Date(f);
							while (h.valueOf() < c.valueOf()) {
								if (i == "millisecond") {
									h.setMilliseconds(h.getMilliseconds() + d)
								} else {
									if (i == "second") {
										h.setSeconds(h.getSeconds() + d)
									} else {
										if (i == "minute") {
											h.setMinutes(h.getMinutes() + d)
										} else {
											if (i == "hour") {
												h.setHours(h.getHours() + d)
											} else {
												if (i == "day") {
													h.setDate(h.getDate() + d)
												} else {
													if (i == "month") {
														h.setMonth(h.getMonth()
																+ d)
													} else {
														if (i == "year") {
															h
																	.setFullYear(h
																			.getFullYear()
																			+ d)
														}
													}
												}
											}
										}
									}
								}
								e++
							}
							return e
						},
						_estAxisInterval : function(e, j, m, c, k) {
							var d = [ 1, 2, 5, 10, 15, 20, 50, 100, 200, 500 ];
							var f = 0;
							var h = c / 50;
							if (this._renderData
									&& this._renderData.length > m
									&& this._renderData[m].categoryAxis
									&& !isNaN(this._renderData[m].categoryAxis.avgWidth)) {
								var o = Math
										.max(
												1,
												this._renderData[m].categoryAxis.avgWidth);
								if (o != 0) {
									h = 0.9 * c / o
								}
							}
							if (h <= 1) {
								return 1
							}
							var n = 0;
							while (true) {
								var l = f >= d.length ? Math.pow(10, 3 + f
										- d.length) : d[f];
								if (this._isDate(e) && this._isDate(j)) {
									n = this._estimateDTIntCnt(e, j, l, k)
								} else {
									n = (j - e) / l
								}
								if (n <= h) {
									return l
								}
								f++
							}
						},
						_getPaddingSize : function(q, f, h, d, t, u) {
							var i = q.min;
							var m = q.max;
							var c = q.interval;
							var e = q.dateTimeUnit;
							if (t) {
								return {
									left : 0,
									right : (d / Math.max(1, m - i + 1)) * c
								}
							}
							if (h && !u) {
								return {
									left : 0,
									right : 0
								}
							}
							if (this._isDate(i) && this._isDate(m)) {
								var s = this._estimateDTIntCnt(i, m, Math.min(
										c, m - i), e);
								var k = d / Math.max(2, s);
								return {
									left : k / 2,
									right : k / 2
								}
							}
							var s = Math.max(1, m - i);
							var k = d / s;
							var l = k * c / 2;
							if (u) {
								l = Math.max(l, k / 2)
							}
							var n = d - 2 * l;
							var j = a.jqx._rnd(n / Math.max(1, (k * c)), 1,
									false);
							var p = j * k * c;
							k = n / s;
							var o = l - (k * c) / 2;
							l -= o;
							return {
								left : l,
								right : l
							}
						},
						_calculateXOffsets : function(f, C) {
							var B = this.seriesGroups[f];
							var o = this._getCategoryAxis(f);
							var v = new Array();
							var m = new Array();
							var n = this._getDataLen(f);
							var e = this._getCategoryAxisStats(f, o, C);
							var u = e.min;
							var z = e.max;
							var c = e.isDateTime;
							var D = e.isTimeUnit;
							var A = this._hasColumnSeries();
							var d = B.polar || B.spider;
							var l = this._alignValuesWithTicks(f);
							var s = this._getPaddingSize(e, o, l, C, d, A);
							var F = z - u;
							if (F == 0) {
								F = 1
							}
							var E = C - s.left - s.right;
							if (d && l) {
								s.left = s.right = 0
							}
							var j = -1, p = -1;
							for ( var w = 0; w < n; w++) {
								var t = (o.dataField === undefined) ? w : this
										._getDataValue(w, o.dataField, f);
								if (e.useIndeces) {
									if (w < u || w > z) {
										v.push(NaN);
										m.push(undefined);
										continue
									}
									v.push(a.jqx._ptrnd(s.left + (w - u) / F
											* E));
									m.push(t);
									if (j == -1) {
										j = w
									}
									if (p == -1 || p < w) {
										p = w
									}
									continue
								}
								t = c ? this._castAsDate(t) : this
										._castAsNumber(t);
								if (isNaN(t) || t < u || t > z) {
									v.push(NaN);
									m.push(undefined);
									continue
								}
								var q = 0;
								if (!c || (c && D)) {
									diffFromMin = t - u;
									q = (t - u) * E / F
								} else {
									q = (t.valueOf() - u.valueOf())
											/ (z.valueOf() - u.valueOf()) * E
								}
								q = a.jqx._ptrnd(s.left + q);
								v.push(q);
								m.push(t);
								if (j == -1) {
									j = w
								}
								if (p == -1 || p < w) {
									p = w
								}
							}
							if (o.flip == true) {
								for ( var w = 0; w < v.length; w++) {
									if (!isNaN(v[w])) {
										v[w] = C - v[w]
									}
								}
							}
							if (D) {
								F = this._getDateDiff(u, z, o.baseUnit);
								F = a.jqx._rnd(F, 1, false)
							}
							var k = Math.max(1, c ? v.length : F);
							var h = E / k;
							if (j == p) {
								v[j] = s.left + E / 2
							}
							return {
								axisStats : e,
								data : v,
								xvalues : m,
								first : j,
								last : p,
								length : p == -1 ? 0 : p - j + 1,
								itemWidth : h,
								intervalWidth : h * e.interval,
								rangeLength : F,
								useIndeces : e.useIndeces,
								padding : s
							}
						},
						_getCategoryAxis : function(c) {
							if (c == undefined || this.seriesGroups.length <= c) {
								return this.categoryAxis
							}
							return this.seriesGroups[c].categoryAxis
									|| this.categoryAxis
						},
						_isGreyScale : function(f, c) {
							var e = this.seriesGroups[f];
							var d = e.series[c];
							if (d.greyScale == true) {
								return true
							} else {
								if (d.greyScale == false) {
									return false
								}
							}
							if (e.greyScale == true) {
								return true
							} else {
								if (e.greyScale == false) {
									return false
								}
							}
							return this.greyScale == true
						},
						_getSeriesColors : function(h, d, f) {
							var c = this._getSeriesColorsInternal(h, d, f);
							if (this._isGreyScale(h, d)) {
								for ( var e in c) {
									c[e] = a.jqx.toGreyScale(c[e])
								}
							}
							return c
						},
						_getColorFromScheme : function(q, n, c) {
							var e = "#000000";
							var p = this.seriesGroups[q];
							var k = p.series[n];
							if (p.type == "pie" || p.type == "donut") {
								var d = this._getDataLen(q);
								e = this._getItemColorFromScheme(k.colorScheme
										|| p.colorScheme || this.colorScheme, n
										* d + c, q, n)
							} else {
								var o = 0;
								for ( var h = 0; h <= q; h++) {
									for ( var f in this.seriesGroups[h].series) {
										if (h == q && f == n) {
											break
										} else {
											o++
										}
									}
								}
								var m = this.colorScheme;
								if (p.colorScheme) {
									m = p.colorScheme;
									sidex = seriesIndex
								}
								if (m == undefined || m == "") {
									m = this.colorSchemes[0].name
								}
								if (!m) {
									return e
								}
								for ( var h = 0; h < this.colorSchemes.length; h++) {
									var l = this.colorSchemes[h];
									if (l.name == m) {
										while (o > l.colors.length) {
											o -= l.colors.length;
											if (++h >= this.colorSchemes.length) {
												h = 0
											}
											l = this.colorSchemes[h]
										}
										e = l.colors[o % l.colors.length]
									}
								}
							}
							return e
						},
						_colorsCache : {
							get : function(c) {
								if (this._store[c]) {
									return this._store[c]
								}
							},
							set : function(d, c) {
								if (this._size < 10000) {
									this._store[d];
									this._size++
								}
							},
							clear : function() {
								this._store = {};
								this._size = 0
							},
							_size : 0,
							_store : {}
						},
						_getSeriesColorsInternal : function(n, e, c) {
							var i = n + "_" + e + "_" + (isNaN(c) ? "NaN" : c);
							if (this._colorsCache.get(i)) {
								return this._colorsCache.get(i)
							}
							var h = this.seriesGroups[n];
							var p = h.series[e];
							var d = {
								lineColor : "#222222",
								lineColorSelected : "#151515",
								lineColorSymbol : "#222222",
								lineColorSymbolSelected : "#151515",
								fillColor : "#222222",
								fillColorSelected : "#333333",
								fillColorSymbol : "#222222",
								fillColorSymbolSelected : "#333333"
							};
							var j = undefined;
							if (a.isFunction(p.colorFunction)) {
								var k = !isNaN(c) ? this._getDataValue(c,
										p.dataField, n) : NaN;
								if (h.type.indexOf("range") != -1) {
									var f = this._getDataValue(c,
											p.dataFieldFrom, n);
									var m = this._getDataValue(c,
											p.dataFieldTo, n);
									k = {
										from : f,
										to : m
									}
								}
								j = p.colorFunction(k, c, p, h);
								if (typeof (j) == "object") {
									for ( var l in j) {
										d[l] = j[l]
									}
								} else {
									d.fillColor = j
								}
							} else {
								for ( var l in d) {
									if (p.key) {
										d[l] = p[l]
									}
								}
								if (!p.fillColor && !p.color) {
									d.fillColor = this._getColorFromScheme(n,
											e, c)
								} else {
									p.fillColor = p.fillColor || p.color
								}
							}
							var o = {
								fillColor : {
									baseColor : "fillColor",
									adjust : 1
								},
								fillColorSelected : {
									baseColor : "fillColor",
									adjust : 1.1
								},
								fillColorSymbol : {
									baseColor : "fillColor",
									adjust : 1
								},
								fillColorSymbolSelected : {
									baseColor : "fillColorSymbol",
									adjust : 2
								},
								lineColor : {
									baseColor : "fillColor",
									adjust : 0.9
								},
								lineColorSelected : {
									baseColor : "lineColor",
									adjust : 0.8
								},
								lineColorSymbol : {
									baseColor : "lineColor",
									adjust : 1
								},
								lineColorSymbolSelected : {
									baseColor : "lineColorSelected",
									adjust : 1
								}
							};
							for ( var l in d) {
								if (typeof (j) != "object" || !j[l]) {
									if (p[l]) {
										d[l] = p[l]
									} else {
										d[l] = a.jqx.adjustColor(
												d[o[l].baseColor], o[l].adjust)
									}
								}
							}
							this._colorsCache.set(i, d);
							return d
						},
						_getItemColorFromScheme : function(e, h, m, l) {
							if (e == undefined || e == "") {
								e = this.colorSchemes[0].name
							}
							for ( var k = 0; k < this.colorSchemes.length; k++) {
								if (e == this.colorSchemes[k].name) {
									break
								}
							}
							var f = 0;
							while (f <= h) {
								if (k == this.colorSchemes.length) {
									k = 0
								}
								var c = this.colorSchemes[k].colors.length;
								if (f + c <= h) {
									f += c;
									k++
								} else {
									var d = this.colorSchemes[k].colors[h - f];
									if (this._isGreyScale(m, l)
											&& d.indexOf("#") == 0) {
										d = a.jqx.toGreyScale(d)
									}
									return d
								}
							}
						},
						getColorScheme : function(c) {
							for ( var d in this.colorSchemes) {
								if (this.colorSchemes[d].name == c) {
									return this.colorSchemes[d].colors
								}
							}
							return undefined
						},
						addColorScheme : function(d, c) {
							for ( var e in this.colorSchemes) {
								if (this.colorSchemes[e].name == d) {
									this.colorSchemes[e].colors = c;
									return
								}
							}
							this.colorSchemes.push({
								name : d,
								colors : c
							})
						},
						removeColorScheme : function(c) {
							for ( var d in this.colorSchemes) {
								if (this.colorSchemes[d].name == c) {
									this.colorSchemes.splice(d, 1);
									break
								}
							}
						},
						colorSchemes : [
								{
									name : "scheme01",
									colors : [ "#307DD7", "#AA4643", "#89A54E",
											"#71588F", "#4198AF" ]
								},
								{
									name : "scheme02",
									colors : [ "#7FD13B", "#EA157A", "#FEB80A",
											"#00ADDC", "#738AC8" ]
								},
								{
									name : "scheme03",
									colors : [ "#E8601A", "#FF9639", "#F5BD6A",
											"#599994", "#115D6E" ]
								},
								{
									name : "scheme04",
									colors : [ "#D02841", "#FF7C41", "#FFC051",
											"#5B5F4D", "#364651" ]
								},
								{
									name : "scheme05",
									colors : [ "#25A0DA", "#309B46", "#8EBC00",
											"#FF7515", "#FFAE00" ]
								},
								{
									name : "scheme06",
									colors : [ "#0A3A4A", "#196674", "#33A6B2",
											"#9AC836", "#D0E64B" ]
								},
								{
									name : "scheme07",
									colors : [ "#CC6B32", "#FFAB48", "#FFE7AD",
											"#A7C9AE", "#888A63" ]
								},
								{
									name : "scheme08",
									colors : [ "#3F3943", "#01A2A6", "#29D9C2",
											"#BDF271", "#FFFFA6" ]
								},
								{
									name : "scheme09",
									colors : [ "#1B2B32", "#37646F", "#A3ABAF",
											"#E1E7E8", "#B22E2F" ]
								},
								{
									name : "scheme10",
									colors : [ "#5A4B53", "#9C3C58", "#DE2B5B",
											"#D86A41", "#D2A825" ]
								},
								{
									name : "scheme11",
									colors : [ "#993144", "#FFA257", "#CCA56A",
											"#ADA072", "#949681" ]
								},
								{
									name : "scheme12",
									colors : [ "#105B63", "#EEEAC5", "#FFD34E",
											"#DB9E36", "#BD4932" ]
								},
								{
									name : "scheme13",
									colors : [ "#BBEBBC", "#F0EE94", "#F5C465",
											"#FA7642", "#FF1E54" ]
								},
								{
									name : "scheme14",
									colors : [ "#60573E", "#F2EEAC", "#BFA575",
											"#A63841", "#BFB8A3" ]
								},
								{
									name : "scheme15",
									colors : [ "#444546", "#FFBB6E", "#F28D00",
											"#D94F00", "#7F203B" ]
								},
								{
									name : "scheme16",
									colors : [ "#583C39", "#674E49", "#948658",
											"#F0E99A", "#564E49" ]
								},
								{
									name : "scheme17",
									colors : [ "#142D58", "#447F6E", "#E1B65B",
											"#C8782A", "#9E3E17" ]
								},
								{
									name : "scheme18",
									colors : [ "#4D2B1F", "#635D61", "#7992A2",
											"#97BFD5", "#BFDCF5" ]
								},
								{
									name : "scheme19",
									colors : [ "#844341", "#D5CC92", "#BBA146",
											"#897B26", "#55591C" ]
								},
								{
									name : "scheme20",
									colors : [ "#56626B", "#6C9380", "#C0CA55",
											"#F07C6C", "#AD5472" ]
								},
								{
									name : "scheme21",
									colors : [ "#96003A", "#FF7347", "#FFBC7B",
											"#FF4154", "#642223" ]
								},
								{
									name : "scheme22",
									colors : [ "#5D7359", "#E0D697", "#D6AA5C",
											"#8C5430", "#661C0E" ]
								},
								{
									name : "scheme23",
									colors : [ "#16193B", "#35478C", "#4E7AC7",
											"#7FB2F0", "#ADD5F7" ]
								},
								{
									name : "scheme24",
									colors : [ "#7B1A25", "#BF5322", "#9DA860",
											"#CEA457", "#B67818" ]
								},
								{
									name : "scheme25",
									colors : [ "#0081DA", "#3AAFFF", "#99C900",
											"#FFEB3D", "#309B46" ]
								},
								{
									name : "scheme26",
									colors : [ "#0069A5", "#0098EE", "#7BD2F6",
											"#FFB800", "#FF6800" ]
								},
								{
									name : "scheme27",
									colors : [ "#FF6800", "#A0A700", "#FF8D00",
											"#678900", "#0069A5" ]
								} ],
						_formatValue : function(h, k, c, j, f, d) {
							if (h == undefined) {
								return ""
							}
							if (this._isObject(h) && !c) {
								return ""
							}
							if (c) {
								if (!a.isFunction(c)) {
									return h.toString()
								}
								try {
									return c(h, d, f, j)
								} catch (i) {
									return i.message
								}
							}
							if (this._isNumber(h)) {
								return this._formatNumber(h, k)
							}
							if (this._isDate(h)) {
								return this._formatDate(h, k)
							}
							if (k) {
								return (k.prefix || "") + h.toString()
										+ (k.sufix || "")
							}
							return h.toString()
						},
						_getFormattedValue : function(p, h, c, d, f) {
							var k = this.seriesGroups[p];
							var t = k.series[h];
							var q = "";
							var j = d, m = f;
							if (!m) {
								m = t.formatFunction || k.formatFunction
							}
							if (!j) {
								j = t.formatSettings || k.formatSettings
							}
							if (!t.formatFunction && t.formatSettings) {
								m = undefined
							}
							if (k.type.indexOf("range") != -1) {
								var i = this._getDataValue(c, t.dataFieldFrom,
										p);
								var o = this._getDataValue(c, t.dataFieldTo, p);
								if (m && a.isFunction(m)) {
									try {
										return m({
											from : i,
											to : o
										}, c, t, k)
									} catch (l) {
										return l.message
									}
								}
								if (undefined != i) {
									q = this._formatValue(i, j, m, k, t, c)
								}
								if (undefined != o) {
									q += ", "
											+ this._formatValue(o, j, m, k, t,
													c)
								}
							} else {
								var n = this._getDataValue(c, t.dataField, p);
								if (undefined != n) {
									q = this._formatValue(n, j, m, k, t, c)
								}
							}
							return q || ""
						},
						_isNumberAsString : function(e) {
							if (typeof (e) != "string") {
								return false
							}
							e = a.trim(e);
							for ( var c = 0; c < e.length; c++) {
								var d = e.charAt(c);
								if ((d >= "0" && d <= "9") || d == ","
										|| d == ".") {
									continue
								}
								if (d == "-" && c == 0) {
									continue
								}
								if ((d == "(" && c == 0)
										|| (d == ")" && c == e.length - 1)) {
									continue
								}
								return false
							}
							return true
						},
						_castAsDate : function(e) {
							if (e instanceof Date && !isNaN(e)) {
								return e
							}
							if (typeof (e) == "string") {
								var d = new Date(e);
								if (!isNaN(d)) {
									if (e.indexOf(":") == -1) {
										d.setHours(0, 0, 0, 0)
									}
								} else {
									if (a.jqx.dataFormat) {
										var c = a.jqx.dataFormat
												.tryparsedate(e);
										if (c) {
											d = c
										} else {
											d = this._parseISO8601Date(e)
										}
									} else {
										d = this._parseISO8601Date(e)
									}
								}
								if (d != undefined && !isNaN(d)) {
									return d
								}
							}
							return undefined
						},
						_parseISO8601Date : function(i) {
							var m = i.split(" ");
							if (m.length < 0) {
								return NaN
							}
							var c = m[0].split("-");
							var d = m.length == 2 ? m[1].split(":") : "";
							var h = c[0];
							var j = c.length > 1 ? c[1] - 1 : 0;
							var k = c.length > 2 ? c[2] : 1;
							var e = d[1];
							var f = d.length > 1 ? d[1] : 0;
							var e = d.length > 2 ? d[2] : 0;
							var l = d.length > 3 ? d[3] : 0;
							return new Date(h, j, k, e, f, l)
						},
						_castAsNumber : function(d) {
							if (d instanceof Date && !isNaN(d)) {
								return d.valueOf()
							}
							if (typeof (d) == "string") {
								if (this._isNumber(d)) {
									d = parseFloat(d)
								} else {
									var c = new Date(d);
									if (c != undefined) {
										d = c.valueOf()
									}
								}
							}
							return d
						},
						_isNumber : function(c) {
							if (typeof (c) == "string") {
								if (this._isNumberAsString(c)) {
									c = parseFloat(c)
								}
							}
							return typeof c === "number" && isFinite(c)
						},
						_isDate : function(c) {
							return c instanceof Date && !isNaN(c.getDate())
						},
						_isBoolean : function(c) {
							return typeof c === "boolean"
						},
						_isObject : function(c) {
							return (c && (typeof c === "object" || a
									.isFunction(c))) || false
						},
						_formatDate : function(d, c) {
							return d.toString()
						},
						_formatNumber : function(p, f) {
							if (!this._isNumber(p)) {
								return p
							}
							f = f || {};
							var t = f.decimalSeparator || ".";
							var q = f.thousandsSeparator || "";
							var o = f.prefix || "";
							var s = f.sufix || "";
							var k = f.decimalPlaces;
							if (isNaN(k)) {
								k = ((p * 100 != parseInt(p) * 100) ? 2 : 0)
							}
							var n = f.negativeWithBrackets || false;
							var j = (p < 0);
							if (j && n) {
								p *= -1
							}
							var e = p.toString();
							var c;
							var m = Math.pow(10, k);
							e = (Math.round(p * m) / m).toString();
							if (isNaN(e)) {
								e = ""
							}
							c = e.lastIndexOf(".");
							if (k > 0) {
								if (c < 0) {
									e += t;
									c = e.length - 1
								} else {
									if (t !== ".") {
										e = e.replace(".", t)
									}
								}
								while ((e.length - 1 - c) < k) {
									e += "0"
								}
							}
							c = e.lastIndexOf(t);
							c = (c > -1) ? c : e.length;
							var h = e.substring(c);
							var d = 0;
							for ( var l = c; l > 0; l--, d++) {
								if ((d % 3 === 0) && (l !== c)
										&& (!j || (l > 1) || (j && n))) {
									h = q + h
								}
								h = e.charAt(l - 1) + h
							}
							e = h;
							if (j && n) {
								e = "(" + e + ")"
							}
							return o + e + s
						},
						_defaultNumberFormat : {
							prefix : "",
							sufix : "",
							decimalSeparator : ".",
							thousandsSeparator : ",",
							decimalPlaces : 2,
							negativeWithBrackets : false
						},
						_getBezierPoints : function(j) {
							var n = [];
							var k = j.split(" ");
							for ( var h = 0; h < k.length; h++) {
								var o = k[h].split(",");
								n.push({
									x : parseFloat(o[0]),
									y : parseFloat(o[1])
								})
							}
							var q = "";
							if (n.length < 3) {
								for ( var h = 0; h < n.length; h++) {
									q += (h > 0 ? " " : "") + n[h].x + ","
											+ n[h].y
								}
							} else {
								for ( var h = 0; h < n.length - 1; h++) {
									var c = [];
									if (0 == h) {
										c.push(n[h]);
										c.push(n[h]);
										c.push(n[h + 1]);
										c.push(n[h + 2])
									} else {
										if (n.length - 2 == h) {
											c.push(n[h - 1]);
											c.push(n[h]);
											c.push(n[h + 1]);
											c.push(n[h + 1])
										} else {
											c.push(n[h - 1]);
											c.push(n[h]);
											c.push(n[h + 1]);
											c.push(n[h + 2])
										}
									}
									var e = [];
									var l = h > 3 ? 9 : 5;
									var m = h == 0 ? 81 : l;
									var f = {
										x : ((-c[0].x + m * c[1].x + c[2].x) / m),
										y : ((-c[0].y + m * c[1].y + c[2].y) / m)
									};
									if (h == 0) {
										m = l
									}
									var d = {
										x : ((c[1].x + m * c[2].x - c[3].x) / m),
										y : ((c[1].y + m * c[2].y - c[3].y) / m)
									};
									e.push({
										x : c[1].x,
										y : c[1].y
									});
									e.push(f);
									e.push(d);
									e.push({
										x : c[2].x,
										y : c[2].y
									});
									q += "C" + a.jqx._ptrnd(e[1].x) + ","
											+ a.jqx._ptrnd(e[1].y) + " "
											+ a.jqx._ptrnd(e[2].x) + ","
											+ a.jqx._ptrnd(e[2].y) + " "
											+ a.jqx._ptrnd(e[3].x) + ","
											+ a.jqx._ptrnd(e[3].y) + " "
								}
							}
							return q
						},
						_animTickInt : 50,
						_createAnimationGroup : function(c) {
							if (!this._animGroups) {
								this._animGroups = {}
							}
							this._animGroups[c] = {
								animations : [],
								startTick : NaN
							}
						},
						_startAnimation : function(e) {
							var f = new Date();
							var c = f.getTime();
							this._animGroups[e].startTick = c;
							this._runAnimation();
							this._enableAnimTimer()
						},
						_enqueueAnimation : function(f, e, d, i, h, c, j) {
							if (i < 0) {
								i = 0
							}
							if (j == undefined) {
								j = "easeInOutSine"
							}
							this._animGroups[f].animations.push({
								key : e,
								properties : d,
								duration : i,
								fn : h,
								context : c,
								easing : j
							})
						},
						_stopAnimations : function() {
							clearTimeout(this._animtimer);
							this._animtimer = undefined;
							this._animGroups = undefined
						},
						_enableAnimTimer : function() {
							if (!this._animtimer) {
								var c = this;
								this._animtimer = setTimeout(function() {
									c._runAnimation()
								}, this._animTickInt)
							}
						},
						_runAnimation : function(t) {
							if (this._animGroups) {
								var w = new Date();
								var l = w.getTime();
								var s = {};
								for ( var n in this._animGroups) {
									var v = this._animGroups[n].animations;
									var o = this._animGroups[n].startTick;
									var k = 0;
									for ( var q = 0; q < v.length; q++) {
										var z = v[q];
										var c = (l - o);
										if (z.duration > k) {
											k = z.duration
										}
										var u = z.duration > 0 ? c / z.duration
												: 1;
										var m = u;
										if (z.easing && z.duration != 0) {
											m = a.easing[z.easing](u, c, 0, 1,
													z.duration)
										}
										if (u > 1) {
											u = 1;
											m = 1
										}
										if (z.fn) {
											z.fn(z.key, z.context, m);
											continue
										}
										var h = {};
										for ( var n = 0; n < z.properties.length; n++) {
											var e = z.properties[n];
											var f = 0;
											if (u == 1) {
												f = e.to
											} else {
												f = easeParecent
														* (e.to - e.from)
														+ e.from
											}
											h[e.key] = f
										}
										this.renderer.attr(z.key, h)
									}
									if (o + k > l) {
										s[n] = ({
											startTick : o,
											animations : v
										})
									}
								}
								this._animGroups = s;
								if (this.renderer instanceof a.jqx.HTML5Renderer) {
									this.renderer.refresh()
								}
							}
							this._animtimer = null;
							for ( var n in this._animGroups) {
								this._enableAnimTimer();
								break
							}
						}
					});
			a.jqx.toGreyScale = function(c) {
				if (c.indexOf("#") == -1) {
					return c
				}
				var d = a.jqx.cssToRgb(c);
				d[0] = d[1] = d[2] = Math.round(0.3 * d[0] + 0.59 * d[1] + 0.11
						* d[2]);
				var e = a.jqx.rgbToHex(d[0], d[1], d[2]);
				return "#" + e[0] + e[1] + e[2]
			}, a.jqx.adjustColor = function(f, e) {
				if (typeof (f) != "string") {
					return "#000000"
				}
				if (f.indexOf("#") == -1) {
					return f
				}
				var h = a.jqx.cssToRgb(f);
				var d = a.jqx.rgbToHsl(h);
				d[2] = Math.min(1, d[2] * e);
				d[1] = Math.min(1, d[1] * e * 1.1);
				h = a.jqx.hslToRgb(d);
				var f = "#";
				for ( var j = 0; j < 3; j++) {
					var k = Math.round(h[j]);
					k = a.jqx.decToHex(k);
					if (k.toString().length == 1) {
						f += "0"
					}
					f += k
				}
				return f.toUpperCase()
			};
	a.jqx.decToHex = function(c) {
		return c.toString(16)
	};
	a.jqx.hexToDec = function(c) {
		return parseInt(c, 16)
	};
	a.jqx.rgbToHex = function(e, d, c) {
		return [ a.jqx.decToHex(e), a.jqx.decToHex(d), a.jqx.decToHex(c) ]
	};
	a.jqx.hexToRgb = function(d, f, c) {
		return [ a.jqx.hexToDec(d), a.jqx.hexToDec(f), a.jqx.hexToDec(c) ]
	};
	a.jqx.cssToRgb = function(c) {
		if (c.indexOf("rgb") <= -1) {
			return a.jqx.hexToRgb(c.substring(1, 3), c.substring(3, 5), c
					.substring(5, 7))
		}
		return c.substring(4, c.length - 1).split(",")
	};
	a.jqx.hslToRgb = function(d) {
		var f = parseFloat(d[0]);
		var e = parseFloat(d[1]);
		var c = parseFloat(d[2]);
		if (e == 0) {
			r = g = b = c
		} else {
			var i = c < 0.5 ? c * (1 + e) : c + e - c * e;
			var j = 2 * c - i;
			r = a.jqx.hueToRgb(j, i, f + 1 / 3);
			g = a.jqx.hueToRgb(j, i, f);
			b = a.jqx.hueToRgb(j, i, f - 1 / 3)
		}
		return [ r * 255, g * 255, b * 255 ]
	};
	a.jqx.hueToRgb = function(e, d, c) {
		if (c < 0) {
			c += 1
		}
		if (c > 1) {
			c -= 1
		}
		if (c < 1 / 6) {
			return e + (d - e) * 6 * c
		} else {
			if (c < 1 / 2) {
				return d
			} else {
				if (c < 2 / 3) {
					return e + (d - e) * (2 / 3 - c) * 6
				}
			}
		}
		return e
	};
	a.jqx.rgbToHsl = function(j) {
		var c = parseFloat(j[0]) / 255;
		var i = parseFloat(j[1]) / 255;
		var k = parseFloat(j[2]) / 255;
		var m = Math.max(c, i, k), e = Math.min(c, i, k);
		var f, o, d = (m + e) / 2;
		if (m == e) {
			f = o = 0
		} else {
			var n = m - e;
			o = d > 0.5 ? n / (2 - m - e) : n / (m + e);
			switch (m) {
			case c:
				f = (i - k) / n + (i < k ? 6 : 0);
				break;
			case i:
				f = (k - c) / n + 2;
				break;
			case k:
				f = (c - i) / n + 4;
				break
			}
			f /= 6
		}
		return [ f, o, d ]
	};
	a.jqx.swap = function(c, e) {
		var d = c;
		c = e;
		e = d
	};
	a.jqx.getNum = function(c) {
		if (!a.isArray(c)) {
			if (isNaN(c)) {
				return 0
			}
		} else {
			for ( var d = 0; d < c.length; d++) {
				if (!isNaN(c[d])) {
					return c[d]
				}
			}
		}
		return 0
	};
	a.jqx._ptdist = function(d, f, c, e) {
		return Math.sqrt((c - d) * (c - d) + (e - f) * (e - f))
	};
	a.jqx._ptrnd = function(d) {
		if (!document.createElementNS) {
			if (Math.round(d) == d) {
				return d
			}
			return a.jqx._rnd(d, 1, false, true)
		}
		var c = a.jqx._rnd(d, 0.5, false, true);
		if (Math.abs(c - Math.round(c)) != 0.5) {
			return c > d ? c - 0.5 : c + 0.5
		}
		return c
	};
	a.jqx._rup = function(d) {
		var c = Math.round(d);
		if (d > c) {
			c++
		}
		return c
	};
	a.jqx.log = function(d, c) {
		return Math.log(d) / (c ? Math.log(c) : 1)
	};
	a.jqx._mod = function(d, c) {
		var e = Math.abs(d > c ? c : d);
		var f = 1;
		if (e != 0) {
			while (e * f < 100) {
				f *= 10
			}
		}
		d = d * f;
		c = c * f;
		return (d % c) / f
	};
	a.jqx._rnd = function(e, h, f, d) {
		if (isNaN(e)) {
			return e
		}
		var c = e - ((d == true) ? e % h : a.jqx._mod(e, h));
		if (e == c) {
			return c
		}
		if (f) {
			if (e > c) {
				c += h
			}
		} else {
			if (c > e) {
				c -= h
			}
		}
		return c
	};
	a.jqx.commonRenderer = {
		pieSlicePath : function(l, k, i, t, C, D, e) {
			if (!t) {
				t = 1
			}
			var n = Math.abs(C - D);
			var q = n > 180 ? 1 : 0;
			if (n >= 360) {
				D = C + 359.99
			}
			var s = C * Math.PI * 2 / 360;
			var j = D * Math.PI * 2 / 360;
			var A = l, z = l, h = k, f = k;
			var o = !isNaN(i) && i > 0;
			if (o) {
				e = 0
			}
			if (e + i > 0) {
				if (e > 0) {
					var m = n / 2 + C;
					var B = m * Math.PI * 2 / 360;
					l += e * Math.cos(B);
					k -= e * Math.sin(B)
				}
				if (o) {
					var w = i;
					A = l + w * Math.cos(s);
					h = k - w * Math.sin(s);
					z = l + w * Math.cos(j);
					f = k - w * Math.sin(j)
				}
			}
			var v = l + t * Math.cos(s);
			var u = l + t * Math.cos(j);
			var d = k - t * Math.sin(s);
			var c = k - t * Math.sin(j);
			var p = "";
			if (o) {
				p = "M " + z + "," + f;
				p += " a" + i + "," + i;
				p += " 0 " + q + ",1 " + (A - z) + "," + (h - f);
				p += " L" + v + "," + d;
				p += " a" + t + "," + t;
				p += " 0 " + q + ",0 " + (u - v) + "," + (c - d)
			} else {
				p = "M " + u + "," + c;
				p += " a" + t + "," + t;
				p += " 0 " + q + ",1 " + (v - u) + "," + (d - c);
				p += " L" + l + "," + k + " Z"
			}
			return p
		},
		measureText : function(q, h, i, p, n) {
			var f = n._getTextParts(q, h, i);
			var k = f.width;
			var c = f.height;
			if (false == p) {
				c /= 0.6
			}
			var d = {};
			if (isNaN(h)) {
				h = 0
			}
			if (h == 0) {
				d = {
					width : a.jqx._rup(k),
					height : a.jqx._rup(c)
				}
			} else {
				var m = h * Math.PI * 2 / 360;
				var e = Math.abs(Math.sin(m));
				var l = Math.abs(Math.cos(m));
				var j = Math.abs(k * e + c * l);
				var o = Math.abs(k * l + c * e);
				d = {
					width : a.jqx._rup(o),
					height : a.jqx._rup(j)
				}
			}
			if (p) {
				d.textPartsInfo = f
			}
			return d
		},
		alignTextInRect : function(t, p, c, u, o, q, k, s, f, e) {
			var m = f * Math.PI * 2 / 360;
			var d = Math.sin(m);
			var l = Math.cos(m);
			var n = o * d;
			var j = o * l;
			if (k == "center" || k == "" || k == "undefined") {
				t = t + c / 2
			} else {
				if (k == "right") {
					t = t + c
				}
			}
			if (s == "center" || s == "" || s == "undefined") {
				p = p + u / 2
			} else {
				if (s == "bottom") {
					p += u - q / 2
				} else {
					if (s == "top") {
						p += q / 2
					}
				}
			}
			e = e || "";
			var h = "middle";
			if (e.indexOf("top") != -1) {
				h = "top"
			} else {
				if (e.indexOf("bottom") != -1) {
					h = "bottom"
				}
			}
			var i = "center";
			if (e.indexOf("left") != -1) {
				i = "left"
			} else {
				if (e.indexOf("right") != -1) {
					i = "right"
				}
			}
			if (i == "center") {
				t -= j / 2;
				p -= n / 2
			} else {
				if (i == "right") {
					t -= j;
					p -= n
				}
			}
			if (h == "top") {
				t -= q * d;
				p += q * l
			} else {
				if (h == "middle") {
					t -= q * d / 2;
					p += q * l / 2
				}
			}
			t = a.jqx._rup(t);
			p = a.jqx._rup(p);
			return {
				x : t,
				y : p
			}
		}
	};
	a.jqx.svgRenderer = function() {
	};
	a.jqx.svgRenderer.prototype = {
		_svgns : "http://www.w3.org/2000/svg",
		init : function(h) {
			var f = "<table id=tblChart cellspacing='0' cellpadding='0' border='0' align='left' valign='top'>" +
					"<tr><td id=tdTop></td></tr>" +
					"<tr><td><div class='chartContainer' onselectstart='return false;'></div></td></tr>" +
					"</table>";
			h.append(f);
			this.host = h;
			var c = h.find(".chartContainer");
			c[0].style.width = h.width() + "px";
			c[0].style.height = h.height() + "px";
			var j;
			try {
				var d = document.createElementNS(this._svgns, "svg");
				d.setAttribute("id", "svgChart");
				d.setAttribute("version", "1.1");
				d.setAttribute("width", "100%");
				d.setAttribute("height", "100%");
				d.setAttribute("overflow", "hidden");
				c[0].appendChild(d);
				this.canvas = d
			} catch (i) {
				return false
			}
			this._id = new Date().getTime();
			this.clear();
			this._layout();
			this._runLayoutFix();
			return true
		},
		refresh : function() {
		},
		_runLayoutFix : function() {
			var c = this;
			this._fixLayout()
		},
		_fixLayout : function() {
			var i = a(this.canvas).position();
			var e = (parseFloat(i.left) == parseInt(i.left));
			var c = (parseFloat(i.top) == parseInt(i.top));
			if (a.jqx.browser.msie) {
				var e = true, c = true;
				var f = this.host;
				var d = 0, h = 0;
				while (f && f.position && f[0].parentNode) {
					var j = f.position();
					d += parseFloat(j.left) - parseInt(j.left);
					h += parseFloat(j.top) - parseInt(j.top);
					f = f.parent()
				}
				e = parseFloat(d) == parseInt(d);
				c = parseFloat(h) == parseInt(h)
			}
			/*if (!e) {
				this.host.find("#tdLeft")[0].style.width = "0px"
			}*/
			if (!c) {
				this.host.find("#tdTop")[0].style.height = "0.5px"
			}
		},
		_layout : function() {
			var d = a(this.canvas).offset();
			var c = this.host.find(".chartContainer");
			this._width = Math.max(a.jqx._rup(this.host.width()) - 1, 0);
			this._height = Math.max(a.jqx._rup(this.host.height()) - 1, 0);
			c[0].style.width = this._width;
			c[0].style.height = this._height;
			this._fixLayout()
		},
		getRect : function() {
			return {
				x : 0,
				y : 0,
				width : this._width,
				height : this._height
			}
		},
		getContainer : function() {
			var c = this.host.find(".chartContainer");
			return c
		},
		clear : function() {
			while (this.canvas.childElementCount > 0) {
				this.canvas.removeChild(this.canvas.firstElementChild)
			}
			this._defaultParent = undefined;
			this._defs = document.createElementNS(this._svgns, "defs");
			this._gradients = {};
			this.canvas.appendChild(this._defs)
		},
		removeElement : function(e) {
			if (e != undefined) {
				try {
					while (e.firstChild) {
						this.removeElement(e.firstChild)
					}
					if (e.parentNode) {
						e.parentNode.removeChild(e)
					} else {
						this.canvas.removeChild(e)
					}
				} catch (d) {
					var c = d
				}
			}
		},
		_openGroups : [],
		beginGroup : function() {
			var c = this._activeParent();
			var d = document.createElementNS(this._svgns, "g");
			c.appendChild(d);
			this._openGroups.push(d);
			return d
		},
		endGroup : function() {
			if (this._openGroups.length == 0) {
				return
			}
			this._openGroups.pop()
		},
		_activeParent : function() {
			return this._openGroups.length == 0 ? this.canvas
					: this._openGroups[this._openGroups.length - 1]
		},
		createClipRect : function(e) {
			var f = document.createElementNS(this._svgns, "clipPath");
			var d = document.createElementNS(this._svgns, "rect");
			this.attr(d, {
				x : e.x,
				y : e.y,
				width : e.width,
				height : e.height,
				fill : "none"
			});
			this._clipId = this._clipId || 0;
			f.id = "cl" + this._id + "_" + (++this._clipId).toString();
			f.appendChild(d);
			this._defs.appendChild(f);
			return f
		},
		setClip : function(d, c) {
			return this.attr(d, {
				"clip-path" : "url(#" + c.id + ")"
			})
		},
		_clipId : 0,
		addHandler : function(c, e, d) {
			c["on" + e] = d
		},
		shape : function(c, f) {
			var d = document.createElementNS(this._svgns, c);
			if (!d) {
				return undefined
			}
			for ( var e in f) {
				d.setAttribute(e, f[e])
			}
			this._activeParent().appendChild(d);
			return d
		},
		_getTextParts : function(t, j, k) {
			var h = {
				width : 0,
				height : 0,
				parts : []
			};
			var o = 0.6;
			var u = t.toString().split("<br>");
			var q = this._activeParent();
			var m = document.createElementNS(this._svgns, "text");
			this.attr(m, k);
			for ( var l = 0; l < u.length; l++) {
				var d = u[l];
				var f = m.ownerDocument.createTextNode(d);
				m.appendChild(f);
				q.appendChild(m);
				var s;
				try {
					s = m.getBBox()
				} catch (p) {
				}
				var n = a.jqx._rup(s.width);
				var c = a.jqx._rup(s.height * o);
				m.removeChild(f);
				h.width = Math.max(h.width, n);
				h.height += c + (l > 0 ? 4 : 0);
				h.parts.push({
					width : n,
					height : c,
					text : d
				})
			}
			q.removeChild(m);
			return h
		},
		_measureText : function(f, e, d, c) {
			return a.jqx.commonRenderer.measureText(f, e, d, c, this)
		},
		measureText : function(e, d, c) {
			return this._measureText(e, d, c, false)
		},
		text : function(z, t, s, E, C, K, M, L, v, m, d) {
			var B = this._measureText(z, K, M, true);
			var l = B.textPartsInfo;
			var j = l.parts;
			var D;
			if (!v) {
				v = "center"
			}
			if (!m) {
				m = "center"
			}
			if (j.length > 1 || L) {
				D = this.beginGroup()
			}
			if (L) {
				var k = this.createClipRect({
					x : a.jqx._rup(t) - 1,
					y : a.jqx._rup(s) - 1,
					width : a.jqx._rup(E) + 2,
					height : a.jqx._rup(C) + 2
				});
				this.setClip(D, k)
			}
			var q = this._activeParent();
			var O = 0, n = 0;
			var c = 0.6;
			O = l.width;
			n = l.height;
			if (isNaN(E) || E <= 0) {
				E = O
			}
			if (isNaN(C) || C <= 0) {
				C = n
			}
			var u = E || 0;
			var J = C || 0;
			if (!K || K == 0) {
				s += n;
				if (m == "center") {
					s += (J - n) / 2
				} else {
					if (m == "bottom") {
						s += J - n
					}
				}
				if (!E) {
					E = O
				}
				if (!C) {
					C = n
				}
				var q = this._activeParent();
				var p = 0;
				for ( var I = j.length - 1; I >= 0; I--) {
					var A = document.createElementNS(this._svgns, "text");
					this.attr(A, M);
					this.attr(A, {
						cursor : "default"
					});
					var H = A.ownerDocument.createTextNode(j[I].text);
					A.appendChild(H);
					var P = t;
					var o = j[I].width;
					var f = j[I].height;
					if (v == "center") {
						P += (u - o) / 2
					} else {
						if (v == "right") {
							P += (u - o)
						}
					}
					this.attr(A, {
						x : a.jqx._rup(P),
						y : a.jqx._rup(s + p),
						width : a.jqx._rup(o),
						height : a.jqx._rup(f)
					});
					q.appendChild(A);
					p -= j[I].height + 4
				}
				if (D) {
					this.endGroup();
					return D
				}
				return A
			}
			var F = a.jqx.commonRenderer.alignTextInRect(t, s, E, C, O, n, v,
					m, K, d);
			t = F.x;
			s = F.y;
			var G = this.shape("g", {
				transform : "translate(" + t + "," + s + ")"
			});
			var e = this.shape("g", {
				transform : "rotate(" + K + ")"
			});
			G.appendChild(e);
			var p = 0;
			for ( var I = j.length - 1; I >= 0; I--) {
				var N = document.createElementNS(this._svgns, "text");
				this.attr(N, M);
				this.attr(N, {
					cursor : "default"
				});
				var H = N.ownerDocument.createTextNode(j[I].text);
				N.appendChild(H);
				var P = 0;
				var o = j[I].width;
				var f = j[I].height;
				if (v == "center") {
					P += (l.width - o) / 2
				} else {
					if (v == "right") {
						P += (l.width - o)
					}
				}
				this.attr(N, {
					x : a.jqx._rup(P),
					y : a.jqx._rup(p),
					width : a.jqx._rup(o),
					height : a.jqx._rup(f)
				});
				e.appendChild(N);
				p -= f + 4
			}
			q.appendChild(G);
			if (D) {
				this.endGroup()
			}
			return G
		},
		line : function(e, h, d, f, i) {
			var c = this.shape("line", {
				x1 : e,
				y1 : h,
				x2 : d,
				y2 : f
			});
			this.attr(c, i);
			return c
		},
		path : function(d, e) {
			var c = this.shape("path");
			c.setAttribute("d", d);
			if (e) {
				this.attr(c, e)
			}
			return c
		},
		rect : function(c, j, d, f, i) {
			c = a.jqx._ptrnd(c);
			j = a.jqx._ptrnd(j);
			d = a.jqx._rup(d);
			f = a.jqx._rup(f);
			var e = this.shape("rect", {
				x : c,
				y : j,
				width : d,
				height : f
			});
			if (i) {
				this.attr(e, i)
			}
			return e
		},
		circle : function(c, h, e, f) {
			var d = this.shape("circle", {
				cx : c,
				cy : h,
				r : e
			});
			if (f) {
				this.attr(d, f)
			}
			return d
		},
		pieSlicePath : function(d, j, i, f, h, e, c) {
			return a.jqx.commonRenderer.pieSlicePath(d, j, i, f, h, e, c)
		},
		pieslice : function(l, j, i, e, h, c, k, d) {
			var f = this.pieSlicePath(l, j, i, e, h, c, k);
			var m = this.shape("path");
			m.setAttribute("d", f);
			if (d) {
				this.attr(m, d)
			}
			return m
		},
		attr : function(c, e) {
			if (!c || !e) {
				return
			}
			for ( var d in e) {
				if (d == "textContent") {
					c.textContent = e[d]
				} else {
					c.setAttribute(d, e[d])
				}
			}
		},
		getAttr : function(d, c) {
			return d.getAttribute(c)
		},
		_gradients : {},
		_toLinearGradient : function(f, i, j) {
			var d = "grd" + this._id + f.replace("#", "") + (i ? "v" : "h");
			var c = "url(#" + d + ")";
			if (this._gradients[c]) {
				return c
			}
			var e = document.createElementNS(this._svgns, "linearGradient");
			this.attr(e, {
				x1 : "0%",
				y1 : "0%",
				x2 : i ? "0%" : "100%",
				y2 : i ? "100%" : "0%",
				id : d
			});
			for ( var h in j) {
				var l = document.createElementNS(this._svgns, "stop");
				var k = "stop-color:" + a.jqx.adjustColor(f, j[h][1]);
				this.attr(l, {
					offset : j[h][0] + "%",
					style : k
				});
				e.appendChild(l)
			}
			this._defs.appendChild(e);
			this._gradients[c] = true;
			return c
		},
		_toRadialGradient : function(f, j, i) {
			var d = "grd" + this._id + f.replace("#", "") + "r"
					+ (i != undefined ? i.key : "");
			var c = "url(#" + d + ")";
			if (this._gradients[c]) {
				return c
			}
			var e = document.createElementNS(this._svgns, "radialGradient");
			if (i == undefined) {
				this.attr(e, {
					cx : "50%",
					cy : "50%",
					r : "100%",
					fx : "50%",
					fy : "50%",
					id : d
				})
			} else {
				this.attr(e, {
					cx : i.x,
					cy : i.y,
					r : i.outerRadius,
					id : d,
					gradientUnits : "userSpaceOnUse"
				})
			}
			for ( var h in j) {
				var l = document.createElementNS(this._svgns, "stop");
				var k = "stop-color:" + a.jqx.adjustColor(f, j[h][1]);
				this.attr(l, {
					offset : j[h][0] + "%",
					style : k
				});
				e.appendChild(l)
			}
			this._defs.appendChild(e);
			this._gradients[c] = true;
			return c
		}
	};
	a.jqx.vmlRenderer = function() {
	};
	a.jqx.vmlRenderer.prototype = {
		init : function(j) {
			var h = "<div class='chartContainer' style=\"position:relative;overflow:hidden;\"><div>";
			j.append(h);
			this.host = j;
			var c = j.find(".chartContainer");
			c[0].style.width = j.width() + "px";
			c[0].style.height = j.height() + "px";
			var f = true;
			try {
				for ( var d = 0; d < document.namespaces.length; d++) {
					if (document.namespaces[d].name == "v"
							&& document.namespaces[d].urn == "urn:schemas-microsoft-com:vml") {
						f = false;
						break
					}
				}
			} catch (k) {
				return false
			}
			if (a.jqx.browser.msie
					&& parseInt(a.jqx.browser.version) < 9
					&& (document.childNodes && document.childNodes.length > 0
							&& document.childNodes[0].data && document.childNodes[0].data
							.indexOf("DOCTYPE") != -1)) {
				if (f) {
					document.namespaces.add("v",
							"urn:schemas-microsoft-com:vml")
				}
				this._ie8mode = true
			} else {
				if (f) {
					document.namespaces.add("v",
							"urn:schemas-microsoft-com:vml");
					document.createStyleSheet().cssText = "v\\:* { behavior: url(#default#VML); display: inline-block; }"
				}
			}
			this.canvas = c[0];
			this._width = Math.max(a.jqx._rup(c.width()), 0);
			this._height = Math.max(a.jqx._rup(c.height()), 0);
			c[0].style.width = this._width + 2;
			c[0].style.height = this._height + 2;
			this._id = new Date().getTime();
			this.clear();
			return true
		},
		refresh : function() {
		},
		getRect : function() {
			return {
				x : 0,
				y : 0,
				width : this._width,
				height : this._height
			}
		},
		getContainer : function() {
			var c = this.host.find(".chartContainer");
			return c
		},
		clear : function() {
			while (this.canvas.childElementCount > 0) {
				this.canvas.removeChild(this.canvas.firstElementChild)
			}
			this._gradients = {};
			this._defaultParent = undefined
		},
		removeElement : function(c) {
			if (c != null) {
				c.parentNode.removeChild(c)
			}
		},
		_openGroups : [],
		beginGroup : function() {
			var c = this._activeParent();
			var d = document.createElement("v:group");
			d.style.position = "absolute";
			d.coordorigin = "0,0";
			d.coordsize = this._width + "," + this._height;
			d.style.left = 0;
			d.style.top = 0;
			d.style.width = this._width;
			d.style.height = this._height;
			c.appendChild(d);
			this._openGroups.push(d);
			return d
		},
		endGroup : function() {
			if (this._openGroups.length == 0) {
				return
			}
			this._openGroups.pop()
		},
		_activeParent : function() {
			return this._openGroups.length == 0 ? this.canvas
					: this._openGroups[this._openGroups.length - 1]
		},
		createClipRect : function(c) {
			var d = document.createElement("div");
			d.style.height = (c.height + 1) + "px";
			d.style.width = (c.width + 1) + "px";
			d.style.position = "absolute";
			d.style.left = c.x + "px";
			d.style.top = c.y + "px";
			d.style.overflow = "hidden";
			this._clipId = this._clipId || 0;
			d.id = "cl" + this._id + "_" + (++this._clipId).toString();
			this._activeParent().appendChild(d);
			return d
		},
		setClip : function(d, c) {
		},
		_clipId : 0,
		addHandler : function(c, e, d) {
			if (a(c).on) {
				a(c).on(e, d)
			} else {
				a(c).bind(e, d)
			}
		},
		_getTextParts : function(q, h, j) {
			var f = {
				width : 0,
				height : 0,
				parts : []
			};
			var o = 0.6;
			var s = q.toString().split("<br>");
			var p = this._activeParent();
			var l = document.createElement("v:textbox");
			this.attr(l, j);
			p.appendChild(l);
			for ( var k = 0; k < s.length; k++) {
				var d = s[k];
				var e = document.createElement("span");
				e.appendChild(document.createTextNode(d));
				l.appendChild(e);
				if (j && j["class"]) {
					e.className = j["class"]
				}
				var n = a(l);
				var m = a.jqx._rup(n.width());
				var c = a.jqx._rup(n.height() * o);
				if (c == 0 && a.jqx.browser.msie
						&& parseInt(a.jqx.browser.version) < 9) {
					var t = n.css("font-size");
					if (t) {
						c = parseInt(t);
						if (isNaN(c)) {
							c = 0
						}
					}
				}
				l.removeChild(e);
				f.width = Math.max(f.width, m);
				f.height += c + (k > 0 ? 2 : 0);
				f.parts.push({
					width : m,
					height : c,
					text : d
				})
			}
			p.removeChild(l);
			return f
		},
		_measureText : function(f, e, d, c) {
			if (Math.abs(e) > 45) {
				e = 90
			} else {
				e = 0
			}
			return a.jqx.commonRenderer.measureText(f, e, d, c, this)
		},
		measureText : function(e, d, c) {
			return this._measureText(e, d, c, false)
		},
		text : function(u, p, o, D, z, J, L, K, t, k) {
			var E;
			if (L && L.stroke) {
				E = L.stroke
			}
			if (E == undefined) {
				E = "black"
			}
			var v = this._measureText(u, J, L, true);
			var f = v.textPartsInfo;
			var c = f.parts;
			var M = v.width;
			var l = v.height;
			if (isNaN(D) || D == 0) {
				D = M
			}
			if (isNaN(z) || z == 0) {
				z = l
			}
			var B;
			if (!t) {
				t = "center"
			}
			if (!k) {
				k = "center"
			}
			if (c.length > 0 || K) {
				B = this.beginGroup()
			}
			if (K) {
				var d = this.createClipRect({
					x : a.jqx._rup(p),
					y : a.jqx._rup(o),
					width : a.jqx._rup(D),
					height : a.jqx._rup(z)
				});
				this.setClip(B, d)
			}
			var n = this._activeParent();
			var s = D || 0;
			var I = z || 0;
			if (Math.abs(J) > 45) {
				J = 90
			} else {
				J = 0
			}
			var A = 0, H = 0;
			if (t == "center") {
				A += (s - M) / 2
			} else {
				if (t == "right") {
					A += (s - M)
				}
			}
			if (k == "center") {
				H = (I - l) / 2
			} else {
				if (k == "bottom") {
					H = I - l
				}
			}
			if (J == 0) {
				o += l + H;
				p += A
			} else {
				p += M + A;
				o += H
			}
			var m = 0, N = 0;
			var e;
			for ( var G = c.length - 1; G >= 0; G--) {
				var C = c[G];
				var q = (M - C.width) / 2;
				if (J == 0 && t == "left") {
					q = 0
				} else {
					if (J == 0 && t == "right") {
						q = M - C.width
					} else {
						if (J == 90) {
							q = (l - C.width) / 2
						}
					}
				}
				var j = m - C.height;
				H = J == 90 ? q : j;
				A = J == 90 ? j : q;
				e = document.createElement("v:textbox");
				e.style.position = "absolute";
				e.style.left = a.jqx._rup(p + A);
				e.style.top = a.jqx._rup(o + H);
				e.style.width = a.jqx._rup(C.width);
				e.style.height = a.jqx._rup(C.height);
				if (J == 90) {
					e.style.filter = "progid:DXImageTransform.Microsoft.BasicImage(rotation=3)"
				}
				var F = document.createElement("span");
				F.appendChild(document.createTextNode(C.text));
				if (L && L["class"]) {
					F.className = L["class"]
				}
				e.appendChild(F);
				n.appendChild(e);
				m -= C.height + (G > 0 ? 2 : 0)
			}
			if (B) {
				this.endGroup();
				return n
			}
			return e
		},
		shape : function(c, f) {
			var d = document.createElement(this._createElementMarkup(c));
			if (!d) {
				return undefined
			}
			for ( var e in f) {
				d.setAttribute(e, f[e])
			}
			this._activeParent().appendChild(d);
			return d
		},
		line : function(f, i, e, h, j) {
			var c = "M " + f + "," + i + " L " + e + "," + h + " X E";
			var d = this.path(c);
			this.attr(d, j);
			return d
		},
		_createElementMarkup : function(c) {
			var d = "<v:" + c + ' style=""></v:' + c + ">";
			if (this._ie8mode) {
				d = d.replace('style=""',
						'style="behavior: url(#default#VML);"')
			}
			return d
		},
		path : function(d, e) {
			var c = document.createElement(this._createElementMarkup("shape"));
			c.style.position = "absolute";
			c.coordsize = this._width + " " + this._height;
			c.coordorigin = "0 0";
			c.style.width = parseInt(this._width);
			c.style.height = parseInt(this._height);
			c.style.left = 0 + "px";
			c.style.top = 0 + "px";
			c.setAttribute("path", d);
			this._activeParent().appendChild(c);
			if (e) {
				this.attr(c, e)
			}
			return c
		},
		rect : function(c, j, d, e, i) {
			c = a.jqx._ptrnd(c);
			j = a.jqx._ptrnd(j);
			d = a.jqx._rup(d);
			e = a.jqx._rup(e);
			var f = this.shape("rect", i);
			f.style.position = "absolute";
			f.style.left = c;
			f.style.top = j;
			f.style.width = d;
			f.style.height = e;
			f.strokeweight = 0;
			if (i) {
				this.attr(f, i)
			}
			return f
		},
		circle : function(c, h, e, f) {
			var d = this.shape("oval");
			c = a.jqx._ptrnd(c - e);
			h = a.jqx._ptrnd(h - e);
			e = a.jqx._rup(e);
			d.style.position = "absolute";
			d.style.left = c;
			d.style.top = h;
			d.style.width = e * 2;
			d.style.height = e * 2;
			if (f) {
				this.attr(d, f)
			}
			return d
		},
		updateCircle : function(e, c, f, d) {
			if (c == undefined) {
				c = parseFloat(e.style.left) + parseFloat(e.style.width) / 2
			}
			if (f == undefined) {
				f = parseFloat(e.style.top) + parseFloat(e.style.height) / 2
			}
			if (d == undefined) {
				d = parseFloat(e.width) / 2
			}
			c = a.jqx._ptrnd(c - d);
			f = a.jqx._ptrnd(f - d);
			d = a.jqx._rup(d);
			e.style.left = c;
			e.style.top = f;
			e.style.width = d * 2;
			e.style.height = d * 2
		},
		pieSlicePath : function(m, l, j, u, E, F, e) {
			if (!u) {
				u = 1
			}
			var o = Math.abs(E - F);
			var s = o > 180 ? 1 : 0;
			if (o > 360) {
				E = 0;
				F = 360
			}
			var t = E * Math.PI * 2 / 360;
			var k = F * Math.PI * 2 / 360;
			var B = m, A = m, h = l, f = l;
			var p = !isNaN(j) && j > 0;
			if (p) {
				e = 0
			}
			if (e > 0) {
				var n = o / 2 + E;
				var D = n * Math.PI * 2 / 360;
				m += e * Math.cos(D);
				l -= e * Math.sin(D)
			}
			if (p) {
				var z = j;
				B = a.jqx._ptrnd(m + z * Math.cos(t));
				h = a.jqx._ptrnd(l - z * Math.sin(t));
				A = a.jqx._ptrnd(m + z * Math.cos(k));
				f = a.jqx._ptrnd(l - z * Math.sin(k))
			}
			var w = a.jqx._ptrnd(m + u * Math.cos(t));
			var v = a.jqx._ptrnd(m + u * Math.cos(k));
			var d = a.jqx._ptrnd(l - u * Math.sin(t));
			var c = a.jqx._ptrnd(l - u * Math.sin(k));
			u = a.jqx._ptrnd(u);
			j = a.jqx._ptrnd(j);
			m = a.jqx._ptrnd(m);
			l = a.jqx._ptrnd(l);
			var i = Math.round(E * 65535);
			var C = Math.round((F - E) * 65536);
			if (j < 0) {
				j = 1
			}
			var q = "";
			if (p) {
				q = "M" + B + " " + h;
				q += " AE " + m + " " + l + " " + j + " " + j + " " + i + " "
						+ C;
				q += " L " + v + " " + c;
				i = Math.round((E - F) * 65535);
				C = Math.round(F * 65536);
				q += " AE " + m + " " + l + " " + u + " " + u + " " + C + " "
						+ i;
				q += " L " + B + " " + h
			} else {
				q = "M" + m + " " + l;
				q += " AE " + m + " " + l + " " + u + " " + u + " " + i + " "
						+ C
			}
			q += " X E";
			return q
		},
		pieslice : function(m, k, j, f, i, c, l, e) {
			var h = this.pieSlicePath(m, k, j, f, i, c, l);
			var d = this.path(h, e);
			if (e) {
				this.attr(d, e)
			}
			return d
		},
		_keymap : [ {
			svg : "fill",
			vml : "fillcolor"
		}, {
			svg : "stroke",
			vml : "strokecolor"
		}, {
			svg : "stroke-width",
			vml : "strokeweight"
		}, {
			svg : "stroke-dasharray",
			vml : "dashstyle"
		}, {
			svg : "fill-opacity",
			vml : "fillopacity"
		}, {
			svg : "stroke-opacity",
			vml : "strokeopacity"
		}, {
			svg : "opacity",
			vml : "opacity"
		}, {
			svg : "cx",
			vml : "style.left"
		}, {
			svg : "cy",
			vml : "style.top"
		}, {
			svg : "height",
			vml : "style.height"
		}, {
			svg : "width",
			vml : "style.width"
		}, {
			svg : "x",
			vml : "style.left"
		}, {
			svg : "y",
			vml : "style.top"
		}, {
			svg : "d",
			vml : "v"
		}, {
			svg : "display",
			vml : "style.display"
		} ],
		_translateParam : function(c) {
			for ( var d in this._keymap) {
				if (this._keymap[d].svg == c) {
					return this._keymap[d].vml
				}
			}
			return c
		},
		attr : function(d, f) {
			if (!d || !f) {
				return
			}
			for ( var e in f) {
				var c = this._translateParam(e);
				if (c == "fillcolor" && f[e].indexOf("grd") != -1) {
					d.type = f[e]
				} else {
					if (c == "fillcolor" && f[e] == "transparent") {
						d.style.filter = "alpha(opacity=0)";
						d["-ms-filter"] = "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)"
					} else {
						if (c == "opacity" || c == "fillopacity") {
							if (d.fill) {
								d.fill.opacity = f[e]
							}
						} else {
							if (c == "textContent") {
								d.children[0].innerText = f[e]
							} else {
								if (c == "dashstyle") {
									d.dashstyle = f[e].replace(",", " ")
								} else {
									if (c.indexOf("style.") == -1) {
										d[c] = f[e]
									} else {
										d.style[c.replace("style.", "")] = f[e]
									}
								}
							}
						}
					}
				}
			}
		},
		getAttr : function(e, d) {
			var c = this._translateParam(d);
			if (c == "opacity" || c == "fillopacity") {
				if (e.fill) {
					return e.fill.opacity
				} else {
					return 1
				}
			}
			if (c.indexOf("style.") == -1) {
				return e[c]
			}
			return e.style[c.replace("style.", "")]
		},
		_gradients : {},
		_toRadialGradient : function(c, e, d) {
			return c
		},
		_toLinearGradient : function(i, k, l) {
			if (this._ie8mode) {
				return i
			}
			var e = "grd" + i.replace("#", "") + (k ? "v" : "h");
			var f = "#" + e + "";
			if (this._gradients[f]) {
				return f
			}
			var h = document.createElement(this._createElementMarkup("fill"));
			h.type = "gradient";
			h.method = "linear";
			h.angle = k ? 0 : 90;
			var d = "";
			for ( var j in l) {
				if (j > 0) {
					d += ", "
				}
				d += l[j][0] + "% " + a.jqx.adjustColor(i, l[j][1])
			}
			h.colors = d;
			var c = document.createElement(this
					._createElementMarkup("shapetype"));
			c.appendChild(h);
			c.id = e;
			this.canvas.appendChild(c);
			return f
		}
	};
	a.jqx.HTML5Renderer = function() {
	};
	a.jqx.ptrnd = function(d) {
		if (Math.abs(Math.round(d) - d) == 0.5) {
			return d
		}
		var c = Math.round(d);
		if (c < d) {
			c = c - 1
		}
		return c + 0.5
	};
	a.jqx.HTML5Renderer.prototype = {
		_elements : {},
		init : function(c) {
			try {
				this.host = c;
				this.host
						.append("<canvas id='__jqxCanvasWrap' style='width:100%; height: 100%;'/>");
				this.canvas = c.find("#__jqxCanvasWrap");
				this.canvas[0].width = c.width();
				this.canvas[0].height = c.height();
				this.ctx = this.canvas[0].getContext("2d")
			} catch (d) {
				return false
			}
			return true
		},
		getContainer : function() {
			if (this.canvas && this.canvas.length == 1) {
				return this.canvas
			}
			return undefined
		},
		getRect : function() {
			return {
				x : 0,
				y : 0,
				width : this.canvas[0].width - 1,
				height : this.canvas[0].height - 1
			}
		},
		beginGroup : function() {
		},
		endGroup : function() {
		},
		setClip : function() {
		},
		createClipRect : function(c) {
		},
		addHandler : function(c, e, d) {
		},
		clear : function() {
			this._elements = {};
			this._maxId = 0;
			this._renderers._gradients = {};
			this._gradientId = 0
		},
		removeElement : function(c) {
			if (undefined == c) {
				return
			}
			if (this._elements[c.id]) {
				delete this._elements[c.id]
			}
		},
		_maxId : 0,
		shape : function(c, f) {
			var d = {
				type : c,
				id : this._maxId++
			};
			for ( var e in f) {
				d[e] = f[e]
			}
			this._elements[d.id] = d;
			return d
		},
		attr : function(c, e) {
			for ( var d in e) {
				c[d] = e[d]
			}
		},
		rect : function(c, j, d, f, i) {
			if (isNaN(c)) {
				throw 'Invalid value for "x"'
			}
			if (isNaN(j)) {
				throw 'Invalid value for "y"'
			}
			if (isNaN(d)) {
				throw 'Invalid value for "width"'
			}
			if (isNaN(f)) {
				throw 'Invalid value for "height"'
			}
			var e = this.shape("rect", {
				x : c,
				y : j,
				width : d,
				height : f
			});
			if (i) {
				this.attr(e, i)
			}
			return e
		},
		path : function(c, e) {
			var d = this.shape("path", e);
			this.attr(d, {
				d : c
			});
			return d
		},
		line : function(d, f, c, e, h) {
			return this.path("M " + d + "," + f + " L " + c + "," + e, h)
		},
		circle : function(c, h, e, f) {
			var d = this.shape("circle", {
				x : c,
				y : h,
				r : e
			});
			if (f) {
				this.attr(d, f)
			}
			return d
		},
		pieSlicePath : function(d, j, i, f, h, e, c) {
			return a.jqx.commonRenderer.pieSlicePath(d, j, i, f, h, e, c)
		},
		pieslice : function(l, j, i, f, h, c, k, d) {
			var e = this.path(this.pieSlicePath(l, j, i, f, h, c, k), d);
			this.attr(e, {
				x : l,
				y : j,
				innerRadius : i,
				outerRadius : f,
				angleFrom : h,
				angleTo : c
			});
			return e
		},
		_getCSSStyle : function(d) {
			var k = document.styleSheets;
			try {
				for ( var f = 0; f < k.length; f++) {
					for ( var c = 0; k[f].cssRules && c < k[f].cssRules.length; c++) {
						if (k[f].cssRules[c].selectorText.indexOf(d) != -1) {
							return k[f].cssRules[c].style
						}
					}
				}
			} catch (h) {
			}
			return {}
		},
		_getTextParts : function(s, h, j) {
			var n = "Arial";
			var t = "10pt";
			var o = "";
			if (j && j["class"]) {
				var c = this._getCSSStyle(j["class"]);
				if (c.fontSize) {
					t = c.fontSize
				}
				if (c.fontFamily) {
					n = c.fontFamily
				}
				if (c.fontWeight) {
					o = c.fontWeight
				}
			}
			this.ctx.font = o + " " + t + " " + n;
			var f = {
				width : 0,
				height : 0,
				parts : []
			};
			var m = 0.6;
			var q = s.toString().split("<br>");
			for ( var k = 0; k < q.length; k++) {
				var e = q[k];
				var l = this.ctx.measureText(e).width;
				var p = document.createElement("span");
				p.font = this.ctx.font;
				p.textContent = e;
				document.body.appendChild(p);
				var d = p.offsetHeight * m;
				document.body.removeChild(p);
				f.width = Math.max(f.width, a.jqx._rup(l));
				f.height += d + (k > 0 ? 4 : 0);
				f.parts.push({
					width : l,
					height : d,
					text : e
				})
			}
			return f
		},
		_measureText : function(f, e, d, c) {
			return a.jqx.commonRenderer.measureText(f, e, d, c, this)
		},
		measureText : function(e, d, c) {
			return this._measureText(e, d, c, false)
		},
		text : function(o, n, l, d, p, h, i, e, j, m, f) {
			var q = this.shape("text", {
				text : o,
				x : n,
				y : l,
				width : d,
				height : p,
				angle : h,
				clip : e,
				halign : j,
				valign : m,
				rotateAround : f
			});
			if (i) {
				this.attr(q, i)
			}
			q.fontFamily = "Arial";
			q.fontSize = "10pt";
			q.fontWeight = "";
			q.color = "#000000";
			if (i && i["class"]) {
				var c = this._getCSSStyle(i["class"]);
				q.fontFamily = c.fontFamily || q.fontFamily;
				q.fontSize = c.fontSize || q.fontSize;
				q.fontWeight = c.fontWeight || q.fontWeight;
				q.color = c.color || q.color
			}
			var k = this._measureText(o, 0, i, true);
			this.attr(q, {
				textPartsInfo : k.textPartsInfo,
				textWidth : k.width,
				textHeight : k.height
			});
			if (d <= 0 || isNaN(d)) {
				this.attr(q, {
					width : k.width
				})
			}
			if (p <= 0 || isNaN(p)) {
				this.attr(q, {
					height : k.height
				})
			}
			return q
		},
		_toLinearGradient : function(d, j, h) {
			if (this._renderers._gradients[d]) {
				return d
			}
			var c = [];
			for ( var f = 0; f < h.length; f++) {
				c.push({
					percent : h[f][0] / 100,
					color : a.jqx.adjustColor(d, h[f][1])
				})
			}
			var e = "gr" + this._gradientId++;
			this.createGradient(e, j ? "vertical" : "horizontal", c);
			return e
		},
		_toRadialGradient : function(d, h) {
			if (this._renderers._gradients[d]) {
				return d
			}
			var c = [];
			for ( var f = 0; f < h.length; f++) {
				c.push({
					percent : h[f][0] / 100,
					color : a.jqx.adjustColor(d, h[f][1])
				})
			}
			var e = "gr" + this._gradientId++;
			this.createGradient(e, "radial", c);
			return e
		},
		_gradientId : 0,
		createGradient : function(e, d, c) {
			this._renderers.createGradient(e, d, c)
		},
		_renderers : {
			_gradients : {},
			createGradient : function(e, d, c) {
				this._gradients[e] = {
					orientation : d,
					colorStops : c
				}
			},
			setStroke : function(c, d) {
				c.strokeStyle = d.stroke || "transparent";
				c.lineWidth = d["stroke-width"] || 1;
				if (d["fill-opacity"]) {
					c.globalAlpha = d["fill-opacity"]
				} else {
					c.globalAlpha = 1
				}
				if (c.setLineDash) {
					if (d["stroke-dasharray"]) {
						c.setLineDash(d["stroke-dasharray"].split(","))
					} else {
						c.setLineDash([])
					}
				}
			},
			setFillStyle : function(o, f) {
				o.fillStyle = "transparent";
				if (f["fill-opacity"]) {
					o.globalAlpha = f["fill-opacity"]
				} else {
					o.globalAlpha = 1
				}
				if (f.fill && f.fill.indexOf("#") == -1
						&& this._gradients[f.fill]) {
					var m = this._gradients[f.fill].orientation != "horizontal";
					var j = this._gradients[f.fill].orientation == "radial";
					var d = a.jqx.ptrnd(f.x);
					var n = a.jqx.ptrnd(f.y);
					var c = a.jqx.ptrnd(f.x + (m ? 0 : f.width));
					var k = a.jqx.ptrnd(f.y + (m ? f.height : 0));
					var l;
					if ((f.type == "circle" || f.type == "path") && j) {
						x = a.jqx.ptrnd(f.x);
						y = a.jqx.ptrnd(f.y);
						r1 = f.innerRadius || 0;
						r2 = f.outerRadius || f.r || 0;
						l = o.createRadialGradient(x, y, r1, x, y, r2)
					}
					if (!j) {
						if (isNaN(d) || isNaN(c) || isNaN(n) || isNaN(k)) {
							d = 0;
							n = 0;
							c = m ? 0 : o.canvas.width;
							k = m ? o.canvas.height : 0
						}
						l = o.createLinearGradient(d, n, c, k)
					}
					var e = this._gradients[f.fill].colorStops;
					for ( var h = 0; h < e.length; h++) {
						l.addColorStop(e[h].percent, e[h].color)
					}
					o.fillStyle = l
				} else {
					if (f.fill) {
						o.fillStyle = f.fill
					}
				}
			},
			rect : function(c, d) {
				if (d.width == 0 || d.height == 0) {
					return
				}
				c.fillRect(a.jqx.ptrnd(d.x), a.jqx.ptrnd(d.y), d.width,
						d.height);
				c.strokeRect(a.jqx.ptrnd(d.x), a.jqx.ptrnd(d.y), d.width,
						d.height)
			},
			circle : function(c, d) {
				if (d.r == 0) {
					return
				}
				c.beginPath();
				c.arc(a.jqx.ptrnd(d.x), a.jqx.ptrnd(d.y), d.r, 0, Math.PI * 2,
						false);
				c.closePath();
				c.fill();
				c.stroke()
			},
			_parsePoint : function(d) {
				var c = this._parseNumber(d);
				var e = this._parseNumber(d);
				return ({
					x : c,
					y : e
				})
			},
			_parseNumber : function(e) {
				var f = false;
				for ( var c = this._pos; c < e.length; c++) {
					if ((e[c] >= "0" && e[c] <= "9") || e[c] == "."
							|| (e[c] == "-" && !f)) {
						f = true;
						continue
					}
					if (!f && (e[c] == " " || e[c] == ",")) {
						this._pos++;
						continue
					}
					break
				}
				var d = parseFloat(e.substring(this._pos, c));
				if (isNaN(d)) {
					return undefined
				}
				this._pos = c;
				return d
			},
			_pos : 0,
			_cmds : "mlcaz",
			_lastCmd : "",
			_isRelativeCmd : function(c) {
				return a.jqx.string.contains(this._cmds, c)
			},
			_parseCmd : function(c) {
				for ( var d = this._pos; d < c.length; d++) {
					if (a.jqx.string.containsIgnoreCase(this._cmds, c[d])) {
						this._pos = d + 1;
						this._lastCmd = c[d];
						return this._lastCmd
					}
					if (c[d] == " ") {
						this._pos++;
						continue
					}
					if (c[d] >= "0" && c[d] <= "9") {
						this._pos = d;
						if (this._lastCmd == "") {
							break
						} else {
							return this._lastCmd
						}
					}
				}
				return undefined
			},
			_toAbsolutePoint : function(c) {
				return {
					x : this._currentPoint.x + c.x,
					y : this._currentPoint.y + c.y
				}
			},
			_currentPoint : {
				x : 0,
				y : 0
			},
			path : function(E, N) {
				var B = N.d;
				this._pos = 0;
				this._lastCmd = "";
				var n = undefined;
				this._currentPoint = {
					x : 0,
					y : 0
				};
				E.beginPath();
				var I = 0;
				while (this._pos < B.length) {
					var H = this._parseCmd(B);
					if (H == undefined) {
						break
					}
					if (H == "M" || H == "m") {
						var F = this._parsePoint(B);
						if (F == undefined) {
							break
						}
						E.moveTo(F.x, F.y);
						this._currentPoint = F;
						if (n == undefined) {
							n = F
						}
						continue
					}
					if (H == "L" || H == "l") {
						var F = this._parsePoint(B);
						if (F == undefined) {
							break
						}
						E.lineTo(F.x, F.y);
						this._currentPoint = F;
						continue
					}
					if (H == "A" || H == "a") {
						var j = this._parseNumber(B);
						var h = this._parseNumber(B);
						var L = this._parseNumber(B) * (Math.PI / 180);
						var P = this._parseNumber(B);
						var f = this._parseNumber(B);
						var q = this._parsePoint(B);
						if (this._isRelativeCmd(H)) {
							q = this._toAbsolutePoint(q)
						}
						if (j == 0 || h == 0) {
							continue
						}
						var k = this._currentPoint;
						var K = {
							x : Math.cos(L) * (k.x - q.x) / 2 + Math.sin(L)
									* (k.y - q.y) / 2,
							y : -Math.sin(L) * (k.x - q.x) / 2 + Math.cos(L)
									* (k.y - q.y) / 2
						};
						var l = Math.pow(K.x, 2) / Math.pow(j, 2)
								+ Math.pow(K.y, 2) / Math.pow(h, 2);
						if (l > 1) {
							j *= Math.sqrt(l);
							h *= Math.sqrt(l)
						}
						var t = (P == f ? -1 : 1)
								* Math
										.sqrt(((Math.pow(j, 2) * Math.pow(h, 2))
												- (Math.pow(j, 2) * Math.pow(
														K.y, 2)) - (Math.pow(h,
												2) * Math.pow(K.x, 2)))
												/ (Math.pow(j, 2)
														* Math.pow(K.y, 2) + Math
														.pow(h, 2)
														* Math.pow(K.x, 2)));
						if (isNaN(t)) {
							t = 0
						}
						var J = {
							x : t * j * K.y / h,
							y : t * -h * K.x / j
						};
						var D = {
							x : (k.x + q.x) / 2 + Math.cos(L) * J.x
									- Math.sin(L) * J.y,
							y : (k.y + q.y) / 2 + Math.sin(L) * J.x
									+ Math.cos(L) * J.y
						};
						var C = function(i) {
							return Math.sqrt(Math.pow(i[0], 2)
									+ Math.pow(i[1], 2))
						};
						var z = function(m, i) {
							return (m[0] * i[0] + m[1] * i[1]) / (C(m) * C(i))
						};
						var O = function(m, i) {
							return (m[0] * i[1] < m[1] * i[0] ? -1 : 1)
									* Math.acos(z(m, i))
						};
						var G = O([ 1, 0 ],
								[ (K.x - J.x) / j, (K.y - J.y) / h ]);
						var p = [ (K.x - J.x) / j, (K.y - J.y) / h ];
						var o = [ (-K.x - J.x) / j, (-K.y - J.y) / h ];
						var M = O(p, o);
						if (z(p, o) <= -1) {
							M = Math.PI
						}
						if (z(p, o) >= 1) {
							M = 0
						}
						if (f == 0 && M > 0) {
							M = M - 2 * Math.PI
						}
						if (f == 1 && M < 0) {
							M = M + 2 * Math.PI
						}
						var z = (j > h) ? j : h;
						var A = (j > h) ? 1 : j / h;
						var w = (j > h) ? h / j : 1;
						E.translate(D.x, D.y);
						E.rotate(L);
						E.scale(A, w);
						E.arc(0, 0, z, G, G + M, 1 - f);
						E.scale(1 / A, 1 / w);
						E.rotate(-L);
						E.translate(-D.x, -D.y);
						continue
					}
					if ((H == "Z" || H == "z") && n != undefined) {
						E.lineTo(n.x, n.y);
						this._currentPoint = n;
						continue
					}
					if (H == "C" || H == "c") {
						var e = this._parsePoint(B);
						var d = this._parsePoint(B);
						var c = this._parsePoint(B);
						E.bezierCurveTo(e.x, e.y, d.x, d.y, c.x, c.y);
						this._currentPoint = c;
						continue
					}
				}
				E.fill();
				E.stroke();
				E.closePath()
			},
			text : function(A, G) {
				var p = a.jqx.ptrnd(G.x);
				var o = a.jqx.ptrnd(G.y);
				var v = a.jqx.ptrnd(G.width);
				var t = a.jqx.ptrnd(G.height);
				var s = G.halign;
				var k = G.valign;
				var D = G.angle;
				var c = G.rotateAround;
				var f = G.textPartsInfo;
				var e = f.parts;
				var E = G.clip;
				if (E == undefined) {
					E = true
				}
				A.save();
				if (!s) {
					s = "center"
				}
				if (!k) {
					k = "center"
				}
				if (E) {
					A.rect(p, o, v, t);
					A.clip()
				}
				var H = G.textWidth;
				var l = G.textHeight;
				var q = v || 0;
				var C = t || 0;
				A.fillStyle = G.color;
				A.font = G.fontWeight + " " + G.fontSize + " " + G.fontFamily;
				if (!D || D == 0) {
					o += l;
					if (k == "center") {
						o += (C - l) / 2
					} else {
						if (k == "bottom") {
							o += C - l
						}
					}
					if (!v) {
						v = H
					}
					if (!t) {
						t = l
					}
					var n = 0;
					for ( var B = e.length - 1; B >= 0; B--) {
						var u = e[B];
						var I = p;
						var m = e[B].width;
						var d = e[B].height;
						if (s == "center") {
							I += (q - m) / 2
						} else {
							if (s == "right") {
								I += (q - m)
							}
						}
						A.fillText(u.text, I, o + n);
						n -= u.height + (B > 0 ? 4 : 0)
					}
					A.restore();
					return
				}
				var z = a.jqx.commonRenderer.alignTextInRect(p, o, v, t, H, l,
						s, k, D, c);
				p = z.x;
				o = z.y;
				var j = D * Math.PI * 2 / 360;
				A.translate(p, o);
				A.rotate(j);
				var n = 0;
				var F = f.width;
				for ( var B = e.length - 1; B >= 0; B--) {
					var I = 0;
					if (s == "center") {
						I += (F - e[B].width) / 2
					} else {
						if (s == "right") {
							I += (F - e[B].width)
						}
					}
					A.fillText(e[B].text, I, n);
					n -= e[B].height + 4
				}
				A.restore()
			}
		},
		refresh : function() {
			this.ctx.clearRect(0, 0, this.canvas[0].width,
					this.canvas[0].height);
			for ( var c in this._elements) {
				var d = this._elements[c];
				this._renderers.setFillStyle(this.ctx, d);
				this._renderers.setStroke(this.ctx, d);
				this._renderers[this._elements[c].type](this.ctx, d)
			}
		}
	}
})(jQuery);