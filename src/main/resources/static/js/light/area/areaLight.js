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
//		'/areaListByuserArea/'+userArea
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
			onClick : zTreeOnClick,
//			onRightClick : zTreeOnRightClick
		}
	};

	return setting;
}

function zTreeOnClick(event, treeId, treeNode) {

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
                        var  d = '<button  id="add" data-id="98" class="btn btn-xs btn-danger" onclick="deleteArea('+row.areaId+','+row.areaLevel+')">删除</button> ';
                        return  e + f + d;
                    }
                }

            ]
        });


}

function addArea( areaId,areaLevel){

    console.log("areaId:"+areaId);
    if(areaLevel >= 5){
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
                            layer.msg(str.msg,{icon:str.icon,time:2000,anim:str.anim},function () {
                                layer.close(index);     //关闭弹层
                                location.reload();
                                $("#table").bootstrapTable('refresh');
                            });
                        }
                    });


//                    layer.close(index);     //关闭弹层
//                    $("#table").bootstrapTable('refresh');
//                    $.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree());
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
                //返回数据，操作数据
                var field = data.field;
                var state = false;//判断是否满足条件

                console.log(field);

                if(field.parentId == -1 || field.parentId == "" || field.parentId == null){//判断是否选择负责区域
                    console.log("未选择");
                    layer.msg("未选择负责区域信息",{icon:0,anim:6});

                    state = false;
                }else {//判断用户角色和负责区域是否匹配
                    console.log("选择成功");
                    state = true;
                }

                //如果匹配，关闭弹层，添加数据
                if(state){
                    //关闭弹层


                    //添加数据
                    $.ajax({
                        url: '/areaEdit',
                        type: 'GET',
                        dataType: 'json',
                        data:field,
                        success: function (str) {

                            layer.msg(str.msg,{icon:str.icon,time:2000,anim:str.anim},function () {
                                layer.close(index);
                                location.reload();
                                $("#table").bootstrapTable('refresh');
                            });
                        }
                    })



                }



            });
            submit.trigger('click');
        },
        success:function (layero,index) {
            //congratulation!
        }
    });

}

function deleteArea( areaId,areaLevel){
    //根据区域id查询是否有子区域，如果有子区域，提示不能删除，如果没有，删除
    console.log("areaId:"+areaId);
    //删除逻辑：查询是否有子节点（parentId列有该节点的编号），如果有，则无法删除该节点，提示先删除所有子节点；如果无，则删除
    layui.use('layer',function(){
        layer = layui.layer;
        layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index){
            //do something
            $.ajax({
                url: '/areaDelete/'+areaId,
                type: 'post',
                dataType: 'json',//dataType为“text”会导致数据获取不到

                async: false,
                cache: false,
                success: function (str) {

                    console.log(str);
                    var i = str.code;
                    console.log(i);

                    layer.msg(str.msg,{icon:str.icon,time:2000,anim:str.anim},function () { location.reload(); });

                }
            })

        });

    })



}
