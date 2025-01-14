<template>
  <el-dialog :close-on-click-modal="false" :title="改造记录" :visible.sync="visible" width="1200px" append-to-body>
    <el-table v-loading="loading" ref="reTable" :data="specHiGlList"  :height="clientHeight" border>
      <el-table-column :label="装置" align="center" fixed="left"  prop="plantCode" :show-overflow-tooltip="true"/>
      <el-table-column :label="单元" align="center"  fixed="left" prop="unit" :show-overflow-tooltip="true"/>
<!--      <el-table-column :label="状态" align="center"  fixed="left" prop="status" :formatter="statusFormat" />-->
<!--      <el-table-column :label="申请状态" align="center" fixed="left"  prop="approveStatus" :formatter="approveStatusFormat" />-->
      <el-table-column :label="装置维修组" align="center" fixed="left"  prop="plantMaint" :show-overflow-tooltip="true"/>
      <el-table-column :label="装置维修工程师" align="center" fixed="left"  prop="engineer" :show-overflow-tooltip="true"/>
      <el-table-column :label="位号" align="center"  fixed="left" prop="devno" :show-overflow-tooltip="true"/>
      <el-table-column :label="设备名称" align="center" prop="devname" :show-overflow-tooltip="true"/>
      <el-table-column :label="型号" align="center" prop="model" :show-overflow-tooltip="true"/>
      <el-table-column :label="使用证号码" align="center" prop="useno" :show-overflow-tooltip="true"/>
      <el-table-column :label="注册编号" align="center" prop="regno" :show-overflow-tooltip="true"/>
      <el-table-column :label="制造单位" align="center" prop="createUnit" :show-overflow-tooltip="true"/>
      <el-table-column :label="燃烧方式" align="center" prop="burnMode" :show-overflow-tooltip="true"/>
      <el-table-column :label="水处理方式" align="center" prop="waterMode" :show-overflow-tooltip="true"/>
      <el-table-column :label="燃烧种类" align="center" prop="burnKind" :show-overflow-tooltip="true"/>
      <el-table-column :label="额定出力" align="center" prop="ratedPower" :show-overflow-tooltip="true"/>
      <el-table-column :label="设计温度+'℃'" align="center" prop="desTemp" :show-overflow-tooltip="true"/>
      <el-table-column :label="操作温度+'℃'" align="center" prop="optTemp" :show-overflow-tooltip="true"/>
      <el-table-column :label="设计压力+'MPa(G)'" align="center" prop="desPressure" :show-overflow-tooltip="true"/>
      <el-table-column :label="操作压力+'MPa(G)'" align="center" prop="optPressure" :show-overflow-tooltip="true"/>

      <el-table-column :label="投用日期" align="center" prop="submitdate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="检验单位" align="center" prop="checkUnit" :show-overflow-tooltip="true"/>

      <el-table-column :label="本次内部检验日期" align="center" prop="warnDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="内部检验结论" align="center" prop="checkConclusion" :show-overflow-tooltip="true"/>
      <el-table-column :label="内部检验报告编号" align="center" prop="reportNo" :show-overflow-tooltip="true"/>
      <el-table-column :label="下次内部检验日期" align="center" prop="nextWarnDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="本次外部检验日期" align="center" prop="outWarnDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="外部检验结论" align="center" prop="outCheckConclusion" :show-overflow-tooltip="true"/>
      <el-table-column :label="外部检验报告编号" align="center" prop="outReportNo" :show-overflow-tooltip="true"/>
      <el-table-column :label="下次外部检验日期" align="center" prop="outNextWarnDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outNextWarnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="备注" align="center" prop="remarks" :show-overflow-tooltip="true"/>
      <el-table-column :label="创建时间" align="center" prop="createdate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

    </el-table>
  </el-dialog>
</template>

<script>
  import {getSpecGlByReform} from "@/api/sems/specGl";
  import { getToken } from "@/utils/auth";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
        name: "specGl-hisReform",
      data() {
        return {
          specHiGlList: [],
          devType: null,
          // 遮罩层
          loading: true,
          visible: false,
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
          // 特种设备检验记录表格数据
          checkList: [],
          // 弹出层标题
          title: "",
          // 部门树选项
          deptOptions: undefined,
          clientHeight:300,
          // 是否显示弹出层
          open: false,
          // 用户导入参数
          upload: {
            // 是否显示弹出层（用户导入）
            open: false,
            // 弹出层标题（用户导入）
            title: "",
            // 是否禁用上传
            isUploading: false,
            // 是否更新已经存在的用户数据
            updateSupport: 0,
            // 设置上传的请求头部
            headers: { Authorization: "Bearer " + getToken() },
            // 上传的地址
            url: process.env.VUE_APP_BASE_API + "/sems/check/importData"
          },
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 20,
            createrCode: null,
            createdate: null,
            updaterCode: null,
            updatedate: null,
            checkUnit: null,
            warnDate: null,
            nextWarnDate: null,
            reportNo: null,
            yearWarnDate: null,
            checkConclusion: null,
            yearNextWarnDate: null,
            yearReportNo: null,
            outWarnDate: null,
            outNextWarnDate: null,
            outCheckConclusion: null,
            outReportNo: null,
            devId: null,
            devType: null,
            safeClass: null
          },

          // 表单参数
          form: {},
          // 表单校验
          rules: {
          }
        };
      },
      watch: {
        // // 根据名称筛选部门树
        // deptName(val) {
        //   this.$refs.tree.filter(val);
        // }
      },
      created() {
        //设置表格高度对应屏幕高度
        this.$nextTick(() => {
          this.clientHeight = document.body.clientHeight -250
        })

      },
      methods: {
        init(row) {
          this.visible = true
          this.queryParams.devId = row.id
          // this.queryParams.devType = type
          // this.devType = type
          // console.log(this.devType)
          this.loading = true;
          this.$nextTick(() => {
            console.log(this.queryParams)
            getSpecGlByReform(row.id).then(response => {
              this.specHiGlList = response.data;
              this.$nextTick(() => {
                this.$refs.reTable.doLayout(); // 解决表格错位
              });
              this.loading = false;
            });
          })

        },
        // 状态字典翻译
        statusFormat(row, column) {
          return this.selectDictLabel(this.statusOptions, row.status);
        },
        // 申请状态字典翻译
        approveStatusFormat(row, column) {
          return this.selectDictLabel(this.approveStatusOptions, row.approveStatus);
        },
      }
    }
</script>

<style scoped>

</style>
