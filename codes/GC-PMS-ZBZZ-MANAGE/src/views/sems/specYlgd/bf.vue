<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="装置" prop="plantCode">
        <el-input
          v-model="queryParams.plantCode"
          placeholder="请输入装置"
          clearable
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
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery"> 搜索 </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"> 重置 </el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlgd:add']"
          icon="el-icon-plus"
          size="mini"
          type="primary"
          @click="handleAdd"
        > 新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlgd:edit']"
          :disabled="single"
          icon="el-icon-edit"
          size="mini"
          type="success"
          @click="handleUpdate"
        > 修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlgd:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          size="mini"
          type="danger"
          @click="handleDelete"
        > 删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlgd:edit']"
          icon="el-icon-upload2"
          size="mini"
          type="info"
          @click="handleImport"
        > 导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sems:specYlgd:export']"
          icon="el-icon-download"
          size="mini"
          type="warning"
          @click="handleExport"
        > 导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="deviceTable" v-loading="loading" :cell-style="tableCellStyle" :data="specYlgdList"
              :height="clientHeight"
              border @selection-change="handleSelectionChange" @sort-change="sortList">
      <el-table-column align="center" fixed="left" type="selection" width="55"/>
      <el-table-column label="装置" :show-overflow-tooltip="true" align="center" fixed="left" prop="plantCode"/>
      <el-table-column label="管道名称" :show-overflow-tooltip="true" align="center" fixed="left"
                       prop="devname"/>
      <el-table-column label="P&ID号" :show-overflow-tooltip="true" align="center" fixed="left" prop="pidNo"/>
      <el-table-column label="管道编号" :show-overflow-tooltip="true" align="center" fixed="left" prop="devno"/>
      <el-table-column label="ISO图纸" :show-overflow-tooltip="true" align="center" prop="isoPicUrl">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.isoPicUrl"
            icon="el-icon-view"
            size="mini"
            type="text"
            @click="handleSee(scope.row)"
          > 预览
          </el-button>
          <span v-else>NA</span>
        </template>
      </el-table-column>
      <el-table-column label="下次检验日期" align="center" prop="nextWarnDate" sortable="custom" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最近一次检验日期" align="center" prop="warnDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="起点" :show-overflow-tooltip="true" align="center" prop="starting"/>
      <el-table-column label="终点" :show-overflow-tooltip="true" align="center" prop="ending"/>
      <el-table-column label="安全阀" :show-overflow-tooltip="true" align="center" prop="reliefValve"/>
      <el-table-column label="管件数量" :show-overflow-tooltip="true" align="center" prop="fitNumber"/>
      <el-table-column label="对接焊缝数量" :show-overflow-tooltip="true" align="center" prop="weldNumber" width="120"/>
      <el-table-column label="管道级别" :show-overflow-tooltip="true" align="center" prop="grade"/>
      <el-table-column label="设计单位" :show-overflow-tooltip="true" align="center" prop="designer"/>
      <el-table-column label="安装单位" :show-overflow-tooltip="true" align="center" prop="installer"/>
      <el-table-column label="安装年月" align="center" prop="installDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="投用年月" align="center" prop="submitdate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="公称直径+'（DN）'" :show-overflow-tooltip="true" align="center" prop="dia" width="120"/>
      <el-table-column label="壁厚(mm)" :show-overflow-tooltip="true" align="center" prop="scheduleNo" width="120"/>
      <el-table-column label="管道长度+'（m）'" :show-overflow-tooltip="true" align="center" prop="length" width="120"/>
      <el-table-column label="材质" :show-overflow-tooltip="true" align="center" prop="material"/>
      <!--      <el-table-column label="敷设方式" align="center" prop="layingMethod" :show-overflow-tooltip="true"/>-->
      <el-table-column label="保温" :show-overflow-tooltip="true" align="center" prop="heatPreservation"/>
      <el-table-column label="厚度" :show-overflow-tooltip="true" align="center" prop="thickness"/>
      <el-table-column label="设计压力+'(MPa)'" :show-overflow-tooltip="true" align="center" prop="desPressure" width="120"/>
      <el-table-column label="工作压力+'(MPa)'" :show-overflow-tooltip="true" align="center" prop="optPressure" width="120"/>
      <el-table-column label="设计温度+'(℃)'" :show-overflow-tooltip="true" align="center" prop="desTemp" width="120"/>
      <el-table-column label="工作温度+'(℃)'" :show-overflow-tooltip="true" align="center" prop="optTemp" width="120"/>
      <el-table-column label="介质" :show-overflow-tooltip="true" align="center" prop="medium"/>
      <el-table-column label="检验结论" :show-overflow-tooltip="true" align="center" prop="checkConclusion"/>
      <el-table-column label="检验机构名称" :show-overflow-tooltip="true" align="center" prop="checkUnit" width="120"/>
      <el-table-column label="报告编号" :show-overflow-tooltip="true" align="center" prop="reportNo"/>
      <el-table-column :formatter="statusFormat" label="状态" align="center" prop="status"/>
      <el-table-column label="检验状态" align="center" prop="checkStatus"/>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right"
                       width="140">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sems:specYlgd:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page-sizes="[20,100,300,500]"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改特种设备压力管道台账对话框 -->
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="title" :visible.sync="open" append-to-body width="1200px">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="装置" prop="plantCode">
              <el-input v-model="form.plantCode" placeholder="请输入装置"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="P&ID号" prop="pidNo">
              <el-input v-model="form.pidNo" placeholder="请输入P&ID号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管道名称" prop="devname">
              <el-input v-model="form.devname" placeholder="请输入管道名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="管道编号" prop="devno">
              <el-input v-model="form.devno" placeholder="请输入管道编号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管道级别" prop="grade">
              <el-input v-model="form.grade" placeholder="请输入管道级别"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设计单位" prop="designer">
              <el-input v-model="form.designer" placeholder="请输入设计单位"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安装单位" prop="installer">
              <el-input v-model="form.installer" placeholder="请输入安装单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安装年月" prop="installDate">
              <el-date-picker v-model="form.installDate" placeholder="请选择+ 安装年月" clearable
                              size="small"
                              style="width: 100%"
                              type="date"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="投用年月" prop="submitdate">
              <el-date-picker v-model="form.submitdate" placeholder="请选择+ 投用年月" clearable
                              size="small"
                              style="width: 100%"
                              type="date"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="材质" prop="material">
              <el-input v-model="form.material" placeholder="请输入材质"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="保温" prop="heatPreservation">
              <el-input v-model="form.heatPreservation" placeholder="请输入保温"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厚度" prop="thickness">
              <el-input v-model="form.thickness" placeholder="请输入厚度"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公称直径(DN)" prop="dia">
              <el-input v-model="form.dia" placeholder="请输入公称直径(DN)"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="壁厚(mm)" prop="scheduleNo">
              <el-input v-model="form.scheduleNo" placeholder="请输入壁厚(mm)"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管道长度+'(m)'" prop="length">
              <el-input v-model="form.length" placeholder="请输入管道长度"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="对接焊缝数量" prop="weldNumber">
              <el-input v-model="form.weldNumber" placeholder="请输入对接焊缝数量"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="起点" prop="starting">
              <el-input v-model="form.starting" placeholder="请输入起点"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="终点" prop="ending">
              <el-input v-model="form.ending" placeholder="请输入终点"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管件数量" prop="fitNumber">
              <el-input v-model="form.fitNumber" placeholder="请输入管件数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="介质" prop="medium">
              <el-input v-model="form.medium" placeholder="请输入介质"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设计压力+'(MPa)'" prop="desPressure">
              <el-input v-model="form.desPressure" placeholder="请输入设计压力"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作压力+'(MPa)'" prop="optPressure">
              <el-input v-model="form.optPressure" placeholder="请输入工作压力"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设计温度+'(℃)'" prop="desTemp">
              <el-input v-model="form.desTemp" placeholder="请输入设计温度"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作温度+'(℃)'" prop="optTemp">
              <el-input v-model="form.optTemp" placeholder="请输入工作温度"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最近一次检验日期" prop="warnDate">
              <el-date-picker v-model="form.warnDate" placeholder="请选择+ 最近一次检验日期" clearable
                              size="small"
                              style="width: 100%"
                              type="month"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次检验日期" prop="nextWarnDate">
              <el-date-picker v-model="form.nextWarnDate" placeholder="请选择+ 下次检验日期" clearable
                              size="small"
                              style="width: 100%"
                              type="month"
                              value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
          </el-col>
          <el-col :span="12">
            <el-form-item label="检验机构名称" prop="checkUnit">
              <el-input v-model="form.checkUnit" placeholder="请输入检验机构名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查结论" prop="checkConclusion">
              <el-input v-model="form.checkConclusion" placeholder="请输入检查结论"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="报告编号" prop="reportNo">
              <el-input v-model="form.reportNo" placeholder="请输入报告编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安全阀" prop="reliefValve">
              <el-input v-model="form.reliefValve" placeholder="请输入安全阀"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="ISO图纸" prop="picUrl">
            <el-upload
              ref="doc"
              :action="doc.url"
              :auto-upload="true"
              :disabled="doc.isUploading"
              :file-list="fileList"
              :headers="doc.headers"
              :multiple="true"
              :on-progress="handleFileDocProgress"
              :on-success="handleFileDocSuccess"
              accept=".jpg,.png"
              list-type="picture"
            >
              <el-button type="primary"><i class="el-icon-upload"></i> 点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status" size="large">
                <el-radio label="1" size="large">在用</el-radio>
                <el-radio label="2" size="large">停用</el-radio>
                <el-radio label="3" size="large">报废</el-radio>
                <el-radio label="-1" size="large">封存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入内容" type="textarea"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm"> 确 定 </el-button>
        <el-button @click="cancel"> 取 消 </el-button>
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
          <em> 点击上传 </em>
        </div>
        <div slot="tip" class="el-upload__tip">
          <!--                  <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据-->
          <el-link style="font-size:12px" type="info" @click="importTemplate"> 下载模板 </el-link>
        </div>
        <form ref="downloadFileForm" :action="upload.downloadAction" target="FORMSUBMIT">
          <input :value="upload.type" hidden name="type"/>
        </form>
        <div slot="tip" class="el-upload__tip" style="color:red"> 提示：仅允许导入“xls”或“xlsx”格式文件！ </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <a v-if="waitSubmit" style="margin-right: 300px"> 正在导入... </a>
        <el-button v-loading.fullscreen.lock="fullscreenLoading" type="primary"
                   @click="submitFileForm"> 确定
        </el-button>
        <el-button @click="upload.open = false"> 取消 </el-button>
      </div>
    </el-dialog>
    <form ref="downloadDevForm" :action="downloadDevAction" target="FORMSUBMIT">
      <input v-model="devType" hidden name="devType"/>
      <input v-model="ids" hidden name="ids"/>
      <input v-model="downloadType" hidden name="downloadType"/>
    </form>
    <el-dialog :close-on-click-modal="false" v-dialogDrag :title="pic.title" :visible.sync="pic.open" append-to-body width="710px">
      <div>
        <el-image
          v-for="url in pic.picUrl"
          :key="url"
          :preview-src-list="pic.picUrl"
          :src="url"
          fit="cover"
          style="width: 200px; height: 200px;margin:10px;border-radius: 5%"/>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addSpecYlgd,
  delSpecYlgd,
  duplicate,
  exportSpecList,
  exportSpecYlgd,
  getSpecYlgd,
  listSpecYlgd,
  updateSpecYlgd,
} from "@/api/sems/specYlgd";
import {treeselect} from "@/api/system/dept";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "SpecYlgd",
  components: {Treeselect},
  data() {
    return {
      picUrl: [],
      pic: {
        title: 'iso图纸预览',
        picUrl: [],
        numPages: null,
        open: false,
        pageNum: 1,
        pageTotalNum: 1,
        loadedRatio: 0,
      },
      fileList: [],
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
        url: process.env.VUE_APP_BASE_API + "/sems/specYlgd/uploadFile",
        commonfileList: null,
        pType: 'traning',
        pId: null
      },
      filterList: [], //删选的list
      fullscreenLoading: false,
      waitSubmit: false,
      hisReformVisible: false,
      dataForm: 0,
      devType: 2,
      // 遮罩层
      loading: true,
      addAprroveVisible: false,
      hisAprroveVisible: false,
      hisCheckVisible: false,
      inspectionInformation: false,
      modifyTitle: '设备修改申请',
      modifyVisible: false,
      downloadType: '',
      downloadDevAction: process.env.VUE_APP_BASE_API + '/common/download/exportDevList',
      // 选中数组
      ids: [],
      dataListSelections: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 特种设备压力管道台账表格数据
      specYlgdList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      clientHeight: 300,
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      plantOptions: [],
      plantMaintOptions: [],
      // 申请状态字典
      approveStatusOptions: [],
      wxjlList: [],
      // 用户导入参数
      upload: {
        downloadAction: process.env.VUE_APP_BASE_API + '/common/template',
        // 是否显示弹出层（用户导入）
        type: "specYlgd",
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
        url: process.env.VUE_APP_BASE_API + "/sems/specYlgd/importData"
      },
      // 查询参数
      queryParams: {
        fuzzy: null,
        dataForm: 0,
        pageNum: 1,
        orderByColumn: null,
        isAsc: null,
        pageSize: 20,
        plantCode: null,
        unit: null,
        devname: null,
        devno: null,
        submitdate: null,
        material: null,
        dia: null,
        scheduleNo: null,
        length: null,
        starting: null,
        ending: null,
        desPressure: null,
        desTemp: null,
        optPressure: null,
        optTemp: null,
        medium: null,
        checkConclusion: null,
        checkUnit: null,
        safeClass: null,
        reportNo: null,
        approveTime: null,
        changeTime: null,
        plantMaint: null,
        engineer: null,
        weldNumber: null,
        layingMethod: null,
        adiabatic: null,
        antiCorrosion: null,
        adiabaticThickness: null,
        isDanger: null,
        installDate: null,
        yearWarnDate: null,
        yearNextWarnDate: null,
        yearReportNo: null,
        status: 3,
      },
      // 表单参数
      form: {},
      modifyForm: {},
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
    this.getDicts("spec_dev_status").then(response => {
      this.statusOptions = response.data;
    });
    //预警等级字典
    this.getDicts("ALARM_LEVEL").then(response => {

      for (let i = 0; i < response.data.length; i++) {
        let items = {
          text: '',
          value: ''
        };
        items.value = response.data[i].dictValue;
        items.text = response.data[i].dictLabel;
        this.filterList.push(items);
      }
    });
    this.getDicts("spec_approve_status").then(response => {
      for (let i = 0; i < response.data.length; i++) {
        if (!["5", "6", "7", "8", "16", "17"].includes(response.data[i].dictValue)) {
          this.approveStatusOptions.push(response.data[i])
        }
      }
    });
    this.getDicts("SPEC_PLANT_MAINT").then(response => {
      this.plantMaintOptions = response.data;
    });
    let plantParams = {
      pType: 1
    }
  },
  methods: {
    handleSee(row) {
      this.pic.picUrl = []
      this.pic.open = true
      // this.pic.title = row.picName
      let picUrls = row.isoPicUrl.split(",");
      for (let i = 0; i < picUrls.length; i++) {
        let url = process.env.VUE_APP_BASE_API + picUrls[i];
        this.pic.picUrl.push(url)
      }
    },
    //附件上传中处理
    handleFileDocProgress(event, file, fileList) {
      this.doc.file = file;
    },
    //附件上传成功处理
    handleFileDocSuccess(response, file, fileList) {
      this.picUrl.push(response.msg)
      // this.picName.push(file.name)
      console.log(fileList)
      console.log(file)
      // this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
    },
    /** 查询特种设备压力管道台账列表 */
    getList() {
      this.loading = true;
      listSpecYlgd(this.queryParams).then(response => {
        this.specYlgdList = response.rows;
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
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 申请状态字典翻译
    approveStatusFormat(row, column) {
      return this.selectDictLabel(this.approveStatusOptions, row.approveStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        //dataForm: 0,
        id: null,
        plantCode: null,
        unit: null,
        devname: null,
        devno: null,
        submitdate: null,
        status: 3,
        delFlag: null,
        createrCode: null,
        createdate: null,
        updaterCode: null,
        updatedate: null,
        deptId: null,
        remarks: null,
        approveStatus: 0,
        regno: null,
        useno: null,
        warnDate: null,
        warnCycle: null,
        nextWarnDate: null,
        warnFlag: null,
        grade: null,
        designer: null,
        installer: null,
        material: null,
        dia: null,
        scheduleNo: null,
        length: null,
        starting: null,
        ending: null,
        desPressure: null,
        desTemp: null,
        optPressure: null,
        optTemp: null,
        medium: null,
        checkConclusion: null,
        checkUnit: null,
        safeClass: null,
        reportNo: null,
        approveTime: null,
        changeTime: null,
        plantMaint: null,
        engineer: null,
        weldNumber: null,
        layingMethod: null,
        adiabatic: null,
        antiCorrosion: null,
        adiabaticThickness: null,
        isDanger: null,
        yearWarnDate: null,
        yearNextWarnDate: null,
        yearReportNo: null
      };
      this.resetForm("form");
      this.dataForm = 0;
      this.fileList = [];
      this.picUrl = [];
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
      this.dataListSelections = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.fileList = [];
      this.picUrl = [];
      this.reset();
      this.open = true;
      this.title = '添加特种设备压力管道台账';
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpecYlgd(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = '修改特种设备压力管道台账';
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.picUrl) {
            this.form.isoPicUrl = this.picUrl.join(',')
          } else {
            this.form.isoPicUrl = null;
          }
          if (this.form.id != null) {
            updateSpecYlgd(this.form).then(response => {
              this.$modal.msgSuccess('修改成功');
              this.open = false;
              this.getList();
            });
          } else {
            addSpecYlgd(this.form).then(response => {
              this.$modal.msgSuccess('新增成功');
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
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function() {
        return delSpecYlgd(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess('删除成功');
      })
    },
    /** 删除按钮操作 */
    handleDup() {
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning"
      }).then(function () {
        return duplicate();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess('删除成功');
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sems/specYlgd/export', {
        ...this.queryParams
      }, `devYlgd_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '用户导入';
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.$refs['downloadFileForm'].submit()
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
      this.waitSubmit = true;
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
        this.$alert('导入成功条数：' + response.msg, '导入结果', {dangerouslyUseHTMLString: true});
      }
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
      this.fullscreenLoading = true;
    },
    addAprrove(row, type) {
      var rows = row ? [row] : this.dataListSelections.map(item => {
        return item
      })
      for (let i = 0; i < rows.length; i++) {
        if (rows[i].approveStatus != 0) {
          this.$alert('当前设备正在申请中，无法重复申请', '提示', {
            type: 'warning'
          })
          return
        }
      }
      this.addAprroveVisible = true
      console.log(rows)
      console.log(type)
      this.$nextTick(() => {
        this.$refs.addApprove.init(rows, type)
      })
    },
    hisApprove(row, type) {
      this.hisAprroveVisible = true
      this.$nextTick(() => {
        this.$refs.hisApprove.init(row, type)
      })
    },
    checkList(row, type) {
      this.hisCheckVisible = true
      this.$nextTick(() => {
        this.$refs.hisCheck.init(row, type)
      })
    },
    hisReform(row) {
      this.hisReformVisible = true
      this.$nextTick(() => {
        this.$refs.hisReformList.init(row);
      })
    },
    //导出excel
    downloadDev(type) {
      var rows = this.dataListSelections.map(item => {
        return item.id
      })
      const queryParams = {ids: null};
      queryParams.ids = rows.join()
      exportSpecList(queryParams).then(response => {
        this.download(response.msg);
      })
    },
    //2021年9月15日 13:40:55 更改路径
    uploadUrl(uploadType) {
      return process.env.VUE_APP_BASE_API + "/sems/specYlgd/updateData"
    },
    //位号颜色变换
    tableCellStyle({row, column, rowIndex, columnIndex}) {
      if (columnIndex == 8 && row.isRepeat == 1) {
        return "color:rgba(255, 26, 26, 0.98);"
      }
      if (row.warnFlag == 1) {
        return "background-color:rgba(255, 255,153, 0.5);"
      }
      if (row.warnFlag == 2) {
        return "background-color:rgba(255, 180, 68, 0.5);"
      }
      if (row.warnFlag == 3) {
        return "background-color:rgba(255, 68,68, 0.5);"
      }
    },
    //element表格排序
    sortList(val) {
      var sort = "asc";
      let sortTip = val.order
      this.queryParams.isAsc = sort;
      this.queryParams.orderByColumn = val.prop;
      if (sortTip === 'descending') {
        sort = 'desc' // 降序
      } else if (sortTip === 'ascending') {
        sort = 'asc' // 升序
      } else if (sortTip === null) {
        this.queryParams.isAsc = null;
        this.queryParams.orderByColumn = null;
      }
      this.loading = true;
      listSpecYlgd(this.queryParams).then(response => {
        this.specYlgdList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
  }
};
</script>
