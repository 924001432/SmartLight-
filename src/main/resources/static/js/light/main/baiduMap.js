var zTree, rMenu;
var timer;
var isRefresh;
var curPage = 0;
var curUrl;
var curDeviceCoord;
var curDeviceSerial;
var curReq;//当前请求
var zoomLevel;
var isCover;//是否有覆盖物
var deviceList;
var isTableExist = undefined;
var DeviceCoord = undefined;
var DeviceCoordList = undefined;
var marker = undefined;
var label = undefined;
var pathName = undefined;
$(document).ready(function(){
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

            // 获取所有的节点
            var treeNodes = zTree.getNodes(); // 获取所有的树节点,相当于根节点
            var childnodes = zTree.transformToArray(treeNodes);
            console.log(childnodes);
            var deviceCoordList = new Array();
            for (i = 0; i < childnodes.length; i++) {
                if(childnodes[i].net !== undefined){
                    deviceCoordList[i] =childnodes[i].net;
                }
            }
            //deviceCoordList.split(",");
            console.log(deviceCoordList);
            $.ajax({
                url: '/deviceListByDeviceCoordList/'+deviceCoordList, // 替换成你的后台接口地址
                type: 'GET',
                success: function(response) {
                    // 成功获取数据后，更新指定位置的内容
                    $('#total').text("总数: "+response.data.length); // 将获取的数据写入到id为total的元素中
                    $.ajax({
                        url: '/deviceListByIsOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#online').text("在线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/deviceListByIsOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#online').text("在线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/deviceListByIsNotOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#offline').text("离线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/alarmListByDeviceCoordList/'+deviceCoordList+'/0', // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#fault').text("故障: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                },
                error: function(xhr) {
                    console.log(xhr.responseText); // 打印错误信息
                }
            });
        }
    })


    // $('#checkTimer').change(function() {
    //     if ($(this).is(':checked')) {
    //
    //         console.log("isRefresh = 1");
    //         isRefresh = 1;
    //         //开启定时器，而且要识别当前的页面
    //         console.log(curPage);
    //
    //         switch(curPage){
    //             case 1:
    //                 console.log("开启定时器页面："+curPage);
    //                 deviceListTimer(curUrl , curDeviceCoord);
    //                 break;
    //             case 2:
    //                 //没有到这个页面就不会有deviceSerial的值
    //                 console.log("开启定时器页面："+curPage);
    //                 infoListTimer(curDeviceSerial);
    //                 break;
    //             case 3:
    //                 console.log("开启定时器页面："+curPage);
    //                 alarmListTimer(curDeviceSerial);
    //                 break;
    //             default://没有数据的界面
    //
    //                 break;
    //         }
    //
    //     } else {
    //         console.log("isRefresh = 0");
    //         isRefresh = 0;
    //         //关闭定时器
    //         clearInterval(timer);
    //     }
    // });



    //数据详情按钮
    $('#details-button-1').click(function() {

        curReq = '/deviceListByDeviceCoord/'+DeviceCoord;
        // 生成表格
        if (isTableExist !== undefined) {
            $('#modalTable').bootstrapTable('destroy');
        }
        var $table = $('#modalTable').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: curReq,
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
                    width: 40,
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
                    width: 70,
                    valign: 'middle'
                },
                {
                    title: '设备标签',
                    field: 'deviceSerial',
                    align: 'center',
                    width: 70,
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
                    title: '模式',
                    field: 'deviceModel',
                    width: 70,
                    align: 'center',
                    formatter: function (cellval, row) {
                        if (cellval == 0){
                            return '<div  style="color:teal"> 普通模式 </div>';
                        } else  if (cellval == 1){
                            return '<div  style="color:gray"> 雷达模式 </div>';
                        }else {
                            return '<div  style="color:gray"> 光敏模式 </div>';;
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

                        var e = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="infoListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-stats"></span> 数据信息 </a>'
                        var d = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="alarmListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-warning-sign"></span> 报警信息 </a>'
                        return  e+d;
                    }
                },

            ],
            title: '该区域所有设备信息',
        });
        isTableExist = 1;
        // 弹出弹窗
        $('#myModal').modal('show');
    });

    $('#details-button-2').click(function() {

        curReq = '/deviceListByIsOnline/'+DeviceCoord;
        // 生成表格
        if (isTableExist !== undefined) {
            $('#modalTable').bootstrapTable('destroy');
        }
        var $table = $('#modalTable').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: curReq,
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
                    width: 40,
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
                    width: 70,
                    valign: 'middle'
                },
                {
                    title: '设备标签',
                    field: 'deviceSerial',
                    align: 'center',
                    width: 70,
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
                    title: '模式',
                    field: 'deviceModel',
                    width: 70,
                    align: 'center',
                    formatter: function (cellval, row) {
                        if (cellval == 0){
                            return '<div  style="color:teal"> 普通模式 </div>';
                        } else  if (cellval == 1){
                            return '<div  style="color:gray"> 雷达模式 </div>';
                        }else {
                            return '<div  style="color:gray"> 光敏模式 </div>';;
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

                        var e = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="infoListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-stats"></span> 数据信息 </a>'
                        var d = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="alarmListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-warning-sign"></span> 报警信息 </a>'
                        return  e+d;
                    }
                },

            ],
            title: '该区域所有在线设备信息',
        });
        isTableExist = 1;
        // 弹出弹窗
        $('#myModal').modal('show');
    });
    $('#details-button-3').click(function() {

        curReq = '/deviceListByIsNotOnline/'+DeviceCoord;
        // 生成表格
        if (isTableExist !== undefined) {
            $('#modalTable').bootstrapTable('destroy');
        }
        var $table = $('#modalTable').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: curReq,
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
                    width: 40,
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
                    width: 70,
                    valign: 'middle'
                },
                {
                    title: '设备标签',
                    field: 'deviceSerial',
                    align: 'center',
                    width: 70,
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
                    title: '模式',
                    field: 'deviceModel',
                    width: 70,
                    align: 'center',
                    formatter: function (cellval, row) {
                        if (cellval == 0){
                            return '<div  style="color:teal"> 普通模式 </div>';
                        } else  if (cellval == 1){
                            return '<div  style="color:gray"> 雷达模式 </div>';
                        }else {
                            return '<div  style="color:gray"> 光敏模式 </div>';;
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

                        var e = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="infoListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-stats"></span> 数据信息 </a>'
                        var d = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="alarmListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-warning-sign"></span> 报警信息 </a>'
                        return  e+d;
                    }
                },

            ],
            title: '该区域所有在线设备信息',
        });
        isTableExist = 1;
        // 弹出弹窗
        $('#myModal').modal('show');
    });
    $('#details-button-4').click(function() {

        curReq = '/alarmListByArea/'+DeviceCoord;
        // 生成表格
        if (isTableExist !== undefined) {
            $('#modalTable').bootstrapTable('destroy');
        }
        var $table = $('#modalTable').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: curReq,
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
                    width: 120,
                    align: 'center',
                    formatter: function (cellval, row) {
                        var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                        var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="dataLead(\'' + row.alarmId + '\')">报修</button> ';

                        return  e + d;
                    }
                },

            ],
            title: '该区域所有故障设备信息',
        });
        isTableExist = 1;
        // 弹出弹窗
        $('#myModal').modal('show');
    });



});




// $(function () {
//     $('#table').bootstrapTable({
//         method: "get",
//         striped: true,
//         singleSelect: false,
//         url: "/deviceList",
//         dataType: "json",
//         pagination: true, //分页
//         pageSize: 10,
//         pageNumber: 1,
//         search: false, //显示搜索框
//         contentType: "application/x-www-form-urlencoded",
//         queryParams:null,
//         columns: [
//
//
//             {
//                 title: "物理地址",
//                 field: 'deviceMac',
//                 align: 'center',
//                 width: 120,
//                 valign: 'middle'
//             }
//
//         ]
//     });
// });


$(".map_btn").each(function (index) {
    $(this).click(function () {
        if ($(this).is('.color')) {
            $(this).removeClass("color");
            $(".content").eq(index).slideUp()
        } else {
            $(".map_btn").removeClass("color");
            $(this).addClass("color");
            $(".content").eq(index).slideDown().siblings(".content").slideUp()
        }

    })
});

$(".myMapNav ul li").each(function (index) {
    $(this).click(function () {
        $(this).addClass("liActive").siblings("li").removeClass("liActive");
        $(".myMapContent .box").eq(index).show().siblings("div").hide().stop()
    })
});

$(".specialNav ul li").each(function (index) {
    $(this).click(function () {
        $(".specialNav").hide();
        $(".specialInformation").eq(index).show().siblings(".specialInformation").hide()
    })
});


function back() {
    $(".specialInformation").hide();
    $(".specialNav").show()
}

function listDown(){
    $("#listDown").hide();
    $("#listUp").show();
    $(".list").slideUp();
}
function listUp(){
    $("#listUp").hide();
    $("#listDown").show();
    $(".list").slideDown();
}

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
    var lon = d['areaLon'];
    var lat = d['areaLat'];

    var node = {
        open : false,
        id : id,
        name : name,
        pId : pId,
        net : net,
        lon : lon,
        lat : lat,
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

    if(isCover !== undefined){
        // 获取当前地图的所有覆盖物
        var allOverlays = map.getOverlays();
        // 移除当前地图的所有覆盖物
        for (var i = 0; i < allOverlays.length; i++) {
            map.removeOverlay(allOverlays[i]);
        }
    }
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
    var pathLen = pathNames.length;
    var pathName = pathNames.join("");
    console.log(pathName);
    // 在地图上标记该地点
    var longitude = treeNode.lon;
    var latitude = treeNode.lat;
    var newPoint = new BMap.Point(longitude, latitude);
    zoomLevel = getZoomLevelByPathLen(pathLen);
    map.centerAndZoom(newPoint, zoomLevel);
    // 创建标注物
    marker = new BMap.Marker(newPoint);
    // 将标注物添加到地图上
    map.addOverlay(marker);
    // 将地图中心移动到标注点
    map.panTo(newPoint);

    //获取天气信息

    //转换为浮点数
    longitude = parseFloat(longitude);
    latitude = parseFloat(latitude);
    var location_id = longitude.toFixed(2)+","+latitude.toFixed(2);
    console.log(location_id);
    getWeather(pathName,location_id);


    if(!treeNode.isParent){//到达最底层，路段信息
        DeviceCoord = treeNode.net;
        console.log(DeviceCoord);
        $.ajax({
            url: '/deviceListByDeviceCoord/'+DeviceCoord, // 替换成你的后台接口地址
            type: 'GET',
            success: function(response) {
                // 成功获取数据后，更新指定位置的内容
                $('#total').text("总数:"+response.data.length); // 将获取的数据写入到id为total的元素中
                ////以下为正确代码
                deviceList = response.data;
                console.log(deviceList);
                $.each(deviceList, function(index, device) {
                    var longitude = device.deviceLon;
                    var latitude = device.deviceLat;
                    var status = device.deviceStatus;
                    var light = device.deviceLight;
                    var model = device.deviceModel;
                    var selectOptions = '<label>在线状态:</label><select id="deviceStatus">';
                    selectOptions += '<option value="1" ' + (status === 1 ? 'selected' : '') + '>在线</option>';
                    selectOptions += '<option value="0" ' + (status === 0 ? 'selected' : '') + '>离线</option>';
                    selectOptions += '</select>';

                    selectOptions += '<label>开关状态:</label><select id="deviceLight">';
                    selectOptions += '<option value="1" ' + (light === 1 ? 'selected' : '') + '>打开</option>';
                    selectOptions += '<option value="2" ' + (light === 2 ? 'selected' : '') + '>关闭</option>';
                    selectOptions += '</select>';

                    selectOptions += '<label>模式:</label><select id="deviceModel">';
                    selectOptions += '<option value="0" ' + (model === 0 ? 'selected' : '') + '>普通模式</option>';
                    selectOptions += '<option value="1" ' + (model === 1 ? 'selected' : '') + '>雷达模式</option>';
                    selectOptions += '<option value="2" ' + (model === 2 ? 'selected' : '') + '>光敏模式</option>';
                    selectOptions += '</select><br>';
                    // 创建设备的经纬度位置对象
                    var point = new BMap.Point(longitude, latitude);
                    $.ajax({
                        url: '/alarmListBydeviceSerial/'+device.deviceSerial, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 根据设备状态创建不同的图标
                            var iconUrl;
                            if(response.data.length > 0){
                                iconUrl = '../../../static/img/guzhang.svg';
                            }else{
                                if(status === 1){
                                    iconUrl = '../../../static/img/zaixian.svg';
                                }else if(status === 0){
                                    iconUrl = '../../../static/img/lixian.svg';
                                }
                            }
                            console.log(iconUrl);
                            // 创建图标对象
                            var icon = new BMap.Icon(iconUrl, new BMap.Size(40, 40));
                            // 创建覆盖物
                            var marker = new BMap.Marker(point, {icon: icon});

                            map.addOverlay(marker);
                            // 将覆盖物添加到地图上
                            // 使用闭包保存每个覆盖物的索引数据
                            (function (i) {
                                // 监听点击覆盖物的事件
                                marker.addEventListener("click", function () {
                                    var infoWindow = new BMap.InfoWindow('<div style="width: 800px; height: 100px;">' +
                                        '<form>' +
                                        '<label>序号:</label><input id="deviceId" type="text" value="' + device.deviceId + '" readonly>' +
                                        '<label>物理地址:</label><input id="deviceMac" type="text" value="' + device.deviceMac + '"><br>' +
                                        '<label>网络地址:</label><input id="deviceShort" type="text" value="' + device.deviceShort + '">' +
                                        '<label>设备标签:</label><input id="deviceSerial" type="text" value="' + device.deviceSerial + '">' +
                                        '<label>网关编号:</label><input id="deviceCoord" type="text" value="' + device.deviceCoord + '"><br>' +
                                        selectOptions
                                        +
                                        '<label>设备类型:</label><input id="deviceType" type="text" value="' + device.deviceType + '"><br>' +
                                        '<label>经度:</label><input id="deviceLongitude" type="text" value="' + device.deviceLon + '">' +
                                        '<label>纬度:</label><input id="deviceLatitude" type="text" value="' + device.deviceLat + '"><br>' +
                                        // 其他设备数据字段...
                                        '<button onclick="updateDevice()" text-align="right"">修改</button>' +
                                        '</form>');

                                    // 打开弹窗
                                    this.openInfoWindow(infoWindow);

                                });
                            })(index);


                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });

                });

                $.ajax({
                    url: '/deviceListByIsOnline/'+DeviceCoord, // 替换成你的后台接口地址
                    type: 'GET',
                    success: function(response) {
                        // 成功获取数据后，更新指定位置的内容
                        $('#online').text("在线:"+response.data.length); // 将获取的数据写入到id为total的元素中

                    },
                    error: function(xhr) {
                        console.log(xhr.responseText); // 打印错误信息
                    }
                });
                $.ajax({
                    url: '/deviceListByIsNotOnline/'+DeviceCoord, // 替换成你的后台接口地址
                    type: 'GET',
                    success: function(response) {
                        // 成功获取数据后，更新指定位置的内容
                        $('#offline').text("离线:"+response.data.length); // 将获取的数据写入到id为total的元素中
                    },
                    error: function(xhr) {
                        console.log(xhr.responseText); // 打印错误信息
                    }
                });
                $.ajax({
                    url: '/alarmListByalarmArea/'+DeviceCoord+'/0', // 替换成你的后台接口地址
                    type: 'GET',
                    success: function(response) {
                        // 成功获取数据后，更新指定位置的内容
                        $('#fault').text("故障:"+response.data.length); // 将获取的数据写入到id为total的元素中
                    },
                    error: function(xhr) {
                        console.log(xhr.responseText); // 打印错误信息
                    }
                });
            },
            error: function(xhr) {
                console.log(xhr.responseText); // 打印错误信息
            }

        });



    } else {//父节点

        if( treeNode.id == 0 ){   //最高级别区域，仍显示默认内容

            deviceListByDeviceCoord("/deviceList","");

        } else {

            var childNodes = zTree.transformToArray(treeNode);
            var deviceCoordList = new Array();
            for(i = 0; i < childNodes.length; i++) {
                deviceCoordList[i] = childNodes[i].net;
            }

            $.ajax({
                url: '/deviceListByDeviceCoordList/'+deviceCoordList, // 替换成你的后台接口地址
                type: 'GET',
                success: function(response) {
                    // 成功获取数据后，更新指定位置的内容
                    $('#total').text("总数: "+response.data.length); // 将获取的数据写入到id为total的元素中
                    $.ajax({
                        url: '/deviceListByIsOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#online').text("在线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/deviceListByIsOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#online').text("在线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/deviceListByIsNotOnlineList/'+deviceCoordList, // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#offline').text("离线: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });
                    $.ajax({
                        url: '/alarmListByDeviceCoordList/'+deviceCoordList+'/0', // 替换成你的后台接口地址
                        type: 'GET',
                        success: function(response) {
                            // 成功获取数据后，更新指定位置的内容
                            $('#fault').text("故障: "+response.data.length); // 将获取的数据写入到id为total的元素中
                        },
                        error: function(xhr) {
                            console.log(xhr.responseText); // 打印错误信息
                        }
                    });



                },
                error: function(xhr) {
                    console.log(xhr.responseText); // 打印错误信息
                }
            });

        }

    }
    isCover = 1;
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


function getZoomLevelByPathLen(pathLen) {
    var zoomLevel;
    if (pathLen === 1) {
        zoomLevel = 7; // 省级别的缩放级别
    } else if (pathLen === 2) {
        zoomLevel = 12; // 城市级别的缩放级别
    } else if (pathLen === 3){
        zoomLevel = 13; // 区县的缩放级别
    } else if (pathLen === 4){
        zoomLevel = 18; //街道
    }
    return zoomLevel;
}


/**
 * 根据经纬度获取当地天气信息
 * @param pathName
 * @param location_id
 */
function getWeather(pathName,location_id) {
    var apiUrl = "https://devapi.qweather.com/v7/weather/now";
    var key = "c6029580da13485089c99793330c83f1";
    var requestUrl = apiUrl + "?location=" + location_id + "&key=" + key;

    // 发送JSONP请求
    $.ajax({
        url: requestUrl,
        dataType: 'json',
        success: function(response) {
            console.log(response.code);
            if (response.code === "200") {
                var temperature = response.now.temp;
                var weather = response.now.text;
                console.log(temperature);
                console.log(weather);
                var weatherInfo= pathName +"\u0020\u0020\u0020\u0020\u0020\u0020"+ weather + "\u0020\u0020\u0020\u0020\u0020\u0020" + temperature + "\u2103";
                document.getElementById("weather").innerText  = weatherInfo;//修改~·#
            } else {
                console.log("天气信息查询失败");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("天气信息查询服务出错:", textStatus, errorThrown);
        }
    });
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
                width: 40,
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
                width: 70,
                valign: 'middle'
            },
            {
                title: '设备标签',
                field: 'deviceSerial',
                align: 'center',
                width: 70,
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
                title: '模式',
                field: 'deviceModel',
                width: 70,
                align: 'center',
                formatter: function (cellval, row) {
                    if (cellval == 0){
                        return '<div  style="color:teal"> 普通模式 </div>';
                    } else  if (cellval == 1){
                        return '<div  style="color:gray"> 雷达模式 </div>';
                    }else {
                        return '<div  style="color:gray"> 光敏模式 </div>';;
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

                    var e = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="infoListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-stats"></span> 数据信息 </a>'
                    var d = '<a style="margin: 2;width:80" class="btn btn-xs btn-success"  onclick="alarmListTimer('+row.deviceSerial+')"><span class="glyphicon glyphicon-warning-sign"></span> 报警信息 </a>'
                    return  e+d;
                }
            },

        ]
    });

}

// 修改设备数据方法
function updateDevice() {
    // 获取表单字段的值
    var deviceId = $("#deviceId").val();
    var deviceMac = $("#deviceMac").val();
    var deviceShort = $("#deviceShort").val();
    var deviceSerial = $("#deviceSerial").val();
    var deviceCoord = $("#deviceCoord").val();
    var deviceStatus = $("#deviceStatus").val();
    var deviceLight = $("#deviceLight").val();
    var deviceModel = $("#deviceModel").val();
    var deviceType = $("#deviceType").val();
    var deviceLongitude = $("#deviceLongitude").val();
    var deviceLatitude = $("#deviceLatitude").val();


    var updatedDeviceData = {
        deviceId: deviceId,
        deviceMac: deviceMac,
        deviceShort: deviceShort,
        deviceSerial: deviceSerial,
        deviceCoord: deviceCoord,
        deviceStatus: deviceStatus,
        deviceLight: deviceLight,
        deviceModel: deviceModel,
        deviceType: deviceType,
        deviceLongitude: deviceLongitude,
        deviceLatitude: deviceLatitude
        // 其他设备数据字段...
    };

    // 发送请求
    $.ajax({
        url: "/updateDevice/",
        type: "POST",
        data: JSON.stringify(updatedDeviceData),
        contentType: "application/json",
        success: function(response) {
            // 处理从后台返回的响应数据
            console.log("设备数据已成功更新:", response);

            // 在这里您可以根据需要执行其他操作，例如更新界面显示等
        },
        error: function(error) {
            // 处理错误情况
            console.error("更新设备数据时出现错误:", error);
        }
    });
}