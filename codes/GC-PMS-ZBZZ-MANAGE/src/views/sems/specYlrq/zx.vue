<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="装置名称" prop="plantCode">
        <el-input
          v-model="queryParams.plantCode"
          :placeholder="请输入 + 装置名称"
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
          v-hasPermi="['sems:specYlrq:add']"
          icon="el-icon-plus"
          size="mini"
          type="primary"
          @click="handleAdd"
        > 新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlrq:edit']"
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
          v-hasPermi="['sems:specYlrq:remove']"
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
          v-hasPermi="['sems:specYlrq:edit']"
          icon="el-icon-upload2"
          size="mini"
          type="info"
          @click="handleImport"
        > 导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlrq:export']"
          icon="el-icon-download"
          size="mini"
          type="warning"
          @click="handleExport"
        > 导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="deviceTable" v-loading="loading" :cell-style="tableCellStyle" :data="specYlrqList"
              :height="clientHeight"
              border @selection-change="handleSelectionChange" @sort-change="sortList">
      <el-table-column align="center" fixed="left" type="selection" width="55"/>
      <el-table-column :index="indexMethod" label="序号" align="center" fixed="left" type="index" width="55"/>
      <el-table-column label="装置" :show-overflow-tooltip="true" align="center" fixed="left" prop="plantCode"/>
      <el-table-column label="容器位号" :show-overflow-tooltip="true" align="center" fixed="left" prop="devno"/>
      <el-table-column label="容器名称" :show-overflow-tooltip="true" align="center" prop="devname" width="200"/>
      <el-table-column label="英文名称" :show-overflow-tooltip="true" align="center" prop="devEnname"
                       width="200"/>
      <el-table-column label="P&ID号" :show-overflow-tooltip="true" align="center" fixed="left" prop="pidNo"/>
      <el-table-column label="安全阀" :show-overflow-tooltip="true" align="center" prop="reliefValve"/>
      <el-table-column label="使用证号码" :show-overflow-tooltip="true" align="center" prop="useno" width="120"/>
      <el-table-column label="注册编号" :show-overflow-tooltip="true" align="center" prop="regno"/>
      <el-table-column label="出厂资料" :show-overflow-tooltip="true" align="center" prop="factoryInfo"/>
      <el-table-column label="下次检验日期" align="center" prop="nextWarnDate" sortable="custom" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设备出厂编号" :show-overflow-tooltip="true" align="center" prop="factoryNo" width="120"/>
      <el-table-column label="设计单位" :show-overflow-tooltip="true" align="center" prop="designUnit"/>
      <el-table-column label="制造单位" :show-overflow-tooltip="true" align="center" prop="createUnit"/>
      <el-table-column label="容器类别" :show-overflow-tooltip="true" align="center" prop="vesselType"/>
      <el-table-column label="用途代号" :show-overflow-tooltip="true" align="center" prop="useCode"/>
      <el-table-column label="压力等级代号" :show-overflow-tooltip="true" align="center" prop="pressureGrade" width="120"/>
      <el-table-column label="安全状况等级" :show-overflow-tooltip="true" align="center" prop="safeClass" width="120"/>
      <el-table-column label="投用日期" align="center" prop="submitdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设计使用年限" :show-overflow-tooltip="true" align="center" prop="desYear" width="120"/>
      <el-table-column label="设计寿命到期" align="center" prop="designDead" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最近检查时间" align="center" prop="warnDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :formatter="statusFormat" label="状态" align="center" fixed="left" prop="status"/>
      <el-table-column label="检验状态" align="center" prop="checkStatus"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                       width="140">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sems:specYlrq:edit']"
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

    <!-- 添加或修改特种设备压力容器台账对话框   disabled="disabled"-->
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="title" :visible.sync="open" append-to-body width="1200px">
      <el-form ref="form" :model="form" :rules="rules" label-width="160px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="装置" prop="plantCode">
              <el-input v-model="form.plantCode" :placeholder="请输入+ 装置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="容器位号" prop="devno">
              <el-input v-model="form.devno" :placeholder="请输入+ 容器位号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="容器名称" prop="devname">
              <el-input v-model="form.devname" :placeholder="请输入+ 容器名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="英文名称" prop="devEnname">
              <el-input v-model="form.devEnname" :placeholder="请输入+ 英文名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="使用证编号" prop="useno">
              <el-input v-model="form.useno" :placeholder="请输入+ 使用证编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册编号" prop="regno">
              <el-input v-model="form.regno" :placeholder="请输入+ 注册代码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="容器类别" prop="vesselType">
              <el-input v-model="form.vesselType" :placeholder="请输入+ 容器类别"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="制造单位" prop="createUnit">
              <el-input v-model="form.createUnit" :placeholder="请输入+ 制造单位"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="投用日期" prop="submitdate">
              <el-date-picker v-model="form.submitdate" :placeholder="请选择+ 投用日期" clearable
                              size="small"
                              style="width:100%"
                              type="date"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="P&ID号" prop="pidNo">
              <el-input v-model="form.pidNo" :placeholder="请输入+ P&ID号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设计使用年限" prop="desYear">
              <el-input v-model="form.desYear" :placeholder="请输入+ 设计使用年限"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安全阀" prop="reliefValve">
              <el-input v-model="form.reliefValve" :placeholder="请输入+ 安全阀"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最近检查日期" prop="warnDate">
              <el-date-picker v-model="form.warnDate" :placeholder="请选择+ 最近检查日期" clearable
                              size="small"
                              style="width:100%"
                              type="date"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次检查日期" prop="nextWarnDate">
              <el-date-picker v-model="form.nextWarnDate" :placeholder="请选择+ 下次检查日期" clearable
                              size="small"
                              style="width:100%"
                              type="date"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出厂资料" prop="factoryInfo">
              <el-input v-model="form.factoryInfo" :placeholder="请输入+ 出厂资料"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设计单位" prop="designUnit">
              <el-input v-model="form.designUnit" :placeholder="请输入+ 设计单位"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安全状况等级" prop="safeClass">
              <el-input v-model="form.safeClass" :placeholder="请输入+ 安全状况等级"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用途代号" prop="useCode">
              <el-input v-model="form.useCode" :placeholder="请输入+ 用途代号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出厂编号" prop="factoryNo">
              <el-input v-model="form.factoryNo" :placeholder="请输入+ 出厂编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="压力等级" prop="pressureGrade">
              <el-input v-model="form.pressureGrade" :placeholder="请输入+ 压力等级"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设计寿命到期" prop="designDead">
              <el-date-picker v-model="form.designDead" :placeholder="请选择+ 设计寿命到期" clearable
                              size="small"
                              style="width:100%"
                              type="month"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
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
              <el-input v-model="form.remarks" :placeholder="请输入+ 备注"/>
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
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="primary" @click="submitFileForm">{{
            确定
          }}
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
  addSpecYlrq,
  duplicate,
  exportSpecList,
  exportSpecYlrq,
  getSpecYlrq,
  listSpecYlrq,
  updateSpecYlrq
} from "@/api/sems/specYlrq";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import {listPostUser} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {delSpecYlrq} from "@/api/sems/specYlrq";

export default {
  name: "SpecYlrq",
  components: {Treeselect},
  data() {
    return {
      filterList: [], //删选的list
      waitSubmit: false,
      fullscreenLoading: false,
      hisReformVisible: false,
      dataForm: 0,
      devType: 1,
      devStatus: 1,
      // 遮罩层
      loading: true,
      addAprroveVisible: false,
      hisAprroveVisible: false,
      hisCheckVisible: false,
      modifyVisible: false,
      inspectionInformation: false,
      modifyTitle: '设备修改申请',
      downloadType: '',
      downloadDevAction: process.env.VUE_APP_BASE_API + '/common/download/exportDevList',
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
      // 特种设备压力容器台账表格数据
      specYlrqList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 申请状态字典
      approveStatusOptions: [],
      plantOptions: [],
      plantMaintOptions: [],
      wxjlList: [],
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        // 是否显示弹出层(用户导入)
        open: false,
        type: "specYlrq",
        // 弹出层标题(用户导入)
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/sems/specYlrq/importData"
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
        status: -1,
        approveStatus: null,
        hisReformTime: null
      },
      // 表单参数
      form: {},
      modifyForm: {},
      // 表单校验
      rules: {
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
    }
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
        // response.data[i].dictLabel = this.$t(response.data[i].dictLabel)
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
    /** 查询特种设备压力容器台账列表 */
    getList() {
      this.loading = true;
      listSpecYlrq(this.queryParams).then(response => {
        this.specYlrqList = response.rows;
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
        status: -1,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        approveStatus: null,
        regno: null,
        useno: null,
        warnDate: null,
        warnCycle: null,
        nextWarnDate: null,
        warnFlag: null,
        plantMaint: null,
        engineer: null,
        vesselType: null,
        isKey: null,
        createUnit: null,
        nation: null,
        outSize: null,
        material: null,
        medium: null,
        desPressure: null,
        desTemp: null,
        optPressure: null,
        optTemp: null,
        checkUnit: null,
        reportNo: null,
        isDanger: null,
        isOverlife: null,
        safeClass: null,
        approveTime: null,
        changeTime: null,
        hisReformTime: null,
        desYear: null,
        yearWarnDate: null,
        checkConclusion: null,
        yearNextWarnDate: null,
        yearReportNo: null,
        factoryNo: null
      };
      this.modifyForm = {
        wxjl: null,
        hisId: null
      }
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
      this.title = '添加特种设备压力容器台账';
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpecYlrq(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = '修改特种设备压力容器台账';
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSpecYlrq(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpecYlrq(this.form).then(response => {
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
      this.$confirm('是否确认删除?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delSpecYlrq(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
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
      this.download('sems/specYlrq/export', {
        ...this.queryParams
      }, `devYlrq_${new Date().getTime()}.xlsx`)
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
      this.upload.isUploading = true;
      this.waitSubmit = true;
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
      this.fullscreenLoading = true
    },
    checkList(row, type) {
      this.hisCheckVisible = true
      this.$nextTick(() => {
        this.$refs.hisCheck.init(row, type)
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
      return process.env.VUE_APP_BASE_API + "/sems/specYlrq/updateData"
    },
    //位号颜色变换
    tableCellStyle({row, column, rowIndex, columnIndex}) {

      if (columnIndex == 7 && row.isRepeat == 1) {
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
      listSpecYlrq(this.queryParams).then(response => {
        this.specYlrqList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //结论备注
    renderHeader(h, {column, $index}) {
      let _this = this
      return [
        column.label,
        h(
          "el-tooltip",
          {
            props: {
              content: (function () {
                return _如果是多腔压力容器
              })(),
              placement: "top"
            }
          },
          [
            h("span", {
              class: {
                "el-icon-question": true,
                "report-headerTips": true
              }
            })
          ]
        )
      ]
    },
    //翻页后继续计算序列值
    indexMethod(index) {
      return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1;
    }
  }
};
</script>
