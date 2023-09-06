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
		var level = n['level'];
		ids.push(id);
		ids.push(level);
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
			onClick : zTreeOnCheck
		}
	};

	return setting;
}

function zTreeOnCheck(event, treeId, treeNode) {

}
