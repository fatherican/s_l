<%--
  Created by IntelliJ IDEA.
  User: YK
  Date: 2014-08-31
  Time: 10:05:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link  rel="stylesheet"  href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.css" type="text/css">
    <link  rel="stylesheet"  href="<%=request.getContextPath()%>/resources/datables/css/jquery.dataTables_themeroller.css" type="text/css">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(
                function(){
                    $('#example').dataTable({
                        "serverSide": true,


                    });

                }
        );
    </script>
</head>
<body>

<table id="example" class="display" cellspacing="0" width="100%">
<thead>
<tr>
    <th>Name</th>
    <th>age</th>
    <th>gendle</th>
</tr>
</thead>
</table>





</body>
</html>
