<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="父级设备类型" prop="pid">
        <el-select v-model="queryParams.pid" placeholder="请选择父级设备类型" clearable>
          <el-option v-for="item in devTypeList" :key="item.id" :label="item.devType" :value="item.id">
          </el-option>
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
          v-hasPermi="['sems:typemgr:add']"
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
          v-hasPermi="['sems:typemgr:edit']"
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
          v-hasPermi="['sems:typemgr:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :span-method="mergeMethod" :data="typemgrList"
              @selection-change="handleSelectionChange" :height="clientHeight" border>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="父级设备类型" align="center" prop="pid" :formatter="devTypeFormat"/>
      <el-table-column label="设备类型" align="center" prop="devType"/>
      <el-table-column label="备注" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sems:typemgr:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sems:typemgr:remove']"
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

    <!-- 添加或修改设备类型管理对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="设备类型" prop="devType">
          <el-input v-model="form.devType" placeholder="请输入设备类型"/>
        </el-form-item>
        <el-form-item label="上级设备类型" prop="pid">
          <el-select v-model="form.pid" placeholder="请选择上级设备类型" clearable>
            <el-option v-for="item in devTypeList" :key="item.id" :label="item.devType" :value="item.id">
            </el-option>
          </el-select>
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
  </div>
</template>

<script>
import {listTypemgr, getTypemgr, delTypemgr, addTypemgr, updateTypemgr, listTypemgrAll} from "@/api/asset/typemgr";

export default {
  name: "Typemgr",
  data() {
    return {
      devTypeList: [],
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
      // 设备类型管理表格数据
      typemgrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        devType: null,
        pid: null,
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
        devType: [
          {required: true, message: "主题不能为空", trigger: "change"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDevType1List();
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight - 80) * 0.8
    });
  },
  methods: {
    devTypeFormat(row){
      for(let item of this.devTypeList){
        if (item.id===row.pid){
          return item.devType;
        }else if(row.pid===0){
          return '设备一级类型'
        }
      }
    },
    /** 查询设备类型管理列表 */
    getList() {
      this.loading = true;
      listTypemgr(this.queryParams).then(response => {
        this.typemgrList = response.rows;
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
        devType: null,
        pid: null,
        delFlag: null,
        status: 0,
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
    getDevType1List() {
      listTypemgrAll({pid: 0}).then(response => {
        this.devTypeList = response.data;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备类型管理";
      this.getDevType1List();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTypemgr(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备类型管理";
        this.getDevType1List();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTypemgr(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTypemgr(this.form).then(response => {
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
        return delTypemgr(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sems/typemgr/export', {
        ...this.queryParams
      }, `typemgr_${new Date().getTime()}.xlsx`)
    },
    //合并单元格
    mergeMethod({row, column, rowIndex, columnIndex}) {
      // if (columnIndex === 0) {
      //   const _row = this.setTable(this.unitList).merge[rowIndex];
      //   const _col = _row > 0 ? 1 : 0;
      //   return {
      //     rowspan: _row,
      //     colspan: _col
      //   };
      // }
      if (columnIndex === 1) {
        const _row = this.setTable(this.typemgrList).merge[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return {
          rowspan: _row,
          colspan: _col
        };
      }
    },
    //单元格整理
    setTable(tableData) {
      let spanArr = [],
        concat = 0;
      tableData.forEach((item, index) => {
        if (index === 0) {
          spanArr.push(1);
        } else {
          if (item.pid === tableData[index - 1].pid) {
            //第一列需合并相同内容的判断条件
            spanArr[concat] += 1;
            spanArr.push(0);
          } else {
            spanArr.push(1);
            concat = index;
          }
        }
      });
      return {
        merge: spanArr
      };
    }
  }
};
</script>
