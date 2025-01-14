<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
             label-width="68px">
      <el-form-item label="编号" prop="washerNo">
        <el-input
          v-model="queryParams.washerNo"
          placeholder="请输入编号"
          clearable
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入位置"
          clearable
          @input="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ok/not ok" prop="status" label-width="100px">
        <el-select v-model="queryParams.status" placeholder="请选择ok/not ok" clearable @change="handleQuery">
          <el-option label="ok" value="1"/>
          <el-option label="not ok" value="0"/>
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
          v-hasPermi="['safety:washer:add']"
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
          v-hasPermi="['safety:washer:edit']"
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
          v-hasPermi="['safety:washer:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['safety:washer:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="washerList" @selection-change="handleSelectionChange"
              :height="clientHeight"
              border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="washerNo" min-width="80"/>
      <el-table-column label="位置" align="center" prop="location" min-width="80"/>
      <el-table-column label="ok/not ok" align="center" prop="status" :formatter="formatStatus" min-width="80"/>
      <el-table-column label="故障描述" align="center" prop="faultDesc" min-width="80"/>
      <el-table-column label="故障解决签名" align="center" prop="faultSloveSign" width="130"/>
      <el-table-column label="检查人" align="center" prop="inspector" min-width="80"/>
      <el-table-column label="检查日期" align="center" prop="inspectdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查状况" align="center" prop="inspectstatus" min-width="80"/>
      <el-table-column label="巡检照片" align="center" prop="fileIds" min-width="80">
        <template slot-scope="scope">
          <span v-if="!scope.row.fileIds">N/A</span>
          <el-button v-else type="text" @click="handleSee(scope.row.fileIds)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" min-width="80"/>
      <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-checked"
            @click="openRecord(scope.row)"
            v-hasPermi="['safety:washer:edit']"
          >巡检记录
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['safety:washer:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['safety:washer:remove']"
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

    <!-- 添加或修改洗眼器对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编号" prop="washerNo">
          <el-input v-model="form.washerNo" placeholder="请输入编号"/>
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入位置"/>
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
    <el-dialog :close-on-click-modal="false" :title="record.title" :visible.sync="record.open" width="80%" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAddRecord"
          >新增
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="record.loading" :data="record.dataList" :max-height="record.clientHeight"
                border>
        <el-table-column label="编号" align="center" prop="washerNo">{{ record.washerNo }}</el-table-column>
        <el-table-column label="ok/not ok" align="center" prop="status">
          <template slot-scope="scope">
            <el-radio-group v-if="scope.row.isEdit" v-model="scope.row.status" @change="changeRadio(scope.row)">
              <el-radio label="1">ok</el-radio>
              <el-radio label="0">not ok</el-radio>
            </el-radio-group>
            <div v-else>{{ formatStatus(scope.row) }}</div>
          </template>
        </el-table-column>
        <el-table-column label="故障描述" align="center" prop="faultDesc">
          <template slot-scope="scope">
            <el-input v-if="scope.row.isEdit" v-model="scope.row.faultDesc"
                      :disabled="scope.row.status==1"></el-input>
            <span v-else>{{ scope.row.faultDesc }}</span>
          </template>
        </el-table-column>
        <el-table-column label="故障解决签名" align="center" prop="faultSloveSign">
          <template slot-scope="scope">
            <div :key="Math.random()">
              <el-input v-if="scope.row.isEdit" v-model="scope.row.faultSloveSign"
                        :disabled="scope.row.status==1"></el-input>
              <span v-else>{{ scope.row.faultSloveSign }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="检查人" align="center" prop="inspector">
          <template slot-scope="scope">
            <el-input v-if="scope.row.isEdit" v-model="scope.row.inspector"></el-input>
            <span v-else>{{ scope.row.inspector }}</span>
          </template>
        </el-table-column>
        <el-table-column label="检查日期" align="center" prop="inspectdate">
          <template slot-scope="scope">
            <el-date-picker style="width: auto"
                            v-if="scope.row.isEdit"
                            v-model="scope.row.inspectdate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            :placeholder="日期">
            </el-date-picker>
            <span v-else>{{ parseTime(scope.row.inspectdate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="检查班组" align="center" prop="inspectstatus">
          <template slot-scope="scope">
            <el-input v-if="scope.row.isEdit" v-model="scope.row.inspectstatus"></el-input>
            <span v-else>{{ scope.row.inspectstatus }}</span>
          </template>
        </el-table-column>
        <el-table-column label="巡检照片" align="center">
          <template slot-scope="scope">
            <el-upload
              v-if="scope.row.isEdit"
              ref="doc"
              :headers="doc.headers"
              :action="doc.url"
              :disabled="doc.isUploading"
              :on-progress="handleFileDocProgress"
              :on-success="handleFileDocSuccess"
              :auto-upload="true"
              accept=".jpg,.png"
              :file-list="fileList"
              :multiple="true"
              list-type="picture"
            >
              <el-button type="primary"><i class="el-icon-upload"></i> 点击上传</el-button>
            </el-upload>
            <span v-else-if="!scope.row.isEdit&&!scope.row.fileIds">N/A</span>
            <el-button v-else type="text" @click="handleSee(scope.row.fileIds)">预览</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="scope.row.isEdit"
                       size="mini"
                       type="text"
                       icon="el-icon-check"
                       @click="save(scope.row)"
            >保存
            </el-button>
            <el-button v-if="scope.row.isEdit"
                       size="mini"
                       type="text"
                       icon="el-icon-close"
                       @click="cancelRecord(scope.row,scope.$index)"
            >取消
            </el-button>
            <el-button v-if="!scope.row.isEdit"
                       size="mini"
                       type="text"
                       icon="el-icon-edit"
                       @click="handleUpdateRecord(scope.row)"
            >修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="record.total>0"
        :total="record.total"
        :page.sync="record.queryParams.pageNum"
        :limit.sync="record.queryParams.pageSize"
        @pagination="getList"
      />
    </el-dialog>
    <el-dialog :close-on-click-modal="false" title="巡检照片" :visible.sync="file.open">
      <div>
        <el-image
          style="width: 30%; height: 30%;margin:10px;border-radius: 5%;vertical-align: middle;'"
          v-for="url in srcList"
          :src="url"
          :preview-src-list="srcList">
        </el-image>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listWasher, getWasher, delWasher, addWasher, updateWasher, exportWasher} from "@/api/safety/washer";
import {addRecord, getRecord, listRecord, updateRecord} from "@/api/safety/record";
import {getToken} from "@/utils/auth";
import {MessageBox} from "element-ui";
import {getFiles} from "@/api/safety/files";

export default {
  name: "Washer",
  data() {
    return {
      fileIds: [],
      fileList: [],
      srcList: [],
      file: {
        open: false,

      },
      doc: {
        file: "",
        // 是否显示弹出层（报告附件）
        open: false,
        // 弹出层标题（报告附件）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 报告附件上传位置编号
        ids: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/safety/files/uploadFile",
        commonfileList: null,
        pType: 'traning',
        pId: null
      },
      record: {
        open: false,
        title: "巡检记录",
        dataList: [],
        loading: false,
        washerNo: null,
        devId: null,
        total: 0,
        clientHeight: 400,
        queryParams: {
          devId: null,
          devType: 1,
          pageNum: 1,
          pageSize: 20,
        },
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
      // 洗眼器表格数据
      washerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        washerNo: null,
        location: null,
        status: null,
        faultDesc: null,
        faultSloveSign: null,
        inspector: null,
        inspectdate: null,
        inspectstatus: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        fileIds: null,
        remarks: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        washerNo: [
          {required: true, message: "编号不能为空", trigger: "blur"}
        ],
        location: [
          {required: true, message: "位置不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8;
      this.record.clientHeight = (document.body.clientHeight - 150) * 0.8;
    });
  },
  methods: {
    handleSee(ids) {
      this.srcList = []
      this.file.open = true;
      for (const id of ids.split(',')) {
        getFiles(id).then(res => {
          this.srcList.push(process.env.VUE_APP_BASE_API + res.data.fileUrl)
        })
      }
    },
    changeRadio(row) {
      console.log(row)
      if (row.status == 1) {
        row.faultDesc = '';
        row.faultSloveSign = '';
      }
    },
    //附件上传中处理
    handleFileDocProgress(event, file, fileList) {
      this.doc.file = file;
    },
    //附件上传成功处理
    handleFileDocSuccess(response, file, fileList) {
      this.fileIds.push(response.data)
    },
    cancelRecord(row, index) {
      // 如果是新增的数据
      if (row.isAdd) {
        this.record.dataList.splice(index, 1)
      } else {
        // 不是新增的数据  还原数据
        for (const i in row.oldRow) {
          row[i] = row.oldRow[i]
        }
        row.isEdit = false
      }
    },
    save(row) {
      let fileIds = '';
      console.log(this.fileIds)
      if (this.fileIds != []) {
        fileIds = this.fileIds.join(',')
      }
      if (row.isAdd) {
        let data = {
          devId: this.record.devId,
          location:this.record.location,
          devType: 1,
          inspectstatus: row.inspectstatus,
          inspectdate: row.inspectdate,
          inspector: row.inspector,
          faultSloveSign: row.faultSloveSign,
          faultDesc: row.faultDesc,
          status: row.status,
          fileIds: fileIds,
        }
        addRecord(data).then(res => {
          this.$message.success("保存成功")
          row.isEdit = false;
          row.isAdd = false;
          this.getList();
          this.getRecordList(this.record.devId);
          this.fileIds=[]
        })
      } else {
        let data = {
          id: row.id,
          devId: this.record.devId,
          location:this.record.location,
          devType: 1,
          inspectstatus: row.inspectstatus,
          inspectdate: row.inspectdate,
          inspector: row.inspector,
          faultSloveSign: row.faultSloveSign,
          faultDesc: row.faultDesc,
          status: row.status,
          fileIds: fileIds,
        }
        updateRecord(data).then(res => {
          this.$message.success("修改成功")
          row.isEdit = false;
          row.isAdd = false;
          this.getList();
          this.getRecordList(this.record.devId);
          this.fileIds=[]
        })
      }
    },
    handleAddRecord() {
      this.record.dataList.push({
        inspectstatus: null,
        inspectdate: null,
        inspector: null,
        faultSloveSign: null,
        faultDesc: null,
        status: null,
        isEdit: true,
        isAdd: true
      })
    },
    handleUpdateRecord(row) {
      // 备份原始数据
      row['oldRow'] = JSON.parse(JSON.stringify(row));
      this.$nextTick(() => {
        row.isEdit = true;
      })
    },
    openRecord(row) {
      this.record.dataList = [];
      this.record.open = true;
      this.record.loading = true;
      this.record.washerNo = row.washerNo;
      this.record.devId = row.id;
      this.record.location = row.location;
      this.getRecordList(row.id)
    },
    getRecordList(id) {
      this.record.queryParams.devId = id;
      listRecord(this.record.queryParams).then(res => {
        this.record.total = res.total;
        this.record.loading = false;
        res.rows.forEach(item => {
          item["isEdit"] = false;
        })
        this.record.dataList = res.rows;
      })
    },
    /** 查询洗眼器列表 */
    getList() {
      this.loading = true;
      listWasher(this.queryParams).then(response => {
        this.washerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    formatStatus(row) {
      if (row.status == "1") {
        return "ok"
      } else if (row.status == "0") {
        return "not ok"
      }
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
        washerNo: null,
        location: null,
        status: null,
        faultDesc: null,
        faultSloveSign: null,
        inspector: null,
        inspectdate: null,
        inspectstatus: null,
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
      this.title = "添加洗眼器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWasher(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改洗眼器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWasher(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWasher(this.form).then(response => {
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
      MessageBox.confirm('是否确认删除该洗眼器').then(function () {
        return delWasher(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有数据项?","警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function() {
        return exportWasher(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }

  }
};
</script>
