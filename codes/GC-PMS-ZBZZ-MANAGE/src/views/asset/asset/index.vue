<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="80px">
      <el-form-item label="PID图号/管线号" label-width="130px" prop="pidNo">
        <el-input
            v-model="queryParams.pidNo"
            clearable
            placeholder="请输入PID图号/管线号"
            size="small"
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分界具体位置及描述" label-width="140px" prop="positionDescribe">
        <el-input
            v-model="queryParams.positionDescribe"
            clearable
            placeholder="请输入分界具体位置及描述"
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
        <el-button
            v-hasPermi="['sems:asset:add']"
            icon="el-icon-plus"
            size="mini"
            type="primary"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:asset:edit']"
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
            v-hasPermi="['sems:asset:remove']"
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
            v-hasPermi="['sems:asset:edit']"
            icon="el-icon-upload2"
            size="mini"
            type="info"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:asset:export']"
            icon="el-icon-download"
            size="mini"
            type="warning"
            @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:asset:export']"
            icon="el-icon-folder-opened"
            plain
            size="mini" type="primary"
            @click="handleSee"
        >附件管理
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetList" :height="clientHeight" border
              @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed="left" type="selection" width="55"/>
      <el-table-column :index="indexMethod" align="center" label="序号" type="index"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="Type" prop="type"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="内容" prop="content"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="PID图号/管线号" prop="pidNo" width="140"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="分界具体位置及描述" prop="positionDescribe"
                       width="140"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="According to" prop="accordingTo"
                       width="140"/>
      <el-table-column align="center" label="资产">
        <el-table-column :show-overflow-tooltip="true" align="center" label="N.A" prop="asset"/>
      </el-table-column>
      <el-table-column align="center" label="DCS操作">
        <el-table-column :show-overflow-tooltip="true" align="center" label="N.A" prop="dcsOperation"/>
      </el-table-column>
      <el-table-column align="center" label="现场操作">
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时" prop="inSceneOperation"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时外" prop="outSceneOperation"/>
      </el-table-column>
      <el-table-column align="center" label="巡检">
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时" prop="inInspection"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时外" prop="outInspection"/>
      </el-table-column>
      <el-table-column align="center" label="维修">
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时" prop="inRepair"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时外" prop="outRepair"/>
      </el-table-column>
      <el-table-column align="center" label="维修协调">
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时" prop="inMaintenanceCoordination"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时外"
                         prop="outMaintenanceCoordination"/>
      </el-table-column>
      <el-table-column align="center" label="技术管理">
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时" prop="inTechnicalManagement"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="8小时外" prop="outTechnicalManagement"/>
      </el-table-column>
      <el-table-column align="center" label="资产分界点信息">
        <el-table-column :show-overflow-tooltip="true" align="center" label="位置" prop="position"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="管径" prop="pipeDiameter"/>
        <el-table-column :show-overflow-tooltip="true" align="center" label="附件" prop="picUrl">
          <template slot-scope="scope">
            <el-button
                icon="el-icon-view"
                size="mini"
                type="text"
                @click="handleSee(scope.row)"
            >查看
            </el-button>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" align="center" label="备注" prop="remarks"/>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button
              v-hasPermi="['sems:asset:edit']"
              icon="el-icon-edit"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
              v-hasPermi="['sems:asset:remove']"
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
          >删除
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

    <!-- 添加或修改资产与运营对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" append-to-body width="1400px">
      <el-form ref="form" :model="form" :rules="rules" label-width="160px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Type" prop="type">
              <el-input v-model="form.type" placeholder="请输入Type"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="内容" prop="content">
              <el-input v-model="form.content" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="PID图号/管线号" prop="pidNo">
              <el-input v-model="form.pidNo" placeholder="请输入PID图号/管线号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分界具体位置及描述" prop="positionDescribe">
              <el-input v-model="form.positionDescribe" placeholder="请输入分界具体位置及描述"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="According to" prop="accordingTo">
              <el-input v-model="form.accordingTo" placeholder="请输入According to"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产" prop="asset">
              <el-input v-model="form.asset" placeholder="请输入资产"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="DCS操作" prop="dcsOperation">
              <el-input v-model="form.dcsOperation" placeholder="请输入DCS操作"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="现场操作：8小时" prop="inSceneOperation">
              <el-input v-model="form.inSceneOperation" placeholder="请输入现场操作8小时"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="8小时外" prop="outSceneOperation">
              <el-input v-model="form.outSceneOperation" placeholder="请输入现场操作8小时外"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="巡检：8小时" prop="inInspection">
              <el-input v-model="form.inInspection" placeholder="请输入巡检8小时"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="8小时外" prop="outInspection">
              <el-input v-model="form.outInspection" placeholder="请输入巡检8小时外"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="维修：8小时" prop="inRepair">
              <el-input v-model="form.inRepair" placeholder="请输入维修8小时"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="8小时外" prop="outRepair">
              <el-input v-model="form.outRepair" placeholder="请输入维修8小时外"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="维修协调：8小时" prop="inMaintenanceCoordination">
              <el-input v-model="form.inMaintenanceCoordination" placeholder="请输入维修协调8小时"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="8小时外" prop="outMaintenanceCoordination">
              <el-input v-model="form.outMaintenanceCoordination" placeholder="请输入维修协调8小时外"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="技术管理：8小时" prop="inTechnicalManagement">
              <el-input v-model="form.inTechnicalManagement" placeholder="请输入技术管理8小时"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="8小时外" prop="outTechnicalManagement">
              <el-input v-model="form.outTechnicalManagement" placeholder="请输入技术管理8小时外"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产分界点信息：位置" prop="position">
              <el-input v-model="form.position" placeholder="请输入位置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管径" prop="pipeDiameter">
              <el-input v-model="form.pipeDiameter" placeholder="请输入管径"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-form-item :label="照片" prop="picUrl">
                  <el-upload
                    ref="doc"
                    :headers="doc.headers"
                    :action="doc.url"
                    :disabled="doc.isUploading"
                    :on-progress="handleFileDocProgress"
                    :on-success="handleFileDocSuccess"
                    :auto-upload="true"
                    :file-list="fileList"
                    :multiple="true"
                  >
                    <el-button type="primary"><i class="el-icon-upload"></i> 点击上传</el-button>
                  </el-upload>
                </el-form-item>-->
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="pic.title" :visible.sync="pic.open" append-to-body width="60%">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
              v-hasPermi="['sems:asset:add']"
              icon="el-icon-plus"
              size="mini"
              type="primary"
              @click="uploadFile"
          >新增
          </el-button>
        </el-col>
      </el-row>
      <el-table :data="picUrl" border>
        <el-table-column align="center" label="附件名称" prop="fileName">
          <template slot-scope="scope">
            <el-button type="text" @click="openFile(scope.row.fileUrl)">
              {{ scope.row.fileName }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="上传时间" prop="uploadDate" width="180px">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="上传人" prop="uploader" width="150px"/>
        <el-table-column align="center" label="备注" prop="remark" width="180px"/>
        <el-table-column align="center" class-name="small-padding fixed-width" label="操作" width="120">
          <template slot-scope="scope">
            <el-button
                v-hasPermi="['sems:asset:remove']"
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click=""
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog :close-on-click-modal="false"  :visible.sync="doc.open" append-to-body title="附件上传" width="400px">
      <el-upload
          ref="doc"
          :action="doc.url"
          :auto-upload="false"
          :disabled="doc.isUploading"
          :file-list="doc.fileList"
          :headers="doc.headers"
          :on-progress="handleFileDocProgress"
          :on-success="handleFileDocSuccess"
          drag
          multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">确 定</el-button>
        <el-button @click="cancelUpload">取 消</el-button>
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
import {addAsset, delAsset, exportAsset, getAsset, listAsset, updateAsset} from "@/api/asset/asset";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listFile} from "@/api/file/file";

export default {
  name: "Asset",
  components: {Treeselect},
  // components: { Editor },
  data() {
    return {
      fileList: [],
      pic: {
        title: '照片预览',
        picUrl: [],
        numPages: null,
        open: false,
        pageNum: 1,
        pageTotalNum: 1,
        loadedRatio: 0,
      },
      doc: {
        id: null,
        file: "",
        fileList: [],
        // 是否显示弹出层
        open: false,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/file/file/uploadFile"
      },
      uploadAction: '',
      picUrl: [],
      picName: [],
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
      // 资产与运营表格数据
      assetList: [],
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
        type: "specAsset",
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
        url: process.env.VUE_APP_BASE_API + "/sems/asset/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        fuzzy: null,
        type: null,
        content: null,
        pidNo: null,
        positionDescribe: null,
        accordingTo: null,
        asset: null,
        dcsOperation: null,
        inSceneOperation: null,
        outSceneOperation: null,
        inInspection: null,
        outInspection: null,
        inRepair: null,
        outRepair: null,
        inMaintenanceCoordination: null,
        outMaintenanceCoordination: null,
        inTechnicalManagement: null,
        outTechnicalManagement: null,
        position: null,
        pipeDiameter: null,
        picUrl: null,
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
      rules: {}
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
  },
  methods: {
    openFile(url) {
      if (url) {
        window.open(process.env.VUE_APP_BASE_API + url)
      }
    },
    submitUpload() {
      this.$refs.doc.submit();
      this.doc.open = false;
      this.doc.fileList = [];
      this.getFile(this.doc.id)
    },
    cancelUpload() {
      this.doc.open = false;
      this.doc.fileList = [];
    },
    uploadFile() {
      this.doc.open = true;
      this.doc.fileList = [];
    },
    /** 查询资产与运营列表 */
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
        type: null,
        content: null,
        pidNo: null,
        positionDescribe: null,
        accordingTo: null,
        asset: null,
        dcsOperation: null,
        inSceneOperation: null,
        outSceneOperation: null,
        inInspection: null,
        outInspection: null,
        inRepair: null,
        outRepair: null,
        inMaintenanceCoordination: null,
        outMaintenanceCoordination: null,
        inTechnicalManagement: null,
        outTechnicalManagement: null,
        position: null,
        pipeDiameter: null,
        picUrl: null,
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
      this.picUrl = [];
      this.fileList = [];
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
      this.picUrl = [];
      this.open = true;
      this.title = "添加资产与运营数据";
      this.fileList = []
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAsset(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产与运营数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (this.picUrl) {
          this.form.picUrl = this.picUrl.join(',')
        } else {
          this.form.picUrl = null;
        }
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
      this.download('sems/asset/export', {
        ...this.queryParams
      }, `devAsset_${new Date().getTime()}.xlsx`)
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
    //附件上传中处理
    handleFileDocProgress(event, file, fileList) {
      this.doc.file = file;
    },
    //附件上传成功处理
    handleFileDocSuccess(response, file, fileList) {
      // this.picUrl.push(response.msg)
      // this.picName.push(file.name)
      console.log(fileList)
      console.log(file)
      // this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
    },
    handleSee(row) {
      this.pic.picUrl = [];
      console.log(row)
      let linkId = null;
      this.doc.id = null;
      if (row.id) {
        this.doc.id = row.id;
        linkId = row.id;
        this.doc.url += '?linkId=' + linkId + '&linkName=tSpecdevAsset';
      } else {
        this.doc.url += '?linkName=tSpecdevAsset';
      }
      this.getFile(linkId);
      this.pic.open = true
    },
    getFile(linkId) {
      listFile({linkName: 'tSpecdevAsset', linkId: linkId}).then(res => {
        this.picUrl = res.rows
      })
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.fullscreenLoading = true;
    },
    //翻页后继续计算序列值
    indexMethod(index) {
      return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1;
    }
  }
};
</script>
