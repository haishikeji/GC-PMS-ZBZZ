<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="pointRecordList" @selection-change="handleSelectionChange" :height="clientHeight" border>
      <el-table-column label="漏点编号" align="center" prop="pointNo" />
      <el-table-column label="泄漏位置" align="center" prop="leakagePosition" />
      <el-table-column label="介质" align="center" prop="leakageMedium" />
      <el-table-column label="挂牌（是/否）" align="center" prop="suspendPlateM" />
      <el-table-column label="泄露状态（未检出/轻微/严重）" align="center" prop="leakageStatusM" />
      <el-table-column label="挂牌（是/否）" align="center" prop="suspendPlateN" />
      <el-table-column label="泄露状态（未检出/轻微/严重）" align="center" prop="leakageStatusN" />
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
import { listPointRecord, getPointRecord, delPointRecord, addPointRecord, updatePointRecord } from "@/api/asset/pointRecord";

export default {
  name: "PointRecord",
  data() {
    return {
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
      // 漏点巡检记录 表格数据
      pointRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        patrolId: null,
        pointNo: null,
        leakagePosition: null,
        leakageMedium: null,
        suspendPlateM: null,
        leakageStatusM: null,
        suspendPlateN: null,
        leakageStatusN: null,
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
        deptId: [
          { required: true, message: "部门编号不能为空", trigger: "blur" }
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
    /** 查询漏点巡检记录 列表 */
    getList() {
      this.loading = true;
      listPointRecord(this.queryParams).then(response => {
        this.pointRecordList = response.rows;
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
        patrolId: null,
        pointNo: null,
        leakagePosition: null,
        leakageMedium: null,
        suspendPlateM: null,
        leakageStatusM: null,
        suspendPlateN: null,
        leakageStatusN: null,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加漏点巡检记录 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPointRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改漏点巡检记录 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePointRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPointRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除漏点巡检记录 编号为"' + ids + '"的数据项？').then(function() {
        return delPointRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('asset/pointRecord/export', {
        ...this.queryParams
      }, `pointRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
