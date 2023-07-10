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
    {	rid:101	,	id:	10	,pId:101,name:"	所有区域",open:true,nocheck:true,
        children:[
            {id:10, name:"山东省", open:true, noR:true,nocheck:true,
                children:[
                    {id:100, name:"青岛市	", noR:true, open:true,nocheck:true,
                        children:[
                            {id:1000, name:"城阳区	", noR:true, open:true,nocheck:true,
                                children:[
                                    {id:0001, name:"瑞阳路	", noR:true, open:false,nocheck:true},
                                    {id:0002, name:"春阳路	", noR:true, open:false,nocheck:true}
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

    if(!treeNode.isParent){//到达最底层，路段信息

        deviceCoord = treeNode.id;
        roadName = treeNode.name;

        document.getElementById("road").innerText  = "灯箱实时监控：" + roadName;//修改


            $('#table').bootstrapTable('destroy');
            $('#table').bootstrapTable({
                        method: "get",
                        striped: true,
                        singleSelect: false,
                        url: '/deviceListByDeviceCoord/'+ deviceCoord,
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
                                title: '操作',
                                field: 'person',
                                width: 120,
                                align: 'center',
                                formatter: function (cellval, row ,index) {

                                    var  e = '<button  id="add" data-id="98" style="outline:none" class="btn btn-xs btn-success" onclick="SingleLightControl(' + row.deviceSerial + ','+ index +',1)">打开</button> ';

                                    var  d = '<button  id="add" data-id="99" style="outline:none" class="btn btn-xs btn-danger" onclick="SingleLightControl(' + row.deviceSerial + ','+ index +',2)">关闭</button> ';
                                    return  e + d;
                                }
                            }

                        ]
                    });
    }


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