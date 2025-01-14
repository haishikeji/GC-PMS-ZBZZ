<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="装置名称" prop="plantCode">
        <el-input
          v-model="queryParams.plantCode"
          placeholder="请输入装置名称"
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
            v-hasPermi="['sems:specAqf:add']"
            icon="el-icon-plus"
            size="mini"
            type="primary"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:specAqf:edit']"
            :disabled="single"
            icon="el-icon-edit"
            size="mini"
            type="success"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:specAqf:remove']"
            :disabled="multiple"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:specAqf:edit']"
            icon="el-icon-upload2"
            size="mini"
            type="info"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:specAqf:export']"
            icon="el-icon-download"
            size="mini"
            type="warning"
            @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="aqfList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column type="selection" fixed="left" width="55" align="center"/>
      <el-table-column type="index" :index="indexMethod" label="序号" width="50" align="center" />
<!--      <el-table-column width="150" label="设备中文名" align="center" prop="devName" :show-overflow-tooltip="true"/>
      <el-table-column width="150" label="设备英文名" align="center" prop="devEnname" :show-overflow-tooltip="true"/>
      <el-table-column label="位号" align="center" prop="devno" :show-overflow-tooltip="true"/>
      <el-table-column width="150" label="是否在PID上" align="center" prop="isPid" :show-overflow-tooltip="true" :formatter="yseOrNoFormat" />
      <el-table-column width="150" label="P&ID图号/其他图号" align="center" prop="pidNo" :show-overflow-tooltip="true"/>
      <el-table-column label="安装位置" align="center" prop="installPosition" :show-overflow-tooltip="true"/>
      <el-table-column label="功能位置" align="center" prop="funcPosition" :show-overflow-tooltip="true"/>
      <el-table-column label="资产号" align="center" prop="assetNo" :show-overflow-tooltip="true"/>
      <el-table-column label="安全相关" align="center" prop="securityRelevant" :show-overflow-tooltip="true"/>
      <el-table-column width="150" label="安全相关来自于" align="center" prop="securityRelevantFrom"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="生产厂家" align="center" prop="manufacturer" :show-overflow-tooltip="true"/>
      <el-table-column label="生产年份" align="center" prop="productionYear" :show-overflow-tooltip="true"/>
      <el-table-column label="型号" align="center" prop="model" :show-overflow-tooltip="true"/>
      <el-table-column width="150" label="主要规格参数" align="center" prop="mainSpecifications"
                       :show-overflow-tooltip="true"/>
      <el-table-column label="设备铭牌" align="center" prop="dataPlate" :show-overflow-tooltip="true"/>-->
      <el-table-column label="装置名称" align="center" prop="plantCode" :show-overflow-tooltip="true"/>
      <el-table-column label="描述" align="center" prop="describe" :show-overflow-tooltip="true"/>
      <el-table-column label="介质" align="center" prop="medium" :show-overflow-tooltip="true"/>
      <el-table-column label="介质相态" align="center" prop="mediumPhase" :show-overflow-tooltip="true"/>
      <el-table-column label="从" align="center" prop="starting" :show-overflow-tooltip="true"/>
      <el-table-column label="到" align="center" prop="arrive" :show-overflow-tooltip="true"/>
      <el-table-column label="出厂编号" align="center" prop="factoryNo" :show-overflow-tooltip="true"/>
      <el-table-column label="阀类型" align="center" prop="valveType" :show-overflow-tooltip="true"/>
      <el-table-column label="数据表" align="center" prop="datasheet" :show-overflow-tooltip="true"/>
      <el-table-column label="计算书" align="center" prop="calculationBook" :show-overflow-tooltip="true"/>
      <el-table-column label="Free Orifice Selected Area mm2" align="center" prop="selectedArea"
                       :show-overflow-tooltip="true" width="220px"/>
      <el-table-column label="Senarios(design case bold)" align="center" prop="senarios" :show-overflow-tooltip="true"
                       width="200px"/>
      <el-table-column label="Required relieving capacity" align="center" prop="requiredRelieving"
                       :show-overflow-tooltip="true" width="200px"/>
      <el-table-column label="Rated relieving  capacity (Kg/hr)" align="center" prop="ratedRelieving"
                       :show-overflow-tooltip="true" width="220px"/>
      <el-table-column label="入口尺寸" align="center" prop="inletSize" :show-overflow-tooltip="true"/>
      <el-table-column label="入口垫片压力等级" align="center" prop="pressureRating" width="140px":show-overflow-tooltip="true"/>
      <el-table-column label="出口尺寸" align="center" prop="outSize" :show-overflow-tooltip="true"/>
      <el-table-column label="出口垫片压力等级" align="center" prop="outPressureRating" width="140px":show-overflow-tooltip="true"/>
      <el-table-column label="设定压力" align="center" prop="setPressure" :show-overflow-tooltip="true"/>
      <el-table-column label="背压" align="center" prop="backPressure" :show-overflow-tooltip="true"/>
      <el-table-column label="是否强检" align="center" prop="isInspection" :show-overflow-tooltip="true":formatter="yseOrNoFormat"/>
      <el-table-column label="本次校验日期" align="center" prop="warnDate" width="100px" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下次校验日期" align="center" prop="nextWarnDate" width="100px" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否需要脚手架" align="center" prop="needScaffold" width="120px" :show-overflow-tooltip="true":formatter="yseOrNoFormat"/>
      <el-table-column label="保温" align="center" prop="keepWarm" :show-overflow-tooltip="true":formatter="yseOrNoFormat"/>
      <el-table-column label="吊机" align="center" prop="crane" :show-overflow-tooltip="true":formatter="yseOrNoFormat"/>
      <el-table-column label="检验状态" align="center" prop="checkStatus" :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="status" :show-overflow-tooltip="true" :formatter="statusFormatter"/>
      <el-table-column label="操作" align="center" fixed="right" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sems:specAqf:edit']"
          >修改
          </el-button>
<!--          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sems:specAqf:remove']"
          >删除
          </el-button>-->
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

    <!-- 添加或修改特种设备安全阀台账对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="60%" append-to-body >
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="装置名称" prop="plantCode">
              <el-input v-model="form.plantCode" placeholder="请输入装置名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述" prop="describe">
              <el-input v-model="form.describe" placeholder="请输入描述"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="介质" prop="medium">
              <el-input v-model="form.medium" placeholder="请输入介质"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="介质相态" prop="mediumPhase">
              <el-input v-model="form.mediumPhase" placeholder="请输入介质相态"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="从" prop="starting">
              <el-input v-model="form.starting" placeholder="请输入从"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到" prop="arrive">
              <el-input v-model="form.arrive" placeholder="请输入到"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出厂编号" prop="factoryNo">
              <el-input v-model="form.factoryNo" placeholder="请输入出厂编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阀类型" prop="valveType">
              <el-input v-model="form.valveType" placeholder="请输入阀类型"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据表" prop="datasheet">
              <el-input v-model="form.datasheet" placeholder="请输入数据表"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计算书" prop="calculationBook">
              <el-input v-model="form.calculationBook" placeholder="请输入计算书"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Free Orifice Selected Area mm2" prop="selectedArea">
              <el-input v-model="form.selectedArea" placeholder="请输入Free Orifice Selected Area mm2"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Senarios(design case bold)" prop="senarios">
              <el-input v-model="form.senarios" placeholder="请输入Senarios(design case bold)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Required relieving capacity" prop="requiredRelieving">
              <el-input v-model="form.requiredRelieving" placeholder="请输入Required relieving capacity"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Rated relieving  capacity (Kg/hr)" prop="ratedRelieving">
              <el-input v-model="form.ratedRelieving" placeholder="请输入Rated relieving  capacity (Kg/hr)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入口尺寸" prop="inletSize">
              <el-input v-model="form.inletSize" placeholder="请输入入口尺寸"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入口垫片压力等级" prop="pressureRating">
              <el-input v-model="form.pressureRating" placeholder="请输入入口垫片压力等级"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出口尺寸" prop="outSize">
              <el-input v-model="form.outSize" placeholder="请输入出口尺寸"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出口垫片压力等级" prop="outPressureRating">
              <el-input v-model="form.outPressureRating" placeholder="请输入出口垫片压力等级"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设定压力" prop="setPressure">
              <el-input v-model="form.setPressure" placeholder="请输入设定压力"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="背压" prop="backPressure">
              <el-input v-model="form.backPressure" placeholder="请输入背压"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="本次校验日期" prop="warnDate">
              <el-date-picker clearable size="small" style="width: 100%"
                              v-model="form.warnDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择本次校验日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次校验日期" prop="nextWarnDate">
              <el-date-picker clearable size="small" style="width: 100%"
                              v-model="form.nextWarnDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择下次校验日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否强检" prop="isInspection">
              <el-radio-group v-model="form.isInspection" size="large">
                <el-radio label="1" size="large" >是</el-radio>
                <el-radio label="0" size="large" >否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="是否需要脚手架" prop="needScaffold">
              <el-radio-group v-model="form.needScaffold" size="large">
                <el-radio label="1" size="large">是</el-radio>
                <el-radio label="0" size="large">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="保温" prop="keepWarm">
              <el-radio-group v-model="form.keepWarm" size="large">
                <el-radio label="1" size="large">是</el-radio>
                <el-radio label="0" size="large">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="吊机" prop="crane">
              <el-radio-group v-model="form.crane" size="large">
                <el-radio label="1" size="large">是</el-radio>
                <el-radio label="0" size="large">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status" size="large">
                <el-radio :label="1" size="large">在用</el-radio>
                <el-radio :label="2" size="large">停用</el-radio>
                <el-radio :label="3" size="large">报废</el-radio>
                <el-radio :label="-1" size="large">封存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!--          <el-form-item label="归属部门" prop="deptId">-->
        <!--              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />-->
        <!--          </el-form-item>-->
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
          <el-checkbox v-model="upload.updateSupport"/>
          是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listAqf, getAqf, delAqf, addAqf, updateAqf, exportAqf, importTemplate} from "@/api/sems/aqf";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Aqf",
  components: {Treeselect},
  data() {
    return {
      yesOrNoOptions: [],
      statusOptions: [],
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
      // 特种设备安全阀台账表格数据
      aqfList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
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
        url: process.env.VUE_APP_BASE_API + "/sems/aqf/importData"
      },
      // 查询参数
      queryParams: {
        fuzzy:null,
        pageNum: 1,
        pageSize: 20,
        plantCode: null,
        devno: null,
        pidNo: null,
        describe: null,
        medium: null,
        mediumPhase: null,
        starting: null,
        arrive: null,
        manufacturer: null,
        model: null,
        factoryNo: null,
        valveType: null,
        datasheet: null,
        calculationBook: null,
        selectedArea: null,
        senarios: null,
        requiredRelieving: null,
        ratedRelieving: null,
        inletSize: null,
        pressureRating: null,
        outSize: null,
        outPressureRating: null,
        setPressure: null,
        backPressure: null,
        isInspection: null,
        warnDate: null,
        nextWarnDate: null,
        needScaffold: null,
        keepWarm: null,
        crane: null,
        submitdate: null,
        status: 2,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        approveStatus: null,
        approveTime: null,
        changeTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        plantCode: [
          {required: true, message: "装置名称不能为空", trigger: "blur"}
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
    this.getDicts("yes_no").then(response => {
      this.yesOrNoOptions = response.data;
    });
    this.getDicts("spec_dev_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    statusFormatter(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 是否字典翻译
    yseOrNoFormat(row, column) {
      switch (column.property) {
        case "isPid":
          return this.selectDictLabel(this.yesOrNoOptions, row.isPid);
        case "isInspection":
          return this.selectDictLabel(this.yesOrNoOptions, row.isInspection);
        case "keepWarm":
          return this.selectDictLabel(this.yesOrNoOptions, row.keepWarm);
        case "crane":
          return this.selectDictLabel(this.yesOrNoOptions, row.crane);
        case "needScaffold":
          return this.selectDictLabel(this.yesOrNoOptions, row.needScaffold);
      }
    },
    /** 查询特种设备安全阀台账列表 */
    getList() {
      this.loading = true;
      listAqf(this.queryParams).then(response => {
        this.aqfList = response.rows;
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
        plantCode: null,
        devno: null,
        pidNo: null,
        describe: null,
        medium: null,
        mediumPhase: null,
        starting: null,
        arrive: null,
        manufacturer: null,
        model: null,
        factoryNo: null,
        valveType: null,
        datasheet: null,
        calculationBook: null,
        selectedArea: null,
        senarios: null,
        requiredRelieving: null,
        ratedRelieving: null,
        inletSize: null,
        pressureRating: null,
        outSize: null,
        outPressureRating: null,
        setPressure: null,
        backPressure: null,
        isInspection: null,
        warnDate: null,
        nextWarnDate: null,
        needScaffold: null,
        keepWarm: null,
        crane: null,
        submitdate: null,
        status: 0,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        approveStatus: 0,
        approveTime: null,
        changeTime: null
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
      this.title = "添加特种设备安全阀台账";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAqf(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改特种设备安全阀台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAqf(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAqf(this.form).then(response => {
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
        return delAqf(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {

      this.download('sems/aqf/export', {
        ...this.queryParams
      }, `devAqf_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
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
      this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //翻页后继续计算序列值
    indexMethod(index) {
      return (this.queryParams.pageNum-1)*this.queryParams.pageSize+index+1;
    }
  }
};
</script>
