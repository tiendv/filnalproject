<%-- 
    Document   : PubsOfAuthor
    Created on : Sep 6, 2010, 8:27:56 PM
    Author     : Hoang-PC
--%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<script type="text/javascript" language="javascript">
    function contextMenuClick(event,id) {
        ev = window.event || event;
        var objContextMenu = document.getElementById("contextmenu");

        objContextMenu.style.left = (ev.pageX + 4) + "px";
        objContextMenu.style.top = (ev.pageY + 4) + "px";
        objContextMenu.style.visibility = "visible";
        objContextMenu.innerHTML = document.getElementById(id).innerHTML;
    }
</script>
<td class="bg4">
    <div id="contextmenu">publication informations
    </div>                
    <div>
        <div style="text-align: left;background-color: transparent;">
            <h2 style="color: green"><c:out value="${authorName}"/></h2>
            <bean:message key="text.total"/>:
            <c:out value="${totalNums}"/><hr>            
        </div>
        <% int i = 1;%>
        <div style="text-align: left;background-color: whitesmoke;">
            <c:forEach var="p" items="${publicationGrouped}">
                <table border="1" style="width: 650px;">
                    <c:forEach var="pub" items="${p}" varStatus="status">
                        <span id="pub<%=i%>" style="visibility: hidden;position: fixed">
                            <h4><bean:message key="text.authors"/></h4>
                            <c:forEach var="au" items="${pub.authors}">
                                <c:out value="${au.author}"/>,
                            </c:forEach>
                            <li>
                                <bean:message key="text.source"/>:
                                <c:out value="${pub.source}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.series"/>:
                                <c:out value="${pub.series}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.type"/>:
                                <c:out value="${pub.type}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.volume"/>:
                                <c:out value="${pub.volume}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.pages"/>:
                                <c:out value="${pub.pages}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.ee"/>:
                                <c:out value="${pub.ee}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.ee_PDF"/>:
                                <c:out value="${pub.ee_PDF}" default="NA"/>
                            </li>
                            <li>
                                <bean:message key="text.url"/>:
                                <c:out value="${pub.url}" default="NA"/>
                            </li>
                        </span>
                        <c:if test="${status.count == 1}">
                            <tr style="text-align: center;background-color: lightcoral">
                                <th><c:out value="${pub.year}"/></th>
                            </tr>
                        </c:if>
                        <tr>
                            <td>
                                <a href="./showPubDetail.do?id=${pub.id}" style="text-decoration: none; "
                                   onmouseover="contextMenuClick(event,'pub<%=i%>');"
                                   onmouseout="document.getElementById('contextmenu').style.visibility = 'hidden'">
                                    <%=i%>. <c:out value="${pub.title}"/>
                                </a>
                                <br>
                                <bean:message key="text.authors"/> : 
                                <c:forEach var="au" items="${pub.authors}">
                                    <c:if test="${au.author==authorName}">
                                        <c:out value="${authorName}"/>,
                                    </c:if>
                                    <c:if test="${au.author!=authorName}">
                                        <a href="showPubsByAuthor.do?authorName=${au.author}">
                                            <c:out value="${au.author}"/>
                                        </a>,
                                    </c:if>
                                </c:forEach>
                            </td>                            
                        </tr>
                        <%i++;%>
                    </c:forEach>
                    <br>
                </table>
            </c:forEach>
        </div>
    </div>
</td>
