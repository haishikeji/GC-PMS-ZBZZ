<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="90px">
      <el-form-item label="设备中文名" prop="devName">
        <el-input
            v-model="queryParams.devName"
            clearable
            placeholder="请输入设备中文名"
            size="small"
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备英文名" prop="devEnname">
        <el-input
            v-model="queryParams.devEnname"
            clearable
            placeholder="请输入设备英文名"
            size="small"
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资产号" prop="assetNo">
        <el-input
            v-model="queryParams.assetNo"
            clearable
            placeholder="请输入资产号"
            size="small"
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input
            v-model="queryParams.model"
            clearable
            placeholder="请输入型号"
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
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:add']"
            icon="el-icon-plus"
            size="mini"
            type="primary"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:edit']"
            :disabled="single"
            icon="el-icon-edit"
            size="mini"
            type="success"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:remove']"
            :disabled="multiple"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:edit']"
            icon="el-icon-upload2"
            size="mini"
            type="info"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:export']"
            icon="el-icon-download"
            size="mini"
            type="warning"
            @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain
            v-hasPermi="['sems:main:add']"
            icon="el-icon-refresh"
            size="mini"
            type="danger"
            @click="syncDev"
        >同步所有设备
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mainList" :height="clientHeight" border
              @selection-change="handleSelectionChange" :row-class-name="tableRowClassName">
      <el-table-column align="center" fixed="left" type="selection" width="55"/>
      <el-table-column :index="indexMethod" align="center" fixed="left" label="序号" type="index" width="50"/>
      <el-table-column :show-overflow-tooltip="true" align="center"  label="设备中文名" prop="devName"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center"  label="设备英文名" prop="devEnname"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" fixed="left" label="位号" prop="devno"/>
      <el-table-column :formatter="yseOrNoFormat" :show-overflow-tooltip="true" fixed="left" align="center" label="是否在PID上"
                       prop="isPid"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" fixed="left" label="P&ID图号/其他图号" prop="pidNo" width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="安装位置" prop="installPosition"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="功能位置" prop="funcPosition"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="资产号" prop="assetNo"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="安全相关" prop="securityRelevant"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="安全相关来自于" prop="securityRelevantFrom"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="生产厂家" prop="manufacturer"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="生产年份" prop="productionYear"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="型号" prop="model"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="主要规格参数" prop="mainSpecifications"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="设备铭牌" prop="dataPlate"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="维修风险" prop="maintenanceRisk"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="生产风险" prop="productionRisk"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="设备分级" prop="devGrade"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="设备来自" prop="devFrom"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="设备类型（一级）" prop="devType1" width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="设备类型（二级）" prop="devType2" width="150"/>
      <el-table-column :formatter="devStatusFormat" :show-overflow-tooltip="true" align="center" label="资产状态"
                       prop="assetStatus"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="资料完整度" prop="dataIntegrity"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="PM状态" prop="pmStatus"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="资产剩余寿命评估"
                       prop="assetResidueLifeAssess"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="资产整体状态" prop="overallAssetStatus"
                       width="150"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="是否删除" prop="delFlag"
                       width="100" :formatter="yseOrNoFormat"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="备注" prop="remarks"/>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button
              v-hasPermi="['sems:main:edit']"
              icon="el-icon-edit"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-if="scope.row.delFlag!=2"
          >修改
          </el-button>
          <el-button
              v-hasPermi="['sems:main:remove']"
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-if="scope.row.delFlag!=2"
          >删除
          </el-button>
          <el-button
              v-hasPermi="['sems:main:remove']"
              icon="el-icon-folder"
              size="mini"
              type="text"
              @click="toFile(scope.row)"
          >附件({{scope.row.countNum}})
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :limit.sync="queryParams.pageSize"
        :page.sync="queryParams.pageNum"
        :total="total"
        @pagination="getList"
    />

    <!-- 添加或修改设备总对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" append-to-body width="1400px">
      <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备中文名" prop="devName">
              <el-input v-model="form.devName" placeholder="请输入设备中文名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备英文名" prop="devEnname">
              <el-input v-model="form.devEnname" placeholder="请输入设备英文名"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="位号" prop="devno">
              <el-input v-model="form.devno" placeholder="请输入位号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否在PID上" prop="isPid">
              <el-radio-group v-model="form.isPid" size="small">
                <el-radio label="1" size="large">是</el-radio>
                <el-radio label="0" size="large">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="P&ID图号/其他图号" prop="pidNo">
              <el-input v-model="form.pidNo" placeholder="请输入P&ID图号/其他图号"/>
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
            <el-form-item label="功能位置" prop="funcPosition">
              <el-input v-model="form.funcPosition" placeholder="请输入功能位置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产号" prop="assetNo">
              <el-input v-model="form.assetNo" placeholder="请输入资产号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安全相关" prop="securityRelevant">
              <el-input v-model="form.securityRelevant" placeholder="请输入安全相关"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安全相关来自于" prop="securityRelevantFrom">
              <el-input v-model="form.securityRelevantFrom" placeholder="请输入安全相关来自于"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产厂家" prop="manufacturer">
              <el-input v-model="form.manufacturer" placeholder="请输入生产厂家"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产年份" prop="productionYear">
              <el-input v-model="form.productionYear" placeholder="请输入生产年份"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="型号" prop="model">
              <el-input v-model="form.model" placeholder="请输入型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主要规格参数" prop="mainSpecifications">
              <el-input v-model="form.mainSpecifications" placeholder="请输入主要规格参数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备铭牌" prop="dataPlate">
              <el-input v-model="form.dataPlate" placeholder="请输入设备铭牌"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维修风险" prop="maintenanceRisk">
              <el-input v-model="form.maintenanceRisk" placeholder="请输入维修风险"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生产风险" prop="productionRisk">
              <el-input v-model="form.productionRisk" placeholder="请输入生产风险"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备来自" prop="devFrom">
              <el-input v-model="form.devFrom" placeholder="请输入设备来自"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备类型（一级）" prop="devType1">
              <el-select v-model="form.devType1" placeholder="请选择设备类型" clearable
                         style="width: 100%" @change="getSubType">
                <el-option
                    v-for="dict in devTypeOptions"
                    :key="dict.devType"
                    :label="dict.devType"
                    :value="dict.devType"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备类型（二级）" prop="devType2">
              <el-select v-model="form.devType2" placeholder="请选择设备类型" clearable
                         style="width: 100%">
                <el-option
                    v-for="dict in devType2Options"
                    :key="dict.devType"
                    :label="dict.devType"
                    :value="dict.devType"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资料完整度" prop="dataIntegrity">
              <el-input v-model="form.dataIntegrity" placeholder="请输入资料完整度"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="PM状态" prop="pmStatus">
              <el-input v-model="form.pmStatus" placeholder="请输入PM状态"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产剩余寿命评估" prop="assetResidueLifeAssess">
              <el-input v-model="form.assetResidueLifeAssess" placeholder="请输入资产剩余寿命评估"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产整体状态" prop="overallAssetStatus">
              <el-input v-model="form.overallAssetStatus" placeholder="请输入资产整体状态"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产状态" prop="assetStatus">
              <el-radio-group v-model="form.assetStatus" size="large">
                <el-radio label="1" size="large">在用</el-radio>
                <el-radio label="2" size="large">停用</el-radio>
                <el-radio label="3" size="large">报废</el-radio>
                <el-radio label="-1" size="large">封存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备分级" prop="devGrade">
              <el-input v-model="form.devGrade" placeholder="请输入设备分级"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门"/>
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
    <el-dialog :close-on-click-modal="false" :title="upload.title" :visible.sync="upload.open" append-to-body width="400px">
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
          <em>点击上传</em>
        </div>
        <div slot="tip" class="el-upload__tip">
          <!--          <el-checkbox v-model="upload.updateSupport"/>-->
          <!--          是否更新已经存在的用户数据-->
          <el-link style="font-size:12px" type="info" @click="importTemplate">下载模板</el-link>
        </div>
        <div slot="tip" class="el-upload__tip" style="color:red">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="primary" @click="submitFileForm">确 定
        </el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <form ref="downloadFileForm" :action="upload.downloadAction" target="FORMSUBMIT">
      <input :value="upload.type" hidden name="type"/>
    </form>
  </div>
</template>

<script>
import {addMain, delMain, exportMain, getMain, listMain, syncAllDev, updateMain} from "@/api/asset/main";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listTypemgrAll} from "@/api/asset/typemgr";

export default {
  name: "Main",
  components: {Treeselect},
  data() {
    return {
      devTypeOptions: [],
      devType2Options: [],
      yesOrNoOptions: [],
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
      // 设备总表格数据
      mainList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      fullscreenLoading: false,
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        type: "specMain",
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
        url: process.env.VUE_APP_BASE_API + "/sems/main/importData"
      },
      // 查询参数
      queryParams: {
        fuzzy: null,
        pageNum: 1,
        pageSize: 20,
        devName: null,
        devEnname: null,
        devno: null,
        isPid: null,
        pidNo: null,
        installPosition: null,
        funcPosition: null,
        assetNo: null,
        securityRelevant: null,
        securityRelevantFrom: null,
        manufacturer: null,
        productionYear: null,
        model: null,
        mainSpecifications: null,
        dataPlate: null,
        maintenanceRisk: null,
        productionRisk: null,
        devGrade: null,
        devFrom: null,
        devType1: null,
        devType2: null,
        assetStatus: null,
        dataIntegrity: null,
        pmStatus: null,
        assetResidueLifeAssess: null,
        overallAssetStatus: null,
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
        pidNo: [
          {required: true, message: 'P&ID图号/其他图号不能为空', trigger: "blur"}
        ],
        devno: [
          {required: true, message: '位号不能为空', trigger: "blur"}
        ],
        devType1: [
          {required: true, message: '设备类型不能为空', trigger: "blur"}
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
    this.getDevType1List();
    this.getDicts("yes_no").then(response => {
      this.yesOrNoOptions = response.data;
    });
    this.getDicts("spec_dev_status").then(response => {
      this.devStatusOptions = response.data;
    });
  },
  methods: {
    toFile(row) {
      this.$router.push({path: '/system/file', query: {linkId: row.id, linkName: 'specMain'}})
    },
    getDevType1List() {
      listTypemgrAll({pid: 0}).then(response => {
        this.devTypeOptions = response.data;
      });
    },
    getDevType2List(pid) {
      listTypemgrAll({pid: pid}).then(response => {
        this.devType2Options = response.data;
      });
    },
    // 是否字典翻译
    yseOrNoFormat(row, column) {
      switch (column.property) {
        case "isPid":
          return this.selectDictLabel(this.yesOrNoOptions, row.isPid);
        case "delFlag":
          return row.delFlag==2?"是":"否";
      }
    },
    // 资产状态字典翻译
    devStatusFormat(row, column) {
      return this.selectDictLabel(this.devStatusOptions, row.assetStatus);
    },
    /** 查询设备总列表 */
    getList() {
      this.loading = true;
      listMain(this.queryParams).then(response => {
        this.mainList = response.rows;
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
        devName: null,
        devEnname: null,
        devno: null,
        isPid: null,
        pidNo: null,
        installPosition: null,
        funcPosition: null,
        assetNo: null,
        securityRelevant: null,
        securityRelevantFrom: null,
        manufacturer: null,
        productionYear: null,
        model: null,
        mainSpecifications: null,
        dataPlate: null,
        maintenanceRisk: null,
        productionRisk: null,
        devGrade: null,
        devFrom: null,
        devType1: null,
        devType2: null,
        assetStatus: null,
        dataIntegrity: null,
        pmStatus: null,
        assetResidueLifeAssess: null,
        overallAssetStatus: null,
        status: null,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null
      };
      this.resetForm("form");
      this.devType2Options = [];
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
      this.form.assetStatus = "1";
      this.open = true;
      this.title = "添加设备总数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMain(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备总数据";
        for (const item of this.devTypeOptions) {
          if (item.devType === this.form.devType1)
            this.getDevType2List(item.id)
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    syncDev() {
      this.$confirm('是否确认同步所有设备清单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return syncAllDev();
      }).then(() => {
        this.$modal.msgSuccess("同步成功");
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMain(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    }
    ,
    /** 导出按钮操作 */
    handleExport() {
      this.download('sems/main/export', {
        ...this.queryParams
      }, `devMain_${new Date().getTime()}.xlsx`)
    }
    ,
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    }
    ,
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    }
    ,
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    }
    ,
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
    }
    ,
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.fullscreenLoading = true;
    }
    ,
    getSubType(val) {
      this.form.devType2 = null;
      for (const item of this.devTypeOptions) {
        if (item.devType === val)
          this.getDevType2List(item.id)
      }
    }
    ,
    //翻页后继续计算序列值
    indexMethod(index) {
      return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1;
    },
    tableRowClassName({row, rowIndex}) {
      if (row.delFlag==2){
        return 'del-row';
      }
      return '';
    }
  }
};
</script>
<style>

.el-table .del-row {
  background: rgb(251, 112, 112);
}
</style>
