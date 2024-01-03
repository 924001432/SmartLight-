var currentID,zTree, rMenu;
var timer;
var isRefresh;
var curPage = 0;

var globalDeviceCoord = undefined;
var globalDeviceCoordList = undefined;
$(document).ready(function() {

    var userArea=0;
    var userAreaName="";
    //ajax——"/getArea"
    $.ajax({
        url: '/getUserArea',
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

    tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
        var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
        var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

        return  e + d;
    });

        $("[name='inlineRadioOptions']").change(function() {
            var val = $(this).val();
            if( val == 0 ) { //未消警
                if(globalDeviceCoord === undefined && globalDeviceCoordList === undefined){

                    $('#table').bootstrapTable('destroy');

                    tableLoad("/alarmListByalarmStatus/0", function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return e + d;
                    });
                }else if(globalDeviceCoord !== undefined && globalDeviceCoordList === undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "alarmListByalarmArea/"+globalDeviceCoord+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return e + d;
                    });
                }else if(globalDeviceCoord === undefined && globalDeviceCoordList !== undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "/alarmListByDeviceCoordList/"+globalDeviceCoordList+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return e + d;
                    });
                }

            } else if( val == 1 ){//已消警

                if(globalDeviceCoord === undefined && globalDeviceCoordList === undefined){

                    $('#table').bootstrapTable('destroy');

                    tableLoad("/alarmListByalarmStatus/1", function (cellval, row) {
//                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return  d;
                    });
                }else if(globalDeviceCoord !== undefined && globalDeviceCoordList === undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "alarmListByalarmArea/"+globalDeviceCoord+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
//                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return  d;
                    });
                }else if(globalDeviceCoord === undefined && globalDeviceCoordList !== undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "/alarmListByDeviceCoordList/"+globalDeviceCoordList+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
//                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                        return  d;
                    });
                }

            }else if( val == 2) {//已报修

                if(globalDeviceCoord === undefined && globalDeviceCoordList === undefined){

                    $('#table').bootstrapTable('destroy');

                    tableLoad("/alarmListByalarmStatus/2", function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="cancelRepair(\'' + row.alarmId + '\')">撤销</button> ';

                        return e + d;
                    });
                }else if(globalDeviceCoord !== undefined && globalDeviceCoordList === undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "alarmListByalarmArea/"+globalDeviceCoord+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="cancelRepair(\'' + row.alarmId + '\')">撤销</button> ';

                        return e + d;
                    });
                }else if(globalDeviceCoord === undefined && globalDeviceCoordList !== undefined){
                    $('#table').bootstrapTable('destroy');
                    myUrl = "/alarmListByDeviceCoordList/"+globalDeviceCoordList+"/"+val;
                    tableLoad(myUrl, function (cellval, row) {
                        var e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="cancelRepair(\'' + row.alarmId + '\')">撤销</button> ';

                        return e + d;
                    });
                }

            }
        });
    });


function tableLoad(myUrl,myFuc){
    $('#table').bootstrapTable({
        method: "get",
        url: myUrl,
        striped: true,
        singleSelect: false,
        dataType: "json",
        pagination: true, //分页
        pageSize: 10,
        pageNumber: 1,
        search: false, //显示搜索框
        contentType: "application/x-www-form-urlencoded",

        columns: [

            {
                checkbox: "true",
                field: 'check',
                align: 'center',

                valign: 'middle'
            },
            {
                title: '报警编号',
                field: 'alarmId',
                width: 40,
                align: 'center',
                valign: 'middle'
            },
            {
                title: '设备编号',
                field: 'deviceSerial',
                align: 'center',
                width: 50,
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
                    }else  if (cellval == 2){
                        return '<div  style="color:grey"> 已报修 </div>';
                    }else {
                        return cellval;
                    }}
            },
            {
                title: '消警时间',
                field: 'alarmHandletime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '消警备注',
                field: 'alarmHandlecomment',
                align: 'center',
                width: 80,
                valign: 'middle'
            },
            {
                title: '处理措施',
                field: 'alarmHandleway',
                align: 'center',
                width: 80,
                valign: 'middle'
            },
            {
                title: '操作',
                field: 'person',
                width: 150,
                align: 'center',
                formatter: myFuc
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

//撤销报修操作，撤销之后的状态是什么呢
function cancelRepair() {
    layer.confirm('确认撤销报修?', {icon: 3, title:'提示'}, function(index){
    //do something
    $.ajax({
          url: '/cancelRepair/'+alarmId,
          type: 'post',
          dataType: 'text',
          success: function (result) {
                $("#table").bootstrapTable('refresh');
          }
      })


    layer.close(index);
    });
    console.log("cancel repair");
}

function repairAlarm(alarmId){

        console.log(alarmId);
        layer.open({
            type: 2,
            title: '故障报修',
            content: '/alarmRepairPage/' + alarmId,
            shade:[0.8,'#393d49'],
            area:['500px','450px'],
            btn:['确定','取消'],
            yes:function (index,layero) {
                var iframeWindow = window['layui-layer-iframe'+index];
                var submit = layero.find('iframe').contents().find("#LAY-front-submit");
                //监听提交
                iframeWindow.layui.form.on('submit(LAY-front-submit)',function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/alarmRepair',
                        data: field,
                        async: false,
                        cache: false,
                        success: function (str) {
                            if(str.code === 0){
                                console.log(1111);
                            }
                            layer.msg(str.msg,{icon:str.icon,anim:str.anim});
                        }
                    });
                    layer.close(index);     //关闭弹层
                    $("#table").bootstrapTable('refresh');
                    $.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree());
                });
                submit.trigger('click');
            },
            success:function (layero,index) {

            }
        });

    }

/**
 * 以下为区域相关
 * @returns {{name: string, id: number, open: boolean}}
 */
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
    var level = d['areaLevel'];
    var net = d['areaNet'];
    var child = d['child'];

    var node = {
        open : false,
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
    document.getElementById("road").innerText  = "故障报警列表：" + pathName;//修改~·#

    $('#table').bootstrapTable('destroy');

    var val = $("[name='inlineRadioOptions']:checked").val();

    if(!treeNode.isParent){//到达最底层，路段信息
        //点击路段，不是父节点，查询设备表，得到所有该路段的设备
        deviceCoord = treeNode.net;
        globalDeviceCoord = deviceCoord;
        globalDeviceCoordList = undefined;

        var alarmStatus = val;
        alarmListByDeviceCoord("alarmListByalarmArea/" , deviceCoord,alarmStatus);

    } else {//父节点

        if( treeNode.id == 0 ){   //所有区域，待定，之后实现数据库获取区域功能


            tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
                var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                return  e + d;
            });


        } else {
            var alarmStatus = val;
            var childNodes = zTree.transformToArray(treeNode);
            var deviceCoordList = new Array();
            for(i = 0; i < childNodes.length; i++) {
                deviceCoordList[i] = childNodes[i].net;
                //do something——去除undefined
            }
            globalDeviceCoordList = deviceCoordList;
            globalDeviceCoord = undefined;
            console.log(globalDeviceCoord);
            console.log(globalDeviceCoordList);
            alarmListByDeviceCoord("/alarmListByDeviceCoordList/" , deviceCoordList, alarmStatus);

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

function alarmListByDeviceCoord(myUrl,deviceCoord,alarmStatus){

    $('#table').bootstrapTable({
        method: "get",
        striped: true,
        singleSelect: false,
        url: myUrl+deviceCoord+"/"+alarmStatus,
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
                width: 40,
                align: 'center',
                valign: 'middle'
            },
            {
                title: '设备编号',
                field: 'deviceSerial',
                align: 'center',
                width: 50,
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
                    }else  if (cellval == 2){
                        return '<div  style="color:grey"> 已报修 </div>';
                    }else {
                        return cellval;
                    }}
            },
            {
                title: '消警时间',
                field: 'alarmHandletime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '消警备注',
                field: 'alarmHandlecomment',
                align: 'center',
                width: 80,
                valign: 'middle'
            },
            {
                title: '处理措施',
                field: 'alarmHandleway',
                align: 'center',
                width: 80,
                valign: 'middle'
            },
            {
                title: '操作',
                field: 'person',
                width: 150,
                align: 'center',
                formatter: function (cellval, row) {
                    var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                    var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                    return  e + d;
                }
            },

        ]
    });
}

