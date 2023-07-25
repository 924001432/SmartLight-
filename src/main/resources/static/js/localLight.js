/**
 * Created by Administrator on 2016/8/4.
 */
var setting = {
    view: {
        dblClickExpand: false
    },
    check: {
        enable: true
    },
    callback: {
        onRightClick: OnRightClick,
        onClick: zTreeOnClick
    }
};
var zNodes =[
    {	rid:101	,	id:	1	,pId:101,name:"	所有区域",open:true,nocheck:true,
        children:[
            {id:10, name:"山东省", open:true, noR:true,nocheck:true,
                children:[
                    {id:100, name:"青岛市	", noR:true, open:true,nocheck:true,
                        children:[
                            {id:1000, name:"城阳区	", noR:true, open:true,nocheck:true,
                                children:[
                                    {id:0001, name:"瑞阳路	", noR:true, open:false,nocheck:true},
                                    {id:0002, name:"春阳路	", noR:true, open:false,nocheck:true},
                                    {id:0003, name:"正阳路	", noR:true, open:false,nocheck:true}
                                ]
                            },
                            {id:1001, name:"即墨区	", noR:true, open:true,nocheck:true,
                                children:[
                                    {id:1101, name:"墨城路	", noR:true, open:false,nocheck:true}
                                ]
                            }
                        ]
                    }
                ]
            },
            {id:11, name:"陕西省", open:false, noR:true,nocheck:true,
                children:[
                    {id:101, name:"西安市	", noR:true, open:false,nocheck:true,
                        children:[
                            {id:1010, name:"雁塔区	", noR:true, open:false,nocheck:true,
                            children:[{id:1010, name:"太白南路	", noR:true, open:false,nocheck:true}]}
                        ]}
                        ]
            }
        ]

    }

];
function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        //showRMenu("root", event.clientX, event.clientY);

        console.log(1);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        //showRMenu("node", event.clientX, event.clientY);
        console.log(2);
        console.log(treeNode.noR);

    }
    console.log(321);
}

function zTreeOnClick(event, treeId, treeNode){

    roadName = treeNode.name;

    console.log(deviceCoord);//输出为整型

    document.getElementById("road").innerText  = "灯箱实时监控：" + roadName;//修改

    if(!treeNode.isParent){//到达最底层，路段信息
        //点击路段，不是父节点，查询设备表，得到所有该路段的设备
        deviceCoord = treeNode.id;

        deviceListByDeviceCoord("deviceListByDeviceCoord/" , deviceCoord);

    } else {//父节点

        if( treeNode.id == 1 ){   //所有区域，待定，之后实现数据库获取区域功能

            deviceListByDeviceCoord("/deviceList","");

        } else {
            //查询所有子节点的网络号，整合为一个列表
            var childNodes = zTree.transformToArray(treeNode);
            var deviceCoordList = new Array();
            for(i = 0; i < childNodes.length; i++) {
              deviceCoordList[i] = childNodes[i].id;
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


function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type=="root") {
        $("#m_del").hide();
        $("#m_check").hide();
        $("#m_unCheck").hide();
    } else {
        $("#m_del").show();
        $("#m_check").show();
        $("#m_unCheck").show();
    }
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}
var addCount = 1;

//添加事件
function addTreeNode(names) {
    hideRMenu();
    var newNode = { name:names + (addCount++)};
    if (zTree.getSelectedNodes()[0]) {
        newNode.checked = zTree.getSelectedNodes()[0].checked;
        zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
    } else {
        zTree.addNodes(null, newNode);
    }
}
function removeTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg)==true){
                zTree.removeNode(nodes[0]);
            }
        } else {
            zTree.removeNode(nodes[0]);
        }
    }
}
function checkTreeNode(checked) {
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length>0) {
        zTree.checkNode(nodes[0], checked, true);
    }
    hideRMenu();
}
function resetTree() {
    hideRMenu();
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}
function OnClick(event, treeId, treeNode){
    $(".dropdown_select").val(treeNode.name);
 }

var zTree, rMenu;
$(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            $.fn.zTree.init($("#treeDemo1"), setting, zNodes);
            $.fn.zTree.init($("#treeDemo2"), setting, zNodes);
            $.fn.zTree.init($("#treeDemo3"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            rMenu = $("#rMenu");
});