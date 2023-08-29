/**
 * Created by Administrator on 2017/6/12.
 */
var currentID;
$(document).ready(function() {
        tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
                                                          var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                                                          var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                                                          return  e + d;
                                                      });

        $("[name='inlineRadioOptions']").change(function() {
            var val = $(this).val();

            //do select table
            if( val == 0 ) { //未消警

                $('#table').bootstrapTable('destroy');
                tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
                                                          var  e = '<button  id="remove" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                                                          var  d = '<button  id="repair1" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                                                          return  e + d;
                                                      });
            } else if( val == 1 ){//已消警

                $('#table').bootstrapTable('destroy');
                tableLoad("/alarmListByalarmStatus/1",function (cellval, row) {
                                                          var  d = '<button  id="repair2" data-id="100" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="repairAlarm(\'' + row.alarmId + '\')">报修</button> ';

                                                          return  d;
                                                      });
            }else if( val == 2){//已报修
                $('#table').bootstrapTable('destroy');
                tableLoad("/alarmListByalarmStatus/2",function (cellval, row) {
                                                          var  f = '<button  id="cancel" data-id="101" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="cancelRepair(\'' + row.alarmId + '\')">撤销</button> ';

                                                          return  f;
                                                      });

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
                        return '<div  style="color:green"> 已报修 </div>';
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

//未完成
function repairAlarm(alarmId) {


    $.ajax({
        url: '/removeAlarm/'+alarmId,
        type: 'post',
        dataType: 'text',
        success: function (result) {
            $('#table').bootstrapTable('destroy');
            tableLoad("/alarmListByalarmStatus/0",function (cellval, row) {
                                                                      var  e = '<button  id="add" data-id="98" style="outline:none;width:40%" class="btn btn-xs btn-warning" onclick="removeAlarm(\'' + row.alarmId + '\')">消除警报</button> ';
                                                                      var  d = '<button  id="add" data-id="99" style="outline:none;width:40%" class="btn btn-xs btn-success" onclick="dataLead(\'' + row.alarmId + '\')">报修</button> ';

                                                                      return  e + d;
                                                                  });


        }
    })
}


//撤销报修操作，撤销之后的状态是什么呢
function cancelRepair() {
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

