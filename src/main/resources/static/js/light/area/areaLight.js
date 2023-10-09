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
		open : true,
		id : id,
		name : name,
		level : level,
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
			onClick : zTreeOnCheck,
//			onRightClick : zTreeOnRightClick
		}
	};

	return setting;
}

function zTreeOnCheck(event, treeId, treeNode) {

    console.log(treeNode.id);
    console.log(treeNode.level);
    console.log("/areaListByareaId/"+treeNode.id);

    initAreaList("/areaListByareaId/",treeNode.id);
//    initAreaList("/areaList","");

}

//右击后具体的事件操作js
function initAreaList(myUrl,areaId){
    $('#table').bootstrapTable('destroy');
        $('#table').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: myUrl + areaId,
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
                    title: "区域编号",
                    field: 'areaId',
                    align: 'center',
                    width: 100,
                    valign: 'middle'
                },
                {
                    title: "区域名称",
                    field: 'areaName',
                    align: 'center',
                    width: 100,
                    valign: 'middle'
                },
                {
                    title: '区域级别',
                    field: 'areaLevel',
                    align: 'center',
                    width: 120,
                    valign: 'middle'
                },
                {
                    title: '级别位次',
                    field: 'areaRank',
                    align: 'center',
                    width: 120,
                    valign: 'middle'
                },
                {
                    title: '区域序列号',
                    field: 'areaSerial',
                    align: 'center',
                    width: 120,
                    valign: 'middle'
                },
                {
                    title: '操作',
                    width: 120,
                    align: 'center',
                    formatter: function (cellval, row) {

                        var  e = '<button  id="add" data-id="98" class="btn btn-xs btn-success" onclick="addArea('+row.areaId+','+row.areaLevel+')">添加子区域</button> ';
                        var  f = '<button  id="add" data-id="98" class="btn btn-xs btn-warning" onclick="editArea('+row.areaId+')">编辑</button> ';
                        var  d = '<button  id="add" data-id="98" class="btn btn-xs btn-danger" >删除</button> ';
                        return  e + f + d;
                    }
                }

            ]
        });


}

function addArea( areaId,areaLevel){

    console.log("areaId:"+areaId);
    if(areaLevel >= 4){
        layer.msg("已经是最小区域，无法添加",{icon:5,anim:6});
        initAreaList("/areaListByareaId/", areaId);
    }else{
        layer.open({
            type: 2,
            title: '添加子区域',
            content: '/areaAddPage/' + areaId,
            shade:[0.8,'#393d49'],
            area:['500px','450px'],
            btn:['确定','取消'],
            yes:function (index,layero) {
                var iframeWindow = window['layui-layer-iframe'+index];
                var submit = layero.find('iframe').contents().find("#LAY-front-submit");
                //监听提交
                iframeWindow.layui.form.on('submit(LAY-front-submit)',function (data) {
                    var field = data.field;

                    console.log(field);

                    $.ajax({
                        url: '/areaAdd',
                        data: field,
                        dataType:'json',
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


}

function editArea( areaId ){

    console.log("areaId:"+areaId);

    layer.open({
        type: 2,
        title: '编辑区域',
        content: '/areaEditPage/' + areaId,
        shade:[0.8,'#393d49'],
        area:['500px','450px'],
        btn:['确定','取消'],
        yes:function (index,layero) {
            var iframeWindow = window['layui-layer-iframe'+index];
            var submit = layero.find('iframe').contents().find("#LAY-front-submit");
            //监听提交
            iframeWindow.layui.form.on('submit(LAY-front-submit)',function (data) {
                var field = data.field;

                console.log(field);

//                $.ajax({
//                    url: '/areaEdit',
//                    data: field,
//                    dataType:'json',
//                    async: false,
//                    cache: false,
//                    success: function (str) {
//                        if(str.code === 0){
//                            console.log(1111);
//                        }
//                            layer.msg(str.msg,{icon:str.icon,anim:str.anim});
//                    }
//                });


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

