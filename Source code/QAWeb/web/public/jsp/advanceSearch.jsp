<%-- 
    Document   : advanceSearch
    Created on : Sep 9, 2010, 3:32:36 PM
    Author     : ThuanHung
--%>

<%@page import="java.util.List"%>
<%@page import="uit.qass.dbconfig.DBInfoUtil"%>
<%@page import="uit.qass.dbconfig.TableInfo"%>
<%@page import="uit.qass.util.StringPool"%>
<%@page import="uit.qass.dbconfig.Type"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="public/javascript/Ajax.js"></script>
<%
List<TableInfo> tables  =   DBInfoUtil.getDBInfo().getTables();
request.setAttribute("tables", tables);
%>
<td class="bg4">
    	<html:form action="loadParams.do">
            <div id ="selectTable"
		<select onchange="retrieveURL(' loadParams.do?tbl=' + this.value);">
                    <c:forEach items="${tables}" var="tbl">
                        <option value="${tbl.name}" ><c:out value="${tbl.aliasName}"/></option>
                    </c:forEach>
		</select>
        </div>
		<br>
		
	</html:form>
    <div>
        <div>
            <span style="color: red;font-weight: bold">
                <html:errors/>
            </span>
            <div id ="advanceSearchForm" >
            <html:form action="/advanceSearch"  >
                <table>
                    <tr style="margin: 5px;">
                        <td>
                        <bean:message key="text.operator" />
                        </td>
                        <td>
                        <html:select property="isAndOperator" name="AdvanceSearchForm">
                            <html:option value="true">AND</html:option>
                            <html:option value="false">OR</html:option>
                        </html:select>
                        </td>
                    </tr>
                    <logic:iterate id="param" name="AdvanceSearchForm" property="params" indexId="id">

                        <tr style="margin: 5px;">
                            <td><bean:write name="param" property="column.aliasName" /></td>
                            <td>
                                <logic:equal value="true"  property="column.type.isNumber" name="param">

                                    <html:select property="param[${id}].operator" name="AdvanceSearchForm">
                                        <html:option value="<%=StringPool.EQUAL %>"><bean:message key="operator.equal"/></html:option>
                                        <html:option value="<%=StringPool.GREATER_THAN_OR_EQUAL %>"><bean:message key="operator.greater_than_or_equal"/></html:option>
                                        <html:option value="<%=StringPool.LESS_THAN_OR_EQUAL %>"><bean:message key="operator.less_than_or_equal"/></html:option>
                                        <html:option value="<%=StringPool.GREATER_THAN %>"><bean:message key="operator.greater_than"/></html:option>
                                        <html:option value="<%=StringPool.LESS_THAN %>"><bean:message key="operator.less_than"/></html:option>
                                    </html:select>

                                </logic:equal>


                                <html:text name="AdvanceSearchForm" property="param[${id}].value" />
                            </td>

                        </tr>
                    </logic:iterate>
                    <tr>
                        <td><html:image property="submit" srcKey="image.submit" altKey="image.submit.alttext"/></td>
                    </tr>
                </table>
                
            </html:form>
            </div>
        </div>
        <!--Show all authors which you search-->
        <div style="text-align: left;background-color: transparent;">
            <span style="color: green"><c:out value="${warning}"/></span>
            <ul>
            <c:forEach var="au" items="${authors}">
                <li style="display: inline-block;width: 200px;">
                    <html:link href="showPubsByAuthor.do?authorName=${au}">
                        <c:out value="${au}"/>
                    </html:link>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div
</td>