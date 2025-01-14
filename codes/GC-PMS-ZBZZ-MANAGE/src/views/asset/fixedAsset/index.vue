<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="140px">
      <el-form-item label="资产类别" prop="assetType">
        <el-input
          v-model="queryParams.assetType"
          placeholder="请输入资产类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资产描述（中文）" prop="devName">
        <el-input
          v-model="queryParams.devName"
          placeholder="请输入资产描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资产描述（英文）" prop="devEnname">
        <el-input
          v-model="queryParams.devEnname"
          placeholder="请输入资产描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="全局搜索" prop="fuzzy">
        <el-input
          v-model="queryParams.fuzzy"
          placeholder="请输入要查询的值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['sems:fixedAsset:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['sems:fixedAsset:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['sems:fixedAsset:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['sems:fixedAsset:edit']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['sems:fixedAsset:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column type="selection" fixed="left" width="55" align="center"/>
      <el-table-column type="index" label="序号" fixed="left" :index="indexMethod" width="55" align="center"/>
      <el-table-column label="资产类别" align="center" fixed="left" prop="assetType" :show-overflow-tooltip="true"/>
      <el-table-column label="资产编号" align="center" fixed="left" prop="assetNo" :show-overflow-tooltip="true"/>
      <el-table-column label="资产子编号" align="center" fixed="left" prop="assetSubNo" :show-overflow-tooltip="true" width="100"/>
      <el-table-column label="资产描述（中文）" align="center" fixed="left" prop="devName" :show-overflow-tooltip="true" width="140"/>
      <el-table-column label="资产描述（英文）" align="center" fixed="left" prop="devEnname" :show-overflow-tooltip="true"
                       width="140"/>
      <el-table-column label="成本中心名称" align="center" prop="costCenter" :show-overflow-tooltip="true" width="140"/>
      <el-table-column label="成本中心代码" align="center" prop="costCenterCode" :show-overflow-tooltip="true"
                       width="140"/>
      <el-table-column label="资产类别AP03XXX" align="center" prop="assetTypeAp" :show-overflow-tooltip="true"
                       width="180"/>
      <el-table-column label="资本化日期Capitalized on" align="center" prop="capitalizedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.capitalizedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数量Quantity" align="center" prop="quantity" :show-overflow-tooltip="true" width="140"/>
      <el-table-column label="币种" align="center" prop="currency" :show-overflow-tooltip="true"/>
      <el-table-column label="原值Current APC" align="center" prop="currentApc" :show-overflow-tooltip="true"
                       width="160"/>
      <el-table-column label="使用年限" align="center" prop="serviceLife" :show-overflow-tooltip="true"/>
      <el-table-column label="管理人" align="center" prop="administrator" :show-overflow-tooltip="true"/>
      <el-table-column label="位号" align="center" prop="devNo" :show-overflow-tooltip="true"/>
      <el-table-column label="资产详细" align="center" prop="assetDetail" :show-overflow-tooltip="true"/>
      <el-table-column label="工艺子单元" align="center" prop="processSubunit" :show-overflow-tooltip="true"
                       width="100"/>
      <el-table-column label="PID图号/其他图号" align="center" prop="pidNo" :show-overflow-tooltip="true" width="180"/>
      <el-table-column label="安装位置" align="center" prop="installPosition" :show-overflow-tooltip="true"/>
      <el-table-column label="创建原由（项目号MOC号）" align="center" prop="createReason" :show-overflow-tooltip="true"
                       width="190"/>
      <el-table-column label="条形码位置" align="center" prop="barcodePosition" :show-overflow-tooltip="true"
                       width="100"/>
      <el-table-column label="报废原由" align="center" prop="scrapReason" :show-overflow-tooltip="true"/>
      <el-table-column label="报废提出时间" align="center" prop="scrapPoseDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.scrapPoseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报废完成时间" align="center" prop="scrapDoneDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.scrapDoneDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报废人" align="center" prop="scraper" :show-overflow-tooltip="true"/>
      <el-table-column label="在用状态" align="center" prop="usingStatus" :show-overflow-tooltip="true"
                       :formatter="devStatusFormat"/>
      <el-table-column label="备注" align="center" prop="remarks" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" fixed="right" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sems:fixedAsset:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sems:fixedAsset:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改固定资产对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="1400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产描述(中文)" prop="devName">
              <el-input v-model="form.devName" placeholder="请输入资产描述"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产描述(英文)" prop="assetNo">
              <el-input v-model="form.devEnname" placeholder="请输入资产描述"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产类别" prop="assetType">
              <el-input v-model="form.assetType" placeholder="请输入资产类别"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产编号" prop="assetNo">
              <el-input v-model="form.assetNo" placeholder="请输入资产编号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产子编号" prop="assetSubNo">
              <el-input v-model="form.assetSubNo" placeholder="请输入资产子编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成本中心名称" prop="costCenter">
              <el-input v-model="form.costCenter" placeholder="请输入成本中心名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成本中心代码" prop="costCenterCode">
              <el-input v-model="form.costCenterCode" placeholder="请输入成本中心代码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="位号" prop="devNo">
              <el-input v-model="form.devNo" placeholder="请输入位号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产类别AP03XXX" prop="assetTypeAp">
              <el-input v-model="form.assetTypeAp" placeholder="请输入资产类别AP03XXX"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资本化日期Capitalized on" prop="capitalizedDate">
              <el-date-picker clearable size="small" style="width: 100%"
                              v-model="form.capitalizedDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择资本化日期Capitalized on">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数量Quantity" prop="quantity">
              <el-input v-model="form.quantity" placeholder="请输入数量Quantity"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="币种" prop="currency">
              <el-input v-model="form.currency" placeholder="请输入币种"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="PID图号/其他图号" prop="pidNo">
              <el-input v-model="form.pidNo" placeholder="请输入PID图号/其他图号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装位置" prop="installPosition">
              <el-input v-model="form.installPosition" placeholder="请输入安装位置"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="原值Current APC" prop="currentApc">
              <el-input v-model="form.currentApc" placeholder="请输入原值Current APC"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用年限" prop="serviceLife">
              <el-input v-model="form.serviceLife" placeholder="请输入使用年限"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管理人" prop="administrator">
              <el-input v-model="form.administrator" placeholder="请输入管理人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产详细" prop="assetDetail">
              <el-input v-model="form.assetDetail" placeholder="请输入资产详细"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工艺子单元" prop="processSubunit">
              <el-input v-model="form.processSubunit" placeholder="请输入工艺子单元"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建原由（项目号MOC号）" prop="createReason">
              <el-input v-model="form.createReason" placeholder="请输入创建原由"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="条形码位置" prop="barcodePosition">
              <el-input v-model="form.barcodePosition" placeholder="请输入条形码位置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报废原由" prop="scrapReason">
              <el-input v-model="form.scrapReason" placeholder="请输入报废原由"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报废提出时间" prop="scrapPoseDate">
              <el-date-picker clearable size="small" style="width: 100%"
                              v-model="form.scrapPoseDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择报废提出时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报废完成时间" prop="scrapDoneDate">
              <el-date-picker clearable size="small" style="width: 100%"
                              v-model="form.scrapDoneDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择报废完成时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报废人" prop="scraper">
              <el-input v-model="form.scraper" placeholder="请输入报废人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在用状态" prop="usingStatus">
              <el-radio-group v-model="form.usingStatus" size="large">
                <el-radio label="1" size="large">在用</el-radio>
                <el-radio label="2" size="large">停用</el-radio>
                <el-radio label="3" size="large">报废</el-radio>
                <el-radio label="-1" size="large">封存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog :close-on-click-modal="false" :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm" v-loading.fullscreen.lock="fullscreenLoading">确 定
        </el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <form ref="downloadFileForm" :action="upload.downloadAction" target="FORMSUBMIT">
      <input name="type" :value="upload.type" hidden/>
    </form>
  </div>
</template>

<script>
import {addAsset, delAsset, exportAsset, getAsset, listAsset, updateAsset} from "@/api/asset/fixedAsset";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Asset",
  components: {Treeselect},
  data() {
    return {
      devStatusOptions: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 固定资产表格数据
      assetList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false, fullscreenLoading: false,
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        type: "fixedAsset",
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
        url: process.env.VUE_APP_BASE_API + "/sems/fixedAsset/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        fuzzy:null,
        assetType: null,
        assetNo: null,
        assetSubNo: null,
        devName: null,
        devEnname: null,
        costCenter: null,
        costCenterCode: null,
        assetTypeAp: null,
        capitalizedDate: null,
        quantity: null,
        currency: null,
        currentApc: null,
        serviceLife: null,
        administrator: null,
        devNo: null,
        assetDetail: null,
        processSubunit: null,
        pidNo: null,
        installPosition: null,
        createReason: null,
        barcodePosition: null,
        scrapReason: null,
        scrapPoseDate: null,
        scrapDoneDate: null,
        scraper: null,
        usingStatus: null,
        status: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        devName: [
          {required: true, message: '资产描述不能为空', trigger: "blur"}
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
      this.devStatusOptions = response.data;
    });
  },
  methods: {
    // 资产状态字典翻译
    devStatusFormat(row, column) {
      return this.selectDictLabel(this.devStatusOptions, row.usingStatus);
    },
    /** 查询固定资产列表 */
    getList() {
      this.loading = true;
      listAsset(this.queryParams).then(response => {
        this.assetList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        assetType: null,
        assetNo: null,
        assetSubNo: null,
        devName: null,
        devEnname: null,
        costCenter: null,
        costCenterCode: null,
        assetTypeAp: null,
        capitalizedDate: null,
        quantity: null,
        currency: null,
        currentApc: null,
        serviceLife: null,
        administrator: null,
        devNo: null,
        assetDetail: null,
        processSubunit: null,
        pidNo: null,
        installPosition: null,
        createReason: null,
        barcodePosition: null,
        scrapReason: null,
        scrapPoseDate: null,
        scrapDoneDate: null,
        scraper: null,
        usingStatus: "0",
        status: 0,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null
      };
      this.resetForm("form");
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
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加固定资产";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAsset(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改固定资产";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAsset(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAsset(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
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
      }).then(function () {
        return delAsset(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sems/fixedAsset/export', {
      ...this.queryParams
    }, `fixedAsset_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
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
    //翻页后继续计算序列值
    indexMethod(index) {
      return (this.queryParams.pageNum-1)*this.queryParams.pageSize+index+1;
    }
  }
};
</script>
