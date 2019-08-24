//菜单请求路径
//var menuDataUrl = "config/data/menu.json"
var menuDataUrl = "/baseAppOrgMapped/getMenu"
//获取应用分支信息
//var appBranchUrl = "config/data/menu.json"
var appBranchUrl = "/baseAppOrgMapped/getAppBranch"
//列表数据请求路径	
// var tableDataUrl = "config/data/data.json"
var tableDataUrl = "/baseAppOrgMapped/list"
//应用类型请求路径	
// var typeUrl = "config/data/type.json"
var typeUrl = "/apptype/list"
//编辑信息请求路径	
// var infoDataUrl = "config/data/info.json"
var infoDataUrl = "/baseAppOrgMapped/info"
//保存信息请求路径	
// var saveFromDataUrl = "config/data/save.json"
var saveFromDataUrl = "/baseAppOrgMapped/save"	
//删除信息请求路径	
// var deleteDataUrl = "config/data/delete.json"
var deleteDataUrl = "/baseAppOrgMapped/delete"
//同步组织机构	
// var syncOrganData = "config/data/delete.json"
var syncOrganUrl = "/organ/import"
//清空应用配置缓存
var clearConfigCacheUrl = "/config/clearcache"
//列表对象	
var $vue_table;
//删除参数ID
var delteParams = "";
//请求级别分类参数
var appLevelParam = "";
//组织机构ID
var orgIdParam = "";
//vue初始对象
var tableMenuDatas = {};
var initFromData = {
		appId:"",
		appSecret:"",
		type:"",
		orgId: "",
		orgName:"",
		gateway:"false",
		appLevel:"",
		url:"",
		fileServer:"",
		appBranch:"",
		remark:""
};
var pageModule = function() {
	// 加载数据
	var initData = function() {

		var vue_menu = new Vue({
			el: '#vue_nav',
			data: {
				menuDatas: [],
				appBranch:{}
			},
			created: function() {
				axios.get(menuDataUrl)
					.then(function(response) {
						vue_menu.menuDatas = response.data;
						vue_content.menuDatas = response.data;
						tableMenuDatas = response.data;
						vue_content.orgDatas = response.data[1];
					})
					.catch(function(error) {
						alert("菜单数据获取失败");
					});
				axios.get(appBranchUrl)
				.then(function(response) {
					vue_menu.appBranch = response.data;
					vue_content.appBranch = response.data;
				})
				.catch(function(error) {
					alert("菜单数据获取失败");
				})
			},
			methods: {
				searchValue: function() {
					// 搜索刷新
					$vue_table.bootstrapTable('refresh');
				},
				getTableData: function(appLevel, orgId) {
					// 获取列表数据
					appLevelParam = appLevel;
					orgIdParam = orgId;
					$vue_table.bootstrapTable('refresh');
				},
				// 同步组织机构数据
				syncOrganData: function() {
					axios.get(syncOrganUrl)
					.then(function(response) {
						alert(response.data);
					})
					.catch(function(error) {
						alert(error);
					})
				},	
				// 清空应用配置缓存
				clearConfigCache: function() {
					axios.get(clearConfigCacheUrl)
					.then(function(response) {
						alert(response.data);
					})
					.catch(function(error) {
						alert(error);
					})
				},	
				// 同步组织机构数据
				refreshTable: function() {
					$vue_table.bootstrapTable('refresh');
				}
			}
		});

		var vue_content = new Vue({
			el: '#vue_content',
			data: {
				menuDatas: [],
				orgDatas: [],
				typeDatas: [],
				appBranch:{},
				newFromData: initFromData
			},
			created: function() {
				axios.get(typeUrl)
					.then(function(response) {
						vue_content.typeDatas = response.data;
					})
					.catch(function(error) {
						vue_content.typeDatas = [];
					});
			},
			mounted() {
				var $vue_table = new TableInit();
				$vue_table.Init();
				fromValidator();
			},
			methods: {
				// 加载新增页面
				addFromData: function() {
					// 重置数据
					vue_content.clearFromData();
					vue_content.newFromData = initFromData;
					$("#type").selectpicker('val',vue_content.newFromData.type);
					$("#appLevel").selectpicker('val',vue_content.newFromData.appLevel);
					$("#orgId").selectpicker('val',vue_content.newFromData.orgId);
					$("#appBranch").selectpicker('val',vue_content.newFromData.appBranch);
					$(".selectpicker").selectpicker('refresh');
					// 显示新增页面
					$('#add_edit').modal('show');
				},
				// 加载编辑页面
				editFromData: function() {
					// 使用getSelections获得选择行数据，row是json格式的数据
					var rows = $vue_table.bootstrapTable('getSelections');
					if(rows.length != 1) {
						alert("请选项一条需要编辑的数据");
						return;
					}
					$("#gatewaytrue").removeAttr('checked');
					$("#gatewayfalse").removeAttr('checked'); 
				    // 加载编辑数据
					axios.get(infoDataUrl,{params:{appId:rows[0].appId}})
					.then(function(response) {
						vue_content.clearFromData();
						var responseData = response.data;
						vue_content.newFromData = responseData;
						console.log(responseData);
						$("#type").selectpicker('val',responseData.appType+":"+responseData.type);
						$("#appLevel").selectpicker('val',responseData.appLevel);
						$("#orgId").selectpicker('val',responseData.orgId);
						$("#appBranch").selectpicker('val',responseData.appBranch);
						$(".selectpicker").selectpicker('refresh');
						if (responseData.gateway == "true") {
						    $("#gatewaytrue").prop('checked',true);
						    $("#gatewayfalse").removeAttr('checked'); 
						} else {
							$("#gatewaytrue").removeAttr('checked');
							$("#gatewayfalse").prop('checked',true); 
						}
						$('#add_edit').modal('show');
					})
					.catch(function(error) {
						alert(error);
					})
				},
				// 删除数据
				deleteData: function() {
					// 使用getSelections获得选择行数据，row是json格式的数据
					var rows = $vue_table.bootstrapTable('getSelections');
					if(rows.length < 1) {
						alert("请选择要删除数据");
						return;
					}
					var appIds = [];
					$.each(rows,function(index,row){
						appIds.push(row.appId);
					});
					delteParams = appIds.toString();
				    $('#btn_delete').modal('show');
				},
				// 确认删除数据
				sureDeleteData: function() {
					axios.get(deleteDataUrl,{params:{appIds:delteParams}})
					.then(function(response) {
						alert(response.data);
						$vue_table.bootstrapTable('refresh');
						$('#btn_delete').modal('hide');
					})
					.catch(function(error) {
						alert(error);
					})
				},
				// 取消删除数据
				quitDeleteData: function() {
					$('#btn_delete').modal('hide');
				},
				// 保存数据
				saveFromData: function() {
					vue_content.newFromData.gateway = $("input[name=gateway]:checked").val();
					vue_content.newFromData.orgName = $("#orgId:selected").text();
					vue_content.newFromData.orgId = $("#orgId").val();
					$("#dataFrom").bootstrapValidator('validate'); // 提交验证
					if($("#dataFrom").data('bootstrapValidator').isValid()) {
						axios.post(saveFromDataUrl, vue_content.newFromData)
							.then(function(response) {　　
								$vue_table.bootstrapTable('refresh');
								alert(response.data);
								$('#add_edit').modal('hide');
							}).catch(function(error) {　　
								alert(error);
							});
					}
				},
				gatewayFunction: function() {
					// 根据appId和是否走网关服务确定服务地址
					if($("input[name=gateway]:checked").val()=="true"){
						vue_content.newFromData.url = vue_content.newFromData.appId;
						// 根据是一包多应用还是一对一应用确定服务地址
						if($("#type").val() && ($("#type").val().indexOf("many") != -1)){
							vue_content.newFromData.url = vue_content.newFromData.appId.split("_app")[0];
						} 
					} else {
						vue_content.newFromData.url = "http://"
					}
				},
				inputFunction: function() {
					// 根据appId和是否走网关服务确定服务地址
					if($("input[name=gateway]:checked").val()=="true"){
						vue_content.newFromData.url = vue_content.newFromData.appId;
						// 根据是一包多应用还是一对一应用确定服务地址
						if($("#type").val() && ($("#type").val().indexOf("many") != -1)){
							vue_content.newFromData.url = vue_content.newFromData.appId.split("_app")[0];
						} 
					}
					// 根据appId确定文件服务存储目录
					if (vue_content.newFromData.appId.contains("_")) {
						var fileStr = vue_content.newFromData.appId.split("_")[0];
						vue_content.newFromData.fileServer=fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length);
					} else {
						vue_content.newFromData.fileServer=vue_content.newFromData.appId;
					}
				},
				organinputFunction: function() {
					// 根据应用级别确认组织机构是否可选
					if($("#appLevel").val() && $("#appLevel").val() == "1"){
						$("#orgId").attr('disabled',false);
						$("#orgId").selectpicker('refresh');
						return;
					} 
					$("#orgId").selectpicker('val','');
					$("#orgId").attr('disabled',true);
					$("#orgId").selectpicker('refresh');
				},
				// 重置数据
				clearFromData: function() {
					vue_content.newFromData.appId = "";
					vue_content.newFromData.appSecret = "";
					vue_content.newFromData.type = "";
					vue_content.newFromData.orgId = "";
					vue_content.newFromData.orgName = "";
					vue_content.newFromData.gateway = "false";
					vue_content.newFromData.appLevel = "";
					vue_content.newFromData.url = "";
					vue_content.newFromData.fileServer = "";
					vue_content.newFromData.appBranch = "";
					vue_content.newFromData.remark = "";
				}
			}
		});

	}

	return {
		// 加载页面处理程序
		initControl: function() {
			initData();
		}
	};

}(); 