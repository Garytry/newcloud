var TableInit = function() {
	var oTableInit = new Object();
	//初始化Table
	oTableInit.Init = function() {
		$vue_table = $('#vue_table').bootstrapTable({
			url: tableDataUrl,
			method: 'GET', //请求方式（*）
			//toolbar: '#toolbar',              //工具按钮用哪个容器
			striped: true, //是否显示行间隔色
			cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true, //是否显示分页（*）
			sortable: true, //是否启用排序
			sortOrder: "asc", //排序方式
			sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber: 1, //初始化加载第一页，默认第一页,并记录
			pageSize: 10, //每页的记录行数（*）
			pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
			search: false, //是否显示表格搜索
			strictSearch: true,
			showColumns: true, //是否显示所有的列（选择显示的列）
			showRefresh: true, //是否显示刷新按钮
			minimumCountColumns: 2, //最少允许的列数
			clickToSelect: true, //是否启用点击选中行
			height: 800,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId: "ID", //每一行的唯一标识，一般为主键列
			showToggle: true, //是否显示详细视图和列表视图的切换按钮
			cardView: false, //是否显示详细视图
			detailView: false, //是否显示父子表
			//得到查询的参数
			queryParams: oTableInit.queryParams,
			columns: [{
				checkbox: true
			}, {
				field: 'appId',
				title: '应用APP标识'
			}, {
				field: 'appSecret',
				title: 'APP的secret码'
			}, {
				field: 'type',
				title: '应用类型'
			}, {
				field: 'appType',
				title: '应用类型分类',
				visible: false,
				formatter: function (value){
					if (value == "many") {
						return "一包多应用";
					}
					return "一包单应用";
				}
			}, {
				field: 'orgId',
				title: '部门ID',
				visible: false
			}, {
				field: 'orgName',
				title: '部门名称 '
			}, {
				field: 'url',
				title: '服务访问地址'
			}, {
				field: 'fileServer',
				title: '文件服务存储目录'
			}, {
				field: 'appLevel',
				title: '应用级别',
				formatter: function (value){
					var LevelName = "";
					$.each(tableMenuDatas,function(index,menu){
						if (menu.appLevel == value) {
							LevelName = menu.LevelName;
						}
					});
					return LevelName;
				}
			}, {
				field: 'gateway',
				title: '是否注册',
				formatter: function (value){
					if (value == "true") {
						return "注册";
					}
					return "未注册";
				}
			}, {
				field: 'appBranch',
				title: '应用分支',
				visible: false
			}, {
				field: 'remark',
				title: '备注',
				visible: false
			}],
			onLoadSuccess: function() {
				//alert("success");
			},
			onLoadError: function() {
				alert("error");
			},
			onDblClickRow: function(row, $element) {
				var id = row.ID;
				EditViewById(id, 'view');
			}
		});
	};

	//得到查询的参数
	oTableInit.queryParams = function(params) {
		//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		var temp = {
			rows: params.limit, //页面大小
			page: (params.offset / params.limit) + 1, //页码
			sort: params.sort, //排序列名  
			sortOrder: params.order, //排位命令（desc，asc） 
			search:  params.search,
			appLevel: appLevelParam,
			orgId: orgIdParam,
			appBranch:$("#appBranchValue").val(),
			searchValue: $("#searchValue").val()
		};
		return temp;
	};
	return oTableInit;
};

var fromValidator = function() {
	$("#dataFrom").bootstrapValidator({
		live: 'enabled', //验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
		excluded: [':disabled', ':hidden', ':not(:visible)'], //排除无需验证的控件，比如被禁用的或者被隐藏的
		submitButtons: '#saveFromData', //指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面
		message: '通用的验证失败消息', //好像从来没出现过
		feedbackIcons: { //根据验证结果显示的各种图标
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			appId: {
				validators: {
					notEmpty: { //检测非空,radio也可用
						message: '不能为空'
					}
				}
			},
			appSecret: {
				validators: {
					notEmpty: { //检测非空,radio也可用
						message: '不能为空'
					}
				}
			},
			type: {
				validators: {
					notEmpty: { //检测非空,radio也可用
						message: '不能为空'
					}
				}
			},
			url: {
				validators: {
					notEmpty: { //检测非空,radio也可用
						message: '不能为空'
					}
				}
			},
			appLevel: {
				validators: {
					notEmpty: { //检测非空,radio也可用
						message: '不能为空'
					}
				}
			}
		}
	});
}