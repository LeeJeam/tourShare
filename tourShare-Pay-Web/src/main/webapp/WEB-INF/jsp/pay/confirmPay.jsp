<%--
  Created by IntelliJ IDEA.
  User: wangzejun
  Date: 9/11/2018
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
    request.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>确认支付</title>
    <script type="text/javascript" src="${basePath}/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        function doConfirm() {
            var parmsJsonStr = $("#payParams").val();
            if (parmsJsonStr == '' || parmsJsonStr == undefined) {
                alert("请输入测试参数");
                return;
            }
            //var paramsJson=JSON.parse(parmsJsonStr);

            $.ajax({
                url: "http://116.228.64.55:9092/service/gateway/frontTrans.do",
                data: JSON.parse(parmsJsonStr),
                type: 'POST',
                dataType: "jsonp",
                crossDomain: true,
                async: true,
                jsonp: 'doCallBack',
                success: function (data) {
                    console.log("data：" + data);
                }
            });
        }

        function doCallBack(data) {
            console.log("callback：" + data);
        }

        function cleanUrl() {
            $("#payUrl").val("");
        }

        function cleanParams() {
            $("#payParams").val("");
        }
    </script>
</head>
<body>
<table>
    <tr>
        <td valign="top">测试参数:</td>
        <td>
            <textarea name="payParams" id="payParams" style="width:300px;height: 150px;"></textarea>
        </td>
    </tr>
    <tr>
        <td>测试地址:</td>
        <td><input type="text" id="payUrl" name="payUrl" style="width:300px;"
                   value="http://116.228.64.55:9092 /service/gateway/frontTrans.do"/></td>
    </tr>
    <tr>
        <td>确认支付:</td>
        <td>
            <button id="confirmPay" onclick="doConfirm()">确认支付</button>
        </td>
    </tr>
    <tr>
        <td>清空操作:</td>
        <td>
            <button id="cleanUrl" onclick="cleanUrl()">清空地址</button>&nbsp;&nbsp;
            <button id="cleanParam" onclick="cleanParams()">清空参数</button>
        </td>
    </tr>

    <tr>
    </tr>
    <tr>
    </tr>

    <tr>
        <td>测试说明:</td>
        <td>打开浏览器的F12 在选择network->js 双击进入确认页面</td>
    </tr>
</table>
</body>
</html>
