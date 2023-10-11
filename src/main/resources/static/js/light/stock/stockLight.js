/**
 * Created by Administrator on 2017/6/12.
 */
var curOptions = 0;
$(document).ready(function() {
        tableLoad("/queryStockBystockStatus/0",function (cellval, row) {
                                                          var  c = '<button  id="add" data-id="98" style="outline:none;width:22%" class="btn btn-xs btn-info" onclick="infoStock(\'' + row.stockId + '\')">详情</button> ';
                                                          var  e = '<button  id="add" data-id="98" style="outline:none;width:22%" class="btn btn-xs btn-success" onclick="editStock(\'' + row.stockId + '\')">编辑</button> ';
                                                          var  d = '<button  id="add" data-id="99" style="outline:none;width:22%" class="btn btn-xs btn-warning" onclick="outStock(\'' + row.stockId + '\')">出库</button> ';
                                                          var  f = '<button  id="add" data-id="100" style="outline:none;width:22%" class="btn btn-xs btn-danger" onclick="deleteStock(\'' + row.stockId + '\')">删除</button> ';

                                                          return  c + e + d + f;
                                                      });

        $("[name='inlineRadioOptions']").change(function() {
            var val = $(this).val();
            curOptions = val;

            //do select table
            arg = "/queryStockBystockStatus/"+curOptions;
            makeList(curOptions,arg);

        });
    });

//涉及到出库状态的获取
function queryStockByDeviceSerial(){
    //获取查询参数
    var e =$("#deviceSerial").val();
    if(e == ""){
        alert("请输入查询条件");
    }else{
        var f =$("[name='inlineRadioOptions']").val();
        //构造查询参数，调用查询方法
        arg = '/stockListByDeviceSerial/'+curOptions+'&'+e;


        makeList(curOptions,arg);

    }
}


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
                title: '库存编号',
                field: 'stockId',
                width: 20,
                align: 'center',
                valign: 'middle'
            },
            {
                title: '设备编号',
                field: 'deviceSerial',
                align: 'center',
                width: 40,
                valign: 'middle'
            },
            {
                title: '设备类型',
                field: 'deviceType',
                align: 'center',
                width: 40,
                formatter: function (cellval, row) {
                    switch(cellval){
                        case 1:
                            return '<div  style="color:red"> 网关 </div>';
                            break;
                        case 2:
                            return '<div  style="color:red"> 路灯 </div>';
                            break;

                        default:
                            return '<div  style="color:red"> 未知 </div>';
                            break;
                    }

                }
            },
            {
                title: '设备名称',
                field: 'deviceName',
                align: 'center',
                width: 70,
                valign: 'middle'
            },
//            {
//                title: '设备型号',
//                field: 'deviceModel',
//                align: 'center',
//                width: 70,
//                valign: 'middle'
//            },
//            {
//                title: '设备品牌',
//                field: 'deviceBrand',
//                align: 'center',
//                width: 70,
//                valign: 'middle'
//            },
//            {
//                title: '批次',
//                field: 'stockBatch',
//                align: 'center',
//                width: 80,
//                valign: 'middle'
//            },
//            {
//                title: '领用人',
//                field: 'stockUser',
//                align: 'center',
//                width: 100,
//                valign: 'middle'
//            },
//            {
//                title: '生产时间',
//                field: 'deviceProducetime',
//                align: 'center',
//                width: 100,
//                valign: 'middle'
//            },
            {
                title: '入库时间',
                field: 'stockIntime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '出库时间',
                field: 'stockOuttime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '更新时间',
                field: 'stockUpdatetime',
                align: 'center',
                width: 100,
                valign: 'middle'
            },
            {
                title: '状态',
                field: 'stockStatus',
                width: 60,
                align: 'center',
                formatter: function (cellval, row) {
                    if (cellval == 0){
                        return '<div  style="color:red"> 待出库 </div>';
                    } else  if (cellval == 1){
                        return '<div  style="color:green"> 已出库 </div>';
                    } else {
                        return '<div  style="color:red"> 未知 </div>';

                    }
                }
            },
//            {
//                title: '维修次数',
//                field: 'deviceRepairnum',
//                align: 'center',
//                width: 40,
//                valign: 'middle'
//            },
            {
                title: '操作用户',
                field: 'stockOperatorName',
                align: 'center',
                width: 40,
                valign: 'middle'
            },
            {
                title: '操作',
                width: 200,
                align: 'center',
                formatter: myFuc
            }



        ]
    });
}
//设备入库
function inStock(){
    layer.open({
        type: 2,
        title: '设备入库',
        content: '/stockAddPage',
        shade:[0.8,'#393d49'],
        area:['500px','490px'],
        btn:['确定','取消'],
        yes:function (index,layero) {
            var iframeWindow = window['layui-layer-iframe'+index];
            var submit = layero.find('iframe').contents().find("#LAY-front-submit");
            //监听提交
            iframeWindow.layui.form.on('submit(LAY-front-submit)',function (data) {
                var field = data.field;

                $.ajax({
                    url: '/stockAdd',
                    data: field,
                    async: false,
                    cache: false,
                    success: function (str) {
                        if(str.code === 0){

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
//库存详情页面
function infoStock(stockId){

    console.log(stockId);
    layer.open({
        type: 2,
        title: '设备详情',
        content: '/stockInfoPage/' + stockId,
        shade:[0.8,'#393d49'],
        area:['500px','350px'],
    });

}
//库存设备编辑
function editStock(stockId){

            console.log(stockId);
            layer.open({
                type: 2,
                title: '编辑库存设备',
                content: '/stockEditPage/' + stockId,
                shade:[0.8,'#393d49'],
                area:['500px','490px'],
                btn:['确定','取消'],
                yes:function (index,layero) {
                    var iframeWindow = window['layui-layer-iframe'+index];
                    var submit = layero.find('iframe').contents().find("#LAY-front-submit");
                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-front-submit)',function (data) {
                        var field = data.field;
                        $.ajax({
                            url: '/stockEdit',
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
//出库
function outStock(stockId) {
    layer.confirm('确认出库?', {icon: 3, title:'提示'}, function(index){
      //do something
      $.ajax({
              url: '/stockOut/'+stockId,
              type: 'post',
              dataType: 'json',
              success: function (str) {

                    layer.msg(str.msg,{icon:str.icon,anim:str.anim});
                    $("#table").bootstrapTable('refresh');
              }
          })


      layer.close(index);
    });
}
//库存设备删除
function deleteStock(stockId) {

    layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index){
        //do something
        $.ajax({
            url: '/stockDelete/'+stockId,
            type: 'post',
            dataType: 'json',
            success: function (str) {

                layer.msg(str.msg,{icon:str.icon,anim:str.anim});
                $("#table").bootstrapTable('refresh');
            }
        })


        layer.close(index);
    });


}



function makeList(curOptions,arg){
    if( curOptions == 0 ) { //待出库

        $('#table').bootstrapTable('destroy');
        tableLoad(arg,function (cellval, row) {
            var  c = '<button  id="add" data-id="98" style="outline:none;width:22%" class="btn btn-xs btn-info" onclick="infoStock(\'' + row.stockId + '\')">详情</button> ';
            var  e = '<button  id="add" data-id="98" style="outline:none;width:22%" class="btn btn-xs btn-success" onclick="editStock(\'' + row.stockId + '\')">编辑</button> ';
            var  d = '<button  id="add" data-id="99" style="outline:none;width:22%" class="btn btn-xs btn-warning" onclick="outStock(\'' + row.stockId + '\')">出库</button> ';
            var  f = '<button  id="add" data-id="100" style="outline:none;width:22%" class="btn btn-xs btn-danger" onclick="deleteStock(\'' + row.stockId + '\')">删除</button> ';

            return  c + e + d + f;
                      });
    } else if( curOptions == 1 ){//已出库

        $('#table').bootstrapTable('destroy');
        tableLoad(arg,function (cellval, row) {
                          var  c = '<button  id="add" data-id="98" style="outline:none;width:30%" class="btn btn-xs btn-info" onclick="infoStock(\'' + row.stockId + '\')">详情</button> ';
                          var  e = '<button  id="repair2" data-id="100" style="outline:none;width:30%" class="btn btn-xs btn-success" onclick="editStock(\'' + row.stockId + '\')">编辑</button> ';
                          var  d = '<button  id="repair2" data-id="100" style="outline:none;width:30%" class="btn btn-xs btn-danger" onclick="deleteStock(\'' + row.stockId + '\')">删除</button> ';

                          return  c + e + d;
                      });
    }


    //
}

//写一个抢票脚本
