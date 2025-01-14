<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable @change="handleQuery">
          <el-option
            v-for="dict in dict.type.basedoc_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文档名称" prop="docName">
        <el-input
          v-model="queryParams.docName"
          placeholder="请输入文档名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="docStatus">
        <el-select v-model="queryParams.docStatus" placeholder="请选择状态" clearable  @change="handleQuery">
          <el-option
            v-for="dict in dict.type.basedoc_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="删除状态" prop="delFlag">
        <el-select v-model="queryParams.delFlag" placeholder="请选择删除状态" clearable @change="handleQuery">
          <el-option
            key="0"
            label="未删除"
            value="0"
          />
          <el-option
            key="1"
            label="已删除"
            value="1"
          />
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
          v-hasPermi="['asset:basedoc:add']"
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
          v-hasPermi="['asset:basedoc:edit']"
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
          v-hasPermi="['asset:basedoc:remove']"
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
          v-hasPermi="['asset:basedoc:add']"
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
          v-hasPermi="['asset:basedoc:export']"
        >导出
        </el-button>
        <!--        <el-checkbox-button-->
        <!--          type="warning"-->
        <!--          plain-->
        <!--          icon="el-icon-download"-->
        <!--          size="mini"-->
        <!--          @click="getDelList"-->
        <!--        > 已删除-->
        <!--        </el-checkbox-button>-->
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="basedocList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.basedoc_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="文档名称" align="center" prop="docName"/>
      <el-table-column label="文档链接" align="center">
        <template slot-scope="scope">
          <el-badge :value="scope.row.fileNum" style="margin-top: 10px;" type="primary">
            <el-button
              size="mini"
              icon="el-icon-folder"
              @click="openFileDialog(scope.row)"
              circle
            ></el-button>
          </el-badge>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="docStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.basedoc_status" :value="scope.row.docStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上传人" align="center" prop="createrCode"/>
      <el-table-column label="上传日期" align="center" prop="createdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:basedoc:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:basedoc:remove']"
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

    <!-- 添加或修改资产管理基础文档清单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.basedoc_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文档名称" prop="docName">
          <el-input v-model="form.docName" placeholder="请输入文档名称"/>
        </el-form-item>
        <el-form-item label="状态" prop="docStatus">
          <el-select v-model="form.docStatus" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.basedoc_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import {addBasedoc, delBasedoc, getBasedoc, listBasedoc, updateBasedoc} from "@/api/asset/basedoc";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import File from "@/views/file/file/index.vue";
import UploadFile from "@/views/file/file/uploadFile.vue";

export default {
  name: "Basedoc",
  dicts: ['basedoc_status', 'basedoc_type'],
  components: {UploadFile, File, Treeselect},
  data() {
    return {
      isDel: false,
      file: {
        open: false,
        linkId: null,
        linkName: 'basedoc'
      },
      // 批量导入全屏遮罩
      fullscreenLoading: false,
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        type: "assetbasedoc",
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
        url: process.env.VUE_APP_BASE_API + "/asset/basedoc/importData"
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
      // 资产管理基础文档清单表格数据
      basedocList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        type: null,
        docName: null,
        docStatus: null,
        createrCode: null,
        createdate: null,
        remarks: null,
        updaterCode: null,
        updatedate: null,
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      fileRules: {}
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
    openFileDialog(row) {
      this.$router.push({path: '/system/file', query: {linkId: row.id, linkName: this.file.linkName}})
    },
    getDelList() {
      if (this.isDel === true) {
        this.isDel = false;
        this.queryParams.delFlag = 0;
      } else {
        this.isDel = true;
        this.queryParams.delFlag = 1;
      }
      this.getList();
    },
    /** 查询资产管理基础文档清单列表 */
    getList() {
      this.loading = true;
      listBasedoc(this.queryParams).then(response => {
        this.basedocList = response.rows;
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
        type: null,
        docName: null,
        docStatus: null,
        createrCode: null,
        createdate: null,
        remarks: null,
        delFlag: null,
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
      this.title = "添加资产管理基础文档清单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBasedoc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产管理基础文档清单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBasedoc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBasedoc(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资产管理基础文档清单编号为"' + ids + '"的数据项？').then(function () {
        return delBasedoc(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('asset/basedoc/export', {
        ...this.queryParams
      }, `basedoc_${new Date().getTime()}.xlsx`)
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
    }
  }
};
</script>
