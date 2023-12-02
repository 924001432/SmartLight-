var zTree, rMenu;
var userArea=0;
var userAreaName="";
var userRoleLevel=0;
$(document).ready(function(){


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
            userRoleLevel = result.userRoleLevel;
            //超级管理员和系统管理员对应区域级别都为0，即角色级别-2
            if(userRoleLevel <= 2){
                userRoleLevel = 2;
            }

        }
    })

});
//根据用户区域，用户区域名称，区域等级初始化树
function initMenuTree(areaLevel) {
    $.fn.zTree.init($("#treeDemo"), getSettting(), getMenuTree(userArea,userAreaName,areaLevel));
    zTree = $.fn.zTree.getZTreeObj("treeDemo");
    rMenu = $("#rMenu");
}

function getMenuTree(userArea,userAreaName,areaLevel) {
	var root = {
		id : userArea,
		name : userAreaName,
		areaLevel : userRoleLevel-2,
		open : true,
	};
	console.log(userArea + " " + userAreaName + " "+ areaLevel);

	$.ajax({
		type : 'get',
        url : '/areaListByAreaLevel/'+userArea+ '/'+ areaLevel,
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(data) {
            console.log(data);
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

function initMenuDatas(areaId){


	var ids = [];
	ids.push(areaId);
	initMenuCheck(ids);
}

function initMenuCheck(ids) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    	var length = ids.length;
    	if(length > 0){
    		var node = treeObj.getNodeByParam("id", 0, null);
    		if(node != null){//不加判断报错，chkDisabled null 异常
                         treeObj.checkNode(node, true, false);
                    }
    	}

    	for(var i=0; i<length; i++){
    		var node = treeObj.getNodeByParam("id", ids[i], null);
    		if(node != null){
                 treeObj.checkNode(node, true, false);
            }

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
		var areaLevel = n['areaLevel'];
		ids.push(id);
		ids.push(areaLevel);
	}

	return ids;
}

function createNode(d) {
	var id = d['areaId'];
	var pId = d['parentId'];
	var name = d['areaName'];
	var areaLevel = d['areaLevel'];//node节点的level是层级，不能取该名作为节点的属性赋值，会报错
	var net = d['areaNet'];
	var child = d['child'];



	var node = {
		open : true,
		id : id,
		name : name,
		areaLevel : areaLevel,
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
		check : {
			enable : true,
			chkStyle : "radio",
			radioType: "all"
		},
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
			onCheck : zTreeOnCheck
		}
	};

	return setting;
}


