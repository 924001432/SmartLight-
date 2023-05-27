/**
 * Created by Administrator on 2017/6/12.
 */
var currentID;
function tableLoad(){
    $('#table').bootstrapTable({
        method: "get",
        url: "/alarmList",
        striped: true,
        singleSelect: false,
        dataType: "json",
        pagination: true, //分页
        pageSize: 10,
        pageNumber: 1,
        search: false, //显示搜索框
        contentType: "application/x-www-form-urlencoded",
        queryParams: queryParams,
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
            {
                title: '处理时间',
                field: 'alarmHandletime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '操作',
                field: 'person',
                width: 120,
                align: 'center',
                formatter: function (cellval, row) {
                    var  e = '<button  id="add" data-id="98" class="btn btn-xs btn-success" onclick="dataLead(\'' + row.id + '\')">查看详情</button> ';

                    return  e;
                }
            }



        ]
    });
}

function queryParams(params) {
    return {
        page: params.pageNumber,
        rows: params.limit,//页码大小
        order: params.order,
        sort: params.sort,
        SendPeople: $("#person").val(),
        Title: $("#tit").val(),
        BeginSendTime: $("#beginSendTime").val(),
        EndSendTime: $("#endSendTime").val()
    };
}

//数据的查询
function checkPersonData() {
    $("#table").bootstrapTable('refresh');
}



function dataLead() {
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
}

//取消操作
function cancel() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//单个下载
function down(id){

        var personId = id;
        $.ajax({
            url: '/OAMessagePush/Delete?Ids=' + personId,
            type: 'post',
            dataType: 'json',
            success: function (result) {
                var result = handleError(result);
                if (result.IsError) {
                    alter("下载失败");
                    return;
                }
                else {
                    alter("下载成功");

                }
            }
        })


}


//批量下载
function downAll(){
    var idArray = $('#table').bootstrapTable('getSelections');
    if(idArray == null || idArray.length ==0){
        alert("请选择你要下载的类容!");
        return false;
    }else {
        var personID = [];
        for(var i=0;i<idArray.length;i++){
            personID.push(idArray[i].id);
        }
        //$.each(idArray, function (index, row) {
        //    personID.push(row.Id);
        //});

            $.ajax({
                url: '/OAMessagePush/Delete?Ids=' + personID.join(","),
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    var result = handleError(result);
                    if (result.IsError) {
                        return;
                    }
                    else {
                        checkPersonData();
                    }
                }
            })
        }



}
//测试下载
function textDel(){
    alert("下载成功")
}
