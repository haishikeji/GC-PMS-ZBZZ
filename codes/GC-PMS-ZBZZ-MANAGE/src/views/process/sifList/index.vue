<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="SN" prop="sifNo">
        <el-input
          v-model="queryParams.sifNo"
          placeholder="请输入SN"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="装置/单元" prop="plant">
        <el-input
          v-model="queryParams.plant"
          placeholder="请输入装置/单元"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SIF位号" prop="sifTag">
        <el-input
          v-model="queryParams.sifTag"
          placeholder="请输入SIF位号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本" prop="ver">
        <el-input
          v-model="queryParams.ver"
          placeholder="请输入版本"
          clearable
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['process:sifList:add']"
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
          v-hasPermi="['process:sifList:edit']"
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
          v-hasPermi="['process:sifList:remove']"
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
          v-hasPermi="['process:sifList:add']"
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
          v-hasPermi="['process:sifList:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sifListList" :span-method="mergeMethod"
              @selection-change="handleSelectionChange" :height="clientHeight" border>
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="SN" align="center" prop="sifNo" width="180"/>
      <el-table-column :label="'装置/单元\nPlant/Unit'" align="center" prop="plant" width="180"/>
      <el-table-column :label="'SIF位号\nSIF Tag'" align="center" prop="sifTag" width="180"/>
      <el-table-column :label="'Deviation'" align="center" prop="deviation" width="180"/>
      <el-table-column :label="'工艺风险分析文件\nRisk Analysis (PHA) Documentation'" align="center" prop="riskAnalysis" width="180"/>
      <el-table-column :label="'CAUSE and CONSEQUENCE\n'" align="center" prop="causeConsequence" width="230"/>
      <el-table-column :label="'要求的SIL\nRequired SIL'" align="center" prop="requiredSil" width="180"/>
      <el-table-column :label="'实现的SIL\nAchieved SIL'" align="center" prop="acheievedSil" width="180"/>
      <el-table-column :label="'要求时的平均失效概率\nPFDavg'" align="center" prop="pfdavg" width="180"/>
      <el-table-column :label="'传感器位号\nSensor(s) Tag'" align="center" prop="sensorTag" width="180"/>
      <el-table-column :label="'类型\nType'" align="center" prop="type" width="180"/>
      <el-table-column :label="'用途\nService'" align="center" prop="service" width="180"/>
      <el-table-column :label="'表决\nVoting'" align="center" prop="votingQ" width="180"/>
      <el-table-column :label="'联锁条件\nCondition'" align="center" prop="condition" width="180"/>
      <el-table-column :label="'测试周期\nMain PTI'" align="center" prop="mainPti" width="180"/>
      <el-table-column :label="'最终元件位号\nFinal Element(s) Tag '" align="center" prop="finalElementTag" width="180"/>
<!--      <el-table-column label="h" align="center" prop="h" width="180"/>-->
      <el-table-column :label="'类型\nType'" align="center" prop="hType" width="180"/>
      <el-table-column :label="'用途\nService'" align="center" prop="hService" width="180"/>
      <el-table-column :label="'表决\nVoting'" align="center" prop="votingH" width="180"/>
      <el-table-column :label="'联锁动作\nAction'" align="center" prop="action" width="180"/>
      <el-table-column :label="'测试周期\nMain PTI'" align="center" prop="action" width="180"/>
      <el-table-column :label="'备注\nRemarks'" align="center" prop="remarks" width="180"/>
      <el-table-column :label="'版本\nVer.'" align="center" prop="ver" width="180"/>
      <el-table-column :label="'Filter'" align="center" prop="filter" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:sifList:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:sifList:remove']"
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

    <!-- 添加或修改SIF Overview List对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="170px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="SN" prop="sifNo">
              <el-input v-model="form.sifNo" placeholder="请输入SN"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="装置/单元" prop="plant">
              <el-input v-model="form.plant" placeholder="请输入装置/单元"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="SIF位号" prop="sifTag">
              <el-input v-model="form.sifTag" placeholder="请输入SIF位号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Deviation" prop="deviation">
              <el-input v-model="form.deviation" placeholder="请输入Deviation"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工艺风险分析文件" prop="riskAnalysis">
              <el-input v-model="form.riskAnalysis" placeholder="请输入工艺风险分析文件"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="'CAUSE and\n CONSEQUENCE'" prop="causeConsequence" style="white-space: pre-line">
              <el-input v-model="form.causeConsequence" placeholder="请输入CAUSE and CONSEQUENCE"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="要求的SIL" prop="requiredSil">
              <el-input v-model="form.requiredSil" placeholder="请输入要求的SIL"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实现的SIL" prop="acheievedSil">
              <el-input v-model="form.acheievedSil" placeholder="请输入实现的SIL"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="版本" prop="ver">
              <el-input v-model="form.ver" placeholder="请输入版本"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="要求时的平均失效概率" prop="pfdavg">
              <el-input v-model="form.pfdavg" placeholder="请输入要求时的平均失效概率"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-card v-for="(recordForm, index) in recordForm" :key="index" shadow="always" class="mb8 mt8">
        <el-form ref="recordForm" :model="recordForm" :rules="rules" label-width="100px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="传感器位号" prop="sensorTag">
                <el-input v-model="recordForm.sensorTag" placeholder="请输入传感器位号"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="类型" prop="type">
                <el-input v-model="recordForm.type" placeholder="请输入类型"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="用途" prop="service">
                <el-input v-model="recordForm.service" placeholder="请输入用途"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="表决" prop="votingQ">
                <el-input v-model="recordForm.votingQ" placeholder="请输入表决"/>
              </el-form-item>
            </el-col>
            <!--            <el-col :span="3">
                          <el-button
                          type="success"
                          plain
                          @click.prevent="copyDomain(recordForm)"
                          style="float: right;">➕
                          </el-button>
                        </el-col>-->
            <el-col :span="1">
              <el-tooltip class="item" effect="dark" content="移除本行" placement="top">
                <el-button
                  type="text"
                  @click.prevent="removeDomain(recordForm)"
                  style="float: right;"
                >❌
                </el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="联锁条件" prop="condition">
                <el-input v-model="recordForm.condition" placeholder="请输入联锁条件"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试周期" prop="mainPti">
                <el-input v-model="recordForm.mainPti" placeholder="请输入测试周期"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最终元件位号" prop="finalElementTag">
                <el-input v-model="recordForm.finalElementTag" placeholder="请输入最终元件位号"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="类型" prop="hType">
                <el-input v-model="recordForm.hType" placeholder="请输入类型"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="用途" prop="hService">
                <el-input v-model="recordForm.hService" placeholder="请输入用途"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="表决" prop="votingH">
                <el-input v-model="recordForm.votingH" placeholder="请输入表决"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="联锁动作" prop="action">
                <el-input v-model="recordForm.action" placeholder="请输入联锁动作"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="测试周期" prop="mainPtiH">
                <el-input v-model="recordForm.mainPtiH" placeholder="请输入测试周期"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="Filter" prop="filter">
                <el-input v-model="recordForm.filter" placeholder="请输入Filter"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
      <div class="mb8 mt8">
        <el-tooltip class="item" effect="dark" content="该按钮会删除所有卡片内容！请谨慎操作！" placement="top">
          <el-button type="danger" plain @click="resetForm1()" :disabled="finalFlag"><i
            class="el-icon-refresh-left"></i></el-button>
        </el-tooltip>
        <el-button type="success" plain @click="add"><i class="el-icon-plus"></i></el-button>
        <el-button type="warning" plain @click="reduce" :disabled="flag||finalFlag"><i class="el-icon-minus"></i>
        </el-button>
      </div>

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
import {addSifList, delSifList, getSifList, listSifList, updateSifList} from "@/api/process/sifList";
import {getToken} from "@/utils/auth";
import {delSisRecord} from "@/api/process/sisRecord";

export default {
  name: "SifList",
  data() {
    return {
      // 批量导入全屏遮罩
      fullscreenLoading: false,
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        type: "processSifList",
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
        url: process.env.VUE_APP_BASE_API + "/process/sifList/importData"
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
      // SIF Overview List表格数据
      sifListList: [],
      recordForm: [{}],
      flag: true,
      finalFlag: false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        sifNo: null,
        plant: null,
        sifTag: null,
        deviation: null,
        riskAnalysis: null,
        causeConsequence: null,
        requiredSil: null,
        acheievedSil: null,
        ver: null,
        pfdavg: null,
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
      rules: {}
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
    resetForm1() {
      this.recordForm = [{}]
      this.flags();
    },
    // 表单添加一行
    add() {
      let arr = {};
      this.recordForm.push(arr)
      this.flags();
    },
    // 表单减少一行
    reduce() {
      this.recordForm.length = this.recordForm.length - 1
      this.flags()
    },
    //拷贝
    copyDomain(domain) {
      let arr = {}
      arr.workType = domain.workType;
      arr.workDescription = domain.workDescription;
      arr.riskLevel = domain.riskLevel;
      arr.workPeopleNumber = domain.workPeopleNumber;
      arr.estimateWorktime = domain.estimateWorktime;
      this.recordForm.push(
        arr
      );
      this.flags()
    },
    //删除自身
    removeDomain(item) {
      if (!item.id) {
        //如果子类大于1  可点击删除按钮
        if (this.recordForm.length > 1) {
          var index = this.recordForm.indexOf(item)
          if (index !== -1) {
            this.recordForm.splice(index, 1)
          }
        }
        this.flags()
      } else {
        var index = this.recordForm.indexOf(item)
        this.$modal.confirm('是否确认删除该行？').then(function () {
          return delSisRecord(item.id);
        }).then(() => {
          this.$modal.msgSuccess("删除成功");
          this.recordForm.splice(index, 1)
        }).catch(() => {
        });
        this.flags()
      }
    },
    // 判断数组长度
    flags() {
      //如果小于1则自动添加一行
      if (this.recordForm.length < 2) {
        this.flag = true
      } else if (this.recordForm.length < 1) {
        this.recordForm.push({});
      } else {
        //先赋值为true再赋为false, 不然会没反应
        this.flag = true
        this.flag = false
      }
    },
    /** 查询SIF Overview List列表 */
    getList() {
      this.loading = true;
      listSifList(this.queryParams).then(response => {
        this.sifListList = response.rows;
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
        sifNo: null,
        plant: null,
        sifTag: null,
        deviation: null,
        riskAnalysis: null,
        causeConsequence: null,
        requiredSil: null,
        acheievedSil: null,
        ver: null,
        pfdavg: null,
        remarks: null,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null
      };
      this.resetForm("form");
      this.recordForm = [{}]
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
      this.title = "添加SIF Overview List";
      this.finalFlag = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSifList(id).then(response => {
        this.form = response.data;
        this.recordForm = response.data.recordList;
        this.open = true;
        this.title = "修改SIF Overview List";
        this.finalFlag = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.recordList = this.recordForm;
          if (this.form.id != null) {
            updateSifList(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSifList(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除SIF Overview List编号为"' + ids + '"的数据项？').then(function () {
        return delSifList(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('process/sifList/exportTmpl', {
        ...this.queryParams
      }, `sifList_${new Date().getTime()}.xlsx`)
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

    //合并单元格
    mergeMethod({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 1 || columnIndex === 2 || columnIndex === 3 || columnIndex === 4
        || columnIndex === 5 || columnIndex === 6 || columnIndex === 7 || columnIndex === 8
        || columnIndex === 9 || columnIndex === 0 || columnIndex === 22 || columnIndex === 23) {
        const _row = this.setTable(this.sifListList).merge[rowIndex];
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
          if (item.id === tableData[index - 1].id) {
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

<style>
.el-table .cell {
  white-space: pre-line;
}
</style>
