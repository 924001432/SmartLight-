var zTree, rMenu;
var timer;
var isRefresh;
var curPage = 0;
var curUrl;

var curDeviceCoord;
var curDeviceSerial;
$(document).ready(function(){
    var userArea=0;
    var userAreaName="";
    //ajax——"/getArea"
    $.ajax({
        url: '/getUser',
        type: 'GET',
        dataType: 'json',
        //开启同步可赋值
        success: function (result) {

                console.log(result);
                userArea  = result.userArea ;//修改
                userAreaName  = result.userAreaName ;//修改
                $.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree(userArea,userAreaName));

                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu = $("#rMenu");

            }
    })


    $('#checkTimer').change(function() {
        if ($(this).is(':checked')) {

            console.log("isRefresh = 1");
            isRefresh = 1;
            //开启定时器，而且要识别当前的页面
            console.log(curPage);

            switch(curPage){
                case 1:
                    console.log("开启定时器页面："+curPage);
                    deviceListTimer(curUrl , curDeviceCoord);
                    break;
                case 2:
                    //没有到这个页面就不会有deviceSerial的值
                    console.log("开启定时器页面："+curPage);
                    infoListTimer(curDeviceSerial);
                    break;
                case 3:
                    console.log("开启定时器页面："+curPage);
                    alarmListTimer(curDeviceSerial);
                    break;
                default://没有数据的界面

                    break;
            }

        } else {
            console.log("isRefresh = 0");
            isRefresh = 0;
            //关闭定时器
            clearInterval(timer);
        }
    });

});

function getMenuTree(userArea,userAreaName) {
	var root = {
		id : userArea,
		name : userAreaName,//变量，如何赋值
		open : true,
	};

	$.ajax({
		type : 'get',
//		url : '/area/all',
		url : '/areaListByuserArea/'+userArea,//返回dto格式，包含区域列表，区域id，区域
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(data) {
			var length = data.length;
			var children = [];
			for (var i = 0; i < length; i++) {
				var d = data[i];
				var node = createNode(d);
				children[i] = node;
			}

			root.children = children;
			console.log(root);
		}
	});

	return root;
}

function initMenuDatas(roleId){
	$.ajax({
		type : 'get',
		url : '/permissions?roleId=' + roleId,
		success : function(data) {
			var length = data.length;
			var ids = [];
			for(var i=0; i<length; i++){
				ids.push(data[i]['id']);
			}

			initMenuCheck(ids);
		}
	});
}

function initMenuCheck(ids) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var length = ids.length;
	if(length > 0){
		var node = treeObj.getNodeByParam("id", 0, null);
		treeObj.checkNode(node, true, false);
	}

	for(var i=0; i<length; i++){
		var node = treeObj.getNodeByParam("id", ids[i], null);
		treeObj.checkNode(node, true, false);
	}

}

function getCheckedMenuIds(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);

	var length = nodes.length;
	var ids = [];
	for(var i=0; i<length; i++){
		var n = nodes[i];
		var id = n['id'];
		ids.push(id);
	}

	return ids;
}

function createNode(d) {
	var id = d['areaId'];
	var pId = d['parentId'];
	var name = d['areaName'];
	var net = d['areaNet'];
	var child = d['child'];

	var node = {
		open : true,
		id : id,
		name : name,
		pId : pId,
		net : net,
	};

	if (child != null) {
		var length = child.length;
		if (length > 0) {
			var children = [];
			for (var i = 0; i < length; i++) {
				children[i] = createNode(child[i]);
			}

			node.children = children;
		}

	}
	return node;
}

function initParentMenuSelect(areaLevel){
	$.ajax({
        type : 'get',
        url : '/permissions/parents/' + areaLevel,
        async : false,
        success : function(data) {
            var select = $("#parentId");
            select.append("<option value='0'>root</option>");
            for(var i=0; i<data.length; i++){
                var d = data[i];
                var id = d['areaId'];
                var name = d['areaName'];

                select.append("<option value='"+ id +"'>" +name+"</option>");
            }
        }
    });
}

function getSettting() {
	var setting = {

		async : {
			enable : true,
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			}
		},
		callback : {
			onClick : zTreeOnCheck
		}
	};

	return setting;
}

function zTreeOnCheck(event, treeId, treeNode) {
    var parentNodes = getPath(treeNode);
    var pathNames = [];
    // 打印所有父节点的id和name
    for (var i = 0; i < parentNodes.length; i++) {
        if(parentNodes[i].name === "所有区域"){
            continue;
        }
        pathNames.push(parentNodes[i].name);
    }
    pathNames.push(treeNode.name);
    var pathName = pathNames.join("");
    console.log(pathName);
    document.getElementById("road").innerText  = "灯箱实时监控：" + pathName;//修改~·#


    if(!treeNode.isParent){//到达最底层，路段信息
        //点击路段，不是父节点，查询设备表，得到所有该路段的设备
        DeviceCoord = treeNode.net;

        deviceListTimer("/deviceListByDeviceCoord/" , DeviceCoord)
//        deviceListByDeviceCoord("deviceListByDeviceCoord/" , deviceCoord);
        //启动定时器，30s刷新一次前端页面

    } else {//父节点

        if( treeNode.id == 0 ){   //所有区域，待定，之后实现数据库获取区域功能

//            deviceListByDeviceCoord("/deviceList","");
            deviceListTimer("/deviceList" , "");

        } else {

            var childNodes = zTree.transformToArray(treeNode);
            var deviceCoordList = new Array();
            for(i = 0; i < childNodes.length; i++) {
              deviceCoordList[i] = childNodes[i].net;
              //do something——去除undefined

            }

//            deviceListByDeviceCoord("/deviceListByDeviceCoordList/" , deviceCoordList);
            deviceListTimer("/deviceListByDeviceCoordList/" , deviceCoordList);

        }

    }
}

function getPath(treeNode) {
    // 获取子节点的所有父节点
    var parentNodes = [];
    var parentNode = treeNode.getParentNode();
    while (parentNode !== null) {
        parentNodes.unshift(parentNode);//在数组的开头添加一个或多个元素
        parentNode = parentNode.getParentNode();
    }
    return parentNodes;
}

function deviceListTimer(myUrl,deviceCoord){


    deviceListByDeviceCoord(myUrl,deviceCoord);
    if( isRefresh == 1){
        if(timer != null){
            console.log("clear timer!");
            clearInterval(timer);
        }
        timer = setInterval(function() {
            console.log('继续输出');
            deviceListByDeviceCoord(myUrl,deviceCoord);
            },5000);

    }

}

function deviceListByDeviceCoord(myUrl,deviceCoord){
    curUrl = myUrl;
    curDeviceCoord = deviceCoord;
    curPage = 1;
         $('#table').bootstrapTable('destroy');
            $('#table').bootstrapTable({
                        method: "get",
                        striped: true,
                        singleSelect: false,
                        url: myUrl+deviceCoord,
                        dataType: "json",
                        pagination: true, //分页
                        pageSize: 10,
                        pageNumber: 1,
                        search: false, //显示搜索框
                        contentType: "application/x-www-form-urlencoded",
                        queryParams:null,
                        columns: [
                            {
                                checkbox: "true",
                                field: 'check',
                                align: 'center',

                                valign: 'middle'
                            },
                            {
                                title: "物理地址",
                                field: 'deviceMac',
                                align: 'center',
                                width: 80,
                                valign: 'middle'
                            },
                            {
                                title: '网络地址',
                                field: 'deviceShort',
                                align: 'center',
                                width: 80,
                                valign: 'middle'
                            },
                            {
                                title: '设备标签',
                                field: 'deviceSerial',
                                align: 'center',
                                width: 60,
                                valign: 'middle'
                            },
                            {
                                title: '网关编号',
                                field: 'deviceCoord',
                                align: 'center',
                                width: 60,
                                valign: 'middle'
                            },
                            {
                                title: '经度',
                                field: 'deviceLon',
                                align: 'center',
                                width: 100,
                                valign: 'middle'
                            },
                            {
                                title: '纬度',
                                field: 'deviceLat',
                                align: 'center',
                                width: 100,
                                valign: 'middle'
                            },
                            {
                                title: '设备类型',
                                field: 'deviceType',
                                align: 'center',
                                width: 70,
                                formatter: function (cellval, row) {
                                    if (cellval == 0){
                                        return '<div  "> 路灯 </div>';
                                    } else  if (cellval == 1){
                                        return '<div  "> 路由 </div>';
                                    }else {
                                        return '<div  "> 网关 </div>';;
                                    }
                                }
                            },


                        ]
                    });

}



