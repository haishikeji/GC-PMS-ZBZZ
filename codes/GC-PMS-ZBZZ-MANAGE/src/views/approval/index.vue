<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">

      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['ehs:approval:add']"
            icon="el-icon-plus"
            plain
            size="mini"
            type="primary"
            @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['ehs:approval:edit']"
            :disabled="single"
            icon="el-icon-edit"
            plain
            size="mini"
            type="success"
            @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['ehs:approval:remove']"
            :disabled="multiple"
            icon="el-icon-delete"
            plain
            size="mini"
            type="danger"
            @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['ehs:approval:export']"
            icon="el-icon-download"
            plain
            size="mini"
            type="warning"
            @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-hasPermi="['sems:specDt:edit']"
            icon="el-icon-upload2"
            size="mini"
            type="info"
            @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="approvalList" :height="clientHeight" border
              @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="批文属性" prop="approvalAttibutes" width="100"/>
      <el-table-column align="center" label="批准日期" prop="effetivedate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effetivedate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="项目名称" prop="itemName" width="150"/>
      <el-table-column align="center" label="项目概况" prop="itemOverview" width="150"/>
      <el-table-column align="center" label="批文名称" prop="approvalname" width="150"/>
      <el-table-column align="center" label="批文编号" prop="fileno" width="150"/>
      <el-table-column align="center" label="审批单位" prop="responsauth" width="150"/>
      <el-table-column align="center" label="主要内容" prop="content" width="200">
        <template slot-scope="scope">
          <span v-if="scope.row.content" style="white-space: pre-line">{{ scope.row.content + '\n' }}</span>
          <span v-if="scope.row.contentFile">
            <el-image
                v-for="item in scope.row.contentFile"
                :preview-src-list="scope.row.contentFile"
                :src="item"
                style="width: 100px; height: 100px;">
            </el-image>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="适用范围" prop="scope" width="200"/>
      <el-table-column align="center" label="相关法规" prop="relatedlaw" width="200"/>
      <el-table-column align="center" label="证书有效期" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.isPermanent==1">永久</span>
          <span v-if="scope.row.isPermanent==0">
            {{ parseTime(scope.row.validityBefore, '{y}-{m}-{d}') }} ~
            {{ parseTime(scope.row.validityAfter, '{y}-{m}-{d}') }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否需要跟进" prop="follow" width="120"/>
      <el-table-column align="center" label="负责人" prop="owner" width="120"/>
      <el-table-column align="center" label="回顾人" prop="reviewer" width="120"/>
      <el-table-column align="center" label="回顾时间" prop="reviewdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reviewdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否合规" prop="isCompliance" width="120"/>
      <el-table-column align="center" label="下次回顾时间" prop="nextreviewdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextreviewdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" prop="remarks" width="120"/>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button
              icon="el-icon-folder"
              size="mini"
              type="text"
              @click="openFileDialog(scope.row)"
          >附件
          </el-button>
          <el-button
              v-hasPermi="['ehs:approval:edit']"
              icon="el-icon-edit"
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
              v-hasPermi="['ehs:approval:remove']"
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

    <!-- 添加或修改批文清单对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" append-to-body width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="批准日期" prop="effetivedate">
          <el-date-picker v-model="form.effetivedate"
                          clearable
                          placeholder="请选择批准日期"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="项目概况" prop="itemOverview">
          <el-input v-model="form.itemOverview" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="批文属性" prop="approvalAttibutes">
          <el-input v-model="form.approvalAttibutes" placeholder="请输入批文属性"/>
        </el-form-item>
        <el-form-item label="批文名称" prop="approvalname">
          <el-input v-model="form.approvalname" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="批文编号" prop="fileno">
          <el-input v-model="form.fileno" placeholder="请输入批文编号"/>
        </el-form-item>
        <el-form-item label="审批单位" prop="responsauth">
          <el-input v-model="form.responsauth" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="主要内容" prop="content">
          <span>包含图文？</span>
          <el-checkbox v-model="form.isInclude"/>
          <el-input v-model="form.content" placeholder="请输入内容" type="textarea"/>
          <el-upload
              v-if="form.isInclude"
              ref="picture"
              :action="picture.url"
              :file-list="fileList"
              :headers="picture.headers"
              :on-remove="handleRemovePic"
              :on-success="handlePicSuccess"
              list-type="picture-card" style="display: inline-block;margin-top: 5px">
            <i class="el-icon-plus "></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="适用范围" prop="scope">
          <el-input v-model="form.scope" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="相关法规" prop="relatedlaw">
          <el-input v-model="form.relatedlaw" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="有效期是否永久" prop="isPermanent">
          <el-radio v-model="form.isPermanent" label="0">否</el-radio>
          <el-radio v-model="form.isPermanent" label="1">是</el-radio>
        </el-form-item>
        <el-form-item v-if="form.isPermanent==0" label="证书有效期" prop="validityBefore">
          <el-date-picker v-model="form.validityBefore"
                          clearable
                          placeholder="请选择证书生效日期"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item v-if="form.isPermanent==0" label="证书有效期" prop="validityAfter">
          <el-date-picker v-model="form.validityAfter"
                          clearable
                          placeholder="请选择证书截止日期"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否需要跟进" prop="follow">
          <el-input v-model="form.follow" placeholder="请输入是否需要跟进"/>
        </el-form-item>
        <el-form-item label="负责人" prop="owner">
          <el-input v-model="form.owner" placeholder="请输入负责人"/>
        </el-form-item>
        <el-form-item label="回顾人" prop="reviewer">
          <el-input v-model="form.reviewer" placeholder="请输入回顾人"/>
        </el-form-item>
        <el-form-item label="回顾时间" prop="reviewdate">
          <el-date-picker v-model="form.reviewdate"
                          clearable
                          placeholder="请选择回顾时间"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否合规" prop="isCompliance">
          <el-input v-model="form.isCompliance" placeholder="请输入是否合规"/>
        </el-form-item>
        <el-form-item label="下次回顾时间" prop="nextreviewdate">
          <el-date-picker v-model="form.nextreviewdate"
                          clearable
                          placeholder="请选择下次回顾时间"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :close-on-click-modal="false" :visible.sync="file.open" append-to-body title="附件管理" width="60%">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
              icon="el-icon-plus"
              plain
              size="mini"
              type="primary"
              @click="handleFileAdd"
          >新增
          </el-button>
        </el-col>
      </el-row>
      <el-table  :data="file.fileList" :height="clientHeight-100"
                border>
        <el-table-column align="center" label="附件名称" prop="fileName">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  type="text"
                  @click="previewFile(scope.row.fileUrl)"
              >{{ scope.row.fileName }}
              </el-button>
          </template>
        </el-table-column>
        <el-table-column align="center" label="上传时间" prop="uploadDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="上传人" prop="uploader">
        </el-table-column>
        <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
          <template slot-scope="scope">
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="handleFileView(scope.row)"
            >查看
            </el-button>
            <el-button
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="handleFileRemove(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="file.total>0"
          :limit.sync="file.queryParams.pageSize"
          :page.sync="file.queryParams.pageNum"
          :total="file.total"
          @pagination="getFileList"
      />
    </el-dialog>
    <el-dialog :close-on-click-modal="false" :visible.sync="doc.open" append-to-body title="附件上传" width="400px">
      <el-upload
          ref="doc"
          :action="doc.url"
          :auto-upload="false"
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
          <em>点击上传</em>
        </div>
        <div slot="tip" class="el-upload__tip">
          <!--                  <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据-->
          <el-link style="font-size:12px" type="info" @click="importTemplate">下载模板</el-link>
        </div>
        <form ref="downloadFileForm" :action="upload.downloadAction" target="FORMSUBMIT">
          <input :value="upload.type" hidden name="type"/>
        </form>
        <div slot="tip" class="el-upload__tip" style="color:red">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <a v-if="waitSubmit" style="margin-right: 300px">正在导入...</a>
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="primary"
                   @click="submitFileForm">确 定
        </el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addApproval, delApproval, getApproval, listApproval, updateApproval,exportApproval} from "@/api/approval/approval";
import {getToken} from "@/utils/auth";

export default {
  name: "Approval",
  data() {
    return {
      fullscreenLoading: false,
      waitSubmit: false,
      file: {
        open: false,
        fileList: [],
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          linkId: null,
          linkName: 'ehsApproval',
        },
        total:0,
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
        url: process.env.VUE_APP_BASE_API + "/file/file/uploadFile",
      },
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        // 是否显示弹出层（用户导入）
        type: "approval",
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
        url: process.env.VUE_APP_BASE_API + "/ehs/approval/importData"
      },
      picUrl: [],
      fileList: [],
      picture: {
        file: "",
        // 报告附件上传位置编号
        ids: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/ehs/environapproval/uploadPic",
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
      // 批文清单表格数据
      approvalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        plantCode: null,
        division: null,
        effetivedate: null,
        itemName: null,
        itemOverview: null,
        approvalAttibutes: null,
        approvalname: null,
        fileno: null,
        responsauth: null,
        content: null,
        scope: null,
        relatedlaw: null,
        validityBefore: null,
        validityAfter: null,
        isPermanent: null,
        follow: null,
        owner: null,
        reviewer: null,
        reviewdate: null,
        isCompliance: null,
        nextreviewdate: null,
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
        approvalname: [
          {required: true, message: "批文名称不能为空", trigger: "blur"}
        ],
        fileno: [
          {required: true, message: "批文编号不能为空", trigger: "blur"}
        ],
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
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    },
    previewFile(url){
      window.open(process.env.VUE_APP_BASE_API + url);
    },
    submitUpload() {
      this.$refs.doc.submit();
      this.doc.open = false;
      this.doc.fileList = [];
      this.getFileList();
    },
    //附件上传中处理
    handleFileDocProgress(event, file, fileList) {
      this.doc.file = file;
    },
    //附件上传成功处理
    handleFileDocSuccess(response, file, fileList) {
      this.$modal.msgSuccess("上传成功");
      this.doc.open = false;
      this.doc.fileList = [];
      this.getFileList();
    },
    cancelUpload() {
      this.doc.open = false;
      this.doc.fileList = [];
    },
    openFileDialog(row) {
      this.file.open = true
      this.file.fileList=[];
      this.file.queryParams.linkId = row.id;
      this.getFileList();
    },
    getFileList() {
      listFile(this.file.queryParams).then(response => {
        this.file.fileList = response.rows;
        this.file.total = response.total;
      })
    },
    handleFileAdd() {
      this.doc.open = true;
      this.doc.url = process.env.VUE_APP_BASE_API + "/file/file/uploadFile?linkId=" + this.file.queryParams.linkId + "&linkName=" + this.file.queryParams.linkName;
    },
    handleFileRemove(row) {
      delFile(row.id).then(res => {
        this.$modal.msgSuccess("删除成功");
        this.getFileList();
      });
    },
    handleRemovePic(file, fileList) {
      console.log(fileList)
      this.picUrl = [];
      for (let fileListElement of fileList) {
        this.picUrl.push(fileListElement.response.msg)
      }
    },
    handlePicSuccess(res, file, fileList) {
      this.picUrl.push(res.msg)
    },
    /** 查询批文清单列表 */
    getList() {
      this.loading = true;
      listApproval(this.queryParams).then(response => {
        for (let item of response.rows) {
          if (item.contentFile) {
            let contentFile = [];
            for (let url of item.contentFile.split(',')) {
              contentFile.push(process.env.VUE_APP_BASE_API + url);
            }
            item.contentFile = contentFile;
          }
        }
        this.approvalList = response.rows;
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
      this.picUrl = [];
      this.fileList = [];
      this.form = {
        id: null,
        plantCode: null,
        division: null,
        effetivedate: null,
        itemName: null,
        itemOverview: null,
        approvalAttibutes: null,
        approvalname: null,
        fileno: null,
        isInclude: false,
        responsauth: null,
        content: null,
        contentFile: null,
        scope: null,
        relatedlaw: null,
        validityBefore: null,
        validityAfter: null,
        isPermanent: null,
        follow: null,
        owner: null,
        reviewer: null,
        reviewdate: null,
        isCompliance: null,
        nextreviewdate: null,
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
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = 用户导入;
      this.upload.open = true;
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
      this.title = "添加批文清单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getApproval(id).then(response => {
        this.form = response.data;
        if (response.data.contentFile) {
          this.form.isInclude = true;
          for (let contentFileElement of response.data.contentFile.split(',')) {
            let item = {url: process.env.VUE_APP_BASE_API + contentFileElement, response: {msg: contentFileElement}}
            this.fileList.push(item);
            this.picUrl.push(contentFileElement);
          }
        }
        this.open = true;
        this.title = "修改批文清单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.picUrl.length > 0) {
            this.form.contentFile = this.picUrl.join(',')
          }
          if (this.form.id != null) {
            updateApproval(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApproval(this.form).then(response => {
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
      this.$confirm('是否确认删除数据项？').then(function () {
        return delApproval(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有批文清单数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function () {
        return exportApproval(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    handleFileView(row){
      this.$router.push({ path: '/ehs/edit', query: { fileId: row.id, ot: 'detail' }});
    }
  }
};
</script>
