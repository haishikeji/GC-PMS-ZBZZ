<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="漏点编号" prop="pointNo">
        <el-input
          v-model="queryParams.pointNo"
          placeholder="请输入漏点编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="泄漏位置" prop="leakagePosition">
        <el-input
          v-model="queryParams.leakagePosition"
          placeholder="请输入泄漏位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="泄漏介质" prop="leakageMedium">
        <el-input
          v-model="queryParams.leakageMedium"
          placeholder="请输入泄漏介质"
          clearable
          @change="handleQuery"
        />
      </el-form-item>

      <el-form-item label="消漏结果" prop="leakageEliminationResults">
        <el-select clearable multiple
                   v-model="queryParams.leakageEliminationResults"
                   placeholder="请选择消漏结果">
          <el-option v-for="dict in dict.type.leakage_result"
                     :value="dict.value"
                     :label="dict.label"
                     :key="dict.value"/>
        </el-select>
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['asset:points:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['asset:points:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['asset:points:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['asset:points:add']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:points:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-date"
          size="mini"
          @click="openRecord"
        >日常巡检记录
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pointsList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="漏点编号" align="center" prop="pointNo" width="130" fixed="left"/>
      <el-table-column label="泄漏位置" align="center" prop="leakagePosition" width="230" fixed="left"/>
      <el-table-column label="泄漏介质" align="center" prop="leakageMedium" width="120" fixed="left"/>
      <el-table-column label="发现时间" align="center" prop="discoveryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.discoveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发现人员" align="center" prop="discoveryName" width="180"/>
      <el-table-column label="泄漏量" align="center" prop="leakageAmount" width="180"/>
      <el-table-column label="SAP通知单号" align="center" prop="sapNo" width="180"/>
      <el-table-column label="消漏时间" align="center" prop="leakageEliminationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.leakageEliminationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="消漏方法" align="center" prop="leakageEliminationFunc" width="180"/>
      <el-table-column label="挂牌" align="center" prop="listing" width="180"/>
      <el-table-column label="带压堵漏" align="center" prop="pressurePlugging" width="180"/>
      <el-table-column label="消漏结果" align="center" prop="leakageEliminationResult" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.leakage_result" :value="scope.row.leakageEliminationResult"/>
        </template>
      </el-table-column>
      <el-table-column label="下次检修日期" align="center" prop="nextCheckDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextCheckDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" width="230"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:points:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:points:remove']"
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

    <!-- 添加或修改漏点清单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="漏点编号" prop="pointNo">
          <el-input v-model="form.pointNo" placeholder="请输入漏点编号"/>
        </el-form-item>
        <el-form-item label="泄漏位置" prop="leakagePosition">
          <el-input v-model="form.leakagePosition" placeholder="请输入泄漏位置"/>
        </el-form-item>
        <el-form-item label="泄漏介质" prop="leakageMedium">
          <el-input v-model="form.leakageMedium" placeholder="请输入泄漏介质"/>
        </el-form-item>
        <el-form-item label="发现时间" prop="discoveryTime">
          <el-date-picker clearable
                          v-model="form.discoveryTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发现时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发现人员" prop="discoveryName">
          <el-input v-model="form.discoveryName" placeholder="请输入发现人员"/>
        </el-form-item>
        <el-form-item label="泄漏量" prop="leakageAmount">
          <el-input v-model="form.leakageAmount" placeholder="请输入泄漏量"/>
        </el-form-item>
        <el-form-item label="SAP通知单号" prop="sapNo">
          <el-input v-model="form.sapNo" placeholder="请输入SAP通知单号"/>
        </el-form-item>
        <el-form-item label="消漏时间" prop="leakageEliminationTime">
          <el-date-picker clearable
                          v-model="form.leakageEliminationTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择消漏时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="消漏方法" prop="leakageEliminationFunc">
          <el-input v-model="form.leakageEliminationFunc" placeholder="请输入消漏方法"/>
        </el-form-item>
        <el-form-item label="挂牌" prop="listing">
          <el-input v-model="form.listing" placeholder="请输入挂牌"/>
        </el-form-item>
        <el-form-item label="带压堵漏" prop="pressurePlugging">
          <el-input v-model="form.pressurePlugging" placeholder="请输入带压堵漏"/>
        </el-form-item>
        <el-form-item label="消漏结果" prop="leakageEliminationResult">
          <el-select clearable v-model="form.leakageEliminationResult" placeholder="请选择消漏结果">
            <el-option v-for="dict in dict.type.leakage_result"
                       :value="dict.value"
                       :label="dict.label"
                       :key="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="下次检修日期" prop="nextCheckDate">
          <el-date-picker clearable
                          v-model="form.nextCheckDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择下次检修日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="日常巡检记录" :visible.sync="record.open" width="70%" append-to-body
               :close-on-click-modal="false">
      <el-form :model="record.queryParams" ref="recordQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="日期" prop="checkDateM">
          <el-date-picker
            v-model="record.queryParams.checkDateM"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期"
            @change="handleRecordQuery">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleRecordQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetRecordQuery">重置</el-button>
          <el-button icon="el-icon-download" size="mini" @click="handleExportPatrol">导出</el-button>
          <el-button icon="el-icon-printer" size="mini" @click="" v-print="printInfoObj">打印</el-button>
        </el-form-item>
        <right-toolbar :showSearch.sync="record.showSearch" @queryTable="getRecordList" :search="false"></right-toolbar>
      </el-form>

      <table style="width: 100%; margin-bottom: 30px" id="printInfo">
        <tr>
          <td rowspan="3" colspan="2">漏点巡检记录表</td>
          <td>日期</td>
          <td colspan="2">{{ parseTime(record.pointPatrol.checkDateM, '{y}年{m}月{d}日') }}</td>
          <td colspan="2">{{ parseTime(record.pointPatrol.checkDateN, '{y}年{m}月{d}日') }}</td>
        </tr>
        <tr>
          <td>班组</td>
          <td colspan="2">
            <span v-if="record.isEdit&&!isNotEmpty(record.pointPatrol.teamM)">
              <el-select clearable v-model="record.pointPatrol.teamM" placeholder="请选择班组">
                <el-option v-for="dict in dict.type.team_divide"
                           :value="dict.value"
                           :label="dict.label"
                           :key="dict.value"/>
              </el-select>
            </span>
            <span v-else><dict-tag :options="dict.type.team_divide" :value="record.pointPatrol.teamM"/></span>
          </td>
          <td colspan="2">
            <span v-if="record.isEdit&&!isNotEmpty(record.pointPatrol.teamN)">
              <el-select clearable v-model="record.pointPatrol.teamN" placeholder="请选择班组">
                <el-option v-for="dict in dict.type.team_divide"
                           :value="dict.value"
                           :label="dict.label"
                           :key="dict.value"/>
              </el-select>
            </span>
            <span v-else><dict-tag :options="dict.type.team_divide" :value="record.pointPatrol.teamN"/></span>
          </td>
        </tr>
        <tr>
          <td>时间</td>
          <td colspan="2">早班一次</td>
          <td colspan="2">夜班一次</td>
        </tr>
        <tr>
          <td>漏点编号</td>
          <td>漏点位置</td>
          <td>介质</td>
          <td>挂牌（是/否）</td>
          <td>泄露状态<br/>（未检出/轻微/严重）</td>
          <td>挂牌（是/否）</td>
          <td>泄露状态<br/>（未检出/轻微/严重）</td>
        </tr>
        <tr v-for="item in record.pointPatrol.records">
          <td>{{ item.pointNo }}</td>
          <td>{{ item.leakagePosition }}</td>
          <td>{{ item.leakageMedium }}</td>
          <td>
            ▢ 是 ▢ 否
          </td>
          <td>
            ▢ 未检出 ▢ 轻微 ▢ 严重
          </td>
          <td>
            ▢ 是 ▢ 否
          </td>
          <td>
            ▢ 未检出 ▢ 轻微 ▢ 严重
          </td>
        </tr>
        <tr>
          <td colspan="7">
            备注：
            <el-input type="textarea" v-model="record.pointPatrol.remarks" style="width: 95%"/>
          </td>
        </tr>
        <tr>
          <td colspan="7" style="text-align: left">
            ● 根据每日巡检状况在相应处打 “✔”<br>
            <span class="no-print">● 每日首次打开时请先点击保存按钮</span><br>
            <span class="no-print">● 打印时请调整纸张为横向或设置纵向页面缩放</span>
          </td>
        </tr>
      </table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPatrolForm" v-if="isToday(record.queryParams.checkDateM)">保 存
        </el-button>
        <el-button @click="cancelPatrol">返 回</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :close-on-click-modal="false" :title="upload.title" :visible.sync="upload.open" append-to-body
               width="400px">
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
import {addPoints, delPoints, getLastPointNum, getPoints, listPoints, updatePoints} from "@/api/asset/points";
import PointRecord from "@/views/asset/pointRecord/index.vue";
import PointPatrol from "@/views/asset/pointPatrol/index.vue";
import {getToken} from "@/utils/auth";
import {addPointPatrol, getTeams, listPointPatrol, updatePointPatrol} from "@/api/asset/pointPatrol";

export default {
  dicts: ['leakage_result', 'team_divide'],
  name: "Points",
  components: {PointPatrol, PointRecord},
  data() {
    return {
      // 打印区域配置对象
      printInfoObj: {
        id: "printInfo",
        popTitle: "漏点清单",
        preview: false,  // 是否开启预览
      },
      fullscreenLoading: false,
      record: {
        open: false,
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          teamM: null,
          checkDateM: new Date(),
          signM: null,
          teamN: null,
          checkDateN: null,
          signN: null,
          remarks: null,
          createrCode: null,
          createdate: null,
          updaterCode: null,
          updatedate: null,
          deptId: null
        },
        isEdit: false,
        pointPatrol: {},
        // 显示搜索条件
        showSearch: false,
      },
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        type: "assetPoints",
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
        url: process.env.VUE_APP_BASE_API + "/asset/points/importData"
      },
      // 页面高度
      clientHeight: 300,
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
      // 漏点清单表格数据
      pointsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        pointNo: null,
        leakagePosition: null,
        leakageMedium: null,
        discoveryTime: null,
        discoveryName: null,
        leakageAmount: null,
        sapNo: null,
        leakageEliminationTime: null,
        leakageEliminationFunc: null,
        listing: null,
        pressurePlugging: null,
        leakageEliminationResult: null,
        leakageEliminationResults: [],
        nextCheckDate: null,
        remarks: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pointNo:[
          {required: true, message: "漏点编号不能为空", trigger: "blur"}
        ],
        leakagePosition:[
          {required: true, message: "漏点位置不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8
    });
  },
  methods: {
    isNotEmpty(value) {
      if (value !== null && value !== "" && typeof (value) !== "undefined") {
        return true
      } else {
        return false
      }
    },
    isToday(date) {
      const today = new Date();
      const todayISOString = today.toISOString().split('T')[0];
      if (typeof (date) == "object") {
        date = date.toISOString().split('T')[0];
      }
      return date === todayISOString;
    },
    getRecordList() {
      this.record.pointPatrol = {};
      if (this.record.queryParams.checkDateM == null) {
        this.record.queryParams.checkDateM = new Date();
      }
      listPointPatrol(this.record.queryParams).then(response => {
        let list = response.rows;
        if (list.length > 0) {
          for (let item of list[0].records) {
            item.suspendPlateMTmpl = item.suspendPlateM == null ? [] : item.suspendPlateM.split(",");
            item.suspendPlateNTmpl = item.suspendPlateN == null ? [] : item.suspendPlateN.split(",");
            item.leakageStatusMTmpl = item.leakageStatusM == null ? [] : item.leakageStatusM.split(",");
            item.leakageStatusNTmpl = item.leakageStatusN == null ? [] : item.leakageStatusN.split(",");
          }
          this.record.pointPatrol = list[0];
          console.log(this.record.pointPatrol)
          this.record.isEdit = false
        } else {
          getTeams(this.record.queryParams.checkDateM).then(res => {
            this.record.pointPatrol = {};
            this.record.pointPatrol.teamM = res.data.teamM;
            this.record.pointPatrol.teamN = res.data.teamN;
          })
          this.record.isEdit = true;

        }
      });
    },
    resetRecordQuery() {
      this.resetForm("recordQueryForm");
      this.getRecordList();
    },
    handleRecordQuery() {
      this.getRecordList();
    },
    cancelPatrol() {
      this.record.open = false;
    },
    submitPatrolForm() {
      this.record.isEdit = false
      this.record.pointPatrol.checkDateM = this.record.queryParams.checkDateM;
      if (this.record.pointPatrol.id != null) {
        for (let item of this.record.pointPatrol.records) {
          item.suspendPlateM = item.suspendPlateMTmpl.join(',')
          item.suspendPlateN = item.suspendPlateNTmpl.join(',')
          item.leakageStatusM = item.leakageStatusMTmpl.join(',')
          item.leakageStatusN = item.leakageStatusNTmpl.join(',')
        }
        updatePointPatrol(this.record.pointPatrol).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.getRecordList();
        });
      } else {
        addPointPatrol(this.record.pointPatrol).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.getRecordList();
        });
      }
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.fullscreenLoading = true;
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
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    },
    openRecord() {
      this.record.open = true;
      this.record.queryParams.checkDateM = new Date();
      this.getRecordList();
    },
    /** 查询漏点清单列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.leakageEliminationResults && this.queryParams.leakageEliminationResults.length > 0) {
        this.queryParams.leakageEliminationResult = this.queryParams.leakageEliminationResults.join(",");
      }
      listPoints(this.queryParams).then(response => {
        this.pointsList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        pointNo: null,
        leakagePosition: null,
        leakageMedium: null,
        discoveryTime: null,
        discoveryName: null,
        leakageAmount: null,
        sapNo: null,
        leakageEliminationTime: null,
        leakageEliminationFunc: null,
        listing: null,
        pressurePlugging: null,
        leakageEliminationResult: null,
        nextCheckDate: null,
        remarks: null,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null
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
      this.queryParams.leakageEliminationResults = []
      this.queryParams.leakageEliminationResult = null;
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
      this.title = "添加漏点清单";
      getLastPointNum().then(res => {
        let pointNo = res.data.pointNo;
        this.form.pointNo = pointNo.substring(0, pointNo.indexOf('-')+1) + (parseInt(pointNo.substring(pointNo.indexOf('-')+1, pointNo.length)) + 1)
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPoints(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改漏点清单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePoints(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPoints(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除漏点清单编号为"' + ids + '"的数据项？').then(function () {
        return delPoints(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('asset/points/export', {
        ...this.queryParams
      }, `points_${new Date().getTime()}.xlsx`)
    },
    /** 导出按钮操作 */
    handleExportPatrol() {
      this.download('asset/pointPatrol/exportRecord', {
        ...this.record.queryParams
      }, `pointsPatrol_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped lang="scss">
/* 表格边框 */
table {
  border-collapse: collapse;
}

table td {
  border: 1px #b4b4b4 solid;
  padding: 10px;
  text-align: center;
}

@media print {
  .no-print {
    display: none;
  }
  ::v-deep .el-radio__input,
  ::v-deep .el-checkbox__input {
    -webkit-print-color-adjust: exact;
    -moz-print-color-adjust: exact;
    color-adjust: exact;
  }
}
</style>
