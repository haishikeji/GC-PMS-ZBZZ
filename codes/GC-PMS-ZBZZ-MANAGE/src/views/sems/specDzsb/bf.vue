<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="装置" prop="plantCode">
        <el-input
          v-model="queryParams.plantCode"
          placeholder="装置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="全局搜索" prop="fuzzy">
        <el-input
          v-model="queryParams.fuzzy"
          clearable
          placeholder="请输入要查询的值"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery"> 搜索 </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"> 重置 </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specDzsb:add']"
          icon="el-icon-plus"
          size="mini"
          type="primary"
          @click="handleAdd"
        > 新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specDzsb:edit']"
          :disabled="single"
          icon="el-icon-edit"
          size="mini"
          type="success"
          @click="handleUpdate"
        > 修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specDzsb:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          size="mini"
          type="danger"
          @click="handleDelete"
        > 删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specDzsb:edit']"
          icon="el-icon-upload2"
          size="mini"
          type="info"
          @click="handleImport"
        > 导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specDzsb:export']"
          icon="el-icon-download"
          size="mini"
          type="warning"
          @click="handleExport"
        > 导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="deviceTable" v-loading="loading" :cell-style="tableCellStyle" :data="specDzsbList"
              :height="clientHeight" border @selection-change="handleSelectionChange" @sort-change="sortList">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column label="设备类别" :show-overflow-tooltip="true" align="center" fixed="left"
                       prop="devType"/>
      <el-table-column label="装置" :show-overflow-tooltip="true" align="center" fixed="left" prop="plantCode"/>
      <el-table-column label="设备类别2" :show-overflow-tooltip="true" align="center" fixed="left"
                       prop="devType2"/>
      <el-table-column label="设备型号" :show-overflow-tooltip="true" align="center" prop="model"/>
      <el-table-column label="下次检验日期" align="center" prop="nextWarnDate" sortable="custom" width="140">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出厂资料" :show-overflow-tooltip="true" align="center" prop="factoryInfo"/>
      <el-table-column label="使用登记证编号 车牌号" :show-overflow-tooltip="true" align="center" width="180"
                       prop="plateNo"/>
      <el-table-column label="设备代码" :show-overflow-tooltip="true" align="center" prop="devCode"/>
      <el-table-column label="发动机号/底盘号" :show-overflow-tooltip="true" align="center" prop="engineNo" width="180"/>
      <el-table-column label="防爆等级" :show-overflow-tooltip="true" align="center" prop="exLevel"/>
      <el-table-column label="制造单位" :show-overflow-tooltip="true" align="center" prop="createUnit"/>
      <el-table-column :formatter="statusFormat" label="状态" align="center" fixed="left" prop="status"/>
      <el-table-column label="检验状态" align="center" fixed="left" prop="checkStatus"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                       width="140">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sems:specDzsb:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page-sizes="[20,100,300,500]"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改特种设备起重机械台账对话框 -->
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="title" :visible.sync="open" append-to-body width="1200px">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="装置" prop="plantCode">
              <el-input v-model="form.plantCode" placeholder="装置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="防爆等级" prop="exLevel">
              <el-input v-model="form.exLevel" placeholder="防爆等级"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备代码" prop="devCode">
              <el-input v-model="form.devCode" placeholder="设备代码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发动机号/底盘号" prop="engineNo">
              <el-input v-model="form.engineNo" placeholder="发动机号/底盘号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备型号" prop="model">
              <el-input v-model="form.model" placeholder="设备型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次检验日期" prop="nextWarnDate">
              <el-date-picker v-model="form.nextWarnDate" placeholder="请选择+ 下次检验日期" clearable
                              size="small"
                              style="width: 100%"
                              type="month"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="制造单位" prop="createUnit">
              <el-input v-model="form.createUnit" placeholder="制造单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出厂资料" prop="factoryInfo">
              <el-input v-model="form.factoryInfo" placeholder="出厂资料"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备类别" prop="devType">
              <el-input v-model="form.devType" placeholder="设备类别"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备类别2" prop="devType2">
              <el-input v-model="form.devType2" placeholder="设备类别2"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="使用登记证编号/车牌号" prop="plateNo">
              <el-input v-model="form.plateNo" placeholder="使用登记证编号/车牌号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status" size="large">
                <el-radio label="1" size="large">在用</el-radio>
                <el-radio label="2" size="large">停用</el-radio>
                <el-radio label="3" size="large">报废</el-radio>
                <el-radio label="-1" size="large">封存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm"> 确 定 </el-button>
        <el-button @click="cancel"> 取 消 </el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="upload.title" :visible.sync="upload.open" append-to-body width="400px">
      <el-upload
        ref="upload"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :auto-upload="false"
        :disabled="upload.isUploading"
        :headers="upload.headers"
        :limit="1"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        accept=".xlsx, .xls"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em> 点击上传 </em>
        </div>
        <div slot="tip" class="el-upload__tip">
          <!--                  <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据-->
          <el-link style="font-size:12px" type="info" @click="importTemplate"> 下载模板 </el-link>
        </div>
        <form ref="downloadFileForm" :action="upload.downloadAction" target="FORMSUBMIT">
          <input :value="upload.type" hidden name="type"/>
        </form>
        <div slot="tip" class="el-upload__tip" style="color:red"> 提示：仅允许导入“xls”或“xlsx”格式文件！ </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <a v-if="waitSubmit" style="margin-right: 300px"> 正在导入... </a>
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="primary"
                   @click="submitFileForm"> 确定
        </el-button>
        <el-button @click="upload.open = false"> 取消 </el-button>
      </div>
    </el-dialog>
    <form ref="downloadDevForm" :action="downloadDevAction" target="FORMSUBMIT">
      <input v-model="devType" hidden name="devType"/>
      <input v-model="ids" hidden name="ids"/>
      <input v-model="downloadType" hidden name="downloadType"/>
    </form>
  </div>
</template>

<script>
import {
  addSpecDzsb,
  delSpecDzsb,
  duplicate,
  exportSpecDzsb,
  exportSpecList,
  getSpecDzsb,
  listSpecDzsb,
  updateSpecDzsb
} from "@/api/sems/specDzsb";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "SpecDzsb",
  components: {Treeselect,},
  data() {
    return {
      filterList: [], //删选的list
      fullscreenLoading: false,
      waitSubmit: false,
      hisReformVisible: false,
      dataForm: 0,
      devType: 3,
      // 遮罩层
      loading: true,
      addAprroveVisible: false,
      hisAprroveVisible: false,
      hisCheckVisible: false,
      inspectionInformation: false,
      downloadType: '',
      downloadDevAction: process.env.VUE_APP_BASE_API + '/common/download/exportDevList',
      modifyTitle: '设备修改申请',
      modifyVisible: false,
      // 选中数组
      ids: [],
      dataListSelections: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 特种设备起重机械台账表格数据
      specDzsbList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      plantOptions: [],
      plantMaintOptions: [],
      // 申请状态字典
      approveStatusOptions: [],
      wxjlList: [],
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        // 是否显示弹出层（用户导入）
        type: "specDzsb",
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/sems/specDzsb/importData"
      },
      // 查询参数
      queryParams: {
        fuzzy: null,
        pageNum: 1,
        pageSize: 20,
        orderByColumn: null,
        isAsc: null,
        plantCode: null,
        unit: null,
        devname: null,
        devno: null,
        submitdate: null,
        remarks: null,
        approveStatus: null,
        regno: null,
        useno: null,
        warnDate: null,
        warnCycle: null,
        nextWarnDate: null,
        warnFlag: null,
        plantMaint: null,
        docno: null,
        model: null,
        capacity: null,
        location: null,
        isEX: null,
        status: 3,
      },
      // 表单参数
      form: {},
      modifyForm: {},
      // 表单校验
      rules: {
        id: [
          {required: true, message: '唯一标识ID不能为空', trigger: "blur"}
        ],
        plantCode: [
          {required: true, message: '装置名称不能为空', trigger: "blur"}
        ],
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = document.body.clientHeight - 250
    })
    this.getList();
    this.getTreeselect();
    this.getDicts("spec_dev_status").then(response => {
      this.statusOptions = response.data;
    });
    //预警等级字典
    this.getDicts("ALARM_LEVEL").then(response => {

      for (let i = 0; i < response.data.length; i++) {
        let items = {
          text: '',
          value: ''
        };
        items.value = response.data[i].dictValue;
        items.text = response.data[i].dictLabel;
        this.filterList.push(items);
      }
    });
    this.getDicts("spec_approve_status").then(response => {
      for (let i = 0; i < response.data.length; i++) {
        if (!["5", "6", "7", "8", "16", "17"].includes(response.data[i].dictValue)) {
          this.approveStatusOptions.push(response.data[i])
        }
      }
    });
    this.getDicts("SPEC_PLANT_MAINT").then(response => {
      this.plantMaintOptions = response.data;
    });
    let plantParams = {
      pType: 1
    }
  },
  methods: {
    /** 查询特种设备起重机械台账列表 */
    getList() {
      this.loading = true;
      listSpecDzsb(this.queryParams).then(response => {
        this.specDzsbList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 申请状态字典翻译
    approveStatusFormat(row, column) {
      return this.selectDictLabel(this.approveStatusOptions, row.approveStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        plantCode: null,
        unit: null,
        devname: null,
        devno: null,
        submitdate: null,
        status: 3,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        perTestConclusion: null,
        approveStatus: 0,
        regno: null,
        useno: null,
        warnDate: null,
        warnCycle: null,
        nextWarnDate: null,
        warnFlag: null,
        plantMaint: null,
        docno: null,
        model: null,
        capacity: null,
        location: null,
        createUnit: null,
        checkStrategy: null,
        yearMaint: null,
        reportNo: null,
        approveTime: null,
        changeTime: null,
        checkUnit: null,
        engineer: null,
        factoryNo: null,
        devCode: null
      };
      this.resetForm("form");
      this.dataForm = 0;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.dataListSelections = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = 添加 + 特种设备起重机械台账;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpecDzsb(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = 修改 + 特种设备起重机械台账;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSpecDzsb(this.form).then(response => {
              this.$modal.msgSuccess(修改成功);
              this.open = false;
              this.getList();
            });
          } else {
            addSpecDzsb(this.form).then(response => {
              this.$modal.msgSuccess(新增成功);
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function() {
        return delSpecDzsb(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess('删除成功');
      })
    },
    /** 去重按钮操作 */
    handleDup() {
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function () {
        return duplicate();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess('删除成功');
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sems/specDzsb/export', {
        ...this.queryParams
      }, `devQzjx_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '用户导入';
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.waitSubmit = true;
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.waitSubmit = false;
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.fullscreenLoading = false;
      if (response.data.length > 0) {
        let failrow = ''
        for (let i = 0; i < response.data.length; i++) {
          failrow += response.data[i] + ','
        }
        this.$alert('导入成功条数:' + response.msg + '<br>' + '失败行数:' + failrow, '导入结果', {dangerouslyUseHTMLString: true});
      } else {
        this.$alert('导入成功条数:' + response.msg, '导入结果', {dangerouslyUseHTMLString: true});
      }
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.fullscreenLoading = true;
    },
    addAprrove(row, type) {
      var rows = row ? [row] : this.dataListSelections.map(item => {
        return item
      })
      for (let i = 0; i < rows.length; i++) {
        if (rows[i].approveStatus != 0) {
          this.$alert('当前设备正在申请中，无法重复申请', '提示', {
            type: 'warning'
          })
          return
        }
      }
      this.addAprroveVisible = true
      console.log(rows)
      console.log(type)
      this.$nextTick(() => {
        this.$refs.addApprove.init(rows, type)
      })
    },
    hisApprove(row, type) {
      this.hisAprroveVisible = true
      this.$nextTick(() => {
        this.$refs.hisApprove.init(row, type)
      })
    },
    checkList(row, type) {
      this.hisCheckVisible = true
      this.$nextTick(() => {
        this.$refs.hisCheck.init(row, type)
      })
    },
    hisReform(row) {
      this.hisReformVisible = true
      this.$nextTick(() => {
        this.$refs.hisReformList.init(row)
      })
    },
    //导出excel
    downloadDev(type) {
      var rows = this.dataListSelections.map(item => {
        return item.id
      })
      const queryParams = {ids: null};
      queryParams.ids = rows.join()
      exportSpecList(queryParams).then(response => {
        this.download(response.msg);
      })
    },
    uploadUrl(uploadType) {
      return process.env.VUE_APP_BASE_API + "/sems/specDzsb/updateData"
    },
    //位号颜色变换
    tableCellStyle({row, column, rowIndex, columnIndex}) {
      if (columnIndex == 8 && row.isRepeat == 1) {
        return "color:rgba(255, 26, 26, 0.98);"
      }
      if (row.warnFlag == 1) {
        return "background-color:rgba(255, 255,153, 0.5);"
      }
      if (row.warnFlag == 2) {
        return "background-color:rgba(255, 180, 68, 0.5);"
      }
      if (row.warnFlag == 3) {
        return "background-color:rgba(255, 68,68, 0.5);"
      }
    },
    //element表格排序
    sortList(val) {
      var sort = "asc";
      let sortTip = val.order
      this.queryParams.isAsc = sort;
      this.queryParams.orderByColumn = val.prop;
      if (sortTip === 'descending') {
        sort = 'desc' // 降序
      } else if (sortTip === 'ascending') {
        sort = 'asc' // 升序
      } else if (sortTip === null) {
        this.queryParams.isAsc = null;
        this.queryParams.orderByColumn = null;
      }
      this.loading = true;
      listSpecDzsb(this.queryParams).then(response => {
        this.specDzsbList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
  }
};
</script>
