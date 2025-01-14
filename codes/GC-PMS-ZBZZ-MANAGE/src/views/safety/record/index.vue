<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="检查日期" prop="inspectdate">
        <el-date-picker clearable
                        v-model="queryParams.inspectdate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择检查日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['safety:record:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange" :height="clientHeight"
              border>
      <el-table-column label="位置" align="center" prop="location"/>
      <el-table-column label="类型" align="center" prop="devType" :formatter="devTypeFormat"/>
      <el-table-column label="ok/not ok" align="center" prop="status" :formatter="formatStatus"/>
      <el-table-column label="故障描述" align="center" prop="faultDesc"/>
      <el-table-column label="故障解决签名" align="center" prop="faultSloveSign"/>
      <el-table-column label="检查人" align="center" prop="inspector"/>
      <el-table-column label="检查日期" align="center" prop="inspectdate" width="180"/>
      <el-table-column label="检查状况" align="center" prop="inspectstatus"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import {exportRecord, listRecord} from "@/api/safety/record";
import {exportFiredoor} from "@/api/safety/firedoor";

export default {
  name: "Record",
  data() {
    return {
      devTypeOption: [],
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
      // 检查记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        washerId: null,
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
        remarks: null,
        fileIds: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        washerId: [
          {required: true, message: "洗眼器id不能为空", trigger: "blur"}
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
    this.getDicts("safety_dev_type").then(res => {
      this.devTypeOption = res.data;
    })
  },
  methods: {
    devTypeFormat(row, column) {
      return this.selectDictLabel(this.devTypeOption, row.devType);
    },
    formatStatus(row) {
      if (row.status == "1") {
        return "ok"
      } else if (row.status == "0") {
        return "not ok"
      }
    },
    /** 查询检查记录列表 */
    getList() {
      this.loading = true;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        washerId: null,
        status: "0",
        faultDesc: null,
        faultSloveSign: null,
        inspector: null,
        inspectdate: null,
        inspectstatus: "0",
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        fileIds: null
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
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有数据项?","警告", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function() {
        return exportRecord(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }

  }
};
</script>
