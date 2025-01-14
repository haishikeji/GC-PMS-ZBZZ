<template>
  <div>
    <!--    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          < <el-form-item label="类型" prop="workType" label-width="50px">
          <el-select v-model="queryParams.workType" :placeholder="请选择 + 类型" clearable size="small" style="width: 100%;">
            <el-option
              v-for="dict in workTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
          <el-form-item label="状态" prop="status" label-width="50px">
            <el-select v-model="queryParams.status" :placeholder="请选择 + 状态" clearable size="small"  style="width: 100%;">
              <el-option
                v-for="dict in agendaStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="回顾/截止时间" prop="deadlineTime" label-width="110px">
            <el-date-picker clearable
              v-model="queryParams.deadlineTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择回顾/截止时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['plant:content:add']"
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
          v-hasPermi="['plant:content:edit']"
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
          v-hasPermi="['plant:content:remove']"
        >删除
        </el-button>
      </el-col>
      <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="contentList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="内容" align="center" prop="agendaContent" width="auto">
        <template slot-scope="scope">
          <p v-html="scope.row.agendaContent" />
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="workType"/>
      <el-table-column label="跟踪人" align="center" prop="responsible"/>
      <el-table-column label="回顾/截止时间" align="center" prop="deadlineTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deadlineTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              icon="el-icon-folder"
              size="mini"
              type="text"
              @click="openFileDialog(scope.row)"
          >附件
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['plant:content:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['plant:content:remove']"
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

    <!-- 添加或修改装置空白会议对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="auto">
        <el-form-item label="会议内容">
          <editor type="textarea" v-model="form.agendaContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="类型" prop="workType">
          <el-select v-model="form.workType" placeholder="请选择类型" clearable size="small" style="width: 100%;"  @change="selectWorkType" >
            <el-option
              v-for="dict in workTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="责任人" prop="responsible" v-if="isI">
          <el-input v-model="form.responsible" placeholder="请输入责任人"/>
        </el-form-item>
        <el-form-item label="状态" v-if="isI">
          <el-select v-model="form.status" placeholder="请选择状态" clearable size="small" style="width: 100%;">
            <el-option
              v-for="dict in agendaStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="回顾/截止时间" prop="deadlineTime"v-if="isI">
          <el-date-picker clearable
                          v-model="form.deadlineTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择回顾/截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容"/>
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
                @click="handleFileRemove(scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
  </div>
</template>

<script>
import {addContent, delContent, getContent, listContent, updateContent} from "@/api/plant/content";
import Editor from "../../../components/Editor/index.vue";
import {delFile,listFile} from "../../../api/file/file";
import {getToken} from "../../../utils/auth";

export default {
  name: "Content",
  components: {Editor},
  props: ['meetingId'],
  data() {
    return {
      file: {
        open: false,
        fileList: [],
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          linkId: null,
          linkName: 'meetContent',
        },
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
      isI: false,
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
      // 装置空白会议表格数据
      contentList: [],
      workTypeOptions: [],
      agendaStatusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        meetingId: this.meetingId,
        agendaContent: null,
        workType: null,
        responsible: null,
        status: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        presided: null,
        isHis: null,
        deadlineTime: null,
        meetingTitle: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8
    });
    this.getDicts("agenda_type").then(res => {
      this.workTypeOptions = res.data;
    });
    this.getDicts("agenda_status").then(res => {
      this.agendaStatusOptions = res.data;
    });
  },
  methods: {
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
        this.file.fileList = response.data;
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
    selectWorkType(val) {
      this.isI = val !== 'I';
      if (val==='A')
        this.form.status='Open'
      else if (val==='P')
        this.form.status='On Going'
    },
    /** 查询装置空白会议列表 */
    getList() {
      if (!this.meetingId) {
        this.queryParams.isHis = 0;
      }
      this.loading = true;
      listContent(this.queryParams).then(response => {
        this.contentList = response.rows;
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
        meetingId: null,
        agendaContent: null,
        workType: null,
        responsible: null,
        status: null,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        presided: null,
        isHis: 0,
        deadlineTime: null,
        meetingTitle: null
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
      this.title = "添加装置空白会议";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getContent(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改装置空白会议";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContent(this.form).then(response => {
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
        return delContent(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('plant/content/export', {
        ...this.queryParams
      }, `content_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
