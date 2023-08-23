var zTree, rMenu;
$(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree());

            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            rMenu = $("#rMenu");
});

function getMenuTree() {
	var root = {
		id : 0,
		name : "所有区域",
		open : true,
	};

	$.ajax({
		type : 'get',
		url : '/area/all',
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
//		check : {
//			enable : true,
//			chkboxType : {
//				"Y" : "ps",
//				"N" : "ps"
//			}
//		},
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
    roadName = treeNode.name;

    console.log(treeNode.net);



    document.getElementById("road").innerText  = "灯箱实时监控：" + roadName;//修改~·#

    if(!treeNode.isParent){//到达最底层，路段信息
        //点击路段，不是父节点，查询设备表，得到所有该路段的设备
        deviceCoord = treeNode.net;

        deviceListByDeviceCoord("deviceListByDeviceCoord/" , deviceCoord);

    } else {//父节点

        if( treeNode.id == 0 ){   //所有区域，待定，之后实现数据库获取区域功能

            deviceListByDeviceCoord("/deviceList","");

        } else {

            var childNodes = zTree.transformToArray(treeNode);
            var deviceCoordList = new Array();
            for(i = 0; i < childNodes.length; i++) {
              deviceCoordList[i] = childNodes[i].net;
              //do something——去除undefined

            }

            deviceListByDeviceCoord("/deviceListByDeviceCoordList/" , deviceCoordList);

        }

    }
}

function deviceListByDeviceCoord(myUrl,deviceCoord){
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
                    }
                    ,
                    {
                        title: "序号",
                        field: 'deviceId',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: "物理地址",
                        field: 'deviceMac',
                        align: 'center',
                        width: 120,
                        valign: 'middle'
                    },
                    {
                        title: '网络地址',
                        field: 'deviceShort',
                        align: 'center',
                        width: 120,
                        valign: 'middle'
                    },
                    {
                        title: '设备标签',
                        field: 'deviceSerial',
                        align: 'center',
                        width: 120,
                        valign: 'middle'
                    },
                    {
                        title: '在线状态',
                        field: 'deviceStatus',
                        width: 70,
                        align: 'center',
                        formatter: function (cellval, row) {
                            if (cellval == 0 ){
                                return '<div  style="color:red"> 离线</div>';
                            } else  if (cellval == 1){
                                return '<div  style="color:green"> 在线 </div>';
                            }else {
                                return cellval;
                            }}
                    },
                    {
                        title: '开关状态',
                        field: 'deviceLight',
                        width: 70,
                        align: 'center',
                        formatter: function (cellval, row) {
                            if (cellval == 1){
                                return '<div  style="color:teal"> 打开 </div>';
                            } else  if (cellval == 2){
                                return '<div  style="color:gray"> 关闭 </div>';
                            }else {
                                return cellval;
                            }}
                    },
                    {
                        title: '网关编号',
                        field: 'deviceCoord',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '设备类型',
                        field: 'deviceType',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },

                    {
                        title: '设备详情',
                        field: 'person',
                        width: 160,
                        align: 'center',
                        formatter: function (cellval, row ,index) {
                            var e = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="infoListByDeviceSerial('+row.deviceSerial+')"><span class="glyphicon glyphicon-stats"></span> 数据信息 </a>'
                            var d = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="alarmListBydeviceSerial('+row.deviceSerial+')"><span class="glyphicon glyphicon-warning-sign"></span> 报警信息 </a>'
                            return  e+d;
                        }
                    },
//                    {
//                        title: '操作',
//                        field: 'person',
//                        width: 120,
//                        align: 'center',
//                        formatter: function (cellval, row ,index) {
//
//                            var  e = '<button  id="add" data-id="98" style="outline:none" class="btn btn-xs btn-success" onclick="SingleLightControl(' + row.deviceSerial + ','+ index +',1)">打开</button> ';
//
//                            var  d = '<button  id="add" data-id="99" style="outline:none" class="btn btn-xs btn-danger" onclick="SingleLightControl(' + row.deviceSerial + ','+ index +',2)">关闭</button> ';
//                            return  e + d;
//                        }
//                    }

                ]
            });
}

function infoListByDeviceSerial(deviceSerial){

    $('#table').bootstrapTable('destroy');
    $('#table').bootstrapTable({
                method: "get",
                striped: true,
                singleSelect: false,
                url: 'infoListByDeviceSerial/'+ deviceSerial,
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
                    }
                    ,
                    {
                        title: "序号",
                        field: 'infoId',
                        align: 'center',
                        width: 40,
                        valign: 'middle'
                    },
                    {
                        title: "设备标签",
                        field: 'infoSerial',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '温度 ℃',
                        field: 'infoTemp',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '湿度 %rh',
                        field: 'infoHumi',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '路灯电压 V',
                        field: 'infoLampV',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '主板电压 V',
                        field: 'infoBoardV',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '光照强度 Lux',
                        field: 'infoLux',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '空气质量 μg/m3',
                        field: 'infoAir',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '更新时间',
                        field: 'infoUpdatetime',
                        align: 'center',
                        width: 120,
                        valign: 'middle'
                    }



                ]
            });
}

function alarmListBydeviceSerial(deviceSerial){

    $('#table').bootstrapTable('destroy');
    $('#table').bootstrapTable({
                method: "get",
                striped: true,
                singleSelect: false,
                url: 'alarmListBydeviceSerial/'+ deviceSerial,
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
                    }
                    ,
                    {
                        title: '报警编号',
                        field: 'alarmId',
                        width: 70,
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: '设备编号',
                        field: 'deviceSerial',
                        align: 'center',
                        width: 100,
                        valign: 'middle'
                    },
                    {
                        title: '报警类型',
                        field: 'alarmType',
                        align: 'center',
                        width: 100,
                        formatter: function (cellval, row) {
                            switch(cellval){
                                case 0:
                                    return '<div  style="color:red"> 湿度报警 </div>';
                                    break;
                                case 1:
                                    return '<div  style="color:red"> 温度报警 </div>';
                                    break;
                                case 2:
                                    return '<div  style="color:red"> 路灯电压报警 </div>';
                                    break;
                                case 3:
                                    return '<div  style="color:red"> 主板电压报警 </div>';
                                    break;
                                case 4:
                                    return '<div  style="color:red"> GPS报警 </div>';
                                    break;
                                case 5:
                                    return '<div  style="color:red"> 路灯报警 </div>';
                                    break;
                            }

                        }
                    },
                    {
                        title: '报警时间',
                        field: 'alarmTime',
                        align: 'center',
                        width: 100,
                        valign: 'middle'
                    },

                    {
                        title: '处理状态',
                        field: 'alarmStatus',
                        width: 100,
                        align: 'center',
                        formatter: function (cellval, row) {
                            if (cellval == 0){
                                return '<div  style="color:red"> 未处理 </div>';
                            } else  if (cellval == 1){
                                return '<div  style="color:green"> 已处理 </div>';
                            }else {
                                return cellval;
                            }}
                    },
//                    {
//                        title: '处理时间',
//                        field: 'alarmHandletime',
//                        align: 'center',
//                        width: 100,
//                        valign: 'middle'
//                    },
                    {
                        title: '操作',
                        field: 'person',
                        width: 120,
                        align: 'center',
                        formatter: function (cellval, row) {
                                     var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                                     var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                                     return  e + d;
                                 }
                    }



                ]
            });
}

function removeAlarm(alarmId) {
    layer.confirm('确认消除警报?', {icon: 3, title:'提示'}, function(index){
      //do something
      $.ajax({
              url: '/removeAlarm/'+alarmId,
              type: 'post',
              dataType: 'text',
              success: function (result) {
                    $("#table").bootstrapTable('refresh');
              }
          })


      layer.close(index);
    });
}

//未完成
function repairAlarm(alarmId) {

    layer.open({
            type: 2,
            title: '分组信息',
            shade: 0.5,
            skin: 'layui-layer-rim',
            area: ['700px', '350px'],
            shadeClose: true,
            closeBtn: 1,
            content: '/alarmLightTail'
        });

//    $.ajax({
//        url: '/removeAlarm/'+alarmId,
//        type: 'post',
//        dataType: 'text',
//        success: function (result) {
//            $('#table').bootstrapTable('destroy');
//            tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
//                                                                      var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
//                                                                      var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="dataLead(\'' + row.alarmId + '\')">报修</button> ';
//
//                                                                      return  e + d;
//                                                                  });
//
//
//        }
//    })
}