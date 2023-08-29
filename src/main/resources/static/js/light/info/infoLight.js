$(function () {
        $('#table').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            url: "/infoList",
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
                }
                ,
                {
                    title: "序号",
                    field: 'infoId',
                    align: 'center',
                    width: 40,
                    valign: 'middle'
                },
                {
                    title: "设备标签",
                    field: 'infoSerial',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '温度 ℃',
                    field: 'infoTemp',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '湿度 %rh',
                    field: 'infoHumi',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '路灯电压 V',
                    field: 'infoLampV',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '主板电压 V',
                    field: 'infoBoardV',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '光照强度 Lux',
                    field: 'infoLux',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '空气质量 μg/m3',
                    field: 'infoAir',
                    align: 'center',
                    width: 80,
                    valign: 'middle'
                },
                {
                    title: '更新时间',
                    field: 'infoUpdatetime',
                    align: 'center',
                    width: 120,
                    valign: 'middle'
                }



            ]
        });
    });

function queryInfoByDeviceSerial(){

    //获取查询参数
    var e =$("#deviceSerial").val();
    //调用查询方法
    infoList('/infoListByDeviceSerial/' , e);

}

function queryInfoByDate(){
    var e =$("#uploadtime").val();
    infoList('/infoListByDate/', e);
    console.log(e);
}


function infoList(myUrl,arg){

    $('#table').bootstrapTable('destroy');
    $('#table').bootstrapTable({
                method: "get",
                striped: true,
                singleSelect: false,
                url: myUrl + arg,
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
                        field: 'infoId',
                        align: 'center',
                        width: 40,
                        valign: 'middle'
                    },
                    {
                        title: "设备标签",
                        field: 'infoSerial',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '温度 ℃',
                        field: 'infoTemp',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '湿度 %rh',
                        field: 'infoHumi',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '路灯电压 V',
                        field: 'infoLampV',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '主板电压 V',
                        field: 'infoBoardV',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '光照强度 Lux',
                        field: 'infoLux',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '空气质量 μg/m3',
                        field: 'infoAir',
                        align: 'center',
                        width: 80,
                        valign: 'middle'
                    },
                    {
                        title: '更新时间',
                        field: 'infoUpdatetime',
                        align: 'center',
                        width: 120,
                        valign: 'middle'
                    }



                ]
            });
}