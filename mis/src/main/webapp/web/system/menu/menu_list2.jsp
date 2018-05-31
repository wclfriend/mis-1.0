<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>Keyboard Navigation in jqxTreeGrid.</title>
    <meta name="description" content="Keyboard Navigation - JavaScript Tree Grid Demo | jQWidgets">
    <link rel="stylesheet" href="${ctx }/resources/lib/css/treetrid/jqx.base.css" type="text/css" />
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxcore.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxdata.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxbuttons.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxscrollbar.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxdatatable.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/jqxtreegrid.js"></script>
	<script type="text/javascript" src="${ctx }/resources/lib/js/treetrid/demos.js"></script>
</head>
<body>
    <div id='content'>
        
        <div id='jqxWidget' v>
            <div id='treeGrid'>
            </div>
        </div>
    </div>
    <script type="text/javascript">
            $(document).ready(function () {
                var employees = [
                    {
                        "EmployeeID": 1, 
                        "FirstName": "Nancy", 
                        "LastName": "Davolio", 
                        "ReportsTo": 2
                    }, 
                    {
                        "EmployeeID": 2, 
                        "FirstName": "Andrew", 
                        "LastName": "Fuller", 
                        "ReportsTo": null
                    }, 
                    {
                        "EmployeeID": 3, 
                        "FirstName": "Janet", 
                        "LastName": "Leverling", 
                        "ReportsTo": 2
                    }, 
                    {
                        "EmployeeID": 4, 
                        "FirstName": "Margaret", 
                        "LastName": "Peacock", 
                        "ReportsTo": 2
                    }, 
                    {
                        "EmployeeID": 5, 
                        "FirstName": "Steven", 
                        "LastName": "Buchanan", 
                        "ReportsTo": 2
                    }, 
                    {
                        "EmployeeID": 6, 
                        "FirstName": "Michael", 
                        "LastName": "Suyama", 
                        "ReportsTo": 5
                    }, 
                    {
                        "EmployeeID": 7, 
                        "FirstName": "Robert", 
                        "LastName": "King", 
                        "ReportsTo": 5
                    }, 
                    {
                        "EmployeeID": 8, 
                        "FirstName": "Laura", 
                        "LastName": "Callahan", 
                        "ReportsTo": 2
                    }, 
                    {
                        "EmployeeID": 9, 
                        "FirstName": "Anne", 
                        "LastName": "Dodsworth", 
                        "ReportsTo": 5
                    }
                ];
                var source =
                {
                    dataType: "json",
                    dataFields: [
                        { name: 'EmployeeID', type: 'number' },
                        { name: 'ReportsTo', type: 'number' }
                    ],
                    hierarchy:
                    {
                        keyDataField: { name: 'EmployeeID' },
                        parentDataField: { name: 'ReportsTo' }
                    },
                    id: 'EmployeeID',
                    localData: employees
                };
                var dataAdapter = new $.jqx.dataAdapter(source);
                $("#treeGrid").jqxTreeGrid(
                {
                    width: 850,
                    source: dataAdapter,
                    editable: true,
                    ready: function () {
//                         $("#treeGrid").jqxTreeGrid('expandRow', '2');
//                         $("#treeGrid").jqxTreeGrid('selectRow', '2');
                        $("#treeGrid").jqxTreeGrid('focus');
                    },
                    columns: [
                      { text: 'FirstName', columnGroup: 'Name', dataField: 'FirstName'},
                      { text: 'LastName', columnGroup: 'Name', dataField: 'LastName'}
                    ]
                });
            });
        </script>
</body>
</html>
